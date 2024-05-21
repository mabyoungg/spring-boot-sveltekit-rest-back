package org.example.springbootsveltekitrestback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootSveltekitRestBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootSveltekitRestBackApplication.class, args);
	}

}
