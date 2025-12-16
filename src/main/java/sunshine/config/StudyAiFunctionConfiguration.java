package sunshine.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;
import sunshine.study.AddDayRequest;
import sunshine.study.DateResponse;

import java.time.LocalDate;

@Configuration
public class StudyAiFunctionConfiguration {
    @Tool(description = "Calculate a date after adding days from today")
    public DateResponse addDaysFromToday(AddDayRequest request) {
        var result = LocalDate.now().plusDays(request.days());
        return new DateResponse(result.toString());
    }


}
