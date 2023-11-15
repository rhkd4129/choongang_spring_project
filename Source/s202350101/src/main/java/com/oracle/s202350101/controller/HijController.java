package com.oracle.s202350101.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.oracle.s202350101.model.HijPrjStep;
import com.oracle.s202350101.model.HijRequestDto;
import com.oracle.s202350101.model.HijSearchRequestDto;
import com.oracle.s202350101.model.HijSearchResponseDto;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.hijSer.HijService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HijController {
	
	private final HijService hs;
//--------------------------------------------------------------------------------------	
// 1. admin 프로젝트 관리
//--------------------------------------------------------------------------------------		
	// 프로젝트 승인신청
	@GetMapping("/admin_approval")
	public String admin_approval(PrjInfo prjInfo, String currentPage,  Model model) {
		log.info("HijController approval Start");
		
		//-------------------------------------------------
		int totalCount = hs.totalCount(); // Prj_info 전체 목록 리스트 갯수
		//-------------------------------------------------
		
		Paging page = new Paging(totalCount, currentPage);
		prjInfo.setStart(page.getStart());
		prjInfo.setEnd(page.getEnd());
		System.out.println("startpage : " + page.getStart());
		System.out.println("endpage : " + page.getEnd());
		
		//-----------------------------------------------------------------
		List<PrjInfo> approveList = hs.approveList(prjInfo); //Prj_info 전체 리스트
		//-----------------------------------------------------------------
		System.out.println("approveList.size : " + approveList.size());
		
		model.addAttribute("approveList", approveList);
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		
		return "admin/admin_approval";
	}
//--------------------------------------------------------------------------------------		
	// 프로젝트 승인(체크박스)
	 @PostMapping("/app_ok")
	 @ResponseBody 
	 public ResponseEntity<?> app_ok(@RequestBody HijRequestDto hijrequest) {
		 System.out.println("HijController app_ok Start");
		//-------------------------------------------------
		 int result = hs.app_ok(hijrequest);
		//-------------------------------------------------
		 System.out.println("HijController app_ok hijrequest.getProject_id().get(0):" +hijrequest.getProject_id().get(0));
		 return ResponseEntity.ok(result);
	 }
//--------------------------------------------------------------------------------------		
	 // 프로젝트 삭제(체크박스)
	 @PostMapping("/app_del")
	 @ResponseBody
	 public ResponseEntity<?> app_del(@RequestBody HijRequestDto hijrequest){
		 System.out.println("HijController app_del START");
		//-------------------------------------------------
		 int result = hs.app_del(hijrequest);
		//-------------------------------------------------
		 return ResponseEntity.ok(result);	 
	}
//--------------------------------------------------------------------------------------- 
// 2. 프로젝트 생성
//--------------------------------------------------------------------------------------		
	//  프로젝트 생성신청 조회 페이지
	@GetMapping(value = "prj_mgr_req_create")
		public String prjCreate(PrjInfo prjInfo, Model model, HttpServletRequest request) {
		
		// UserInfo에서 project_id 들고 오기
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    String user_id = userInfoDTO.getUser_id();
		
		System.out.println("HijController prj_mgr_req_create Start");
		//--------------------------------------------------------
		List<UserInfo> listName = hs.listName(user_id); 	// user_id랑 같은 class_id를 가진학생 리스트
		//--------------------------------------------------------
		System.out.println("listName size : " + listName.size());
		
		model.addAttribute("userInfoDTO", userInfoDTO);
		model.addAttribute("listName", listName);
		
		return "/project/manager/prj_mgr_req_create";
	}
//--------------------------------------------------------------------------------------				
	// 프로젝트 생성 수행
	@PostMapping(value = "req_create")
	public String reqCreate(PrjInfo prjInfo, Model model) {
		System.out.println("HijController req_create Start");
		//-------------------------------------------------
		int createResult = hs.reqCreate(prjInfo);
		//-------------------------------------------------
		System.out.println("test11111 -> " + createResult);
		if(createResult > 0) return "/main";
		return "forword:prj_mgr_req_create";
	}
//--------------------------------------------------------------------------------------	
// 3. 프로젝트 단계 프로파일
//--------------------------------------------------------------------------------------	
// 프로젝트 조회 화면(상단 조회)
	@GetMapping(value = "prj_mgr_step_list")
	public String stepList(Model model, HttpServletRequest request) {
		System.out.println("HijController prj_mgr_step_list Start");
		
		// UserInfo에서 project_id 들고 오기
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    int project_id = userInfoDTO.getProject_id();
	      
		System.out.println("request userInfo prj_mgr_step_list->"+ userInfoDTO.getProject_id());
		
		if(project_id > 0) {
			//-------------------------------------------------
			List<PrjMemList> listMember = hs.listMember(project_id);	// 멤버조회
			//-------------------------------------------------
			PrjInfo prjInfo = hs.listStep(project_id);				// 초기설정조회
			//-------------------------------------------------
			List<PrjStep> titleList = hs.titleList(project_id);		// 단계조회
			//-------------------------------------------------
			System.out.println("HijController listMember listMember.size() : " +listMember.size());
			System.out.println("HijController titleList.size() : " + titleList.size());
			System.out.println("listStep 프로젝트 아이디 : " + prjInfo.getProject_id());
			model.addAttribute("listMember", listMember);
			model.addAttribute("titleListCount",titleList.size());
			model.addAttribute("prjInfo", prjInfo);
			model.addAttribute("titleList", titleList);
			model.addAttribute("ProjectNotFound", "FALSE");
		} else {
			model.addAttribute("ProjectNotFound", "TRUE");
		}
		model.addAttribute("userInfoDTO",userInfoDTO);
		return "/project/manager/prj_mgr_step_list";
	}	
//--------------------------------------------------------------------------------------		
	// list에 프로젝트 시작, 종료 버튼
	@PostMapping(value = "prj_status_bnt")
	@ResponseBody
	public String changeStatus(@RequestBody PrjInfo prjInfo, Model model) {
		System.out.println("HijController prj_status_bnt Start");
		String resultStatus = null;
		System.out.println("Project_id : " + prjInfo.getProject_id());
		System.out.println("Project_status : " + prjInfo.getProject_status());
		//-------------------------------------------------
		int prjStatus = hs.prjStatus(prjInfo);
		//-------------------------------------------------
		System.out.println("prjStatus : " +prjStatus );
		if(prjStatus > 0) {
			resultStatus = "success";
		}else {
			resultStatus = "fail";
		}
		model.addAttribute("prjStatus", prjStatus);
		System.out.println("resultStatus : " +resultStatus );
		return resultStatus;
	}	

//--------------------------------------------------------------------------------------		
	// 프로젝트 정보 수정 조회
	@GetMapping(value = "prj_mgr_req_edit")
	public String prjMgrReqEdit(Model model, HttpServletRequest request) {
		
		// UserInfo에서 project_id 들고 오기
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    int project_id = userInfoDTO.getProject_id();
	    String user_id = userInfoDTO.getUser_id();
	    
		System.out.println("HijController prjMgrReqEdit Start");
		//-------------------------------------------------
		List<PrjMemList> listMember = hs.listMember(project_id);	// 프로젝트 멤버 리스트
		//-------------------------------------------------
		List<UserInfo> listName = hs.listName(user_id);			 	// user_id랑 같은 class_id를 가진학생 리스트
		//-------------------------------------------------
		PrjInfo prjInfo = hs.listStep(project_id); 					// 프로젝트 조회 
		//-------------------------------------------------
		
		model.addAttribute("listMember", listMember);
		model.addAttribute("listName", listName);
		model.addAttribute("prjInfo", prjInfo);
		model.addAttribute("userInfoDTO", userInfoDTO);
		return "/project/manager/prj_mgr_req_edit";
	}
//--------------------------------------------------------------------------------------		
	// 프로젝트 정보 수정 수행
	@PostMapping(value = "req_edit")
	public String reqEdit(PrjInfo prjInfo, Model model) {
		System.out.println("HijController req_edit Start");
		System.out.println("★prjInfo.getMember_user_id() ---> : " + prjInfo.getMember_user_id());
		//-------------------------------------------------
		int editResult = hs.reqEdit(prjInfo);
		//-------------------------------------------------
		return "redirect:/prj_mgr_step_list";
	}
//--------------------------------------------------------------------------------------		
	// 프로젝트 단계 추가 
	@GetMapping(value = "prj_mgr_step_insert")
	public String insertForm(PrjStep prjStep, Model model) {
		System.out.println("HijController prj_mgr_step_insert Start");
		return "/project/manager/prj_mgr_step_insert";	
	}
//--------------------------------------------------------------------------------------
	// 프로젝트 단계 추가 수행
	@PostMapping(value = "step_insert")
	public String stepInsert(PrjStep prjStep, Model model) {
		System.out.println("HijController step_insert Start");
		//-------------------------------------------------
		int insertResult = hs.insertStep(prjStep);
		//-------------------------------------------------
		if(insertResult > 0) return "redirect:prj_mgr_step_list";
		return "forword:prj_mgr_step_insert";
	}	
//--------------------------------------------------------------------------------------
	// 단계 선택 
	@PostMapping(value = "/prj_order_update")
	@ResponseBody
	public ResponseEntity<?> prjOrderUpdate(@RequestBody List<HijPrjStep> hijPrjStepList) {
		System.out.println("HijController prjOrderUpdate Start");
		//-------------------------------------------------
		int result = hs.prjOrder(hijPrjStepList);
		//-------------------------------------------------
		System.out.println("result : " + result);
		return ResponseEntity.ok(result);
	}
//--------------------------------------------------------------------------------------
	// 프로젝트 단계 수정 조회
	@GetMapping(value = "prj_mgr_step_edit")
	public String stepEdit(int project_id, int project_step_seq, Model model) {
		System.out.println("HijController prj_mgr_step_edit Start");
		//-------------------------------------------------
		PrjStep prjStep = hs.detailStep(project_id, project_step_seq);
		//-------------------------------------------------
		model.addAttribute("prjStep", prjStep);
		System.out.println("prject id--->" + prjStep.getProject_id());
		return "/project/manager/prj_mgr_step_edit";
	}
//--------------------------------------------------------------------------------------				
	// 프로젝트 단계 수정 수행
	@PostMapping(value = "prj_mgr_step_update")
	public String stepUpdate(PrjStep prjStep, Model model) {
		System.out.println("HijController prj_mgr_step_edit Start");
		//-------------------------------------------------
		int updateCount = hs.updateStep(prjStep);
		//-------------------------------------------------
		System.out.println("HijController updateCount : " + updateCount);
		model.addAttribute("prjStep", prjStep);
		return "redirect:prj_mgr_step_list";
	}
//--------------------------------------------------------------------------------------				
	// 프로젝트 단계 삭제
	@RequestMapping(value = "deleteStep")
	public String deleteStep(int project_id, int project_step_seq, Model model) {
		System.out.println("HijController deleteStep Start");
		//-------------------------------------------------
		int result = hs.deleteStep(project_id, project_step_seq);
		//-------------------------------------------------
		return "redirect:prj_mgr_step_list";
	}
//--------------------------------------------------------------------------------------		
// 포트폴리오 생성
	@GetMapping(value = "prj_mgr_step_read")
	public String stepRead(int project_id, Model model, HttpServletRequest request) {

		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		System.out.println("HijController prj_mgr_step_read Start");
		//-------------------------------------------------
		List<PrjMemList> listMember = hs.listMember(project_id);
		//-------------------------------------------------
		List<PrjStep> titleList = hs.titleList(project_id);
		//-------------------------------------------------
		PrjInfo prjInfo = hs.listStep(project_id);	
		//-------------------------------------------------
		
		model.addAttribute("listMember", listMember);
		model.addAttribute("titleList", titleList);
		model.addAttribute("prjInfo", prjInfo);
		model.addAttribute("userInfoDTO", userInfoDTO);

		return "/project/manager/prj_mgr_step_read";
	}

//--------------------------------------------------------------------------------------
	@PostMapping(value = "search_all")
	@ResponseBody
	public List<HijSearchResponseDto> searchAll(@RequestBody HijSearchRequestDto hijSearchRequestDto, Model model){
		System.out.println("HijController searchAll Start");
		
		//HijSearchRequestDto hijSearchRequestDto = new HijSearchRequestDto();
		//hijSearchRequestDto.setKeyword(keyword);
		System.out.println("keyword : " + hijSearchRequestDto.getKeyword());
		
		// 검색한 결과를 hijSearchResponseDto List로 넣어줌
		List<HijSearchResponseDto> hijSearchResponseDto = hs.searchAll(hijSearchRequestDto);
		System.out.println("검색 결과 수 : " +hijSearchResponseDto.size() );
		System.out.println("HijController searchAll END");
		return hijSearchResponseDto;
		
	}
		
}
