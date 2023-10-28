package com.oracle.s202350101.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.kjoSer.ClassRoomService;
import com.oracle.s202350101.service.kjoSer.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KjoController {
	
	private final ClassRoomService CRser;
	
	private final UserInfoService UIser;

	@GetMapping("/hello")
	public String test() {
		log.info("hi");
		return "admin/hello";
	}
	
	@GetMapping("/admin_board")
	public String boardManage() {
		log.info("boardManage");
		return "admin/admin_board";
	}
	
	@GetMapping("/createnote")
	public String createnote() {
		log.info("createnote");
		return "admin/createnote";
	}
	
	//	팀장 권한 페이지 GET
	@GetMapping("/admin_projectmanager")
	public String captainManage(@RequestParam(defaultValue = "1") int cl_id, Model model) {
		log.info("captainManage");
		List<ClassRoom> CRList =CRser.findAllClassRoom();			// 모든 강의실 조회
		log.info(CRList.toString());
//		List<UserInfo> USList = UIser.findbyclassuser(cl_id);		// 특정 강의실 학생 조회
		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);		// 특정 강의실 학생 조회

		
		model.addAttribute("CRList",CRList);
		model.addAttribute("UIList",UIList);
		
		
		return "admin/admin_projectmanager";
	}
	
//	(defaultValue = "1")
	//	팀장 권한 페이지 GET
	@GetMapping("/admin_projectmanagerRest")
	@ResponseBody
	public HttpEntity<List> admin_projectmanagerRest(@RequestParam(defaultValue = "1") int cl_id, Model model) {
//		log.info("captainManage");
//		List<ClassRoom> CRList =CRser.findAllClassRoom();
//		log.info(CRList.toString());
//		log.info("");
		log.info("admin_projectmanagerRest");
		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);
		HttpEntity<List> entity = new HttpEntity<>(UIList);
		
//		model.addAttribute("CRList",CRList);
		model.addAttribute("UIList",UIList);
		System.out.println("UILIST" + UIList.stream().collect(Collectors.toList()));
		log.info("admin_projectmanagerRest");
		
		
		return entity;
	}
	
	
}
