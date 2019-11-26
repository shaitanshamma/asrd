package com.kropotov.asrd;

import com.kropotov.asrd.config.SystemConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@EnableDiscoveryClient // организация микросервисной архитектуры если потребуется TODO
@SpringBootApplication // Этот класс точка старта, декларирует что включаем набор автоконфигураций, сканирование пакетов на поиск конфигураций и properties
@Import(SystemConfig.class) // подтягиваем конфигурации
public class AsrdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsrdApplication.class, args);
	}

}
