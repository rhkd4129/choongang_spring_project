package com.oracle.s202350101.controller;

import java.util.List;

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
		List<ClassRoom> CRList =CRser.findAllClassRoom();
		log.info(CRList.toString());
//		log.info("");
		List<UserInfo> USList = UIser.findbyclassuser(cl_id);
		
		model.addAttribute("CRList",CRList);
		model.addAttribute("USList",USList);
		
		
		return "admin/admin_projectmanager";
	}
	
	//	팀장 권한 페이지 GET
	@GetMapping("/admin_projectmanagerRest")
	@ResponseBody
	public HttpEntity<List> admin_projectmanagerRest(@RequestParam(defaultValue = "1") int cl_id, Model model) {
//		log.info("captainManage");
//		List<ClassRoom> CRList =CRser.findAllClassRoom();
//		log.info(CRList.toString());
//		log.info("");
		log.info("admin_projectmanagerRest");
		List<UserInfo> USList = UIser.findbyclassuser(cl_id);
		HttpEntity<List> entity = new HttpEntity<List>(USList);
		
//		model.addAttribute("CRList",CRList);
		model.addAttribute("USList",USList);
		log.info("admin_projectmanagerRest");
		
		
		return entity;
	}
	
	
}
