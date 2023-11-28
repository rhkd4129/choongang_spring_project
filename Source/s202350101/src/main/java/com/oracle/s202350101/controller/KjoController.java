package com.oracle.s202350101.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.*;
import com.oracle.s202350101.service.kjoSer.*;
import lombok.Data;
import org.hibernate.TypeMismatchException;
import org.json.simple.parser.ParseException;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
//	비즈니스 로직	=>	서비스로 코드 간소화 예정

    private final ClassRoomService  CRser;              //	강의실
    private final UserInfoService   UIser;              //	유저정보
    private final PrjInfoService    PIser;              //	프로젝트 정보
    private final BdFreeService     BFser;              //	공용게시판
    private final ChatRoomService   CHser;              //	채팅방
    private final ChatMsgService    CMser;              //	메시지
    private final PrjBdDataService  PBDser;             //  PrjBdData


//--------------------------------------------------------------------------------------
    //	반 생성 페이지 Get
    @GetMapping("/admin_add_class")
    public String admin_add_class(HttpServletRequest request) {

        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
        log.info("admin_add_class GET");
        return "admin/admin_add_class";
    }
//--------------------------------------------------------------------------------------
    //	반 생성 Post
    @PostMapping("/admin_add_class")
    public String admin_add_class(@Validated @ModelAttribute("cr")
                                  ClassRoom cr, BindingResult bindingResult, Model model) {
        log.info("admin_add_class POST");

        if (bindingResult.hasErrors()) {
            log.info("admin_add_class POST .ERROR : {}", bindingResult);
            model.addAttribute("cr", cr);
            log.info(bindingResult.getFieldError().toString());
            log.info("admin_add_class POST .ERROR Return");
            return "admin/admin_add_class";
        }

        //--------------------------------------------------------
        int result = CRser.saveClassRoom(cr);            // 강의실 생성
        //--------------------------------------------------------
        log.info("반 생성 개수: " + result);
        return "redirect:/admin_class_list";
    }
//--------------------------------------------------------------------------------------
    //	반 목록 페이지	GET
    @GetMapping("/admin_class_list")
    public String admin_class_list(HttpServletRequest request, Model model) {

        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

        log.info("admin_class_list");
        //--------------------------------------------------------
        List<ClassRoom> CRList = CRser.findAllClassRoom();            // 모든 강의실 조회
        //--------------------------------------------------------

        //  날짜처리
        SimpleDateFormat newDtFormat = new SimpleDateFormat("yy-MM-dd");
        for (int i = 0; i < CRList.size(); i++) {
            Date startDate = CRList.get(i).getClass_start_date();
            Date endDate = CRList.get(i).getClass_end_date();
            CRList.get(i).setStartDate(newDtFormat.format(startDate));
            CRList.get(i).setEndDate(newDtFormat.format(endDate));
        }

        model.addAttribute("CRList", CRList);
        return "admin/admin_class_list";
    }
//--------------------------------------------------------------------------------------
    //	반 제거
    @GetMapping("/admin_del_class")
    public String admin_del_class(ClassRoom cr) {
        log.info("admin_del_class POST");
        //--------------------------------------------------------
        //	강의실 id를 기준으로 삭제
        int result = CRser.deletebyId(cr);
        //--------------------------------------------------------
        return "redirect:/admin_class_list";
    }
