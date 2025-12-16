package sunshine.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;
import sunshine.data.WeatherData;
import sunshine.service.WeatherProviderService;

@Configuration
public class WeatherToolConfiguration {
    private final WeatherProviderService weatherProviderService;

    public WeatherToolConfiguration(WeatherProviderService weatherProviderService) {
        this.weatherProviderService = weatherProviderService;
    }

    @Tool(description = "open-meteo 날씨를 위도, 경도를 통해 조회하는 기능")
    public WeatherData searchWeather(double latitude, double longitude) {
        System.out.println("::::: searchWeather: latitude: " + latitude + ", longitude: " + longitude);
        return weatherProviderService.getCurrent(latitude, longitude);
    }
}
