package com.bbd10.activemqredis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.bbd10.activemqredis.Dao")
public class ActivemqRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivemqRedisApplication.class, args);
	}

}
