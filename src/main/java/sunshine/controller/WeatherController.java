package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.domain.City;
import sunshine.dto.WeatherResponse;
import sunshine.exception.CityNotFoundException;
import sunshine.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        City foundCity = City.fromName(city)
                .orElseThrow(() -> new CityNotFoundException(city));

        WeatherResponse response = weatherService.getWeather(foundCity);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<String> handleCityNotFound(CityNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
