package sunshine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WeatherCodeTranslatorTest {

    @Autowired
    private WeatherCodeTranslator translator;

    @Test
    @DisplayName("코드 0은 맑음으로 변환된다")
    void translateClearSky() {
        String result = translator.translate(0);

        assertThat(result).isEqualTo("맑음");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 대체로 맑음",
            "2, 구름 조금",
            "3, 흐림"
    })
    @DisplayName("코드 1-3은 구름 상태로 변환된다")
    void translatePartlyCloudy(int code, String expected) {
        String result = translator.translate(code);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("코드 45는 안개로 변환된다")
    void translateFog() {
        String result = translator.translate(45);

        assertThat(result).isEqualTo("안개");
    }

    @Test
    @DisplayName("코드 61은 비로 변환된다")
    void translateRain() {
        String result = translator.translate(61);

        assertThat(result).isEqualTo("비");
    }

    @Test
    @DisplayName("코드 71은 눈으로 변환된다")
    void translateSnow() {
        String result = translator.translate(71);

        assertThat(result).isEqualTo("눈");
    }

    @Test
    @DisplayName("코드 95는 뇌우로 변환된다")
    void translateThunderstorm() {
        String result = translator.translate(95);

        assertThat(result).isEqualTo("뇌우");
    }
}
