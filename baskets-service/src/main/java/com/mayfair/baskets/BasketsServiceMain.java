package com.mayfair.baskets;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(version = "V1.0.0", title = "Baskets Service API"))
@SpringBootApplication
public class BasketsServiceMain {

    public static void main(String[] args) {
        SpringApplication.run(BasketsServiceMain.class, args);
    }
}
