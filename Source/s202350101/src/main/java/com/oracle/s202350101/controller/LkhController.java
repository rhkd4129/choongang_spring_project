package com.oracle.s202350101.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	private final LkhDaoImpl a;
	
	@GetMapping("Lee")
	public String Hello() {
		System.out.println("Hello");
		List<Task> c = null;
		c= a.work_status();
		return "project/board/Hello";
	}
	
} 
