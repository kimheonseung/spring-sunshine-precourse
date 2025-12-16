package sunshine.data;

public record WeatherForecastData(
        String place,
        double temperature,
        double apparentTemperature,
        int humidity,
        String weatherCondition,
        RecommendClothsData recommendClothsData
) {
}
