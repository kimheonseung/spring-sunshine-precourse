package sunshine.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import sunshine.domain.City;
import sunshine.dto.WeatherResponse;
import sunshine.exception.GlobalExceptionHandler;
import sunshine.service.WeatherService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherController.class)
@Import(GlobalExceptionHandler.class)
class WeatherControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    @DisplayName("유효한 도시로 요청하면 200 OK와 날씨 정보를 반환한다")
    void getWeatherWithValidCity() throws Exception {
        WeatherResponse response = new WeatherResponse(
                "Seoul", "서울", 5.0, 3.0, 50, "맑음",
                "현재 서울의 기온은 5.0°C이며, 체감 온도는 3.0°C입니다. 날씨는 맑음입니다."
        );
        given(weatherService.getWeather(City.SEOUL)).willReturn(response);

        mockMvc.perform(get("/api/weather").param("city", "Seoul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Seoul"))
                .andExpect(jsonPath("$.cityKorean").value("서울"))
                .andExpect(jsonPath("$.temperature").value(5.0))
                .andExpect(jsonPath("$.weatherCondition").value("맑음"));
    }

    @Test
    @DisplayName("지원하지 않는 도시로 요청하면 400 Bad Request와 에러 응답을 반환한다")
    void getWeatherWithInvalidCity() throws Exception {
        mockMvc.perform(get("/api/weather").param("city", "Unknown"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("CITY_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("city 파라미터 없이 요청하면 400 Bad Request와 에러 응답을 반환한다")
    void getWeatherWithoutCityParam() throws Exception {
        mockMvc.perform(get("/api/weather"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("MISSING_PARAMETER"))
                .andExpect(jsonPath("$.message").exists());
    }
}
