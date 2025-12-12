package sunshine.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sunshine.entity.City;
import sunshine.exception.CityNotFoundException;
import sunshine.repository.CityRepository;

@Service
@Transactional(readOnly = true)
public class CityReadService {

    private final CityRepository cityRepository;

    public CityReadService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City findByName(String name) {
        return cityRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new CityNotFoundException(name));
    }
}
