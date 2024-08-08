package com.eazybytes.springsecuritybasic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.eazybytes.springsecuritybasic.controller") //Main 패키지의 하위 패키지에 컨트롤러가 존재 하지 않다면 이 어노테이션은 필수디
public class SpringsecuritybasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringsecuritybasicApplication.class, args);
	}

}
