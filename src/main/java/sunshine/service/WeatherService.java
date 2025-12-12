package sunshine.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import sunshine.domain.City;
import sunshine.domain.WeatherCodeTranslator;
import sunshine.domain.WeatherSummaryGenerator;
import sunshine.dto.OpenMeteoResponse;
import sunshine.dto.WeatherResponse;

@Service
public class WeatherService {

    private static final String OPEN_METEO_API_URL = "https://api.open-meteo.com/v1/forecast";

    private final RestClient restClient;
    private final WeatherCodeTranslator weatherCodeTranslator;
    private final WeatherSummaryGenerator summaryGenerator;

    public WeatherService(
            RestClient.Builder restClientBuilder,
            WeatherCodeTranslator weatherCodeTranslator,
            WeatherSummaryGenerator summaryGenerator
    ) {
        this.restClient = restClientBuilder.build();
        this.weatherCodeTranslator = weatherCodeTranslator;
        this.summaryGenerator = summaryGenerator;
    }

    public WeatherResponse getWeather(City city) {
        OpenMeteoResponse apiResponse = fetchWeatherData(city);
        return buildWeatherResponse(city, apiResponse);
    }

    private OpenMeteoResponse fetchWeatherData(City city) {
        return restClient.get()
                .uri(OPEN_METEO_API_URL + buildQueryParams(city))
                .retrieve()
                .body(OpenMeteoResponse.class);
    }

    private String buildQueryParams(City city) {
        return String.format(
                "?latitude=%f&longitude=%f&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code",
                city.getLatitude(),
                city.getLongitude()
        );
    }

    private WeatherResponse buildWeatherResponse(City city, OpenMeteoResponse apiResponse) {
        OpenMeteoResponse.CurrentWeather current = apiResponse.current();
        String weatherCondition = weatherCodeTranslator.translate(current.weatherCode());
        String summary = summaryGenerator.generate(
                city.getKoreanName(),
                current.temperature(),
                current.apparentTemperature(),
                weatherCondition
        );

        return new WeatherResponse(
                city.getName(),
                city.getKoreanName(),
                current.temperature(),
                current.apparentTemperature(),
                current.humidity(),
                weatherCondition,
                summary
        );
    }
}
