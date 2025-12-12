package sunshine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sunshine.entity.WeatherCode;

import java.util.Optional;

public interface WeatherCodeRepository extends JpaRepository<WeatherCode, Long> {

    Optional<WeatherCode> findByCode(Integer code);

    @Query("SELECT w FROM WeatherCode w WHERE w.code <= :code ORDER BY w.code DESC LIMIT 1")
    Optional<WeatherCode> findClosestByCode(@Param("code") Integer code);
}
