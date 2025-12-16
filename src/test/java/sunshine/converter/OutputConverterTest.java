package sunshine.converter;

import org.junit.jupiter.api.Test;

public class OutputConverterTest {
    @Test
    public void convert() {
        OutputConverter outputConverter = new OutputConverter();
        System.out.println(outputConverter.getWeatherForecastOutputTemplate());
    }
}
