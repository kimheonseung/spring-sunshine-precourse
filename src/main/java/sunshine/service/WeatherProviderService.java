package sunshine.service;

import sunshine.data.WeatherData;

public interface WeatherProviderService {
    WeatherData getCurrent(double latitude, double longitude);
}
