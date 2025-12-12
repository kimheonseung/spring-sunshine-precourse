package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.dto.WeatherResponse;
import sunshine.entity.City;
import sunshine.service.CityReadService;
import sunshine.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CityReadService cityReadService;

    public WeatherController(WeatherService weatherService, CityReadService cityReadService) {
        this.weatherService = weatherService;
        this.cityReadService = cityReadService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        City foundCity = cityReadService.findByName(city);
        WeatherResponse response = weatherService.getWeather(foundCity);
        return ResponseEntity.ok(response);
    }
}
