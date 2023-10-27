package com.oracle.s202350101.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	
	
	@GetMapping("Lee")
	public String Hello() {
		System.out.println("Hello");
		
		return "project/board/Hello";
	}
	
} 