//--------------------------------------------------------------------------------------
    //	게시판 관리 페이지	GET
    @GetMapping("/admin_board")
    public String admin_board(@RequestParam(defaultValue = "1") String currentPage, ClassRoom cr, Model model, HttpServletRequest request) {
        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
//        if (!userInfoDTO.getUser_id().equals("admin")) {
//            return "/main";
//        }

        log.info("admin_board");
        //--------------------------------------------------------
        // 모든 강의실 조회
        List<ClassRoom> CRList = CRser.findAllClassRoom();
        //--------------------------------------------------------
        /*		프로젝트 목록		*/
        /*	    상단 셀렉트바	    */
        List<PrjInfo> PIList = null;
        if (cr.getClass_id() != 0) {
            //	강의실별 프로젝트 목록
            //--------------------------------------------------------
            PIList = PIser.findbyClassId(cr);
            //--------------------------------------------------------
        } else {
            // 첫 접근 시 1번 강의실 조회
            cr.setClass_id(1);
            //--------------------------------------------------------
            PIList = PIser.findbyClassId(cr);
            //--------------------------------------------------------
        }
        //--------------------------------------------------------
        /*	상단 셀렉트바	*/
        // prj_board_data 전체 Count
        /*		페이징 		*/
        int totCnt = PBDser.totalCount();
        //--------------------------------------------------------

        Paging page2 = new Paging(totCnt, currentPage, 5);
        PrjBdData prjBdData = new PrjBdData();
        prjBdData.setStart(page2.getStart());        //	시작1
        prjBdData.setEnd(page2.getEnd());            //	시작10
        prjBdData.setClass_id(1);
        prjBdData.setProject_id(1);
        /*		페이징 		*/

        //--------------------------------------------------------
//<!--pbd 게시글 페이징 조회-->
        List<PrjBdData> prjBdDataList = PBDser.boardList(prjBdData);
        //--------------------------------------------------------
        /*		프로젝트 목록		*/
        /*		이벤트 게시글		*/
        BdFree bf = new BdFree();
        bf.setBd_category("공지");
        //--------------------------------------------------------
        int BFListCnt = BFser.findBdFreeByCategory(bf).size();
        //--------------------------------------------------------
        /*		페이징 		*/
        Paging page1 = new Paging(BFListCnt, currentPage, 5);
        bf.setStart(page1.getStart());
        bf.setEnd(page1.getEnd());
        /*		페이징 		*/

        //--------------------------------------------------------
//	카테고리 별 BdFree게시글 페이징 조회
        List<BdFree> BFList = BFser.pageBdFreeByCategoryAndPage(bf);
        //--------------------------------------------------------
        /*		이벤트 게시글		*/
        /*------------------비즈니스 로직--------------------*/

        model.addAttribute("page", page1);
        model.addAttribute("page2", page2);
        model.addAttribute("PBDList", prjBdDataList);
        model.addAttribute("CRList", CRList);
        model.addAttribute("PIList", PIList);
        model.addAttribute("BFList", BFList);

        return "admin/admin_board";
    }
//--------------------------------------------------------------------------------------
    //	AJAX	강의실 조회
    @ResponseBody
    @GetMapping("/admin_board_ajax")
    public KjoResponse admin_board_ajax(ClassRoom cr) {
        log.info("admin_board_ajax");
        /*------------------비즈니스 로직--------------------*/
        //--------------------------------------------------------
        // 모든 강의실 조회
        List<ClassRoom> CRList = CRser.findAllClassRoom();
        //--------------------------------------------------------

        List<PrjInfo> PIList = null;
        //	강의실을 선택하지 않았을 경우
        if (cr.getClass_id() != 0) {
            //--------------------------------------------------------
            //	해당 강의실에 포함된 프로젝트 리스트
            PIList = PIser.findbyClassId(cr);
            //--------------------------------------------------------
            log.info("cr:   " + cr.toString());
        } else {
            //--------------------------------------------------------
            //	모든 프로젝트 리스트
            PIList = PIser.findAll();
            //--------------------------------------------------------
        }
        /*------------------비즈니스 로직--------------------*/
        KjoResponse res = new KjoResponse();
        //	해당 강의실에 포함된 프로젝트
        res.setSecList(PIList);
        //	모든 강의실
        res.setFirList(CRList);
        return res;
    }
//--------------------------------------------------------------------------------------
    //	PrjBdData ajax처리
    @ResponseBody
    @GetMapping("/admin_board_pbd_ajax")
    public KjoResponse admin_board_pbd_ajax(PrjBdData prjBdData, String currentpage) {
        KjoResponse res = new KjoResponse();
        //--------------------------------------------------------
        int totcnt = PBDser.findByClassProjectId(prjBdData).size();
        //--------------------------------------------------------
//		페이징	글 개수 : 5		이벤트 수	현재 페이지	목록 노출 수
        Paging page = new Paging(totcnt, currentpage, 5);
        prjBdData.setStart(page.getStart());
        prjBdData.setEnd(page.getEnd());

        //--------------------------------------------------------
        List<PrjBdData> prjBdDataList = PBDser.boardList(prjBdData);        //PrjBdData 페이징 조회
        //--------------------------------------------------------      
        res.setFirList(prjBdDataList);
        res.setObj(page);
        return res;
    }
//--------------------------------------------------------------------------------------
    //	공지/자료 삭제
    @PostMapping("/admin_pbd_del")
    @ResponseBody
    public ResponseEntity<?> admin_pbd_del(@RequestBody KjoRequestDto kjorequest) {
//	firList: prj_delbox , secList:doc_delbox
        //	RequestDto를 통해 불필요한 데이터 처리를 하지 않아도 된다.
        //--------------------------------------------------------
        int result = PBDser.delpbd(kjorequest);
        //--------------------------------------------------------

        return ResponseEntity.ok(result);
    }
//--------------------------------------------------------------------------------------
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
        /*------------------비즈니스 로직--------------------*/
        //--------------------------------------------------------
//	이벤트 개수
        int BFListCnt = BFser.findByCategorySearch(bf);
        //--------------------------------------------------------
