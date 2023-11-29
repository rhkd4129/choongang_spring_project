package com.oracle.s202350101.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
	public String admin_approval(PrjInfo prjInfo, String currentPage,  Model model, HttpServletRequest request) {
		System.out.println("HijController approval Start");
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
		if(createResult > 0) return "/main"; //createResult 성공시 main
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
		
		// project_id =0 에 prj_step 기본설정을 넣어놔서 조건 걸어줌
		if(project_id > 0) {
			//-------------------------------------------------
			List<PrjMemList> listMember = hs.listMember(project_id);	// 멤버조회
			//-------------------------------------------------
			PrjInfo prjInfo = hs.listStep(project_id);				// 초기설정조회
			//-------------------------------------------------
			if(userInfoDTO.getUser_id().equals(prjInfo.getProject_manager_id())){
				System.out.println("승인신청 알람 해제");
				
				//-------------------------------------------------
				int updateAlarmCount = hs.updateAlarmCount(prjInfo); //알람설정
				//-------------------------------------------------
			}
			
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
		// prj_mgr_step_list의 prj_start(), prj_end() AJAX Call
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
	public String prjMgrReqEdit(PrjInfo prjInfo, Model model, MultipartFile file1, HttpServletRequest request) {
		
		// UserInfo에서 project_id 들고 오기
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    
	    int project_id = prjInfo.getProject_id();
	    
		System.out.println("HijController prjMgrReqEdit Start");
		//------------------------------------------------------------------
		List<PrjMemList> listMember = hs.listMember(project_id);		// 프로젝트 멤버 리스트
		//------------------------------------------------------------------
		prjInfo = hs.listStep(project_id); 										// 프로젝트 조회 
		//------------------------------------------------------------------
		List<UserInfo> listName = hs.listName(prjInfo.getProject_manager_id());	// 팀장ID(project_manager_id)랑 같은 class_id를 가진학생 리스트
		//------------------------------------------------------------------
		
		model.addAttribute("listMember", listMember);
		model.addAttribute("listName", listName);
		model.addAttribute("prjInfo", prjInfo);
		model.addAttribute("userInfoDTO", userInfoDTO);
		return "/project/manager/prj_mgr_req_edit";
	}
	
//--------------------------------------------------------------------------------------		
	// 프로젝트 정보 수정 수행
	@PostMapping(value = "req_edit")
	public String reqEdit(PrjInfo prjInfo, HttpServletRequest request, Model model, MultipartFile file1) throws Exception {
		System.out.println("HijController req_edit Start");
		System.out.println("★prjInfo.getMember_user_id() ---> : " + prjInfo.getMember_user_id());
		
		// 파일 업로드 경로 설정
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		
		// 이전 설정된 첨부파일 삭제 플래그 설정 및 그 경로 가져오기
		String before_attach_delete_flag = prjInfo.getAttach_delete_flag();
		String before_attach_path = prjInfo.getAttach_path();
		
		System.out.println("before_attach_delete_flag->"+before_attach_delete_flag);
		System.out.println("before_attach_path->"+before_attach_path);
		
		// 이전 첨부파일을 삭제해야 되는 경우와 파일 경로가 비어있지 않은 경우 빈문자열로 삭제처리
		if(before_attach_delete_flag.equals("D") && !before_attach_path.isEmpty()) {
			prjInfo.setAttach_name("");
			prjInfo.setAttach_path("");
		}
		System.out.println("file1.getSize() : " +file1.getSize());
		
		// 전송된 파일이 있는 경우
		if(file1.getSize()>0) {
			
			System.out.println("FileUpload START...");
			log.info("uploadPath: " 	+ uploadPath);
			log.info("originalName:"	+ file1.getOriginalFilename());
			log.info("size: " 			+ file1.getSize());
			log.info("contentType: " 	+ file1.getContentType());
			
			System.out.println("file1.getSize() 안:" +file1.getSize());
			
			// 파일을 업로드하고 저장된 파일명을 받아옴
			//----------------------------------------------------------------------------------------
			String savedName = hijUploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			//----------------------------------------------------------------------------------------
			log.info("savedName: " 	+ savedName);
			prjInfo.setAttach_name(file1.getOriginalFilename());
			prjInfo.setAttach_path(savedName);
	
		}
		//-------------------------------------------------
		int editResult = hs.reqEdit(prjInfo); //프로젝트 정보 업데이트
		//-------------------------------------------------
		
		if(editResult > 0) {
			if(before_attach_delete_flag.equals("D")&& !before_attach_path.isEmpty()) {
				//수정이 정상수행 되었을때 기존파일 삭제처리
				String deleteFile = uploadPath + before_attach_path;
				
				int delResult = hijUpFileDelete(deleteFile); //이전파일 삭제 작업
				log.info("deleteFile: " + deleteFile);
			}
			model.addAttribute("prjInfo", prjInfo);
		}
		return "redirect:/prj_mgr_step_list";
	}
//--------------------------------------------------------------------------------------		
	//파일추가
	//내부 메소드 private으로
		private String hijUploadFile(String originalName, byte[] fileData, String uploadPath) throws IOException {
			
			// Universally Unique Identified (UUID) 유일한 식별자
			UUID uid = UUID.randomUUID();
			
			// requestPath = requestPath + "/resources/image";
			System.out.println("uploadPath->"+uploadPath);
			
			//Directory생성
			File fileDirectory = new File(uploadPath);
			
			if(!fileDirectory.exists()) {
				//신규 폴더(Directory) 생성 : 폴더가 없으면 새로 자동 생성해줌.
				fileDirectory.mkdirs();
				System.out.println("업로드용 폴더 생성: "+uploadPath);
			}
			
			// 저장될 파일명 설정
			String savedName = uid.toString() + "_" + originalName;
			log.info("savedName: "+savedName);
			// 실제 파일의 객체 (저장될경로 + 파일명)
			File target = new File(uploadPath, savedName);
						
			//실제 업로드 순간
			FileCopyUtils.copy(fileData, target); //org.springframework.util.FileCopyUtils
				
			return savedName;
		}
//--------------------------------------------------------------------------------------
		//파일삭제
		@RequestMapping(value="hijUploadFileDelete", method = RequestMethod.GET)
		public String hijUploadFileDelete(String attach_path, HttpServletRequest request, Model model) 
				throws Exception {
			
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/"); //업로드된 파일이 저장된 경로 가져옴
			String deleteFile = uploadPath + attach_path; // 삭제할 파일의 전체 경로 설정
			
			log.info("deleteFile: " + deleteFile);
			System.out.println("uploadFileDelete GET Start");
			
			int delResult = hijUpFileDelete(deleteFile); //파일 삭제
			
			log.info("deleteFile result-> "+delResult);
			
			// 삭제된 파일 결과를 model에 저장
			model.addAttribute("deleteFile", deleteFile);
			model.addAttribute("delResult", delResult);
			
			return "uploadResult";
			
		}
//--------------------------------------------------------------------------------------	
		//파일삭제 로직
		private int hijUpFileDelete(String deleteFileName) throws Exception {
			int result = 0;
			
			log.info("upFileDelete result-> " + deleteFileName);
			
			File file = new File(deleteFileName);
			
			// 파일 존재하는경우
			if(file.exists()) {
				// 파일 삭제 
				if(file.delete()) {
					System.out.println("파일삭제 성공");
					result = 1;
				}
				else {
					System.out.println("파일삭제 실패");
					result = 0;
				}
			}
			// 파일 존재하지 않을 때 
			else {
				System.out.println("삭제할 파일이 존재하지 않습니다.");
				result = -1;
			}
			return result;
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
		System.out.println("****keyword : " + hijSearchRequestDto.getKeyword());
		System.out.println("****project_id : " + hijSearchRequestDto.getProject_id());
		
		// 검색한 결과를 hijSearchResponseDto List로 넣어줌
		//----------------------------------------------------------------------------------
		List<HijSearchResponseDto> hijSearchResponseDto = hs.searchAll(hijSearchRequestDto);
		//----------------------------------------------------------------------------------
		System.out.println("검색 결과 수 : " +hijSearchResponseDto.size() );
		System.out.println("HijController searchAll END");
		return hijSearchResponseDto;
		
	}
		
}
