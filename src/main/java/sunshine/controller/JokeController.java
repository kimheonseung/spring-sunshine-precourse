package sunshine.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.config.StudyAiFunctionConfiguration;
import sunshine.study.ActorFilms;

import java.util.Map;

@RestController
public class JokeController {
    private final ChatClient client;

    public JokeController(ChatClient.Builder builder) {
        this.client = builder.build();
    }

    @GetMapping("/joke")
    public ChatResponse joke(
            @RequestParam(defaultValue = "Hans") String name,
            @RequestParam(defaultValue = "pirate") String voice
    ) {
//        var promptTemplate = new PromptTemplate(message);
//        var prompt = promptTemplate.render(Map.of("topic", topic));
//        return client.prompt(prompt)
//                .call()
//                .chatResponse();

        // user 프롬프트
        var user = new UserMessage("""
                Tell me about three famous pirates from the Golden Age of Piracy and what they did.
                Write at least one sentence for each pirate.
                """);

        // system message
        var systemPromptTemplate = new SystemPromptTemplate("""
                You are a helpful AI assistant.
                You are an AI assistant that helps people find information.
                Your name is {name}.
                You should reply to the user's request using your name and in the style of a {voice}.
                """
        );
        var system = systemPromptTemplate.createMessage(Map.of("name", name, "voice", voice));
        var prompt = new Prompt(user, system);
        return client.prompt(prompt).call().chatResponse();
    }

    @GetMapping("filmography")
    public ActorFilms filmography(
            @RequestParam(defaultValue = "Robert Downey Jr.") String actor
    ) {
        var beanOutputConverter = new BeanOutputConverter<>(
                ActorFilms.class
        );

        var format = beanOutputConverter.getFormat();
        System.out.println(format);
        var userMessage = """
                Generate the filmography of 5 movies for {actor}.
                {format}
                """;
        var promptTemplate = new PromptTemplate(userMessage)
                .create(Map.of("actor", actor, "format", format));
        var text = client.prompt(promptTemplate).call().chatResponse().getResult().getOutput().getText();
        System.out.println(text);

        return beanOutputConverter.convert(text);
    }

    @GetMapping("/add-days")
    public String addDays(
            @RequestParam(defaultValue = "0") int days
    ) {
        var promptTemplate = new PromptTemplate("오늘 기준으로 {days}일 뒤 날짜를 알려줘.")
                .render(Map.of("days", days));
        return client.prompt(promptTemplate)
                .tools(new StudyAiFunctionConfiguration())
                .call()
                .content();
    }
}
