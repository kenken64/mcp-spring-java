package sg.edu.nus.spring_mcp;

import java.util.List;

import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbacks;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringMcpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMcpApplication.class, args);
	}

	@Bean
	public List<ToolCallback> courseTools(CourseService courseService){
		return List.of(ToolCallbacks.from(courseService));
	}

}
