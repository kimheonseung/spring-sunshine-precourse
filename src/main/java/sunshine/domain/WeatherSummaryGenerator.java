package sunshine.domain;

import org.springframework.stereotype.Component;

@Component
public class WeatherSummaryGenerator {

    public String generate(String cityKorean, double temperature, double apparentTemperature, String weatherCondition) {
        return String.format(
                "현재 %s의 기온은 %.1f°C이며, 체감 온도는 %.1f°C입니다. 날씨는 %s입니다.",
                cityKorean,
                temperature,
                apparentTemperature,
                weatherCondition
        );
    }
}
