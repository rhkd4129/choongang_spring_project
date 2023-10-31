package com.oracle.s202350101.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.s202350101.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {
	
	private final CommonService cs;
	
	@RequestMapping(value = "/main")
	public String mainPage(Model model) {
		return "main";
	}

	@RequestMapping(value = "/template_frame")
	public String mainTemplateFramePage(Model model) {
		return "template_frame";
	}

	@RequestMapping(value = "/main_header")
	public String mainHeaderPage(Model model) {
		return "main_header";
	}

	@RequestMapping(value = "/main_menu")
	public String mainMenuPage(Model model) {
		return "main_menu";
	}

	@RequestMapping(value = "/main_center")
	public String mainCenterPage(Model model) {
		return "main_center";
	}

	@RequestMapping(value = "/main_footer")
	public String mainFooterPage(Model model) {
		return "main_footer";
	}
}
