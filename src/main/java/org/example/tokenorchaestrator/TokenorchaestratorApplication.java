package org.example.tokenorchaestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TokenorchaestratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenorchaestratorApplication.class, args);
    }

}
