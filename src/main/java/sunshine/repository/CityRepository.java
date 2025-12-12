package sunshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sunshine.entity.City;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long> {

    Optional<City> findByNameIgnoreCase(String name);
}
