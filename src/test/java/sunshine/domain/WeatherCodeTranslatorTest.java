package sunshine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherCodeTranslatorTest {

    private WeatherCodeTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new WeatherCodeTranslator();
    }

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

    @ParameterizedTest
    @CsvSource({
            "45, 안개",
            "48, 안개"
    })
    @DisplayName("코드 45-48은 안개로 변환된다")
    void translateFog(int code, String expected) {
        String result = translator.translate(code);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "61, 비",
            "63, 비",
            "65, 비"
    })
    @DisplayName("코드 61-65는 비로 변환된다")
    void translateRain(int code, String expected) {
        String result = translator.translate(code);

        assertThat(result).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "71, 눈",
            "73, 눈",
            "75, 눈"
    })
    @DisplayName("코드 71-75는 눈으로 변환된다")
    void translateSnow(int code, String expected) {
        String result = translator.translate(code);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("코드 95 이상은 뇌우로 변환된다")
    void translateThunderstorm() {
        String result = translator.translate(95);

        assertThat(result).isEqualTo("뇌우");
    }
}
