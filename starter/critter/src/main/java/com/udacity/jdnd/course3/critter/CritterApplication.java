package com.udacity.jdnd.course3.critter;

import org.hibernate.MappingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.logging.Logger;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
public class CritterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}

	@ExceptionHandler(MappingException.class)
	public void handle(MappingException ex){
		System.out.println("PRINTING STACK TRACE");
		ex.printStackTrace();
	}

}
