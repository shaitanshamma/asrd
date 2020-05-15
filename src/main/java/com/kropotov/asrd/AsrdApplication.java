package com.kropotov.asrd;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableDiscoveryClient // организация микросервисной архитектуры если потребуется TODO
@SpringBootApplication // Этот класс точка старта, декларирует что включаем набор автоконфигураций, сканирование пакетов на поиск конфигураций и properties
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class AsrdApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsrdApplication.class, args);
	}

}
