package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.LjhResponse;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.ljhSer.LjhService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LjhController {
	
	private final LjhService ljhs;
	
//-----------------------------------------------------------------------------------------------
// 프로젝트 캘린더
//-----------------------------------------------------------------------------------------------
   @RequestMapping(value = "prj_calendar")
   public String prjCalendar(Model model,  HttpServletRequest request) {
      System.out.println("LjhController prjCalendar");

      // user 정보 세션에 저장해오기
      System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
      UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

      // 세션에 저장된 project_id
      int project_id = userInfoDTO.getProject_id();
      
      PrjInfo prjInfo = new PrjInfo();
      //------------------------------------------------
      prjInfo = ljhs.getProject(project_id);            // 프로젝트 전체 기간 조회
      //------------------------------------------------
      
      List<Meeting> meetingDateList = new ArrayList<Meeting>();
      //---------------------------------------------------------------
      meetingDateList = ljhs.getMeetingList(project_id);   // 전체 회의 리스트 조회
      //---------------------------------------------------------------
      // java.sql.Date에서 Calendar로 변환
      Calendar c = Calendar.getInstance();
      c.setTime(prjInfo.getProject_enddate());

      // Calendar에 1일을 더함
      c.add(Calendar.DATE, 1);

      // Calendar에서 java.sql.Date로 다시 변환
      java.sql.Date newEndDate = new java.sql.Date(c.getTimeInMillis());

      prjInfo.setProject_enddate(newEndDate);
      
      model.addAttribute("prj", prjInfo);
      model.addAttribute("meetingDateList", meetingDateList);
      
      return "project/prj_calendar";
   }

//-----------------------------------------------------------------------------------------------
// 회의록 캘린더
//-----------------------------------------------------------------------------------------------
	// 회의록 캘린더 페이지 이동
	@RequestMapping(value = "prj_meeting_calendar")
	public String meetingCalendar(Model model, HttpServletRequest request) {
		System.out.println("LjhController meetingCalendar");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		int project_id = userInfoDTO.getProject_id();
		
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();
		//--------------------------------------------------------
		prjMemList = ljhs.getPrjMember(project_id);				// 프로젝트 팀원 목록
		//--------------------------------------------------------
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		//-----------------------------------------------------------------
		meetingList = ljhs.getMeetingReportList(project_id);	// meeting_status = 2, 3 (회의록만, 일정+회의록)
		//-----------------------------------------------------------------
		
		List<Meeting> meetingDateList = new ArrayList<Meeting>();
		//-----------------------------------------------------------------
		meetingDateList = ljhs.getMeetingDate(project_id);		// meeting_status = 1 (회의일정만)
		//-----------------------------------------------------------------
		
		model.addAttribute("prjMemList", prjMemList);				// 프로젝트 팀원 목록
		model.addAttribute("meetingDateList", meetingDateList);		// meeting_status = 1 (회의일정만)
		model.addAttribute("meetingList", meetingList);				// meeting_status = 2, 3 (회의록만, 일정+회의록)
		model.addAttribute("project_id", project_id);
		
		return "project/meeting/prj_meeting_calendar";
	}
	
	// 회의록 목록 (사용 X)
//	@RequestMapping(value = "prj_meeting_report_list")
//	public String meetingList(int project_id, Model model) {
//		System.out.println("LjhController meetingList");
//		
//		List<Meeting> meetingList = new ArrayList<Meeting>();
//		meetingList = ljhs.getMeetingList(project_id);
//		
//		model.addAttribute("meetingList", meetingList);
//		model.addAttribute("project_id", project_id);
//		
//		return "project/meeting/prj_meeting_report_list";
//	}
	
	// 회의록 목록 (ajax)
	@ResponseBody
	@RequestMapping(value = "prj_meeting_report_list_ajax")
	public LjhResponse meetingList_ajax(Meeting meeting, String currentPage, Model model) {
		System.out.println("LjhController meetingList_ajax");
		
		LjhResponse ljhResponse = new LjhResponse();		// ajax 조회용
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		//------------------------------------------------------------------------
		meetingList = ljhs.getMeetingReportList(meeting.getProject_id());	// 회의록 목록 조회 (meeting_status = 2,3 만 해당)
		//------------------------------------------------------------------------
		
		int total = meetingList.size();
		
		Paging paging = new Paging(total, currentPage, 7);			// 회의록 7개씩 페이징 작업
		meeting.setStart(paging.getStart());
		meeting.setEnd(paging.getEnd());
		
		//---------------------------------------------------------------
		List<Meeting> mtList = ljhs.getMtRpListPage(meeting);		// 회의록 페이징 작업용
		//---------------------------------------------------------------
		
		ljhResponse.setFirList(mtList);
		ljhResponse.setObj(paging);
		
		return ljhResponse;
	}
	
	// 회의록 조회 (상세 페이지)
	@RequestMapping(value = "prj_meeting_report_read")
	public String meetingRead(Meeting meeting, Model model, HttpServletRequest request) {
		System.out.println("LjhController meetingRead");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();
		meeting.setUser_id(loginUserId);
		
		List<Meeting> meetingRead = new ArrayList<Meeting>();
		//---------------------------------------------------------------------
		meetingRead = ljhs.getMeetingRead(meeting.getMeeting_id());		// 선택한 회의록의 정보와 회의 참석자 정보까지 함께 조회
		//---------------------------------------------------------------------
		
		model.addAttribute("meeting", meetingRead);
		model.addAttribute("project_id", meeting.getProject_id());
		model.addAttribute("meeting_id", meeting.getMeeting_id());
		
		return "project/meeting/prj_meeting_report_read";
	}
	
	// 회의록 수정 페이지 이동
	@RequestMapping(value = "prj_meeting_report_update")
	public String meetingUpdate(int meeting_id, int project_id, Model model) {
		System.out.println("LjhController meetingUpdate");
		 
		List<Meeting> meetingRead = new ArrayList<Meeting>();
		//----------------------------------------------------------
		meetingRead = ljhs.getMeetingRead(meeting_id);	// 선택한 회의록의 정보와 회의 참석자 정보까지 함께 조회
		//----------------------------------------------------------
		
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();
		//----------------------------------------------------------
		prjMemList = ljhs.getPrjMember(project_id);		// 프로젝트 전체 팀원 조회
		//----------------------------------------------------------
		
		model.addAttribute("meeting", meetingRead);
		model.addAttribute("prjMemList", prjMemList);
		model.addAttribute("meeting_id", meeting_id);
		model.addAttribute("project_id", project_id);
		
		return "project/meeting/prj_meeting_report_update";
	}
	
	//---------------------------------------------------------------------------------------------------------
	// 파일 업로드 메소드
	private String uploadFile(String originalFilename, byte[] bytes, String uploadPath) throws IOException {
		// Universally Unique Identity (UUID)
		UUID uid = UUID.randomUUID();
		System.out.println("uploadPath : " + uploadPath);			// 파일 저장되는 주소

		// Directory 생성
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalFilename;	// 저장되는 파일명
		log.info("savedName : " + savedName);
		File target = new File(uploadPath, savedName);

		FileCopyUtils.copy(bytes, target);	// org.springframework.util.FileCopyUtils
		
		return savedName;
	}
	//---------------------------------------------------------------------------------------------------------
	
	// 회의록 수정 페이지에서 수정 시
	@PostMapping(value = "prj_meeting_report_update_2")
	public String meetingReportUpdate(Meeting meeting, Model model, HttpServletRequest request, 
						@RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController meetingReportUpdate");
		
		// 첨부파일 업로드
		if (!file1.isEmpty()) {
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(file1.getOriginalFilename());
			meeting.setAttach_path(savedName);
		}
		
		model.addAttribute("meeting", meeting);
		
		//---------------------------------------------------------
		// 회의록 수정 = MEETING TBL update + MEETING_MEMBER TBL delete + MEETING_MEMBER TBL (insert * MemberCount)
		int result = ljhs.meetingReportUpdate(meeting);
		//---------------------------------------------------------
		
		int meeting_id = meeting.getMeeting_id();
		model.addAttribute("meeting_id", meeting_id);
		
		return "redirect:prj_meeting_report_read?meeting_id="+meeting.getMeeting_id()+"&project_id="+meeting.getProject_id();
	}
	
	// 회의록 삭제
	@ResponseBody
	@RequestMapping(value = "prj_meeting_report_delete")
	public int prjMeetingReportDelete(Meeting meeting, Model model, HttpServletRequest request) {
		log.info("request : {}", request.getRequestURI());
		
		System.out.println("LjhController prjMeetingReportDelete Start");

		int deleteResult = 0;
		
		//-------------------------------------------------------------
		// 회의록 삭제 = MEETING_MEMBER TBL delete + MEETING TBL delete
		deleteResult = ljhs.deleteMeetingReport(meeting);
		//-------------------------------------------------------------
		
		return deleteResult;
	}
	
	// 회의 등록
	@PostMapping(value = "prj_meeting_date_write")
	public String prjMeetingDateWrite(Meeting meeting, Model model, HttpServletRequest request, 
						@RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController prjMeetingDateWrite Start");

		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();		// 세션에 저장된 user_id
		meeting.setUser_id(loginUserId);
		
		// 첨부파일 업로드
		if (!file1.isEmpty()) {
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(file1.getOriginalFilename());
			meeting.setAttach_path(savedName);
		}
		
		System.out.println("meeting -> " + meeting);
		System.out.println("meetuser_id -> : " + meeting.getMeetuser_id());
		
		int result = 0; 

		if (meeting.getMeeting_status() == 1) {
			//---------------------------------------------------
			result = ljhs.insertMeeting(meeting);	// 회의일정 등록 + 참석자 등록 (meeting_status = 1)
			//---------------------------------------------------
		}
		
		if (meeting.getMeeting_status() == 2) {
			//---------------------------------------------------
			result = ljhs.insertReport(meeting);	// 회의록 등록 + 참석자 등록 (meeting_status = 2)
			//---------------------------------------------------
		}
		
		return "redirect:prj_meeting_calendar";
	}
	
	// 회의일정 수정
	@ResponseBody
	@PostMapping(value = "/prj_meeting_date_update")
	public int prjMeetingDateUpdate(Meeting meeting, Model model, HttpServletRequest request, 
					@RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		
		System.out.println("LjhController prjMeetingDateUpdate Start");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();		// 세션에 저장된 user_id
		meeting.setUser_id(loginUserId);
		
		// 첨부파일 업로드
		if (file1 != null && !file1.isEmpty()) {
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(file1.getOriginalFilename());
			meeting.setAttach_path(savedName);
		}
		
		int updateResult = 0;
		
		//------------------------------------------------------------
		// 회의일정 수정 = MEETING TBL update + MEETING_MEMBER TBL delete + MEETING_MEMBER TBL (insert * MemberCount)
		updateResult = ljhs.updateMeetingDate(meeting);
		//------------------------------------------------------------
		
		return updateResult;
	}
	
	// 회의일정 클릭 시 정보 가져오기
	@ResponseBody
	@RequestMapping(value = "prj_meeting_date_select")
	public LjhResponse prjMeetingDateSelect(int meeting_id, int project_id, Model model) {
		System.out.println("LjhController prjMeetingDateSelect Start");
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();
		
		//------------------------------------------------------------
		meetingList = ljhs.getMeetingRead(meeting_id);	// 회의일정 리스트 조회
		//------------------------------------------------------------
		prjMemList = ljhs.getPrjMember(project_id);		// 프로젝트 팀원 조회
		//------------------------------------------------------------
		
		LjhResponse ljhResponse = new LjhResponse();	// ajax 반환용 모델 생성
		ljhResponse.setFirList(meetingList);
		ljhResponse.setSecList(prjMemList);
		
		return ljhResponse;
	}
	
	// 회의일정 -> 회의록 등록 (meeting_status = 1 -> 3)
	@RequestMapping(value = "prj_meeting_report_insert")
	public String prjMeetingReportInsert(Meeting meeting, Model model, HttpServletRequest request, 
					@RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController prjMeetingReportInsert Start");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();		// 세션에 저장된 user_id
		meeting.setUser_id(loginUserId);
		
		// 첨부파일 업로드
		if (!file1.isEmpty()) {
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(file1.getOriginalFilename());
			meeting.setAttach_path(savedName);
		}
		
		System.out.println("meeting -> " + meeting);
		
		System.out.println("meetuser_id -> : " + meeting.getMeetuser_id());
		
		//-------------------------------------------------------------
		int result = ljhs.insertMeetingReport(meeting);		// 회의록 등록 + 참석자 등록
		//-------------------------------------------------------------
		
		return "redirect:/prj_meeting_calendar";
	}
	

//-----------------------------------------------------------------------------------------------
// 알림
//-----------------------------------------------------------------------------------------------
	// 회의일정 알림
	@MessageMapping("/meet")
	@SendTo("/noti/meeting")
	public LjhResponse selMeetingList(HashMap<String, String> map) {
		System.out.println("LjhController selMeetingList Start");	
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(map.get("user_id"));
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		//-----------------------------------------------------
		meetingList = ljhs.getUserMeeting(map);		// 알림 - 접속한 회원 별 회의일정 조회
		//-----------------------------------------------------
		
		System.out.println("meetingList.size() -> " + meetingList.size());
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(meetingList);
		ljhResponse.setObj(userInfo);
		
		return ljhResponse;
	}
	
	// 게시판 답글 알림 (프로젝트 업무보고 + 질문 게시판 통합)
	@MessageMapping("/rep")
	@SendTo("/noti/boardRep")
	public LjhResponse getBoardRep(HashMap<String, String> map) {
		System.out.println("LjhController getBoardRep Start");
		
		List<PrjBdData> boardRep = new ArrayList<PrjBdData>();
		//-----------------------------------------------------
		boardRep = ljhs.getBoardRep(map);		// 프로젝트 업무보고 + 질문게시판에 작성한 내 글과 등록된 답글 함께 조회
		//-----------------------------------------------------
		
		System.out.println("boardRep.size() -> " + boardRep.size());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(map.get("user_id"));
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(boardRep);
		ljhResponse.setObj(userInfo);
		
		return ljhResponse;
	}
	
	// 게시판 댓글 알림 (공용게시판 + 프로젝트 업무보고 + 프로젝트 공지/자료 통합)
	@MessageMapping("/comt")
	@SendTo("/noti/boardComt")
	public LjhResponse getBoardComt(HashMap<String, String> map) {
		System.out.println("LjhController getBoardComt Start");
		
		List<PrjBdData> boardComt = new ArrayList<PrjBdData>();
		//-----------------------------------------------------
		boardComt = ljhs.getBoardComt(map);		// 공용게시판 + 프로젝트 업무보고 + 공지/자료 게시판에 작성한 내 글과 댓글 개수 조회
		//-----------------------------------------------------

		System.out.println("boardComt.size() -> " + boardComt.size());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(map.get("user_id"));
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(boardComt);
		ljhResponse.setObj(userInfo);
		
		return ljhResponse;
	}
	
	// 프로젝트 생성 승인 알림 - 팀장 계정만 해당
	@MessageMapping("/approve")
	@SendTo("/noti/prjapprove")
	public LjhResponse getPrjApprove(HashMap<String, String> map) {
		System.out.println("LjhController getPrjApprove Start");
		
		List<PrjInfo> prjApprove = new ArrayList<PrjInfo>();
		//-----------------------------------------------------
		prjApprove = ljhs.getPrjApprove(map);	// 프로젝트 생성 승인 상태, 알림 확인 여부, 삭제 여부를 비교해 조회
		//-----------------------------------------------------

		System.out.println("prjApprove.size() -> " + prjApprove.size());
		
		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(map.get("user_id"));
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(prjApprove);
		ljhResponse.setObj(userInfo);
		
		return ljhResponse;
	}
	
	// 프로젝트 생성 신청 - admin 계정만 해당
	@MessageMapping("/prj")
	@SendTo("/noti/newprj")
	public LjhResponse getNewPrj(HashMap<String, String> map) {
		System.out.println("LjhController getNewPrj Start");
		
		List<PrjInfo> newPrjList = new ArrayList<PrjInfo>();
		
		String login_user_auth = map.get("user_auth");
		System.out.println("LjhController getNewPrj login_user_auth -> " + login_user_auth);
		
		// 접속한 계정이 admin 권한이 있는 경우 실행
		if ("admin".equals(login_user_auth)) {
			//-----------------------------------------------------
			newPrjList = ljhs.getNewPrj(map);	// 미승인 상태인 프로젝트 신청 건수 조회
			//-----------------------------------------------------
			
			System.out.println("newPrjList.size() -> " + newPrjList.size());
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setUser_id(map.get("user_id"));
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(newPrjList);
		ljhResponse.setObj(userInfo);
		
		return ljhResponse;
	}
	
}