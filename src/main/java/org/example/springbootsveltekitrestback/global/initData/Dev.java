package org.example.springbootsveltekitrestback.global.initData;

import lombok.RequiredArgsConstructor;
import org.example.springbootsveltekitrestback.global.app.AppConfig;
import org.example.springbootsveltekitrestback.standard.util.Ut;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class Dev {
    @Bean
    @Order(4)
    ApplicationRunner initDev() {
        return args -> {
            String backUrl = AppConfig.getSiteBackUrl();
            String cmd = "cd ../spring-boot-sveltekit-rest-front && npx openapi-typescript " + backUrl + "/v3/api-docs/apiV1 -o ./src/lib/types/api/v1/schema.d.ts";
            Ut.cmd.runAsync(cmd);
        };
    }
}
