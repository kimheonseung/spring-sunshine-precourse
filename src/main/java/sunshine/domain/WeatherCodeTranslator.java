package sunshine.domain;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sunshine.repository.WeatherCodeRepository;

@Component
@Transactional(readOnly = true)
public class WeatherCodeTranslator {

    private static final String DEFAULT_DESCRIPTION = "알 수 없음";

    private final WeatherCodeRepository weatherCodeRepository;

    public WeatherCodeTranslator(WeatherCodeRepository weatherCodeRepository) {
        this.weatherCodeRepository = weatherCodeRepository;
    }

    public String translate(int weatherCode) {
        return weatherCodeRepository.findByCode(weatherCode)
                .or(() -> weatherCodeRepository.findClosestByCode(weatherCode))
                .map(wc -> wc.getDescription())
                .orElse(DEFAULT_DESCRIPTION);
    }
}
