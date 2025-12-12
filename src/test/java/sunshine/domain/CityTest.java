package sunshine.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class CityTest {

    @Test
    @DisplayName("도시 이름으로 City를 조회할 수 있다")
    void findCityByName() {
        Optional<City> city = City.fromName("Seoul");

        assertThat(city).isPresent();
        assertThat(city.get()).isEqualTo(City.SEOUL);
    }

    @Test
    @DisplayName("도시 이름은 대소문자를 구분하지 않는다")
    void findCityByNameIgnoreCase() {
        Optional<City> city = City.fromName("SEOUL");

        assertThat(city).isPresent();
        assertThat(city.get()).isEqualTo(City.SEOUL);
    }

    @ParameterizedTest
    @ValueSource(strings = {"Seoul", "Tokyo", "NewYork", "Paris", "London"})
    @DisplayName("5개 도시를 모두 지원한다")
    void supportFiveCities(String cityName) {
        Optional<City> city = City.fromName(cityName);

        assertThat(city).isPresent();
    }

    @Test
    @DisplayName("지원하지 않는 도시는 빈 Optional을 반환한다")
    void unsupportedCityReturnsEmpty() {
        Optional<City> city = City.fromName("Unknown");

        assertThat(city).isEmpty();
    }

    @Test
    @DisplayName("서울의 좌표가 올바르게 설정되어 있다")
    void seoulCoordinates() {
        City seoul = City.SEOUL;

        assertThat(seoul.getLatitude()).isEqualTo(37.5665);
        assertThat(seoul.getLongitude()).isEqualTo(126.9780);
        assertThat(seoul.getKoreanName()).isEqualTo("서울");
    }
}
