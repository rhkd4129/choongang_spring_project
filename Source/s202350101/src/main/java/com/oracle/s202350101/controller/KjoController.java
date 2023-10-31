package com.oracle.s202350101.controller;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.*;
import lombok.Data;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.oracle.s202350101.service.kjoSer.ClassRoomService;
import com.oracle.s202350101.service.kjoSer.UserInfoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

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
	//	반 생성 페이지	Post
	@PostMapping("/admin_add_class")
	public String admin_add_class(ClassRoom cr, Model model) {
		log.info("admin_add_class POST");
		int result = CRser.saveClassRoom(cr);            // 강의실 생성
		log.info("반 생성 개수: "+result);
		return "redirect:/admin_class_list";
	}

	//	반 목록 페이지	GET
	@GetMapping("/admin_class_list")
	public String admin_class_list(Model model) {
		log.info("admin_class_list");
		List<ClassRoom> CRList = CRser.findAllClassRoom();            // 모든 강의실 조회

		model.addAttribute("CRList", CRList);

		return "admin/admin_class_list";
	}

	//	게시판 관리 페이지	GET
	@GetMapping("/admin_board")
	public String admin_board(Model model) {

		log.info("admin_board");
		List<ClassRoom> CRList = CRser.findAllClassRoom();            // 모든 강의실 조회

		model.addAttribute("CRList", CRList);

		return "admin/admin_board";
	}
//	채팅방 팝업
	@GetMapping("/chat_room")
	public String chat_room() {
		log.info("chat_room");
		return "admin/chat_room";
	}
//
//	@GetMapping("/chat_student_list")
//	public String chat_student_list() {
//		log.info("chat_room");
//		return "admin/chat_student_list";
//	}


	//	팀장 권한 페이지 GET
	@GetMapping("/admin_projectmanager")
	public String captainManage(UserInfo userInfo, String currentPage, @RequestParam(defaultValue = "1") int cl_id, Model model) {
		log.info("captainManage");
		userInfo.setClass_id(cl_id);
		List<ClassRoom> CRList =CRser.findAllClassRoom();			// 모든 강의실 조회
		int total = UIser.findbyclassuser(userInfo.getClass_id()).size();

		Paging page = new Paging(total, currentPage);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(total);
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);		// 특정 강의실 학생 조회

		model.addAttribute("cl_id",cl_id);
		model.addAttribute("CRList",CRList);
		model.addAttribute("UIList",UIList);
		model.addAttribute("page",page);

		return "admin/admin_projectmanager";
	}

	//	페이징하기
	//	지역, 강의실 선택 시 작동.	EX_ 이대 501
	//	팀장 권한 페이지 RestGET
	@GetMapping("/admin_projectmanagerRest/{cl_id}")	//	cl_id = Class_Room(class_id)
	@ResponseBody
	public  KjoResponse admin_projectmanagerRest(UserInfo userInfo, String currentPage, @PathVariable int cl_id, Model model) {
		log.info("admin_projectmanagerRest");
		int totalUi = UIser.findbyclassuser(cl_id).size();
		userInfo.setClass_id(cl_id);
		log.info("total: ",totalUi);

		Paging page = new Paging(totalUi, currentPage);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(totalUi);
		log.info("page: {}",page);
		log.info("userInfo: {}",userInfo);
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);	// 반 학생의 정보 + 참여 프로젝트 명

		KjoResponse res = new KjoResponse();
		res.setFirList(UIList);
		res.setObj(page);

		log.info("UIList: {}",UIList);

		return res;
	}
//	http://localhost:8282/admin_projectmanagerRest/?cl_id=1&currentPage=11
	@GetMapping("/admin_projectmanagerRest/{currentPage}/{cl_id}")	//	cl_id = Class_Room(class_id)
	@ResponseBody
	public  List<UserInfo> admin_projectmanagerRest_v2(UserInfo userInfo, String currentPage, @PathVariable int cl_id, Model model) {
		log.info("admin_projectmanagerRest");
//		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);	// 반 학생의 정보 + 참여 프로젝트 명
		//	model을 사용하지 않는 이유: return으로 Json에 UIList를 전달하여
		//			jsp를 통해 값을 보여준다.
		int totalUi = UIser.findbyclassuser(cl_id).size();
		userInfo.setClass_id(cl_id);
		log.info("tota l: ",totalUi);

		Paging page = new Paging(totalUi, currentPage);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(totalUi);
		log.info("page: {}",page);
		log.info("userInfo: {}",userInfo);
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);	// 반 학생의 정보 + 참여 프로젝트 명

		log.info("UIList: {}",UIList);
//		전송할 데이터 : page, UIList

		System.out.println("UILIST" + UIList.stream().collect(Collectors.toList()));

		return UIList;
	}

	//	페이징하기
	//	팀장 권한 페이지 RestGET
	@GetMapping("/admin_projectmanagerRest_v1/{currentPage}/{cl_id}")	//	cl_id = Class_Room(class_id)
	@ResponseBody
	public ModelAndView admin_projectmanagerRest_v1(UserInfo userInfo, @PathVariable String currentPage, @PathVariable int cl_id, Model model) {
		log.info("admin_projectmanagerRest");
//		List<UserInfo> UIList = UIser.findbyClassUserProject(cl_id);	// 반 학생의 정보 + 참여 프로젝트 명
		//	model을 사용하지 않는 이유: return으로 Json에 UIList를 전달하여
		//			jsp를 통해 값을 보여준다.
		int totalUi = UIser.findbyclassuser(cl_id).size();

		Paging page = new Paging(totalUi, currentPage);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(totalUi);
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);	// 반 학생의 정보 + 참여 프로젝트 명

		ModelAndView mav = new ModelAndView();
		mav.addObject("page",page);
		mav.addObject("cl_id",cl_id);
		mav.addObject("UIList",UIList);
		mav.addObject("totalUi",totalUi);

		mav.setViewName("admin/admin_projectmanager");
//		전송할 데이터 : page, UIList

		System.out.println("UILIST" + UIList.stream().collect(Collectors.toList()));
		return mav;
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
