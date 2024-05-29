package com.example.api_comandas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.example.api_comandas"})
@PropertySource("classpath:application.properties")
public class ApiComandasApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiComandasApplication.class, args);
    }

    @Bean
    public DataSource getDataSource() {
        @SuppressWarnings("rawtypes")
		DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:postgresql://192.168.1.138:5432/Bar_ElEscobar_2.0");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("12345");
        return dataSourceBuilder.build();
    }
}

