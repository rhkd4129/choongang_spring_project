package com.oracle.s202350101.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.service.cyjSer.CyjService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
// @Slf4j
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
		
		// 추천수 가장 높은 row 3개
		List<BdFree> goodListRow = cs.goodRow(bdFree);
		System.out.println("CyjController goodListRow-> " + goodListRow);
		model.addAttribute("goodListRow", goodListRow);
		
		// 총 갯수
		int totalBdFree = cs.totalBdFree();
		model.addAttribute("totalBdFree", totalBdFree);
		
		// paging 작업
		Paging page = new Paging(totalBdFree, currentPage);
		bdFree.setStart(page.getStart());  // 시작시 1
		bdFree.setEnd(page.getEnd());      // 시작시 10
		model.addAttribute("page", page);
		
		// 전체 list
		List<BdFree> bdFreeList = cs.listBdFree(bdFree);
		System.out.println("CyjController board_notify bdFreeList.size()-> " + bdFreeList.size());
		model.addAttribute("bdFreeList", bdFreeList);
		
		return "board/board_notify/board_notify_list";
	}

// -----------------------------------------------------------------------	
	
	// 새 글 입력하기 위한 페이지 이동 
	@RequestMapping(value = "board_write_insert_form")
	public String board_write_insert_form() {
		System.out.println("CyjController board_write_insert_form GET Start--------------------------");
			
		return "board/board_notify/board_notify_write_form";	
	}
	
	// 새 글 입력 
	@PostMapping(value =  "board_write_insert")														
