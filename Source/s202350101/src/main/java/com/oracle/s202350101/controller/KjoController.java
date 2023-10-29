package com.oracle.s202350101.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.KjoRequestDto;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	//	반 생성 페이지 Get
	@GetMapping("/admin_add_class")
	public String admin_add_class(Model model) {
		log.info("admin_add_class GET");
		return "admin/admin_add_class";
	}
	//	반 생성	Post
	@PostMapping("/admin_add_class")
	public String admin_add_class(ClassRoom cr, Model model) {
		log.info("admin_add_class POST");
		List<ClassRoom> CRList =CRser.findAllClassRoom();			// 모든 강의실 조회
		
		model.addAttribute("CRList",CRList);

		return "admin/admin_add_class";
	}

	@GetMapping("/admin/admin_class_list")
	public String admin_class_list(Model model) {
		log.info("admin_class_list");

		return "admin/admin_class_list";
	}

	//	게시판 관리 페이지 Get
	@GetMapping("/admin_board")
	public String admin_board(Model model) {

		log.info("admin_board");
		List<ClassRoom> CRList = CRser.findAllClassRoom();            // 모든 강의실 조회

		model.addAttribute("CRList", CRList);

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
		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);		// 특정 강의실 학생 조회

		model.addAttribute("CRList",CRList);
		model.addAttribute("UIList",UIList);

		return "admin/admin_projectmanager";
	}

	//	팀장 권한 페이지 RestGET
	@GetMapping("/admin_projectmanagerRest/{cl_id}")	//	cl_id = Class_Room(class_id)
	@ResponseBody
	public  List<UserInfo> admin_projectmanagerRest(@PathVariable int cl_id, Model model) {
		log.info("admin_projectmanagerRest");
		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);	// 반 학생의 정보 + 참여 프로젝트 명
		//	model을 사용하지 않는 이유: return으로 Json에 UIList를 전달하여
		//			jsp를 통해 값을 보여준다.
		System.out.println("UILIST" + UIList.stream().collect(Collectors.toList()));
		return UIList;
	}

	//	팀장 권한 수정	Rest
	@PostMapping("/auth_mod")
	@ResponseBody
	public ResponseEntity<?> auth_mod(@RequestBody KjoRequestDto kjorequest) {
		//	RequestDto를 통해 불필요한 데이터 처리를 하지 않아도 된다.
		int result = UIser.auth_modify(kjorequest);
		return ResponseEntity.ok(result);
	}

}
