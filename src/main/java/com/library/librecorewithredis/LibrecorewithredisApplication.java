package com.library.librecorewithredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibrecorewithredisApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrecorewithredisApplication.class, args);
	}

}
