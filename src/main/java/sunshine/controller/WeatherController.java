package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.service.CityReadService;
import sunshine.service.GeminiWeatherProviderService;
import sunshine.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CityReadService cityReadService;
    private final GeminiWeatherProviderService geminiWeatherProviderService;

    public WeatherController(WeatherService weatherService,
                             CityReadService cityReadService,
                             GeminiWeatherProviderService geminiWeatherProviderService
    ) {
        this.weatherService = weatherService;
        this.cityReadService = cityReadService;
        this.geminiWeatherProviderService = geminiWeatherProviderService;
    }

    @GetMapping
    public ResponseEntity<Object> getWeatherByPlace(
            @RequestParam(defaultValue = "서울") String place
    ) {
        return ResponseEntity.ok(weatherService.getWeatherByPlace(place));
    }
}
