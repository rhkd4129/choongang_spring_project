package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.service.cyjSer.CyjService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
@RequiredArgsConstructor
public class CyjController {
	
	private final CyjService cs;
	
// ------------------------- 공지 게시판 ------------------------------------
	
	// 전체 공지사항 리스트  
	   @RequestMapping(value = "board_notify")
	   public String notifyList(HttpServletRequest request, BdFree bdFree, String currentPage, Model model) {
	      System.out.println("CyjController notify_list Start--------------------------");
	      
	      // userInfo 정보 get
	      System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo")); // userInfo 정보 갖고옴
	      UserInfo userInfoDTO  = (UserInfo) request.getSession().getAttribute("userInfo");          // 정보를 UserInfo model의 인스턴스에 담음
	      
	      // loginId : Id가 admin인 것만 공지사항 작성할 수 있게 하기 위함 
	      String loginId = userInfoDTO.getUser_id();
	      
	      // 접속자가 admin인지 비교 --> 같으면 1, 다르면 0
	      int result = 0;
	      if (loginId.equals("admin")){
	         result = 1;
	         System.out.println("admin 계정으로 접속 O");
	      } else {
	         result = 0;
	         System.out.println("admin 계정으로 접속 X");
	      }
	      model.addAttribute("result", result);
	      
	      // 추천수 가장 높은 row 3개
	      //--------------------------------------------------------------
	      List<BdFree> goodListRow = cs.goodRow(bdFree);
	      //--------------------------------------------------------------
	      System.out.println("CyjController goodListRow-> " + goodListRow);
	      model.addAttribute("goodListRow", goodListRow);
	      
	      // 총 갯수
	      //--------------------------------------------------------------
	      int totalBdFree = cs.totalBdFree();
	      //--------------------------------------------------------------
	      model.addAttribute("totalBdFree", totalBdFree);
	      
	      // paging 작업
	      Paging page = new Paging(totalBdFree, currentPage);
	      bdFree.setStart(page.getStart());  // 시작시 1
	      bdFree.setEnd(page.getEnd());      // 시작시 10
	      model.addAttribute("page", page);
	      
	      // 전체 리스트
	      //--------------------------------------------------------------
	      List<BdFree> bdFreeList = cs.listBdFree(bdFree);
	      //--------------------------------------------------------------
	      System.out.println("CyjController board_notify bdFreeList.size()-> " + bdFreeList.size());
	      model.addAttribute("bdFreeList", bdFreeList);
	      
	      return "board/board_notify/board_notify_list";
	   }

// -----------------------------------------------------------------------	
	
	// 새 글 입력하기 위한 페이지 이동 
	@RequestMapping(value = "board_write_insert_form")
	public String board_write_insert_form(Model model) {
		System.out.println("CyjController board_write_insert_form GET Start--------------------------");
		
		BdFree bdFree = new BdFree();
		model.addAttribute("bdFree", bdFree);
		
		return "board/board_notify/board_notify_write_form";	
	}
	
	// 새 글 입력 
	@PostMapping(value =  "board_write_insert")														
	public String boardWrite(@Valid @ModelAttribute BdFree bdFree, BindingResult bindingResult
						   , @RequestParam(value = "file1", required = false) MultipartFile file1
						   , HttpServletRequest request, Model model) throws IOException {
		
		System.out.println("CyjController board_write_insert POST Start--------------------------");
		
		// validation
		if (bindingResult.hasErrors()) {
			System.out.println("CyjController board_write_insert hasErrors");
			return "board/board_notify/board_notify_write_form";
		}
		
		// file Upload
		String attach_path = "upload"; // 실제 파일이 저장되는 폴더명, uploadPath의 이름과 동일하게 해야 오류 X
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/"); // 저장 위치 지정 
		
		System.out.println("CyjController File Upload Post Start");
		
		log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
		log.info("size : "         + file1.getSize());					// 파일 사이즈
		log.info("contextType : "  + file1.getContentType());			// 파일 타입
		log.info("uploadPath : "   + uploadPath);						// 파일 저장되는 주소
		
		String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);  // 저장되는 파일명 
		log.info("saveName: " + saveName);
		
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo"); 
		
