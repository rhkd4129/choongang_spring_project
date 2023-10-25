package com.oracle.home.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;




import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller

@Slf4j
public class HomeController {

	@GetMapping("home")
	public String home() {
		System.out.println("들어옴");
		return "home";
	}
}
