package amuz.todo_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TodoBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoBackApplication.class, args);
	}

}
