package com.springboot.nmsbackend;

import jakarta.persistence.EntityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NmsbackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(NmsbackendApplication.class, args);
	}

	@Bean  // This makes Spring run this code when starting
	public CommandLineRunner clearCache(EntityManager em) {
		return args -> {  // This is a shortcut for writing a method
			System.out.println("⚡ Clearing Hibernate's memory cache...");
			em.getEntityManagerFactory().getCache().evictAll();
			System.out.println("✅ Cache cleared successfully!");
		};
	}

}