//		페이징	글 개수 : 5		이벤트 수	현재 페이지	목록 노출 수
        Paging page = new Paging(BFListCnt, currentPage, 5);
        bf.setStart(page.getStart());
        bf.setEnd(page.getEnd());

        //--------------------------------------------------------
//		카테고리 별 BdFree게시글 페이징 조회		(use in start, end, keyword, bd_category)
        List<BdFree> BFList = BFser.findByCategorySearchAndPage(bf);
        //--------------------------------------------------------
        /*------------------비즈니스 로직--------------------*/

//		조회된 게시글 & 페이지 전달		//	조회된 게시글 리스트와 페이지 객체 전송을 위한 객체 생성
        KjoResponse res = new KjoResponse();
        res.setFirList(BFList);
        res.setObj(page);

//		정상 작동 시 HttpResponse status 200과 함께 KjoResponse 객체 전달
        return ResponseEntity.ok(res);
    }
//--------------------------------------------------------------------------------------
//	게시글 삭제
    @PostMapping("/admin_board_del")
    @ResponseBody
    public ResponseEntity<?> admin_board_del(@RequestBody KjoRequestDto kjorequest) {
        //	RequestDto를 통해 불필요한 데이터 처리를 하지 않아도 된다.
        //--------------------------------------------------------
        int result = BFser.del_bdf(kjorequest);     //	게시글 삭제
        //--------------------------------------------------------
        return ResponseEntity.ok(result);
    }
//--------------------------------------------------------------------------------------
// 	<팀장 권한 설정>	버튼	클릭 시
//	팀장 권한 페이지 GET
    @GetMapping("/admin_projectmanager")                                //	url에 값을 입력하지 않기 위함.
    public String captainManage(HttpServletRequest request, String currentPage, @RequestParam(defaultValue = "1") int cl_id, Model model) {
        log.info("captainManage");
        //	로그인 사용자DTO
        UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
        /*------------------비즈니스 로직--------------------*/

        //--------------------------------------------------------
        List<ClassRoom> CRList = CRser.findAllClassRoom();                  // 모든 강의실 조회
        //--------------------------------------------------------
        userInfo.setClass_id(cl_id);

        //--------------------------------------------------------
        // 	조회하는 사용자는 어드민
        ////	강의실 별 학생 조회.(ADMIN 제외)
        userInfo.setTotal(UIser.findbyclassuser(userInfo).size());
        //--------------------------------------------------------

        KjoResponse kjoResponse = new KjoResponse();

        //--------------------------------------------------------
        kjoResponse = UIser.pageUserInfov2(userInfo, currentPage);          // 반 학생의 이름 + 참여 프로젝트 명 + 권한여부 + 페이징
        //--------------------------------------------------------
        /*------------------비즈니스 로직--------------------*/

        model.addAttribute("cl_id", cl_id);
        model.addAttribute("CRList", CRList);
        model.addAttribute("UIList", kjoResponse.getFirList());
        model.addAttribute("page", kjoResponse.getObj());

        return "admin/admin_projectmanager";
    }
//--------------------------------------------------------------------------------------
//	페이징하기
//	지역, 강의실 선택 시 작동.	EX_ 이대 501
//	팀장 권한 페이지 RestGET
    @GetMapping("/admin_projectmanagerRest/{cl_id}")    //	cl_id = Class_Room(class_id)
    @ResponseBody
    public KjoResponse admin_projectmanagerRest(HttpServletRequest request, UserInfo userInfo, String currentPage, @PathVariable int cl_id, Model model) {
        log.info("admin_projectmanagerRest");
        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

        /*------------------비즈니스 로직--------------------*/
        //	선택한 강의실 id => userInfoDTO에 저장.
        userInfo.setClass_id(cl_id);
        userInfo.setUser_id(userInfoDTO.getUser_id());

        //--------------------------------------------------------
        int totalUi = UIser.findbyclassuser(userInfo).size();        //	admin	제외	모든 학생 수
        //--------------------------------------------------------
        userInfo.setTotal(totalUi);

        log.info("userInfo: {}", userInfo);

        //--------------------------------------------------------
        KjoResponse res = UIser.pageUserInfov2(userInfo, currentPage);        // 반 학생의 이름 + 참여 프로젝트 명 + 권한여부 + 페이징
        //--------------------------------------------------------
        /*------------------비즈니스 로직--------------------*/

        return res;
    }
//--------------------------------------------------------------------------------------
//	팀장 권한 수정	Rest
//	private List<String> user_id;
//	private List<String> user_auth;
    @PostMapping("/auth_mod")
    @ResponseBody
    public ResponseEntity<?> auth_mod(@RequestBody KjoRequestDto kjorequest) {
        //	RequestDto를 통해 불필요한 데이터 처리를 하지 않아도 된다.
        //--------------------------------------------------------
        int result = UIser.auth_modify(kjorequest);         //팀장 권한 수정
        //--------------------------------------------------------
        //  HTTP 200과 db 업데이트 수 반환 result == 15가 정상.
        return ResponseEntity.ok(result);
    }
