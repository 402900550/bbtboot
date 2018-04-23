package com.hfkj.bbt;

import com.hfkj.bbt.repository.base.BaseRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.hfkj.bbt"},
		repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class//指定自己的工厂类
)
@SpringBootApplication
public class BbtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BbtApplication.class, args);
	}
}
