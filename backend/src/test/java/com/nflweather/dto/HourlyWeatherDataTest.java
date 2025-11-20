package com.nflweather.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HourlyWeatherDataTest {

    @Test
    void testGetWeatherDescription_ClearSky() {
        HourlyWeatherData data = HourlyWeatherData.builder()
                .weatherCode(0)
                .build();
        
        assertEquals("Clear sky", data.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_PartlyCloudy() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(1)
                .build();
        assertEquals("Partly cloudy", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(2)
                .build();
        assertEquals("Partly cloudy", data2.getWeatherDescription());

        HourlyWeatherData data3 = HourlyWeatherData.builder()
                .weatherCode(3)
                .build();
        assertEquals("Partly cloudy", data3.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_Foggy() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(45)
                .build();
        assertEquals("Foggy", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(48)
                .build();
        assertEquals("Foggy", data2.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_Rain() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(61)
                .build();
        assertEquals("Rain", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(63)
                .build();
        assertEquals("Rain", data2.getWeatherDescription());

        HourlyWeatherData data3 = HourlyWeatherData.builder()
                .weatherCode(65)
                .build();
        assertEquals("Rain", data3.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_Snow() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(71)
                .build();
        assertEquals("Snow", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(73)
                .build();
        assertEquals("Snow", data2.getWeatherDescription());

        HourlyWeatherData data3 = HourlyWeatherData.builder()
                .weatherCode(75)
                .build();
        assertEquals("Snow", data3.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_Thunderstorm() {
        HourlyWeatherData data = HourlyWeatherData.builder()
                .weatherCode(95)
                .build();
        assertEquals("Thunderstorm", data.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_ThunderstormWithHail() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(96)
                .build();
        assertEquals("Thunderstorm with hail", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(99)
                .build();
        assertEquals("Thunderstorm with hail", data2.getWeatherDescription());
    }

    @Test
    void testGetWeatherDescription_Unknown() {
        HourlyWeatherData data1 = HourlyWeatherData.builder()
                .weatherCode(999)
                .build();
        assertEquals("Unknown", data1.getWeatherDescription());

        HourlyWeatherData data2 = HourlyWeatherData.builder()
                .weatherCode(null)
                .build();
        assertEquals("Unknown", data2.getWeatherDescription());
    }

    @Test
    void testBuilder() {
        HourlyWeatherData data = HourlyWeatherData.builder()
                .time("2025-11-24T16:00:00-06:00")
                .temperature(68.5)
                .precipitationProbability(10)
                .precipitation(0.0)
                .weatherCode(1)
                .windSpeed(8.5)
                .build();

        assertNotNull(data);
        assertEquals("2025-11-24T16:00:00-06:00", data.getTime());
        assertEquals(68.5, data.getTemperature());
        assertEquals(10, data.getPrecipitationProbability());
        assertEquals(0.0, data.getPrecipitation());
        assertEquals(1, data.getWeatherCode());
        assertEquals(8.5, data.getWindSpeed());
        assertEquals("Partly cloudy", data.getWeatherDescription());
    }

    @Test
    void testSetWeatherDescription() {
        HourlyWeatherData data = new HourlyWeatherData();
        data.setWeatherCode(0);
        data.setWeatherDescription(data.getWeatherDescription());
        
        assertEquals("Clear sky", data.getWeatherDescription());
    }
}
