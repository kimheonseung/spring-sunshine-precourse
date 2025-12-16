package sunshine.service;

import org.springframework.stereotype.Service;
import sunshine.data.RecommendClothsData;
import sunshine.data.WeatherData;
import sunshine.data.WeatherForecastData;
import sunshine.domain.WeatherCodeTranslator;
import sunshine.domain.WeatherSummaryGenerator;
import sunshine.entity.City;

@Service
public class WeatherService {

    private final WeatherProviderService weatherProviderService;
    private final WeatherCodeTranslator weatherCodeTranslator;
    private final WeatherSummaryGenerator summaryGenerator;
    private final CityReadService cityReadService;
    private final GeminiWeatherProviderService geminiWeatherProviderService;

    public WeatherService(
            WeatherProviderService weatherProviderService,
            WeatherCodeTranslator weatherCodeTranslator,
            WeatherSummaryGenerator summaryGenerator,
            CityReadService cityReadService,
            GeminiWeatherProviderService geminiWeatherProviderService
    ) {
        this.weatherProviderService = weatherProviderService;
        this.weatherCodeTranslator = weatherCodeTranslator;
        this.summaryGenerator = summaryGenerator;
        this.cityReadService = cityReadService;
        this.geminiWeatherProviderService = geminiWeatherProviderService;
    }

    public Object getWeatherByPlace(String place) {
        try {
            return geminiWeatherProviderService.getCurrentWeatherAndRecommendCloths(place);
        } catch (Exception e) {
            City foundCityOp = cityReadService.findByNameOptional("seoul");
            return getWeather(foundCityOp);
        }
    }

    public WeatherForecastData getWeather(City city) {
        WeatherData weatherData = weatherProviderService.getCurrent(city.getLatitude(), city.getLongitude());
        return buildWeatherResponse(city, weatherData);
    }

    private WeatherForecastData buildWeatherResponse(City city, WeatherData weatherData) {
        String weatherCondition = weatherCodeTranslator.translate(weatherData.weatherCode());
        String summary = createSummary(city, weatherData.temperature(), weatherData.apparentTemperature(), weatherCondition);
        return createWeatherResponse(city, weatherData.temperature(), weatherData.apparentTemperature(), weatherData.humidity(), weatherCondition, summary);
    }

    private String createSummary(City city, double temperature, double apparentTemperature, String condition) {
        return summaryGenerator.generate(
                city.getKoreanName(), temperature, apparentTemperature, condition
        );
    }

    private WeatherForecastData createWeatherResponse(
            City city, double temperature, double apparentTemperature, int humidity, String condition, String summary
    ) {
        return new WeatherForecastData(
                city.getKoreanName(), temperature,
                apparentTemperature, humidity, condition, RecommendClothsData.empty()
        );
    }
}
