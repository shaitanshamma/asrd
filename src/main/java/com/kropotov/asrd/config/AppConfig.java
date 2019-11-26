package com.kropotov.asrd.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:private.properties") // мои приватные настройки
@ComponentScan("com.kropotov.asrd")
public class AppConfig implements WebMvcConfigurer {
}
