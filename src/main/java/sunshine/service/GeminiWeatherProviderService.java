package sunshine.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import sunshine.converter.OutputConverter;
import sunshine.data.WeatherForecastData;

import java.util.Map;

@Service
public class GeminiWeatherProviderService {
    private final ChatClient client;
    private final WeatherProviderService weatherProviderService;
    private final OutputConverter outputConverter;

    public GeminiWeatherProviderService(ChatClient.Builder clientBuilder,
                                        WeatherProviderService weatherProviderService,
                                        OutputConverter outputConverter) {
        this.client = clientBuilder.build();
        this.weatherProviderService = weatherProviderService;
        this.outputConverter = outputConverter;
    }

    public WeatherForecastData getCurrentWeatherAndRecommendCloths(String place) {
        String format = outputConverter.getWeatherForecastOutputTemplate();
        var promptTemplate = new PromptTemplate("""
             {place} 지역의 위도와 경도를 알아내서 날씨를 조회해주고, 다음 요구사항 두개를 반영해서 요약해줘.
             <추론규칙>
             1. 위도와 경도를 내가 알려주지 않아도 무조건 너가 {place} 바탕으로 추측해서 답해.
             2. 지역이 광범위하면 대표되는 지역을 너가 골라서 답해.
                 예: 수도권, 호남권 등 "모호한" 지역명이 입력되면, 각각 서울, 광주 등으로 간주하고 답해.
             3. 절대 "구체적으로 어느 지역인가요?"라는 질문을 추가로 하지 마.
             
             <답변 요구사항>
             1. 현재 주어진 지역의 날씨 정보 조회
             예시는 "현재 서울의 기온은 3.4°C이며, 풍속은 5.7m/s입니다. 날씨는 흐림입니다." 를 참고.
             
             2. 현재 날씨 기준으로 복장 추천
             기온 구간, 강수 여부, 체감온도, 바람 세기를 추천 기준으로 하여 청년층, 중년층, 노년층에게 각각 적합한 복장을 추천.
            
             
             {format}
             
             결과 필드 설명:
               - apparentTemperature: 체감 온도
               - humidity: 습도
               - place: 지역
               - recommendClothsData: 추천 복장
                 - forElder: 노년층 추천 복장
                 - forMiddle: 중년층 추천 복장
                 - forYoung: 청년층 추천 복장
             """)
                .create(Map.of("place", place, "format", format));

        var text = client.prompt(promptTemplate).call().chatResponse().getResult().getOutput().getText();
        System.out.println(text);

        return outputConverter.convertWeatherForecastDataByText(text);
    }
}
