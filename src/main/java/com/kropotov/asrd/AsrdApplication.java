package com.kropotov.asrd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

//@EnableDiscoveryClient // организация микросервисной архитектуры если потребуется TODO
@SpringBootApplication // Этот класс точка старта, декларирует что включаем набор автоконфигураций, сканирование пакетов на поиск конфигураций и properties
public class AsrdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsrdApplication.class, args);
	}

	//test pull
}
