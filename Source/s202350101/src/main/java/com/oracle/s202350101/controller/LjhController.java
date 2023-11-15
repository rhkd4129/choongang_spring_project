package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	
	// 프로젝트 캘린더
	@RequestMapping(value = "prj_calendar")
	public String prjCalendar(Model model,  HttpServletRequest request) {
		System.out.println("LjhController prjCalendar");

		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

		// 세션에 저장된 project_id
		int project_id = userInfoDTO.getProject_id();
		
		PrjInfo prjInfo = new PrjInfo();
		prjInfo = ljhs.getProject(project_id);
		
		List<Meeting> meetingDateList = new ArrayList<Meeting>();
		meetingDateList = ljhs.getMeetingList(project_id);
		
		model.addAttribute("prj", prjInfo);
		model.addAttribute("meetingDateList", meetingDateList);
		
		return "project/prj_calendar";
	}
	
	// 회의록 캘린더
	@RequestMapping(value = "prj_meeting_calendar")
	public String meetingCalendar(Model model, HttpServletRequest request) {
		System.out.println("LjhController meetingCalendar");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		int project_id = userInfoDTO.getProject_id();
		
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();	// 프로젝트 팀원 목록
		prjMemList = ljhs.getPrjMember(project_id);
		
		List<Meeting> meetingList = new ArrayList<Meeting>();		// meeting_status = 2, 3 (회의록만, 일정+회의록)
		meetingList = ljhs.getMeetingReportList(project_id);
		
		List<Meeting> meetingDateList = new ArrayList<Meeting>();	// meeting_status = 1 (회의일정만)
		meetingDateList = ljhs.getMeetingDate(project_id);
		
		model.addAttribute("prjMemList", prjMemList);				// 프로젝트 팀원 목록
		model.addAttribute("meetingDateList", meetingDateList);		// meeting_status = 1 (회의일정만)
		model.addAttribute("meetingList", meetingList);				// meeting_status = 2, 3 (회의록만, 일정+회의록)
		model.addAttribute("project_id", project_id);
		
		return "project/meeting/prj_meeting_calendar";
	}
	
	// 회의록 목록
	@RequestMapping(value = "prj_meeting_report_list")
	public String meetingList(int project_id, Model model) {
		System.out.println("LjhController meetingList");
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		meetingList = ljhs.getMeetingList(project_id);
		
		model.addAttribute("meetingList", meetingList);
		model.addAttribute("project_id", project_id);
		
		return "project/meeting/prj_meeting_report_list";
	}
	
	// 회의록 목록	ajax
	@ResponseBody
	@RequestMapping(value = "prj_meeting_report_list_ajax")		//?project_id=~~~
	public List<Meeting> meetingList_ajax(int project_id, Model model) {
		
		System.out.println("LjhController meetingList_ajax");
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		meetingList = ljhs.getMeetingReportList(project_id);
		
		return meetingList;
	}
	
	// 회의록 조회
	@RequestMapping(value = "prj_meeting_report_read")
	public String meetingRead(Meeting meeting, Model model, HttpServletRequest request) {
		System.out.println("LjhController meetingRead");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();
		meeting.setUser_id(loginUserId);
		
		List<Meeting> meetingRead = new ArrayList<Meeting>();
		meetingRead = ljhs.getMeetingRead(meeting.getMeeting_id());
		
		model.addAttribute("meeting", meetingRead);
		model.addAttribute("project_id", meeting.getProject_id());
		model.addAttribute("meeting_id", meeting.getMeeting_id());
		
		return "project/meeting/prj_meeting_report_read";
	}
	
	// 회의록 수정
	@RequestMapping(value = "prj_meeting_report_update")
	public String meetingUpdate(int meeting_id, int project_id, Model model) {
		System.out.println("LjhController meetingUpdate");
		 
		List<Meeting> meetingRead = new ArrayList<Meeting>();		//미팅멤버
		meetingRead = ljhs.getMeetingRead(meeting_id);
		
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();		//	프로젝트전체멤버
		prjMemList = ljhs.getPrjMember(project_id);
		
		model.addAttribute("meeting", meetingRead);
		model.addAttribute("prjMemList", prjMemList);
		model.addAttribute("meeting_id", meeting_id);
		model.addAttribute("project_id", project_id);
		
		return "project/meeting/prj_meeting_report_update";
	}
	
	// 회의록 수정 2
	@PostMapping(value = "prj_meeting_report_update_2")
	public String meetingReportUpdate(Meeting meeting, Model model, HttpServletRequest request, @RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController meetingReportUpdate");
		
		if (!file1.isEmpty()) {
			// 첨부파일 업로드
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(savedName);
			meeting.setAttach_path(attach_path);
		}
		
		model.addAttribute("meeting", meeting);
		
		int result = ljhs.meetingReportUpdate(meeting);					//	update + delete + (insert * MemberCount)
		
		int meeting_id = meeting.getMeeting_id();
		model.addAttribute("meeting_id", meeting_id);
//		model.addAttribute("savedName", savedName);
		
		return "redirect:prj_meeting_report_read?meeting_id="+meeting.getMeeting_id()+"&project_id="+meeting.getProject_id();
	}
	
	// 회의일정 등록
	@PostMapping(value = "prj_meeting_date_write")
	public String prjMeetingDateWrite(Meeting meeting, Model model, HttpServletRequest request, 
						@RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController prjMeetingDateWrite Start");

		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();		// 세션에 저장된 user_id
		meeting.setUser_id(loginUserId);
		
		if (!file1.isEmpty()) {
			// 첨부파일 업로드
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(savedName);
			meeting.setAttach_path(attach_path);
		}
		
		System.out.println("meeting -> " + meeting);
		
		System.out.println("meetuser_id -> : " + meeting.getMeetuser_id());
		
		int result = 0; 

		// meeting_status = 1 등록
		if (meeting.getMeeting_status() == 1) {
			// 회의일정 등록 + 참석자 등록
			result = ljhs.insertMeeting(meeting);
		} 
		
		if (meeting.getMeeting_status() == 2) {
			// meeting_status = 2 등록
			result = ljhs.insertReport(meeting);
		}
		
		return "redirect:prj_meeting_calendar";
	}
	
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
	
	@ResponseBody
	@RequestMapping(value = "prj_meeting_report_delete")
	// 이상이 없으면 200 리턴
	public ResponseEntity prjMeetingReportDelete(Meeting meeting, Model model, HttpServletRequest request) {
		log.info("request : {}", request.getRequestURI());
		
		System.out.println("LjhController prjMeetingReportDelete Start");

		int deleteResult = 0;
		
		deleteResult = ljhs.deleteMeetingReport(meeting);
		
		return ResponseEntity.ok(deleteResult);
	}
	
	// 회의일정 클릭 시 정보 가져오기
	@ResponseBody
	@RequestMapping(value = "prj_meeting_date_select")
	public LjhResponse prjMeetingDateSelect(int meeting_id, int project_id, Model model) {
		System.out.println("LjhController prjMeetingDateSelect Start");
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		List<PrjMemList> prjMemList = new ArrayList<PrjMemList>();
		
		meetingList = ljhs.getMeetingRead(meeting_id);
		prjMemList = ljhs.getPrjMember(project_id);
		
		LjhResponse ljhResponse = new LjhResponse();
		ljhResponse.setFirList(meetingList);
		ljhResponse.setSecList(prjMemList);
		
		return ljhResponse;
	}
	
	// 회의일정 -> 회의록 등록 (meeting_status = 1 -> 3)
	@RequestMapping(value = "prj_meeting_report_insert")
	public String prjMeetingReportInsert(Meeting meeting, Model model, HttpServletRequest request, @RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("LjhController prjMeetingReportInsert Start");
		
		// user 정보 세션에 저장해오기
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		String loginUserId = userInfoDTO.getUser_id();		// 세션에 저장된 user_id
		meeting.setUser_id(loginUserId);
		
		if (!file1.isEmpty()) {
			// 첨부파일 업로드
			String attach_path = "upload";
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			meeting.setAttach_name(savedName);
			meeting.setAttach_path(attach_path);
		}
		
		System.out.println("meeting -> " + meeting);
		
		System.out.println("meetuser_id -> : " + meeting.getMeetuser_id());
		
		// 회의록 등록 + 참석자 등록
		int result = ljhs.insertMeetingReport(meeting);

		return "redirect:/prj_meeting_calendar";
	}
	
	// 회의일정 알림
	@MessageMapping("/meet")
	@SendTo("/noti/meeting")
	public List<Meeting> selMeetingList(HashMap<String, String> map) {
		System.out.println("LjhController selMeetingList Start");	
		
 		int loginUserPrj = Integer.parseInt(map.get("project_id"));		// 세션에 저장된 userinfo의 project_id
 		String loginUserId = map.get("user_id");
		
		System.out.println("loginUserPrj : " + loginUserPrj);
		System.out.println("loginUserId : " + loginUserId);
		
		List<Meeting> meetingList = new ArrayList<Meeting>();
		
		// 알림 - 접속한 회원 별 회의일정 select
		meetingList = ljhs.getUserMeeting(map);
		
		// meetingList = ljhs.getMeetingList(loginUserPrj);
		System.out.println("meetingList.size() -> " + meetingList.size());
		
		return meetingList;
	}
	
	// 게시판 답글 알림 (프로젝트 업무보고 + 질문 게시판 통합)
	@MessageMapping("/rep")
	@SendTo("/noti/boardRep")
	public List<PrjBdData> getBoardRep(HashMap<String, String> map) {
		System.out.println("LjhController getBoardRep Start");
		
		List<PrjBdData> boardRep = new ArrayList<PrjBdData>();
		boardRep = ljhs.getBoardRep(map);
		
		System.out.println("boardRep.size() -> " + boardRep.size());
		
		return boardRep;
	}
	
	// 게시판 댓글 알림 (공용게시판 + 프로젝트 업무보고 + 프로젝트 공지/자료 통합)
	@MessageMapping("/comt")
	@SendTo("/noti/boardComt")
	public List<PrjBdData> getBoardComt(HashMap<String, String> map) {
		System.out.println("LjhController getBoardComt Start");
		
		List<PrjBdData> boardComt = new ArrayList<PrjBdData>();
		boardComt = ljhs.getBoardComt(map);
		
		System.out.println("boardComt.size() -> " + boardComt.size());
		
		return boardComt;
	}
	
	// 프로젝트 생성 승인 알림 (팀장)
	@MessageMapping("/prj")
	@SendTo("/noti/newprj")
	public List<PrjInfo> getPrjApprove(HashMap<String, String> map) {
		System.out.println("LjhController getPrjApprove Start");
		
		List<PrjInfo> prjApprove = new ArrayList<PrjInfo>();
		prjApprove = ljhs.getPrjApprove(map);
		
		System.out.println("prjApprove.size() -> " + prjApprove.size());
		
		return prjApprove;
	}
	
	
	
}