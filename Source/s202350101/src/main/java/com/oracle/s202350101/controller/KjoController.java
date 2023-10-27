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
	
	@GetMapping("/boardManage")
	public String boardManage() {
		log.info("boardManage");
		return "admin/boardManage";
	}
	
	@GetMapping("/createnote")
	public String createnote() {
		log.info("createnote");
		return "admin/createnote";
	}
	
	@GetMapping("/captainManage")
	public String captainManage() {
		log.info("captainManage");
		return "admin/captainManage";
	}

}
