package com.minicloud.insight.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MiniCloud Insight - Todo API")
                        .version("1.0.0")
                        .description("Todo 관리 및 모니터링 API 문서")
                        .contact(new Contact()
                                .name("mgs06380@naver.com")
                                .email("mgs06380@naver.com")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local Server"),
                        new Server()
                                .url("http://your-aws-ec2-url:8080")
                                .description("AWS EC2 Server")
                ));
    }
}