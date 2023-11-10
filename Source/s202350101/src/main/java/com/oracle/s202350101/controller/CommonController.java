package com.oracle.s202350101.controller;

import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.kjoSer.ChatRoomService;
import com.oracle.s202350101.service.kjoSer.ClassRoomService;
import com.oracle.s202350101.service.kjoSer.UserInfoService;
import com.oracle.s202350101.service.mkhser.MkhService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.oracle.s202350101.service.CommonService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommonController {
	
	private final CommonService cs;
	private final UserInfoService uis;
	private final ChatRoomService chs;
	private final MkhService mkhService;
	
	@RequestMapping(value = "/main")
	public String mainPage(HttpServletRequest request,UserInfo userInfoDTO, Model model) {
		return "main";
	}

	@RequestMapping(value = "/template_frame")
	public String mainTemplateFramePage(Model model) {
		return "template_frame";
	}

//	@RequestMapping(value = "/main_header")
//	public String mainHeaderPage(Model model) {
//		List<UserInfo> chatUIList = uis.findbyclassuser(1);		//	사용자 클래스ID 필요함.	추후 로그인이 완성되면 변경 예정
//		log.info(String.valueOf(chatUIList.size()));
//		model.addAttribute("chatUIList", chatUIList);
//
//		return "main_header";
//	}
	@ResponseBody
	@RequestMapping(value = "/main_header")
	public ModelAndView mainHeaderPage(HttpServletRequest request, Model model) {

		System.out.println("Comm mainHeaderPage Start..");
		log.info("mainHeaderPage start");
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));

		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		log.info("userInfo: {}",userInfoDTO);
		//	로그인 사용자와 같은 반 학생들 조회.(어드민제외)
		List<UserInfo> chatUIList = uis.findbyclassuser(userInfoDTO);
		//	사용자가 참여중인 채팅방 조회
		List<ChatRoom> chatRooms = chs.findByUserId(userInfoDTO);



		model.addAttribute("userInfo", userInfoDTO);
		model.addAttribute("chatUIList", chatUIList);
		model.addAttribute("chatRooms", chatRooms);


		return new ModelAndView("main_header");
	}


//	@ResponseBody
//	@RequestMapping(value = "/main_header_chat")
//	public List<UserInfo> main_header_chat(Model model) {
//
//
//		List<UserInfo> chatUIList = uis.findbyclassuser(1);		//	사용자 클래스ID 필요함.	추후 로그인이 완성되면 변경 예정
//		return chatUIList;
//	}
	@RequestMapping(value = "/main_menu")
	public String mainMenuPage(Model model) {
		return "main_menu";
	}

	@RequestMapping(value = "/main_center")
	public String mainCenterPage(Model model) {
		return "main_center";
	}

	@RequestMapping(value = "/main_footer")
	public String mainFooterPage(Model model) {
		return "main_footer";
	}
}
