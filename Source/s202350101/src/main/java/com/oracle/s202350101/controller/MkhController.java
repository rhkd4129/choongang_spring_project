package com.oracle.s202350101.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.UserEnv;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.mkhser.MkhService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MkhController {
	
	private final MkhService mkhService;
	
	// SMTP(Send Mail Transport protocol) 메일 전송 객체
	private final JavaMailSender mailSender;
	
	/* 로그인  */
	// 로그인 화면
	@RequestMapping(value = "user_login")
	public String userLogin(Model model) {
		System.out.println("MkhController userLogin Start..");
	
		return "user/user_login";
	}
	
	// 로그인 인터셉터 체크
	// 2번째 실행
	@PostMapping(value = "user_login_check")
	public String interCeptor(@ModelAttribute("userInfo") @Valid UserInfo userInfo
							 , BindingResult result     , HttpSession session
							  ) {
		System.out.println("MkhController userLoginCheck Start..");
		System.out.println("MkhController userLoginCheck userInfo.getUser_id()->"+userInfo.getUser_id());
		System.out.println("MkhController userLoginCheck userInfo.getUser_pw()->"+userInfo.getUser_pw());
		
		// Validation 오류시 결과
		if(result.hasErrors()) {
			System.out.println("MkhController user_login_check hasErrors...");
			// 오류 메세지를 띄어주기 위해 forward
			return "forward:user_login";
		}
		
		// Login 검증
		UserInfo userInfoDTO = mkhService.userLoginCheck(userInfo);
		
		if(userInfoDTO != null) {	// userInfo가 있으면 main으로 가라
			System.out.println("user_login_check userInfo exists");
			// 검증된 userInfo를 세션에 담음
			session.setAttribute("userInfo", userInfoDTO);
			System.out.println("session.getAttribute(userInfo)->"+session.getAttribute("userInfo"));
			return "redirect:/main";
		} else {
			System.out.println("user_login_check userInfois not exist");
			return "redirect:/user_login";	// userInfo가 없으면 user_login으로 가라
		}
	}
	// 로그아웃
	@RequestMapping(value = "user_logout")
	public String userLogout(HttpSession session) {
		// 세션 정보 삭제
		session.invalidate();
		return "redirect:/user_login";
	}
	
	/* 회원가입 */
	// 회원가입 동의 페이지로 이동
	@RequestMapping(value = "user_join_agree")
	public String userJoinAgree() {
		System.out.println("MkhController userJoinAgree Start..");
		return "user/user_join_agree";
	}
	
	// 회원가입 페이지로 이동
	@RequestMapping(value = "user_join_write")
	public String userJoinWrite(Model model) {
		System.out.println("MkhController userJoinWrite Start..");
		// classroom 모든 정보 가져옴
		List<ClassRoom> classList = mkhService.createdClass();
		System.out.println("MkhController user_join_write classList.size->"+classList.size());
		model.addAttribute("classList", classList);
		
		return "user/user_join_write";
	}
	
	// 중복확인 (PK를 주고 모든 정보 SELECT)
	@GetMapping(value = "id_confirm")
	public String confirm(String user_id, Model model) {
		// ID을 주면 dto를 돌려주는 메소드
		System.out.println("userId->"+user_id);
		UserInfo userInfo = mkhService.confirm(user_id);
		// 입력한 사번을 중복 확인하고 view로 보내주기 위해 model 사용
		model.addAttribute("userInfo", userInfo);
		if (userInfo != null) {
			System.out.println("중복된 ID..");
			model.addAttribute("msg", "중복된 ID 입니다");
			return "forward:user_join_write";
		} else {
			System.out.println("MkhController confirm 사용 가능한 사번..");
			model.addAttribute("msg", "사용 가능한 ID 입니다");
			return "forward:user_join_write";
		}
	}
	
	// 회원가입 정보 insert
	@PostMapping(value = "writeUserInfo")
	public String writeUserInfo(UserInfo userInfo) {
		System.out.println("MkhController writeUserInfo Start...");
	
		int result = mkhService.insertUserInfo(userInfo);
		if(result > 0) {
			System.out.println("가입완료");
			return "user/user_login";
		} else {
			System.out.println("가입실패");
			return "redirect:user_join_write";
		}
	}
	
	/* 마이페이지 */
	// 마이페이지 수정으로 이동
	@RequestMapping(value = "mypage_main")
	public String mypageMain(HttpServletRequest request, Model model) {
		System.out.println("MkhController mypageMain Start..");
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		
		// 세션값 받음
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		// user_id 마이페이지
		UserInfo userInfoDto = mkhService.confirm(userInfo.getUser_id());
		model.addAttribute("userInfoDto", userInfoDto);
		
		// user_id 환경설정
		UserEnv userEnv = mkhService.selectEnv(userInfo.getUser_id());
		model.addAttribute("userEnv", userEnv);
		
		// user_id 소속
		ClassRoom classRoom = mkhService.selectClass(userInfo.getUser_id());
		model.addAttribute("classRoom", classRoom);
		
		
		return "mypage/mypage_main";
	}
	
	// 개인정보 수정용 비밀번호 확인 페이지
	@RequestMapping(value = "mypage_check_pw")
	public String mypageCheckPw() {
		System.out.println("MkhController mypageCheckPw Start..");
	
		return "mypage/mypage_check_pw";
	}
	
	// 개인정보 수정 페이지로 이동
	@RequestMapping(value = "mypage_update")
	public String mypageUpdate(HttpServletRequest request, Model model, String user_id, String user_pw) {
		System.out.println("MkhController mypage_update_view Start..");
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		
		// 세션에 담긴 값들
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		System.out.println("userInfoDTO.getUser_id()->"+userInfoDTO.getUser_id());
		System.out.println("userInfoDTO.getUser_pw()->"+userInfoDTO.getUser_pw());
		
		// 수정 페이지에서 받은 값들
		System.out.println("user_id->"+user_id);
		System.out.println("user_pw->"+user_pw);
		
		// 반 목록 출력
		List<ClassRoom> classList = mkhService.createdClass();
		model.addAttribute("classList", classList);
		
		
		// DB랑 비교해야됨. 비번 수정하고 바로 또 수정하면 안맞음
		if(!user_id.equals(userInfoDTO.getUser_id())) {
			System.out.println("아이디 다름");
			model.addAttribute("msg", "ID를 다시 확인해주세요.");
			return "forward:/mypage_check_pw";
		} else if (!user_pw.equals(userInfoDTO.getUser_pw())) {
			System.out.println("비밀번호가 다름");
			model.addAttribute("msg", "PW를 다시 확인해주세요.");
			return "forward:/mypage_check_pw";
		} else {
			System.out.println("ID / PW 맞음");
			model.addAttribute("userInfoDTO", userInfoDTO);
			return "mypage/mypage_update";
		}
		
	}
	
	// 수정페이지 업데이트 액션
	@ResponseBody
	@RequestMapping(value = "mypage_update_result")
	public String mypageUpdateResult (UserInfo userInfo) {
		System.out.println("MkhController mypageUpdateResult Start..");

		System.out.println("userInfo.getUser_id()"+userInfo.getUser_id());
		System.out.println("userInfo.getUser_pw()"+userInfo.getUser_pw());
		System.out.println("userInfo.getUser_birth()"+userInfo.getUser_birth());

		int result = mkhService.updateUser(userInfo);

		System.out.println("result->"+result);
		if(result == 1) {
			System.out.println("수정성공");
			return "1";
		} else {
			System.out.println("수정실패");
			return "0";
		}
	}
	
	/* MYPOST - 내가 글 모음*/
	@RequestMapping(value = "mypost_board_list")
	public String mypostBoardList(HttpServletRequest request, Model model) {
		System.out.println("MkhController mypostBoardList Start..");
	    System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
	    // userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    System.out.println("userinfo.getUser_id()->"+userInfoDTO.getUser_id());

		/* 내가 쓴 게시글  Count */
	    
		// 전체 게시판 Count
		int totalBDCount = mkhService.totalBDcount(userInfoDTO);
		System.out.println("totalBdCount->"+totalBDCount);
		model.addAttribute("totalBDCount", totalBDCount);
		
		// Q&A게시판 Count
		int totalBdQna = mkhService.totalQna(userInfoDTO);
		System.out.println("totalBdQnaCount->"+totalBdQna);
		model.addAttribute("totalBdQna", totalBdQna);
		
		// 공용게시판 Count
		int totalBdFree = mkhService.totalFree(userInfoDTO);
		System.out.println("totalFreeCount->"+totalBdFree);
		model.addAttribute("totalBdFree", totalBdFree);
		
		// 프로젝트 & 공지자료 게시판 Count
		int totalDtPrj = mkhService.totalDtPj(userInfoDTO);
		System.out.println("totalDtPjCount->"+totalDtPrj);
		model.addAttribute("totalDtPrj", totalDtPrj);
		
		// 업무보고 게시판 Count
		int totalRepPrj = mkhService.totalRepPj(userInfoDTO);
		System.out.println("totalRepPjCount->"+totalRepPrj);
		model.addAttribute("totalRepPrj", totalRepPrj);
		
		/* 내가 쓴 게시글 List */
		
		// Q&A 게시판
		List<BdQna> qnaList = mkhService.bdQnaList(userInfoDTO);
		System.out.println("MkhController mypostBoardList qnaList.size->"+qnaList.size());
		model.addAttribute("qnaList", qnaList);
		// 공용 게시판
		List<BdFree> freeList = mkhService.bdFreeList(userInfoDTO);
		System.out.println("MkhController mypostBoardList freeList.size->"+freeList.size());
		model.addAttribute("freeList", freeList);
		// 프로젝트 & 공지자료 게시판
		List<PrjBdData> dataPrjList = mkhService.prjDataList(userInfoDTO);
		System.out.println("MkhController mypostBoardList prjList.size->"+dataPrjList.size());
		model.addAttribute("dataPrjList", dataPrjList);
		// 업무보고 게시판
		List<PrjBdRep> RepPrjList = mkhService.prjRepList(userInfoDTO);
		System.out.println("MkhController mypostBoardList RepPrjList.size->"+RepPrjList.size());
		model.addAttribute("RepPrjList", RepPrjList);
		
		
		/* 내가 쓴 댓글 출력 */

		
		return "mypost/mypost_board_list";
	}
	
	// 내가 쓴 댓글
	@RequestMapping(value = "mypost_comment_list")
	public String mypostCommentList(HttpServletRequest request, Model model) {
		System.out.println("MkhController mypostCommentList Start..");
		// userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    
	    /* 내가 쓴 댓글 출력  */
		List<BdFreeComt> freeComt = mkhService.freeComt(userInfoDTO);
		model.addAttribute("freeComt", freeComt);
		
		List<BdDataComt> dataComt = mkhService.dataComt(userInfoDTO);
		model.addAttribute("dataComt", dataComt);
		
		List<BdRepComt> repComt = mkhService.repComt(userInfoDTO);
		model.addAttribute("repComt", repComt);
	    
		return "mypost/mypost_comment_list";
	}
	
	// 내가 추천한 게시글
	@RequestMapping(value = "mypost_good_list")
	public String mypostGoodList(HttpServletRequest request, Model model) {
		System.out.println("MkhController mypostGoodList Start..");
		// userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    
	    /* 내가 추천한 게시글 출력  */
		List<BdQna> qnaGood = mkhService.qnaGood(userInfoDTO);
		System.out.println("MkhController mypostBoardList qnaGood.size->"+qnaGood.size());
		model.addAttribute("qnaGood", qnaGood);
		
		List<BdFree> freeGood = mkhService.freeGood(userInfoDTO);
		System.out.println("MkhController mypostBoardList freeGood.size->"+freeGood.size());
		model.addAttribute("freeGood", freeGood);
		
		List<PrjBdData> prjDataGood = mkhService.prjDataGood(userInfoDTO);
		System.out.println("MkhController mypostBoardList prjDataGood.size->"+prjDataGood.size());
		model.addAttribute("prjDataGood", prjDataGood);
	    
		return "mypost/mypost_good_list";
	}
	
	// 아이디 찾기
	@RequestMapping(value = "user_find_id")
	public String userFindId() {
		System.out.println("MkhController userFindId Start..");
		
		return "user/user_find_id";
	}
	
	// 아이디 찾기 결과
	@RequestMapping(value = "user_find_id_result")
	public String userFindIdResult(UserInfo userInfo, Model model) {
		System.out.println("MkhController userFindIdResult Start..");
		System.out.println("userInfo.getUser_name()->"+userInfo.getUser_name());
		System.out.println("userInfo.getUser_number()->"+userInfo.getUser_number());
		
		UserInfo userInfoDto = mkhService.userFindId(userInfo);
		
		if(userInfoDto == null) {
			System.out.println("이름과 번호가 다름");
			model.addAttribute("msg", "가입되어 있지 않습니다");
			return "forward:/user_find_id";
		}
		System.out.println("이름과 번호가 같음");
		model.addAttribute("userInfoDto", userInfoDto);
		return "user/user_find_id_result";
		
	}
	
	// 비밀번호 찾기
	@RequestMapping(value = "user_find_pw")
	public String userFindPw() {
		System.out.println("MkhController userFindPw Start..");
		
		return "user/user_find_pw";
	}
	
	// 비밀번호 찾기 인증
	@ResponseBody
	@RequestMapping(value = "user_find_pw_auth")
	public String userFindPwAuth(String user_id, String auth_email) {
		System.out.println("MkhController userFindPwAuth Start..");
		
		System.out.println("userFindPwAuth userId->"+user_id);
		System.out.println("auth_email->"+auth_email);
		
		// user_id의 email 가져오기 위함
		UserInfo userInfo = mkhService.confirm(user_id);

		// 입력한 ID가 가입할 때 E-mail과 맞는지 확인		
		if (userInfo != null) {
			System.out.println("ID 존재");
			if(auth_email.equals(userInfo.getUser_email())) {
				System.out.println("이메일 주소가 같음");
				return "1";
			} else {
				System.out.println("이메일 주소 다름");
				return "0";
			}
		} else {
			System.out.println("아이디가 존재X");
			return "2";
		}

	}
	
	// 이메일 값 가져옴 + 이메일 전송
	@ResponseBody
	@PostMapping(value = "send_save_mail")
	public String mailCheck(Model model, String auth_email) {
		System.out.println("MkhController mailCheck Start..");
		
		String toMail = auth_email;
		System.out.println("auth_email->"+toMail);
		String setfrom = "cristalmoon112@gmail.com";
		String title = "[ChoongAng] 인증번호 입니다";
		String authNumber = (int) (Math.random() * 999999) + 1 + "";
		try {
			MimeMessage message = mailSender.createMimeMessage();
															// true는 멀티파트 메세지를 사용하겠다는 의미
			MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
			messageHelper.setFrom(setfrom);
			messageHelper.setTo(toMail);
			messageHelper.setSubject(title); // 생략 가능
			
			messageHelper.setText("이메일 인증번호 입니다: " + authNumber);
			System.out.println("이메일 인증번호 입니다: " + authNumber);
			
			// 첨부파일 보내기 위한 로직
		//	DataSource dataSource = new FileDataSource("c:\\\\log\\\\hwa.png");
		//	messageHelper.addAttachment(MimeUtility.encodeText("ReName.png", "UTF-8", "B"), dataSource);
			mailSender.send(message);
			model.addAttribute("check", 1);  // 정상 전달
			
		} catch (Exception e) {
			System.out.println("mailTransport e.getMessage()->"+e.getMessage());
			model.addAttribute("check", 2);  // 전달 실패
		}  
		return authNumber; 	// 인증번호 돌려줌
	}
	
	// 새로운 비밀번호 만들기 페이지
	@RequestMapping(value = "user_find_pw_new")
	public String userFindPwNew(String user_id, Model model) {
		System.out.println("MkhController userFindPwNew Start..");
		
		System.out.println("user_id ->"+user_id);
		model.addAttribute("user_id", user_id);
		
		return "user/user_find_pw_new";
	}
	
	// 비밀번호 업데이트
	@RequestMapping(value = "user_find_pw_update")
	public String userFindPwUpdate(String user_pw, String user_id) {
		System.out.println("MkhController userFindPwNewUpdate Start..");
		System.out.println("user_id ->"+user_id);
		System.out.println("changing PW ->"+user_pw);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("user_id", user_id);
		map.put("user_pw", user_pw);
		
		int result = mkhService.updatePw(map);
		System.out.println("result->"+result);
	
		return "redirect:/user_login";
	
	}
	

}
