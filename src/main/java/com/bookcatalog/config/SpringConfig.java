package com.bookcatalog.config;

import com.bookcatalog.UUIDGenerator;
import com.bookcatalog.UUIDGeneratorImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:gmail.properties")
public class SpringConfig {
    @Bean
    public UUIDGenerator uuidGenerator(){
        return new UUIDGeneratorImpl();
    }
}
