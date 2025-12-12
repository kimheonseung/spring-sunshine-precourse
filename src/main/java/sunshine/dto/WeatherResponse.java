package sunshine.dto;

public record WeatherResponse(
        String city,
        String cityKorean,
        double temperature,
        double apparentTemperature,
        int humidity,
        String weatherCondition,
        String summary
) {
}
