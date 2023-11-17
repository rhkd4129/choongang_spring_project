package com.oracle.s202350101.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.Todo;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.cyjSer.CyjService;
import com.oracle.s202350101.service.cyjSer.CyjServiceTodo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @Slf4j
@Controller
@RequiredArgsConstructor
public class CyjControllerTodo {
	
	private final CyjService cs;
	private final CyjServiceTodo cst;
	
	
	@RequestMapping(value = "todo_list")
	public String todoList(Model model) {
		System.out.println("CyjControllerTodo todo_list Start----------------------");
	
		// 우선순위 분류 코드 가져오기
		Code code = new Code();
		code.setTable_name("TODO");
		code.setField_name("TODO_PRIORITY");
		 
		// todo_우선순위 분류 코드 가져오기
		List<Code> codeList = cs.codeList(code); 
		System.out.println("CyjControllerTodo codeList-> " + codeList.size());
		model.addAttribute("codeList", codeList);	
		
		return "todo/todo_list";
	}
	
	// insert
	@ResponseBody
	@PostMapping(value = "todo_list_insert")
	public String todoListInsert(@RequestBody Todo todo, HttpServletRequest request) {
		System.out.println("CyjControllerTodo todo_list_insert Start----------------------");
		
		System.out.println("!!!!!!!!!! " + todo.getTodo_list());
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		// loginId : 새 글 작성에는 접속자이자 작성자 
		String loginId = userInfoDTO.getUser_id();
		System.out.println(request.getSession().getAttribute("loginId"));
		todo.setUser_id(loginId);
		
		int todoInsert = cst.todoInsert(todo);
		System.out.println("CyjControllerTodo todoInsert-> " + todoInsert);
		
		return loginId;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
