import openmeteo_requests
import pandas as pd
import requests_cache
from retry_requests import retry
from geopy.geocoders import Nominatim
from datetime import datetime, timezone, timedelta
import pytz

# Setup the Open-Meteo API client with cache and retry on error
cache_session = requests_cache.CachedSession('.cache', expire_after = 3600)
retry_session = retry(cache_session, retries = 5, backoff_factor = 0.2)
openmeteo = openmeteo_requests.Client(session = retry_session)  # type: ignore

# Get user input for city and state
city = input("Enter city: ")
state = input("Enter state: ")

# Use geopy to get coordinates
geolocator = Nominatim(user_agent="weather_app")
location = geolocator.geocode(f"{city}, {state}, USA")

if location is None:
	print(f"Could not find coordinates for {city}, {state}")
	exit()

latitude = location.latitude  # type: ignore
longitude = location.longitude  # type: ignore

# Get timezone from location
from geopy.geocoders import Nominatim
from timezonefinder import TimezoneFinder
tf = TimezoneFinder()
timezone_str = tf.timezone_at(lat=latitude, lng=longitude)
if timezone_str is None:
	timezone_str = "UTC"
local_tz = pytz.timezone(timezone_str)

print(f"\nFound: {location.address}")  # type: ignore
print(f"Coordinates: {latitude}°N, {longitude}°E")
print(f"Timezone: {timezone_str}\n")

# Make sure all required weather variables are listed here
# The order of variables in hourly or daily is important to assign them correctly below
url = "https://api.open-meteo.com/v1/forecast"
params = {
	"latitude": latitude,
	"longitude": longitude,
	"hourly": "temperature_2m",
	"temperature_unit": "fahrenheit",
}
responses = openmeteo.weather_api(url, params=params)

# Process first location. Add a for-loop for multiple locations or weather models
response = responses[0]
print(f"Coordinates: {response.Latitude()}°N {response.Longitude()}°E")
print(f"Elevation: {response.Elevation()} m asl")

# Get current time in the city's timezone
current_time_utc = datetime.now(pytz.utc)
local_time = current_time_utc.astimezone(local_tz)
print(f"Current local time: {local_time.strftime('%I:%M %p, %B %d, %Y')}")

# Process hourly data. The order of variables needs to be the same as requested.
hourly = response.Hourly()
if hourly is None:
	print("No hourly data available")
else:
	hourly_variable = hourly.Variables(0)
	if hourly_variable is None:
		print("No temperature data available")
	else:
		hourly_temperature_2m = hourly_variable.ValuesAsNumpy()

		hourly_data = {
			"date": pd.date_range(
				start = pd.to_datetime(hourly.Time(), unit = "s", utc = True),
				end =  pd.to_datetime(hourly.TimeEnd(), unit = "s", utc = True),
				freq = pd.Timedelta(seconds = hourly.Interval()),
				inclusive = "left"
			),
			"temperature_2m": hourly_temperature_2m
		}

		hourly_dataframe = pd.DataFrame(data = hourly_data)
		
		# Find the index closest to current local time
		current_time_utc = pd.Timestamp(datetime.now(timezone.utc))
		time_diffs = [(date - current_time_utc).total_seconds() for date in hourly_dataframe['date']]  # type: ignore
		current_index = min(range(len(time_diffs)), key=lambda i: abs(time_diffs[i]))
		
		# Display current temp and next two hours
		print("\nTemperature Forecast:")
		for i in range(3):
			idx = current_index + i
			if idx < len(hourly_dataframe):
				time_label = "Current" if i == 0 else f"+{i} hour{'s' if i > 1 else ''}"
				local_time_hour = hourly_dataframe.iloc[idx]['date'].astimezone(local_tz)  # type: ignore
				print(f"{time_label}: {hourly_dataframe.iloc[idx]['temperature_2m']:.1f}°F at {local_time_hour.strftime('%I:%M %p')}")
