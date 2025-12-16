package sunshine.data;

public record WeatherData(
        double temperature,
        double apparentTemperature,
        int humidity,
        int weatherCode
) { }
