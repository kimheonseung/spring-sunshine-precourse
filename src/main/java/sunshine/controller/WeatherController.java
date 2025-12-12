package sunshine.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.dto.WeatherResponse;
import sunshine.entity.City;
import sunshine.exception.CityNotFoundException;
import sunshine.repository.CityRepository;
import sunshine.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;
    private final CityRepository cityRepository;

    public WeatherController(WeatherService weatherService, CityRepository cityRepository) {
        this.weatherService = weatherService;
        this.cityRepository = cityRepository;
    }

    @GetMapping
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam String city) {
        City foundCity = cityRepository.findByNameIgnoreCase(city)
                .orElseThrow(() -> new CityNotFoundException(city));

        WeatherResponse response = weatherService.getWeather(foundCity);
        return ResponseEntity.ok(response);
    }
}
