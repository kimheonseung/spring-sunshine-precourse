package sunshine.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WeatherSummaryGeneratorTest {

    private WeatherSummaryGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new WeatherSummaryGenerator();
    }

    @Test
    @DisplayName("날씨 요약 문장을 올바르게 생성한다")
    void generateSummary() {
        String summary = generator.generate("서울", 3.4, 1.2, "흐림");

        assertThat(summary).isEqualTo(
                "현재 서울의 기온은 3.4°C이며, 체감 온도는 1.2°C입니다. 날씨는 흐림입니다."
        );
    }

    @Test
    @DisplayName("음수 온도도 올바르게 표시한다")
    void generateSummaryWithNegativeTemperature() {
        String summary = generator.generate("도쿄", -5.0, -8.5, "눈");

        assertThat(summary).isEqualTo(
                "현재 도쿄의 기온은 -5.0°C이며, 체감 온도는 -8.5°C입니다. 날씨는 눈입니다."
        );
    }

    @Test
    @DisplayName("소수점 첫째자리까지 표시한다")
    void generateSummaryWithDecimal() {
        String summary = generator.generate("파리", 15.55, 14.33, "맑음");

        assertThat(summary).contains("15.6°C");
        assertThat(summary).contains("14.3°C");
    }
}
