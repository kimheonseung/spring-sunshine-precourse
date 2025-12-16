package sunshine.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import sunshine.data.WeatherData;
import sunshine.dto.OpenMeteoResponse;

@Component
public class OpenMeteoWeatherProviderService implements WeatherProviderService {

    private static final String BASE_URL = "https://api.open-meteo.com/v1/forecast";
    private final RestClient restClient;

    public OpenMeteoWeatherProviderService(RestClient.Builder builder) {
        this.restClient = builder.build();
    }

    @Override
    public WeatherData getCurrent(double latitude, double longitude) {
        String uri = String.format(
                BASE_URL + "?latitude=%f&longitude=%f&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code",
                latitude, longitude
        );
        OpenMeteoResponse res = restClient.get().uri(uri).retrieve().body(OpenMeteoResponse.class);
        OpenMeteoResponse.CurrentWeather c = res.current();
        return new WeatherData(c.temperature(), c.apparentTemperature(), c.humidity(), c.weatherCode());
    }
}
