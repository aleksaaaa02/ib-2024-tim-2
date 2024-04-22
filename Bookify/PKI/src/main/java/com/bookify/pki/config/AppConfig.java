package com.bookify.pki.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:password.properties")
public class AppConfig {
}