package com.kropotov.asrd.config;

import com.kropotov.asrd.entities.ControlSystem;
import com.kropotov.asrd.repositories.SystemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class SystemConfig {

    // CommandLineRunner - SpringContext запускает при старте приложения все реализации интерфейса CommandLineRunner
    // При инициализации бина Spring найдет в контексте репозиторий SystemRepository и заинжектит сюда
    @Bean
    public CommandLineRunner loadService(SystemRepository systemRepository) {
        return args -> {
            log.info("Start load data");
            /*log.info("Data={}", systemRepository.save(new ControlSystem("0110001", "Испытания")));
            log.info("Data={}", systemRepository.save(new ControlSystem("0110002", "Испытания")));
            log.info("Data={}", systemRepository.save(new ControlSystem("0110003", "Испытания")));*/
            log.info("Data was loaded");
        };
    }

}
