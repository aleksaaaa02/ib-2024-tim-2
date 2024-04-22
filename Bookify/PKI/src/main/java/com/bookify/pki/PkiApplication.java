package com.bookify.pki;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
public class PkiApplication {


	public static void main(String[] args) {

		SpringApplication.run(PkiApplication.class, args);

	}

}
