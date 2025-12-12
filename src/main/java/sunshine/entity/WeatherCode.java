package sunshine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "weather_code")
public class WeatherCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer code;

    @Column(nullable = false)
    private String description;

    protected WeatherCode() {
    }

    public Long getId() {
        return id;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
