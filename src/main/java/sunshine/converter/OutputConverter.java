package sunshine.converter;

import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.stereotype.Component;
import sunshine.data.WeatherForecastData;

@Component
public class OutputConverter {
    public String getWeatherForecastOutputTemplate() {
        var beanOutputConverter = new BeanOutputConverter<>(
                WeatherForecastData.class
        );

        String result = beanOutputConverter.getFormat();

        System.out.println(result);
        return result;
    }

    public WeatherForecastData convertWeatherForecastDataByText(String text) {
        var beanOutputConverter = new BeanOutputConverter<>(
                WeatherForecastData.class
        );

        return beanOutputConverter.convert(text);
    }
}