//--------------------------------------------------------------------------------------



    //	채팅방 팝업
    @GetMapping("/chat_room")                        //	상대방
    public String chat_room(HttpServletRequest request, UserInfo ui, Model model) {
        log.info("chat_room");
        //	로그인 사용자DTO
        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

        /*------------------비즈니스 로직--------------------*/

        ChatRoom cr = new ChatRoom();
        cr.setSender_id(userInfoDTO.getUser_id());
        cr.setReceiver_id(ui.getUser_id());
        //--------------------------------------------------------
        ChatRoom nowChatRoom = CHser.findByYouAndMeNotEmpty(cr);    //	채팅할 대상자와 로그인 사용자의 채팅방 조회
        //--------------------------------------------------------

        //	채팅방 상대방
        nowChatRoom.setReceiver_id(ui.getUser_id());
        //	채팅방 로그인 사용자
        nowChatRoom.setSender_id(userInfoDTO.getUser_id());

        //--------------------------------------------------------
        UserInfo youUser = UIser.findbyuserId(ui);                  //	상대방 이름 조회
        //--------------------------------------------------------

        //	로그인 사용자DTO
        model.addAttribute("userInfo", userInfoDTO);
        //	채팅할 대상자와 로그인 사용자의 채팅방
        model.addAttribute("ChatRoom", nowChatRoom);
        //	상대방 사용자 정보
        model.addAttribute("your", youUser);

        return "chat/chat_room";
    }
//--------------------------------------------------------------------------------------

    //	채팅방 입장 시 메세지들 조회 및 읽음 업데이트
    @MessageMapping("/chat/receive")
    @SendTo("/app/great")
    public KjoResponse receiveMsg(ChatRoom room) {
//	chat_room_id: ${ChatRoom.chat_room_id}, //  현재 채팅방
//	sender_id: '${userInfo.user_id}',       //  본인 id
//	receiver_id: '${your.user_id}',         //  상대 id
        //--------------------------------------------------------
        List<ChatMsg> CMList = CMser.inviteChatRoom(room);
        //--------------------------------------------------------

        KjoResponse response = new KjoResponse();
        response.setFirList(CMList);
        response.setObj(room);
        /*------------------비즈니스 로직--------------------*/
        return response;
    }
//--------------------------------------------------------------------------------------

    //	WebSocketConfig에서 prefix "/app"을하여 생략
    @MessageMapping("/chat/send")        //	소켓 메시지를 객체로 변환
    @SendTo("/app/chatreceive")
    public ChatMsg sendMsg(ChatMsg message) {        //	json을 왜 parse 안했는지.
//		STOMP 메시지를 JSON으로 변환하거나 JSON을 STOMP 메시지로 변환하는 기능을 자동으로 제공

        //--------------------------------------------------------
        ChatMsg findmsg = CMser.findsaveMsg(message);           //		전송 받은 메시지 저장 후 반환
        //--------------------------------------------------------
        log.info("Sending message to /app/chatreceive: {}", findmsg);
        /*------------------비즈니스 로직--------------------*/
        return findmsg;
    }
//--------------------------------------------------------------------------------------		                                      //	WebSocketConfig에서 prefix "/app"을하여 생략

    @MessageMapping("/chat/cnt")        //	소켓 메시지를 객체로 변환
    @SendTo("/app/cnttotmsg")
    public KjoResponse showMsg(ChatRoom cr) {
//		STOMP 메시지를 JSON으로 변환하거나 JSON을 STOMP 메시지로 변환하는 기능을 자동으로 제공
        //	읽지 않은 메시지 수
        //	채팅방
        //	채팅방 별 읽지 않은 메시지 수
        //	채팅방 별 최신 메시지
        //--------------------------------------------------------

        UserInfo user = new UserInfo();
        user.setUser_id(cr.getSender_id());

        //--------------------------------------------------------
        List<ChatRoom> chatRooms = CHser.findByUserId(user);    //	나의 채팅방
        //--------------------------------------------------------


        //--------------------------------------------------------
        KjoResponse res = CMser.cnttomsg(cr, user, chatRooms);  //  모든 메시지 조회 및 최신 메시지, 채팅방, 읽지 않은 메시지 수 반환
        //--------------------------------------------------------

//	요청의 주체
        res.setSecobj(user.getUser_id());
//	요청의 피주체
        res.setTrdobj(cr.getReceiver_id());
        return res;
    }


}