//	public String boardWrite(HttpServletRequest request, @RequestParam("subject"),  @RequestParam("doc_body"), @RequestParam("file") Model model) {
//	public String boardWrite(HttpServletRequest request, @ModelAttribute("BdFree")  bdFree Model model) {
	public String boardWrite(HttpServletRequest request, BdFree bdFree, Model model) {
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo"); 
		
		// loginId : 새 글 작성에서는 접속자이면서 작성자 
		String loginId = userInfoDTO.getUser_id(); 						   // UserInfo에 있는 user_id 갖고 옴
		System.out.println(request.getSession().getAttribute("loginId"));  // loginId을 session에 저장 
		bdFree.setUser_id(loginId);										   // xml의 parameterType="BdFree" 에 담기 위해 
		
		System.out.println("CyjController board_write_insert POST Start--------------------------");
		
		int insertResult = cs.insertBdFree(bdFree);
		System.out.println("CyjController board_notify insertResult-> " + insertResult);
		
		model.addAttribute("insertResult", insertResult);

		return "redirect:/board_notify";
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
		
		// 상세 
		BdFree bdFreeContent = cs.bdFreeContent(doc_no);
		System.out.println("CyjController board_content boardContent-> " + bdFreeContent);
		System.out.println("-----------------------------");
		System.out.println(loginId);
		System.out.println(bdFreeContent.getUser_id());
		
		// 접속자랑 작성자 비교 --> 같으면 1, 다르면 0
		int result;
		if (loginId.equals(bdFreeContent.getUser_id())) {
			result = 1;
			System.out.println("같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 조회수 
		int bdCount = cs.bdCount(doc_no);
		System.out.println("CyjController board_content bdCount-> " + bdCount);
		
		model.addAttribute("content", bdFreeContent);
		model.addAttribute("bdCount", bdCount);
		
		return "board/board_notify/board_notify_content";
	}
					
// -----------------------------------------------------------------------	
	
	// 전체 공지사항_수정: 상세 정보 get
	@GetMapping(value = "board_update")
	public String boardUpdate(int doc_no, Model model) {
		System.out.println("CyjController board_update Start--------------------------");
	
		BdFree free = cs.bdFreeContent(doc_no); 
		System.out.println("CyjController board_content free-> " + free);
		
		model.addAttribute("free", free);
		
		return "board/board_notify/board_notify_update";
	}
	
	// 수정
	@PostMapping(value = "board_update2")
	public String boardUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjController board_update2 Start--------------------------");
		
		int bdBoardUpdate = cs.bdFreeUpdate2(bdFree);
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
		
		int ajaxDelete = cs.boardDelete(doc_no);
		System.out.println("ajaxDelete-> " + ajaxDelete);
		 
		return ajaxDelete; 
	}
	
// ------------------------------------------------------------------------	
//  1. 추천 버튼 누르면 추천(0), 다시 누르면 추천 취소(1)  --> 0이면 버튼의 색상이 변함 
//  2. 회원마다 게시글별 1개씩 추천 가능 (중복 X)
// 	3. 추천하면 해당 게시들을 올린 회원의 추천수가 +1, 취소시 -1

	// 추천
	@ResponseBody
	@RequestMapping(value = "ajaxGoodCount")
	public int goodCount(int doc_no, Model model) {
		System.out.println("CyjController goodCount Start--------------------------");
		
		// 1. 추천자목록에 현재 로그인사용자가 있는지 확인   selectGood(bdFreeGoodDTO) select * from bd_free_good where don_no=#~ and user_id=#~
		//    1-1 이미 추가된 경우  return변수 = "duplicated"
		// 2. 1수행후 추천기록이 없으면 추가 insertGood(bdFreeGoodDTO)   insert into bd_free_good() values( , )
		// 3. 추천자목록에 해당글(doc_no)의 추천갯수를 현재문서의 추천수 업데이트 updateCount(int doc_no) update bd_free set good_count=(select count(*) from bd_free_good) where doc_no=# ) where doc_no=#
		//     return변수 = "success"
		// return변수 = "error"
		// inertCount 현재 열려 있는 글 doc_no , 로그인 유저  
		
		// 추천수 +1 올리기 
		int goodCount = cs.goodCount(doc_no);
		System.out.println("doc_no-> "    + doc_no);
		System.out.println("goodCount-> " + goodCount);
		
		// 올린 추천수 갖고 오기 
		int goodCountView = cs.goodCountView(doc_no);
		System.out.println("goodCountView-> " + goodCountView);
		
		model.addAttribute("goodCountView", goodCountView);   
		
		return goodCountView;
	}
	
// ------------------------------------------------------------------------	
// ------------------------- 이벤트 게시판 ------------------------------------

	// 이벤트_전체 리스트
	@RequestMapping(value = "event")
	public String eventList(HttpServletRequest request, BdFree bdFree, String currentPage, Model model) {
		System.out.println("CyjController event Start--------------------------");
		 
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// 추천수 가장 높은 row 3개 
		List<BdFree> eventGood = cs.eventCount(bdFree);
		System.out.println("eventGood.size()-> " + eventGood.size());
		model.addAttribute("eventGood", eventGood);
		
		// 총 갯수
		int eventCount = cs.eventCount();
		System.out.println("CyjController eventCount-> " + eventCount);
		model.addAttribute("eventCount", eventCount);
		
		// paging 작업
		Paging page = new Paging(eventCount, currentPage);
		bdFree.setStart(page.getStart());  // 시작시 1
		bdFree.setEnd(page.getEnd());      // 시작시 10
		model.addAttribute("page", page);
		
		// 전체 리스트
		List<BdFree> eventList = cs.eventList(bdFree);
		System.out.println("CyjController eventList.size()-> " + eventList.size());
		model.addAttribute("eventList", eventList);;
		
		return "board/board_event/board_event_list";
	}
	
// ------------------------------------------------------------------------
	
	// 이벤트_상세 페이지 
	@GetMapping(value = "event_content")
	public String eventContent(HttpServletRequest request, int doc_no, Model model) {
		System.out.println("CyjController eventContent Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지의 접속자 loginId-> " + loginId);
		
		// 이벤트_상세
		BdFree eventContent = cs.eventContent(doc_no);
		System.out.println("CyjController eventContent-> " + eventContent);
		System.out.println("상세페이지의 작성자 user_Id-> " + eventContent.getUser_id());
		model.addAttribute("eventContent", eventContent);
		
		// 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(eventContent.getUser_id())) {
			result = 1;
			System.out.println("접속자와 작성자 같다");
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 이벤트_조회수 
		int eventCount = cs.eventBdCount(doc_no);
		System.out.println("CyjController eventCount-> " + eventCount);
		model.addAttribute("eventCount", eventCount);
		
		// 이벤트_댓글리스트
		List<BdFreeComt> comt = cs.eventComt(doc_no);
		System.out.println("상세페이지 댓글 comt-> " + comt);
		model.addAttribute("comt", comt);
		
		return "board/board_event/board_event_content";
	}
		
	// 이벤트_댓글
	@ResponseBody
	@PostMapping(value = "ajaxComt")
	public List<BdFreeComt> ajaxComt(BdFreeComt bdFreeComt) {
		System.out.println("CyjController ajaxComt Start--------------------------");
		
		// 댓글 입력
		int comt = cs.ajaxComt(bdFreeComt);
		System.out.println("CyjController comt-> " + comt);
		
		// 입력한 댓글 갖고 오기
		List<BdFreeComt> comtSelect = cs.eventSelect(bdFreeComt); 
		System.out.println("CyjController comtSelect-> " + comt);
		
		return comtSelect;
	}
	
// ------------------------------------------------------------------------	
	
	// 이벤트_추천
	@ResponseBody
	@RequestMapping(value = "ajaxEventGoodCount")
	public int eventGoodCount(int doc_no, Model model) {
		System.out.println("CyjController eventGoodCount Start--------------------------");
		
		// 추천수 +1 올리기
		int eventCountUp = cs.goodCount(doc_no);
		System.out.println("추천 doc_no-> "    + doc_no);
		System.out.println("eventCountUp-> " + eventCountUp);
		
		// 올린 추천수 갖고 오기 
		int eventCountView = cs.goodCountView(doc_no);
		System.out.println("올린 추천수 eventCountView-> " + eventCountView);
		
		model.addAttribute("eventCountView", eventCountView);
		
		return eventCountView;
	}
	
// ------------------------------------------------------------------------	

	// 이벤트_새 글 입력하기 위한 페이지 이동 
	@RequestMapping(value = "event_insert_form")
	public String eventInsertForm() {
		System.out.println("CyjController eventInsertForm Start--------------------------");
		
		return "board/board_event/board_event_insert";
	}
	
	// 새 글 입력
	@PostMapping(value = "event_insert")
	public String eventInsert(HttpServletRequest request, BdFree bdFree, Model model) {
		
		System.out.println("CyjController eventInsert Start--------------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 새 글 작성에는 접속자이면서 작성자
		String loginId = userInfoDTO.getUser_id();
		System.out.println(request.getSession().getAttribute("loginId"));
		bdFree.setUser_id(loginId);
		
		int eventInsert = cs.eventInsert(bdFree);
		System.out.println("event 새 글 입력-> " + eventInsert);
		
		model.addAttribute("eventInsert", eventInsert);
		
		return "redirect:/event";
	}
	
// ------------------------------------------------------------------------	
	
	// 이벤트_수정 : 상세정보 get
	@GetMapping(value = "event_update")
	public String eventUpdate(int doc_no, Model model) {
		System.out.println("CyjController eventUpdate Start--------------------------");
		
		BdFree eventGet = cs.eventContent(doc_no);
		System.out.println("event 수정 상세정보 get-> " + eventGet);
		
		model.addAttribute("event", eventGet);
		
		return "board/board_event/board_event_update";
	}
	
	// 수정
	@PostMapping(value = "event_update2")
	public String eventUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjController eventUpdate2 Start--------------------------");
		
		int eventUpdate = cs.eventUpdate(bdFree);
		System.out.println("event 수정-> " + eventUpdate);
		
		model.addAttribute("eventUpdate", eventUpdate);
		
		redirectAttributes.addAttribute("doc_no", bdFree.getDoc_no());
		
		return "redirect:/event_content?doc_no={doc_no}";
	}
	
// ------------------------------------------------------------------------	

	// 이벤트_삭제
	@ResponseBody
	@RequestMapping(value = "ajaxDelete")
	public int delete(int doc_no) {
		System.out.println("CyjController delete Start--------------------------");
		
		int delete = cs.eventDelete(doc_no);
		System.out.println("event 삭제-> " + delete);
		
		return delete;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
			 