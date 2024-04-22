package com.bookify.pki;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@SpringBootApplication
@EnableEncryptableProperties
public class PkiApplication {


	public static void main(String[] args) {

		SpringApplication.run(PkiApplication.class, args);

	}

}
