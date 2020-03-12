package com.kropotov.asrd.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySource("classpath:private.properties") // мои приватные настройки
@ComponentScan("com.kropotov.asrd")
public class AppConfig implements WebMvcConfigurer {
    // Первичная настройка для хранения файлов, пока что все в одной папке TODO JCR
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/files/**")) {
            registry.addResourceHandler("/files/**").addResourceLocations("file:files/");
        }
    }
}
