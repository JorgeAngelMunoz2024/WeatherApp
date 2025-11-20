package com.nflweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NflWeatherBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(NflWeatherBackendApplication.class, args);
    }
}
