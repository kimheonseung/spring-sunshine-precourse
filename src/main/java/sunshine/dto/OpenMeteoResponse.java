package sunshine.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OpenMeteoResponse(
        @JsonProperty("current") CurrentWeather current
) {
    public record CurrentWeather(
            @JsonProperty("temperature_2m") double temperature,
            @JsonProperty("apparent_temperature") double apparentTemperature,
            @JsonProperty("relative_humidity_2m") int humidity,
            @JsonProperty("weather_code") int weatherCode
    ) {
    }
}
