package com.todo.springboot;

import javax.swing.JOptionPane;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import com.todo.springboot.configuration.JpaConfiguration;
/**
 * 
 * User - Pavan Ammanchi
 */

@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages={"com.todo.springboot"})// same as @Configuration @EnableAutoConfiguration @ComponentScan
public class SpringBootTodoApp {
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootTodoApp.class, args);
		
		//JOptionPane.showMessageDialog(null, "Todo Application URL: <a>http://localhost:8222/SpringBootTodoApp</a>");
	}
	
	
}
