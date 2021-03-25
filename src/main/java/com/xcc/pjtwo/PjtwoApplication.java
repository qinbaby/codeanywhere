package com.xcc.pjtwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import springfox.documentation.oas.annotations.EnableOpenApi;

@EnableOpenApi
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//排除自动配置
public class PjtwoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PjtwoApplication.class, args);
    }
}
