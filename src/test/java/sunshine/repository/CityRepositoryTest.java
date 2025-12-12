package sunshine.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import sunshine.entity.City;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @Test
    @DisplayName("도시 이름으로 City를 조회할 수 있다")
    void findCityByName() {
        Optional<City> city = cityRepository.findByNameIgnoreCase("Seoul");

        assertThat(city).isPresent();
        assertThat(city.get().getName()).isEqualTo("Seoul");
        assertThat(city.get().getKoreanName()).isEqualTo("서울");
    }

    @Test
    @DisplayName("도시 이름은 대소문자를 구분하지 않는다")
    void findCityByNameIgnoreCase() {
        Optional<City> city = cityRepository.findByNameIgnoreCase("SEOUL");

        assertThat(city).isPresent();
        assertThat(city.get().getName()).isEqualTo("Seoul");
    }

    @ParameterizedTest
    @ValueSource(strings = {"Seoul", "Tokyo", "NewYork", "Paris", "London"})
    @DisplayName("5개 도시를 모두 지원한다")
    void supportFiveCities(String cityName) {
        Optional<City> city = cityRepository.findByNameIgnoreCase(cityName);

        assertThat(city).isPresent();
    }

    @Test
    @DisplayName("지원하지 않는 도시는 빈 Optional을 반환한다")
    void unsupportedCityReturnsEmpty() {
        Optional<City> city = cityRepository.findByNameIgnoreCase("Unknown");

        assertThat(city).isEmpty();
    }

    @Test
    @DisplayName("서울의 좌표가 올바르게 설정되어 있다")
    void seoulCoordinates() {
        City seoul = cityRepository.findByNameIgnoreCase("Seoul").orElseThrow();

        assertThat(seoul.getLatitude()).isEqualTo(37.5665);
        assertThat(seoul.getLongitude()).isEqualTo(126.9780);
    }
}
