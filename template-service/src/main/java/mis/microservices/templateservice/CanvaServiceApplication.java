package mis.microservices.templateservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CanvaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CanvaServiceApplication.class, args);
	}

}
