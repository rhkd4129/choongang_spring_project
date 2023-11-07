package com.oracle.s202350101.controller;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.*;
import com.oracle.s202350101.service.kjoSer.*;
import lombok.Data;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.apache.ibatis.session.LocalCacheScope.SESSION;

@Slf4j
@Controller
@RequiredArgsConstructor
public class KjoController {
	
	private final ClassRoomService CRser;		//	강의실
	private final UserInfoService UIser;		//	유저정보
	private final PrjInfoService PIser;			//	프로젝트 정보
	private final BdFreeService BFser;			//	공용게시판
	private final ChatRoomService CHser;		//	채팅방
	private final ChatMsgService CMser;			//	메시지

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

	//	반 생성 Post
	@PostMapping("/admin_add_class")
	public String admin_add_class(ClassRoom cr, Model model, BindingResult bindingResult) {
//		if (bindingResult.hasErrors()) {
//			bindingResult.
//		}
		log.info("admin_add_class POST");
		int result = CRser.saveClassRoom(cr);            // 강의실 생성
		log.info("반 생성 개수: " + result);
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
	public String admin_board(@RequestParam(defaultValue = "1")  String currentPage, ClassRoom cr, Model model) {

		log.info("admin_board");
		// 모든 강의실 조회
		List<ClassRoom> CRList = CRser.findAllClassRoom();

		/*		프로젝트 목록		*/
		List<PrjInfo> PIList = null;
		if (cr.getClass_id() != 0) {
			//	강의실별 프로젝트 목록
			PIList = PIser.findbyClassId(cr);
			log.info("cr:   "+cr.toString());
		} else {
			// 첫 접근 시 1번 강의실 조회
			cr.setClass_id(1);
			PIList = PIser.findbyClassId(cr);
		}
		/*		프로젝트 목록		*/

		/*		이벤트 게시글		*/
		BdFree bf = new BdFree();
		bf.setBd_category("공지");
		int BFListCnt = BFser.findBdFreeByCategory(bf).size();

		Paging page1 = new Paging(BFListCnt, currentPage,5);
		bf.setStart(page1.getStart());
		bf.setEnd(page1.getEnd());


		List<BdFree> BFList = BFser.pageBdFreeByCategoryAndPage(bf);
		/*		이벤트 게시글		*/


		model.addAttribute("page", page1);
		model.addAttribute("CRList", CRList);
		model.addAttribute("PIList", PIList);
		model.addAttribute("BFList", BFList);

		return "admin/admin_board";
	}

	//	AJAX_학원전체_이벤트_페이징
	//	게시판 관리 페이지	GET
	@ResponseBody
	@GetMapping("/admin_board_ajax_paging")
	public ResponseEntity admin_board_ajax_paging( String currentPage, BdFree bf, Model model) {
		log.info("admin_board_ajax_paging");
		/*		이벤트 게시글		*/
		//	이벤트 카테고리 목록
		bf.setBd_category("이벤트");
		//	이벤트 개수
		int BFListCnt = BFser.findBdFreeByCategory(bf).size();
//		페이징	글 개수 : 5
		Paging page = new Paging(BFListCnt, currentPage,5);
		bf.setStart(page.getStart());
		bf.setEnd(page.getEnd());
		List<BdFree> BFList = BFser.pageBdFreeByCategoryAndPage(bf);

//		조회된 게시글 & 페이지 전달
		KjoResponse res = new KjoResponse();
		res.setFirList(BFList);
		res.setObj(page);

		return ResponseEntity.ok(res);
	}

	//	AJAX_학원전체_이벤트_페이징_검색
	//	게시판 관리 페이지	GET
	@ResponseBody
	@GetMapping("/admin_board_ajax_paging_search")
	public ResponseEntity admin_board_ajax_paging_search(
			BdFree bf,
			@RequestParam("currentPage") String currentPage) {
		/*		이벤트 게시글		*/
		//	이벤트 카테고리 목록
		log.info("keyword: {}", bf.getKeyword());
//		bf.setBd_category("이벤트");
		//	이벤트 개수
		int BFListCnt = BFser.findByCategorySearch(bf);
//		페이징	글 개수 : 5
		Paging page = new Paging(BFListCnt, currentPage, 5);
		bf.setStart(page.getStart());
		bf.setEnd(page.getEnd());
		List<BdFree> BFList = BFser.findByCategorySearchAndPage(bf);

//		조회된 게시글 & 페이지 전달
		KjoResponse res = new KjoResponse();
		res.setFirList(BFList);
		res.setObj(page);

		return ResponseEntity.ok(res);
	}

	@ResponseBody
	@GetMapping("/admin_board_ajax")
	public KjoResponse admin_board_ajax( ClassRoom cr, Model model) {

		log.info("admin_board_ajax");
		List<ClassRoom> CRList = CRser.findAllClassRoom();            // 모든 강의실 조회

		List<PrjInfo> PIList = null;
		if (cr.getClass_id() != 0) {
			PIList = PIser.findbyClassId(cr);
			log.info("cr:   "+cr.toString());
		} else {
			PIList = PIser.findAll();
		}
		KjoResponse res = new KjoResponse();
		res.setSecList(PIList);
		res.setFirList(CRList);

		return res;
	}


//	채팅방 팝업
	@GetMapping("/chat_room")
	public String chat_room(HttpServletRequest request, UserInfo ui, Model model) {
		log.info("chat_room");
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		ChatRoom cr = new ChatRoom();
		cr.setSender_id(userInfoDTO.getUser_id());
		cr.setReceiver_id(ui.getUser_id());

		ChatRoom nowChatRoom =  CHser.findByYouAndMe(cr);
		List<ChatMsg> CMList = CMser.findByRoomId(nowChatRoom);

		log.info("CMList: " + CMList.toString());
		model.addAttribute("userInfo", userInfoDTO);
		model.addAttribute("ChatRoom", nowChatRoom);
		model.addAttribute("CMList", CMList);

		return "chat/chat_room";
	}



	private final SimpMessagingTemplate simpMessagingTemplate;

	@MessageMapping("/chat/send")		// 소켓 메시지를 객체로 변환
	@SendTo("/queue/greetings")
	public ChatMsg sendMsg(ChatMsg message) {		//	json을 왜 parse 안했는지.

		ChatMsg cm = new ChatMsg();
		cm.setChat_room_id(message.getChat_room_id());
		cm.setSender_id(message.getSender_id());
		cm.setMsg_con(message.getMsg_con());

		CMser.cntsaveMsg(cm);
//		simpMessagingTemplate.convertAndSend("/topic/greetings/"+message.getChat_room_id(),message);
		return message;
	}


//
	@GetMapping("/admin_approval")
	public String admin_approval() {
		log.info("admin_approval");
		return "admin/admin_approval";
	}


//	<팀장 권한 설정>	버튼	클릭 시
	//	팀장 권한 페이지 GET
	@GetMapping("/admin_projectmanager")								//	url에 값을 입력하지 않기 위함.
	public String captainManage(UserInfo userInfo, String currentPage, @RequestParam(defaultValue = "1") int cl_id, Model model) {
		log.info("captainManage");
		userInfo.setClass_id(cl_id);
		List<ClassRoom> CRList =CRser.findAllClassRoom();			// 모든 강의실 조회
		userInfo.setUser_id("admin");								//	조회하는 사용자는 어드민
		int total = UIser.findbyclassuser(userInfo).size();	//	강의실 별 학생 조회.(ADMIN 제외)

		//	페이징을 하기 위한 START, END,	TOTAL 지정.
		Paging page = new Paging(total, currentPage);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(total);
		// 반 학생의 이름 + 참여 프로젝트 명 + 권한여부 + 페이징
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);

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
		
		//	admin	제외	모든 학생 수
		int totalUi = UIser.findbyclassuser(userInfo).size();
		//	선택한 강의실 id => userInfoDTO에 저장.
		userInfo.setClass_id(cl_id);
		log.info("total: ",totalUi);

		//	페이징을 하기 위한 START, END,	TOTAL 지정.
		Paging page = new Paging(totalUi, currentPage);
		page.setPageBlock(3);
		userInfo.setStart(page.getStart());
		userInfo.setEnd(page.getEnd());
		userInfo.setTotal(totalUi);

		log.info("page: {}",page);
		log.info("userInfo: {}",userInfo);
		// 반 학생의 이름 + 참여 프로젝트 명 + 권한여부 + 페이징
		List<UserInfo> UIList = UIser.pageUserInfo(userInfo);

		KjoResponse res = new KjoResponse();
		res.setFirList(UIList);
		res.setObj(page);
		log.info("res: "+res.getFirList().toString());
		log.info("page: "+page.toString());

		log.info("UIList: {}",UIList);

		return res;
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
