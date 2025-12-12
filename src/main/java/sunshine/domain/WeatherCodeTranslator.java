package sunshine.domain;

import org.springframework.stereotype.Component;

@Component
public class WeatherCodeTranslator {

    public String translate(int weatherCode) {
        if (weatherCode == 0) {
            return "맑음";
        }
        if (weatherCode <= 3) {
            return translatePartlyCloudy(weatherCode);
        }
        if (weatherCode <= 48) {
            return "안개";
        }
        if (weatherCode <= 57) {
            return "이슬비";
        }
        if (weatherCode <= 67) {
            return "비";
        }
        if (weatherCode <= 77) {
            return "눈";
        }
        if (weatherCode <= 82) {
            return "소나기";
        }
        if (weatherCode <= 86) {
            return "눈 소나기";
        }
        return "뇌우";
    }

    private String translatePartlyCloudy(int weatherCode) {
        if (weatherCode == 1) {
            return "대체로 맑음";
        }
        if (weatherCode == 2) {
            return "구름 조금";
        }
        return "흐림";
    }
}
