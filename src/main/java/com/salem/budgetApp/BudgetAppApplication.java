package com.salem.budgetApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.Encoder;

@SpringBootApplication
public class BudgetAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgetAppApplication.class, args);
	}

}