		// loginId : 새 글 작성에서는 접속자이면서 작성자 
		String loginId = userInfoDTO.getUser_id(); 						   // UserInfo에 있는 user_id 갖고 옴
		System.out.println(request.getSession().getAttribute("loginId"));  // loginId을 session에 저장 
		bdFree.setUser_id(loginId);										   // xml의 parameterType="BdFree" 에 담기 위해 
		
		if(!file1.isEmpty()) {
			log.info("파일이 존재합니다  ");
			bdFree.setAttach_path(saveName);
			bdFree.setAttach_name(file1.getOriginalFilename());
		}
		
		// 새 글 입력 
		//--------------------------------------------------------------
		int insertResult = cs.insertBdFree(bdFree);
		//--------------------------------------------------------------
		System.out.println("CyjController board_notify insertResult-> " + insertResult);
		model.addAttribute("insertResult", insertResult);

		return "redirect:/board_notify"; 
	}
	
	// file upload method
	private String uploadFile(String originalName, byte[] bytes, String uploadPath) throws IOException {
		// universally unique identifier (UUID)
		UUID uid = UUID.randomUUID();
		System.out.println("uploadPath-> " + uploadPath);
		
		// 신규 폴더(Directory) 생성
		File fileDirectory = new File(uploadPath);
		if(!fileDirectory.exists()) {
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName;
		System.out.println("savedName: " + savedName);
		File target = new File(uploadPath, savedName);
		FileCopyUtils.copy(bytes, target);  
		
		return savedName;
	}

// -----------------------------------------------------------------------	

	// 전체 공지사항 상세 페이지  
	@GetMapping(value = "board_content")
	public String boardContent(HttpServletRequest request, int doc_no, Model model) {
		System.out.println("CyjController board_content Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");    
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		
		// 상세 내역 
		//--------------------------------------------------------------
		BdFree bdFreeContent = cs.bdFreeContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController board_content boardContent-> " + bdFreeContent);
		System.out.println(loginId);
		System.out.println(bdFreeContent.getUser_id());
		model.addAttribute("content", bdFreeContent);
		
		// 접속자랑 작성자 비교 --> 같으면 1, 다르면 0
		int result;
		if (loginId.equals(bdFreeContent.getUser_id())) {
			result = 1;
			System.out.println("같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 조회수 증가
		//--------------------------------------------------------------
		int bdCount = cs.bdCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController board_content bdCount-> " + bdCount);
		model.addAttribute("bdCount", bdCount);
		
		return "board/board_notify/board_notify_content";
	}
					
// -----------------------------------------------------------------------	
	
	// 전체 공지사항_수정하기 위한 상세 정보 get
	@GetMapping(value = "board_update")
	public String boardUpdate(int doc_no, Model model) {
		System.out.println("CyjController board_update Start--------------------------");
		
		// 상세 정보 get
		//--------------------------------------------------------------
		BdFree free = cs.bdFreeContent(doc_no); 
		//--------------------------------------------------------------
		System.out.println("CyjController board_content free-> " + free);
		model.addAttribute("free", free);
		
		return "board/board_notify/board_notify_update";
	}
	
	// 수정
	@PostMapping(value = "board_update2")
	public String boardUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjController board_update2 Start--------------------------");
		
		//--------------------------------------------------------------
		int bdBoardUpdate = cs.bdFreeUpdate2(bdFree);
		//--------------------------------------------------------------
		System.out.println("CyjController bdBoardUpdate-> " + bdBoardUpdate);
		model.addAttribute("bdBoardUpdate", bdBoardUpdate);
		
		redirectAttributes.addAttribute("doc_no", bdFree.getDoc_no());
		
		return "redirect:/board_content?doc_no={doc_no}";  
	}
	
// ------------------------------------------------------------------------
	
	// 전체 공지사항_삭제 
	@ResponseBody
	@RequestMapping(value = "delete")
	public int ajaxDelete(int doc_no) {
		System.out.println("CyjController ajaxDelete Start--------------------------");
		
		//--------------------------------------------------------------
		int ajaxDelete = cs.boardDelete(doc_no);
		//--------------------------------------------------------------
		System.out.println("ajaxDelete-> " + ajaxDelete);
		 
		return ajaxDelete; 
	}
	
// ------------------------------------------------------------------------	
	
	// 전체 공지사항_추천
	@ResponseBody
	@RequestMapping(value = "ajaxGoodCount")
	public String goodCount(HttpServletRequest request, int doc_no) {
		System.out.println("CyjController goodCount Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");    
		
		// loginId : 추천에서 접속자 
		String loginId = userInfoDTO.getUser_id();
		
		BdFreeGood bdFreeGood = new BdFreeGood();
		bdFreeGood.setDoc_no(doc_no);
		bdFreeGood.setUser_id(loginId);
		System.out.println(bdFreeGood.getDoc_no());
		System.out.println(bdFreeGood.getUser_id()); 
	
		// 1. 추천자 목록에 있는지 중복 체크
		//--------------------------------------------------------------
		int goodConfirm = cs.goodConfirm(bdFreeGood);
		//--------------------------------------------------------------
		System.out.println("CyjController goodConfirm-> " + goodConfirm);  
		
		String result = "";
		BdFree bdFree = new BdFree();
		bdFree.setDoc_no(doc_no);
		
		if (goodConfirm == 1) { 		// 중복 0
			result = "duplication";		           
		} else {                        // 중복 X
			//--2. 추천 insert_ 추천기록이 없으면 BdFreeGood 테이블에 추천 추가----
			int notifyGoodInsert = cs.notifyGoodInsert(bdFreeGood);  
			//--------------------------------------------------------------
			System.out.println("CyjController notifyGoodInsert-> " + notifyGoodInsert);
		
			if(notifyGoodInsert == 1) {
				//--3. 추천 update_ bd_free 테이블에 good_count를 업데이트 -----
				int notifyGoodUpdate = cs.notifyGoodUpdate(bdFreeGood);
				//--------------------------------------------------------------
				System.out.println("CyjController notifyGoodUpdate-> " + notifyGoodUpdate);
				//--4. 추천 select_ bd_free 테이블에서 추천수 select------
				int notifyGoodSelect = cs.notifyGoodSelect(bdFree);
				//--------------------------------------------------------------
				System.out.println("CyjController notifyGoodSelect-> " + notifyGoodSelect);
				result = String.valueOf(notifyGoodSelect);
			} else {
				result= "error";  	  // error
			}
		}
		return result;
	}
	
// ------------------------------------------------------------------------	
// ------------------------- 이벤트 게시판 ------------------------------------

	// 이벤트_전체 리스트
	@RequestMapping(value = "board_event")
	public String eventList(HttpServletRequest request, BdFree bdFree, String currentPage, Model model) {
		System.out.println("CyjController event Start--------------------------");
		 
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// 추천수 가장 높은 row 3개 
		//--------------------------------------------------------------
		List<BdFree> eventGood = cs.eventCount(bdFree);
		//--------------------------------------------------------------
		System.out.println("eventGood.size()-> " + eventGood.size());
		model.addAttribute("eventGood", eventGood);
		
		// 총 갯수
		//--------------------------------------------------------------
		int eventCount = cs.eventCount();
		//--------------------------------------------------------------
		System.out.println("CyjController eventCount-> " + eventCount);
		model.addAttribute("eventCount", eventCount);
		
		// paging 작업
		Paging page = new Paging(eventCount, currentPage);
		bdFree.setStart(page.getStart());  // 시작시 1
		bdFree.setEnd(page.getEnd());      // 시작시 10
		model.addAttribute("page", page);
		
		// 전체 리스트
		//--------------------------------------------------------------
		List<BdFree> eventList = cs.eventList(bdFree);
		//--------------------------------------------------------------
		System.out.println("CyjController eventList.size()-> " + eventList.size());
		model.addAttribute("eventList", eventList);;
		
		return "board/board_event/board_event_list";
	}
	
// ------------------------------------------------------------------------
	
	// 이벤트_상세 페이지 
	@GetMapping(value = "event_content")
	public String eventContent(HttpServletRequest request, String currentPage, int doc_no, Model model) {
		System.out.println("CyjController eventContent Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지의 접속자 loginId-> " + loginId);
		
		// 이벤트_상세
		//--------------------------------------------------------------
		BdFree eventContent = cs.eventContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventContent-> " + eventContent);
		model.addAttribute("eventContent", eventContent);
		
		// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
		if(userInfoDTO.getUser_id().equals(eventContent.getUser_id())) {
			System.out.println("글작성자가 자신글 조회시 댓글들 alarm_flag='Y'로 일괄 변경처리");
			//--------------------------------------------------------------
			int updateCommentAlarmCount = cs.cyUpdateCommentAlarmFlag(eventContent);
			//--------------------------------------------------------------
		}	
//		model.addAttribute("userInfoDTO", userInfoDTO); // 로그인사용자 정보
		
		// 게시글의 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(eventContent.getUser_id())) {
			result = 1;
			System.out.println("접속자와 작성자 같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 이벤트_조회수 
		//--------------------------------------------------------------
		int eventCount = cs.eventBdCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventCount-> " + eventCount);
		model.addAttribute("eventCount", eventCount);
		
		// 이벤트_해당 게시글에 대한 댓글 총 갯수(free도 같이 쓰임)
		//--------------------------------------------------------------
		int eventComtCount = cs.eventComtCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventComtCount-> " + eventComtCount);
		model.addAttribute("eventComtCount", eventComtCount);
		
		BdFreeComt bdFreeComt = new BdFreeComt();
		bdFreeComt.setDoc_no(doc_no);
		
		// 이벤트_댓글 paging 작업
		Paging page = new Paging(eventComtCount, currentPage);   
		bdFreeComt.setStart(page.getStart());
		bdFreeComt.setEnd(page.getEnd());
		model.addAttribute("page", page);
		
		// 이벤트_페이징 작업한 댓글리스트 -> doc_no가 아닌 객체가 들어가야 페이징 작업 가능 
		//--------------------------------------------------------------
		List<BdFreeComt> commentList = cs.eventComt(bdFreeComt);
		//--------------------------------------------------------------
		System.out.println("CyjController commentList.size()-> " + commentList.size());
		model.addAttribute("commentList", commentList);

		return "board/board_event/board_event_content";
	}
	
	// 이벤트_댓글 입력 (free 같이 사용)
	@PostMapping(value = "comtInsert")
	public String eventComtInsert(HttpServletRequest request, BdFreeComt bdFreeComt) {
		System.out.println("CyjController comtInsert Start--------------------------");	
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 댓글 입력 작성자
		String loginId = userInfoDTO.getUser_id();
		System.out.println("댓글 입력 작성자 loginId-> " + loginId);
		bdFreeComt.setUser_id(loginId);
		
		// 댓글 입력 (free도 사용)
		//--------------------------------------------------------------
		int comt = cs.comtInsert(bdFreeComt);
		//--------------------------------------------------------------
		System.out.println("CyjController comt-> " + comt);  
		
		return "redirect:/event_content?doc_no="+bdFreeComt.getDoc_no();
	}
	
// ------------------------------------------------------------------------	
	
	// 이벤트_댓글 삭제
	@ResponseBody
	@PostMapping(value = "event_comt_delete")
	public int eventComtDelete(int doc_no, int comment_doc_no) {
		System.out.println("CyjController event_comt_delete Start----------------------");
		
		BdFreeComt bdFreeComt = new BdFreeComt();
		bdFreeComt.setDoc_no(doc_no);
		bdFreeComt.setComment_doc_no(comment_doc_no);
		
		// free와 같이 쓰임 (mapper id="cyFreeComtDelete")
		//--------------------------------------------------------------
		int comtDelete = cs.freeComtDelete(bdFreeComt);  
		//--------------------------------------------------------------
		System.out.println("CyjController comtDelete-> " + comtDelete);
		
		return comtDelete;
	}

// ------------------------------------------------------------------------	
	
	// 이벤트_추천
	@ResponseBody
	@RequestMapping(value = "ajaxEventGoodCount")
	public String eventGoodCount(HttpServletRequest request, int doc_no) {
		System.out.println("CyjController eventGoodCount Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 추천에서 접속자
		String loginId = userInfoDTO.getUser_id();
		
		BdFreeGood bdFreeGood = new BdFreeGood();
		bdFreeGood.setDoc_no(doc_no);
		bdFreeGood.setUser_id(loginId);
		System.out.println(bdFreeGood.getDoc_no());
		System.out.println(bdFreeGood.getUser_id());
		
		// 공지와 같은 mapper
		// 1. 추천자 목록에 있는지 중복 체크 
		//--------------------------------------------------------------
		int eventGoodConfirm = cs.goodConfirm(bdFreeGood);
		//--------------------------------------------------------------
		System.out.println("CyjController eventGoodConfirm-> " + eventGoodConfirm);
		
		String result = "";
		BdFree bdFree = new BdFree();
		bdFree.setDoc_no(doc_no);
		
		if (eventGoodConfirm == 1) {   // 중복 O
			result = "duplication";
		} else {					   // 중복 X
			// 공지와 같은 mapper
			// 2. 추천 insert----------------------------------------------
			int eventGoodInsert = cs.notifyGoodInsert(bdFreeGood);
			//--------------------------------------------------------------
			System.out.println("CyjController eventGoodInsert-> " + eventGoodInsert);
			
			if (eventGoodInsert == 1) {
				// 3. 추천 update----------------------------------------------
				int eventGoodUpdate = cs.notifyGoodUpdate(bdFreeGood);
				//--------------------------------------------------------------
				System.out.println("CyjController eventGoodUpdate-> " + eventGoodUpdate);
				// 4. 추천 select----------------------------------------------
				int eventGoodSelect = cs.notifyGoodSelect(bdFree);
				//--------------------------------------------------------------
				System.out.println("CyjController eventGoodSelect-> " + eventGoodSelect);
				result = String.valueOf(eventGoodSelect);
			} else {
				result = "error";       // error
			}
		}
		return result;  
	}
	
// ------------------------------------------------------------------------	

	// 이벤트_새 글 입력하기 위한 페이지 이동 
	@RequestMapping(value = "event_insert_form")
	public String eventInsertForm(Model model) {
		System.out.println("CyjController eventInsertForm Start--------------------------");
		
		BdFree bdFree = new BdFree();
		model.addAttribute("bdFree", bdFree);
		
		return "board/board_event/board_event_insert";
	}
	
	// 새 글 입력
	@PostMapping(value = "event_insert")
	public String eventInsert(@Valid @ModelAttribute BdFree bdFree, BindingResult bindingResult
							 ,@RequestParam(value = "file1", required = false) MultipartFile file1
							 ,HttpServletRequest request, Model model) throws IOException {
		
		System.out.println("CyjController eventInsert Start--------------------------");
		
		// validation
		if (bindingResult.hasErrors()) {
			System.out.println("CyjController event_insert hasErrors");
			return "board/board_event/board_event_insert";
		}
		
		// file Upload
		String attach_path = "upload"; // 실제 파일이 저장되는 폴더명, uploadPath의 이름과 동일하게 해야 오류 X
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/"); // 저장 위치 지정 
		
		System.out.println("CyjController File Upload Post Start");
		
		log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
		log.info("size : "         + file1.getSize());					// 파일 사이즈
		log.info("contextType : "  + file1.getContentType());			// 파일 타입
		log.info("uploadPath : "   + uploadPath);						// 파일 저장되는 주소
		
		String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);  // 저장되는 파일명 
		log.info("saveName: " + saveName);

		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 새 글 작성에는 접속자이면서 작성자
		String loginId = userInfoDTO.getUser_id();
		System.out.println(request.getSession().getAttribute("loginId"));
		bdFree.setUser_id(loginId);
		
		if(!file1.isEmpty()) {
			log.info("파일이 존재합니다  ");
			bdFree.setAttach_path(saveName);
			bdFree.setAttach_name(file1.getOriginalFilename());
		}
		
		//--------------------------------------------------------------
		int eventInsert = cs.eventInsert(bdFree);
		//--------------------------------------------------------------
		System.out.println("event 새 글 입력-> " + eventInsert);
		model.addAttribute("eventInsert", eventInsert);
		
		return "redirect:/board_event";
	}
	
// ------------------------------------------------------------------------	
	
	// 이벤트_수정 : 상세정보 get
	@GetMapping(value = "event_update")
	public String eventUpdate(int doc_no, Model model) {
		System.out.println("CyjController eventUpdate Start--------------------------");
		
		//--------------------------------------------------------------
		BdFree eventGet = cs.eventContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("event 수정 상세정보 get-> " + eventGet);
		
		model.addAttribute("event", eventGet);
		
		return "board/board_event/board_event_update";
	}
	
	// 수정
	@PostMapping(value = "event_update2")
	public String eventUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjController eventUpdate2 Start--------------------------");
		
		//--------------------------------------------------------------
		int eventUpdate = cs.eventUpdate(bdFree);
		//--------------------------------------------------------------
		System.out.println("event 수정-> " + eventUpdate);
		model.addAttribute("eventUpdate", eventUpdate);
		
		redirectAttributes.addAttribute("doc_no", bdFree.getDoc_no());
		
		return "redirect:/event_content?doc_no={doc_no}";
	}
	
// ------------------------------------------------------------------------	

	// 이벤트_삭제 (qna 같이 사용)
	@ResponseBody
	@RequestMapping(value = "ajaxDelete")
	public int delete(int doc_no) {
		System.out.println("CyjController delete Start--------------------------");
		
		//--------------------------------------------------------------
		int delete = cs.eventDelete(doc_no);
		//--------------------------------------------------------------
		System.out.println("event 삭제-> " + delete);
		
		return delete;
	}
	
// ------------------------------------------------------------------------	

	// 공지 : 팝업 조회용
	@RequestMapping(value = "board_notify_read")
	public String boardNotifyRead(HttpServletRequest request, String currentPage, int doc_no, Model model) {
		System.out.println("CyjController eventContent Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지의 접속자 loginId-> " + loginId);
		
		// 이벤트_상세
		//--------------------------------------------------------------
		BdFree content = cs.bdFreeContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventContent-> " + content);
		model.addAttribute("notifyContent", content);
		
		// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
		if(userInfoDTO.getUser_id().equals(content.getUser_id())) {
			System.out.println("글작성자가 자신글 조회시 댓글들 alarm_flag='Y'로 일괄 변경처리");
			//--------------------------------------------------------------
			int updateCommentAlarmCount = cs.cyUpdateCommentAlarmFlag(content);
			//--------------------------------------------------------------
		}	
//		model.addAttribute("userInfoDTO", userInfoDTO); // 로그인사용자 정보
		
		// 게시글의 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(content.getUser_id())) {
			result = 1;
			System.out.println("접속자와 작성자 같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 이벤트_조회수 
		//--------------------------------------------------------------
		int count = cs.eventBdCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController count-> " + count);
		model.addAttribute("count", count);
				
		return "board/board_notify/board_notify_read";
	}
	
	// 이벤트_read : 팝업 조회용
	@RequestMapping(value = "board_event_read")
	public String boardEventRead(HttpServletRequest request, String currentPage, int doc_no, Model model) {
		System.out.println("CyjController eventContent Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지의 접속자 loginId-> " + loginId);
		
		// 이벤트_상세
		//--------------------------------------------------------------
		BdFree eventContent = cs.eventContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventContent-> " + eventContent);
		model.addAttribute("eventContent", eventContent);
		
		// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
		if(userInfoDTO.getUser_id().equals(eventContent.getUser_id())) {
			System.out.println("글작성자가 자신글 조회시 댓글들 alarm_flag='Y'로 일괄 변경처리");
			//--------------------------------------------------------------
			int updateCommentAlarmCount = cs.cyUpdateCommentAlarmFlag(eventContent);
			//--------------------------------------------------------------
		}	
//		model.addAttribute("userInfoDTO", userInfoDTO); // 로그인사용자 정보
		
		// 게시글의 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(eventContent.getUser_id())) {
			result = 1;
			System.out.println("접속자와 작성자 같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 이벤트_조회수 
		//--------------------------------------------------------------
		int eventCount = cs.eventBdCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventCount-> " + eventCount);
		model.addAttribute("eventCount", eventCount);
		
		// 이벤트_해당 게시글에 대한 댓글 총 갯수
		//--------------------------------------------------------------
		int eventComtCount = cs.eventComtCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventComtCount-> " + eventComtCount);
		model.addAttribute("eventComtCount", eventComtCount);
		
		BdFreeComt bdFreeComt = new BdFreeComt();
		bdFreeComt.setDoc_no(doc_no);
		
		// 이벤트_댓글 paging 작업
		Paging page = new Paging(eventComtCount, currentPage);   
		bdFreeComt.setStart(page.getStart());
		bdFreeComt.setEnd(page.getEnd());
		model.addAttribute("page", page);
		
		// 이벤트_댓글리스트 보여주기 위해 -> doc_no가 아닌 객체가 들어가야 페이징 작업 가능 
		//--------------------------------------------------------------
		List<BdFreeComt> commentList = cs.eventComt(bdFreeComt);
		//--------------------------------------------------------------
		System.out.println("CyjController commentList.size()-> " + commentList.size());
		model.addAttribute("commentList", commentList);

		return "board/board_event/board_event_read";
	}
	
	// 자유 : 팝업 조회용
	@RequestMapping(value = "board_free_read")
	public String boardFreeRead(HttpServletRequest request, String currentPage, int doc_no, Model model) {
		System.out.println("CyjController eventContent Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지의 접속자 loginId-> " + loginId);
		
		// 이벤트_상세
		//--------------------------------------------------------------
		BdFree content = cs.freeContent(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController eventContent-> " + content);
		model.addAttribute("freeContent", content);
		
		// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
		if(userInfoDTO.getUser_id().equals(content.getUser_id())) {
			System.out.println("글작성자가 자신글 조회시 댓글들 alarm_flag='Y'로 일괄 변경처리");
			//--------------------------------------------------------------
			int updateCommentAlarmCount = cs.cyUpdateCommentAlarmFlag(content);
			//--------------------------------------------------------------
		}	
//		model.addAttribute("userInfoDTO", userInfoDTO); // 로그인사용자 정보
		
		// 게시글의 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(content.getUser_id())) {
			result = 1;
			System.out.println("접속자와 작성자 같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 이벤트_조회수 
		//--------------------------------------------------------------
		int count = cs.eventBdCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController count-> " + count);
		model.addAttribute("eventCount", count);
		
		// 이벤트_해당 게시글에 대한 댓글 총 갯수
		//--------------------------------------------------------------
		int comtCount = cs.eventComtCount(doc_no);
		//--------------------------------------------------------------
		System.out.println("CyjController comtCount-> " + comtCount);
		model.addAttribute("eventComtCount", comtCount);
		
		BdFreeComt bdFreeComt = new BdFreeComt();
		bdFreeComt.setDoc_no(doc_no);
		
		// 이벤트_댓글 paging 작업
		Paging page = new Paging(comtCount, currentPage);   
		bdFreeComt.setStart(page.getStart());
		bdFreeComt.setEnd(page.getEnd());
		model.addAttribute("page", page);
		
		// 이벤트_댓글리스트 보여주기 위해 -> doc_no가 아닌 객체가 들어가야 페이징 작업 가능 
		//--------------------------------------------------------------
		List<BdFreeComt> commentList = cs.eventComt(bdFreeComt);
		//--------------------------------------------------------------
		System.out.println("CyjController commentList.size()-> " + commentList.size());
		model.addAttribute("freeComtList", commentList);

		return "board/board_free/board_free_read";
	}

}
			 