import openmeteo_requests

import pandas as pd
import requests_cache
from retry_requests import retry

# Setup the Open-Meteo API client with cache and retry on error
cache_session = requests_cache.CachedSession('.cache', expire_after = 3600)
retry_session = retry(cache_session, retries = 5, backoff_factor = 0.2)
openmeteo = openmeteo_requests.Client(session = retry_session)  # type: ignore

# Get user input for location
latitude = float(input("Enter latitude: "))
longitude = float(input("Enter longitude: "))

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
print(f"Timezone difference to GMT+0: {response.UtcOffsetSeconds()}s")

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
		print("\nHourly data\n", hourly_dataframe)
