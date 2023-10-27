package com.oracle.s202350101.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class KjoController {

	@GetMapping("/test")
	public String test() {
		log.info("hi");
		return "admin/hello";
	}
	@GetMapping("/captainManage")
	public String captainManage() {
		log.info("hi");
		return "admin/captainManage";
	}

}
