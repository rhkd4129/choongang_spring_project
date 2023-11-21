package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.UserEnv;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.kjoSer.UserInfoService;
import com.oracle.s202350101.service.mkhser.MkhService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MkhController {
	// kjo 서비스 사용
	private final UserInfoService uis;
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
	public String interCeptor(UserInfo userInfo, HttpSession session, Model model) {
		System.out.println("MkhController userLoginCheck Start..");
		System.out.println("MkhController userLoginCheck userInfo.getUser_id()->"+userInfo.getUser_id());
		System.out.println("MkhController userLoginCheck userInfo.getUser_pw()->"+userInfo.getUser_pw());
		
		// Login 검증
		UserInfo userInfoDTO = mkhService.userLoginCheck(userInfo);
		
		if(userInfo.getUser_id() == "" && userInfo.getUser_pw() == "") {
			System.out.println("ID, PW 공백");
			model.addAttribute("idMsgBox", "아이디를 입력해 주세요.");
			model.addAttribute("pwMsgBox", "비밀번호를 입력해 주세요.");
			return "forward:/user_login";
		} else if (userInfo.getUser_id() == "") {
			System.out.println("ID 공백");
			model.addAttribute("idMsgBox", "아이디를 입력해 주세요.");
			return "forward:/user_login";
		} else if (userInfo.getUser_pw() == "") {
			System.out.println("PW 공백");
			model.addAttribute("user_id", userInfo.getUser_id());
			model.addAttribute("pwMsgBox", "비밀번호를 입력해 주세요.");
			return "forward:/user_login";
		} else if (userInfoDTO == null) {
			System.out.println("ID or PW 틀림");
			model.addAttribute("pwMsgBox", "아이디 또는 비밀번호를 확인해 주세요.");
			return "forward:/user_login";
		} else {
			System.out.println("user_login_check userInfo exists");
			session.setAttribute("userInfo", userInfoDTO);
			System.out.println("session.getAttribute(userInfo)->"+session.getAttribute("userInfo"));
			return "redirect:/main";
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
	
	// 회원가입 페이지 ajax
//	@ResponseBody
//	@RequestMapping(value = "validation_ajax")
//	public String validationAjax(@ModelAttribute("userInfo") @Valid UserInfo userInfo
//			  					, BindingResult bindingResult, Model model) {
//		System.out.println("MkhController writeUserIvalidationAjaxnfo Start...");
//		if(bindingResult.hasErrors()) {
//			System.out.println("MkhController user_login_check hasErrors...");
//			// 오류 메세지를 띄어주기 위해 forward
//			return "forward:user_join_write";
//		} else {
//			model.addAttribute("userInfo", userInfo.getUser_id());
//			return "";
//		}
//	}
	
	// 중복확인 (PK를 주고 모든 정보 SELECT) .Ver 1
	@ResponseBody
	@GetMapping(value = "id_confirm")
	public String confirm(String user_id, Model model) {
		// ID을 주면 dto를 돌려주는 메소드
		System.out.println("userId->"+user_id);
		UserInfo userInfo = mkhService.confirm(user_id);
		// 입력한 사번을 중복 확인하고 view로 보내주기 위해 model 사용
		if (userInfo != null) {
			System.out.println("중복된 ID..");
			return "1";
		} else {
			System.out.println("MkhController confirm 사용 가능한 사번..");
			model.addAttribute("user_id", user_id);
			return "2";
		}
	}
	
	
	// 회원가입 정보 insert (ver.1)
	@PostMapping(value = "write_user_info")
	public String writeUserInfo(HttpServletRequest request
							  , @RequestParam(value = "file1", required = false)MultipartFile file1
							  , @ModelAttribute("userInfo") @Valid UserInfo userInfo
							  , BindingResult bindingResult, Model model
							  , String sample6_postcode		, String sample6_address
							  , String sample6_detailAddress, String sample6_extraAddress
							   )
									  throws IOException {
		System.out.println("MkhController writeUserInfo Start...");
		// Validation 오류시 결과
		if(bindingResult.hasErrors()) {
			System.out.println("MkhController user_login_check hasErrors...");
			// 오류 메세지를 띄어주기 위해 forward
			return "forward:user_join_write";
		} else {
			model.addAttribute("userInfo", userInfo);
		}
		System.out.println("sample6_postcode->"+sample6_postcode);
		System.out.println("sample6_detailAddress->"+sample6_detailAddress);
//		ample6_address == null || sample6_detailAddress == null || sample6_extraAddress == null
		// 주소
		if(sample6_postcode.isEmpty() || sample6_address.isEmpty()) {
			System.out.println("주소값 NULL");
			userInfo.setUser_address("");
		} else {
			String user_address = sample6_postcode +"~"+ sample6_address +"~"+ sample6_detailAddress + sample6_extraAddress;
			System.out.println("user_address->"+ user_address);
			userInfo.setUser_address(user_address);
		}
		
		// user_Env
//		userInfo.setEnv_alarm_comm(userEnv.getEnv_alarm_comm());
		
		
		// 이미지파일 업로드
		String attach_path = "upload";	// 파일경로
		
		// userInfo에 디폴트 이미지와 파일경로 세팅
		userInfo.setAttach_name("user_default.png");
		userInfo.setAttach_path(attach_path);
		
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
		System.out.println("userInfo.getUser_id() -> " + userInfo.getUser_id());
		
		// user_id 마이페이지
		UserInfo userInfoDto = mkhService.confirm(userInfo.getUser_id());
		model.addAttribute("userInfoDto", userInfoDto);
		
		// user_id 환경설정
		UserEnv userEnv = mkhService.selectEnv(userInfo.getUser_id());
		model.addAttribute("userEnv", userEnv);
		
		// user_id 소속
		ClassRoom classRoom = mkhService.selectClass(userInfo.getUser_id());
		model.addAttribute("classRoom", classRoom);
		
		// user_birth
		if(userInfoDto.getUser_birth() != null) {
			System.out.println("생일있음");
			Date birth = userInfoDto.getUser_birth();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String user_birth = sd.format(birth);
			model.addAttribute("user_birth", user_birth);
		} else
			model.addAttribute("user_birth", "");
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
	public String mypageUpdate(HttpServletRequest request, Model model, UserInfo userInfo, HttpSession session) {
		System.out.println("MkhController mypage_update_view Start..");
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		
		// 세션에 담긴 값들
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		System.out.println("userInfoDTO.getUser_id()->"+userInfoDTO.getUser_id());
		System.out.println("userInfoDTO.getUser_pw()->"+userInfoDTO.getUser_pw());
		
		// user_birth
		if(userInfoDTO.getUser_birth() != null) {
			System.out.println("생일있음");
			Date birth = userInfoDTO.getUser_birth();
			SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
			String user_birth = sd.format(birth);
			model.addAttribute("user_birth", user_birth);
		} else
			model.addAttribute("user_birth", "");
		
		// 주소값 slice
		System.out.println("userInfoDTO.getUser_address()->"+userInfoDTO.getUser_address());
		String address = userInfoDTO.getUser_address();
		System.out.println("address -> " + address);
		//if(!(address == null)) 
		log.info("{}",address != null); 
		if(address != null && !address.isEmpty() ) {			//	address != null
			String[] addressList = address.split("~");
			
			String sample6_postcode = addressList[0];
			String sample6_address = addressList[1];
			if(addressList.length < 4) {
				String detailAddress = addressList[2];
				if(detailAddress.contains("(")) {
					String[] addressdetailList = detailAddress.split("\\(");
					String sample6_detailAddress = addressdetailList[0];
					String sample6_extraAddress = "(" + addressdetailList[1];

					model.addAttribute("detailAddress", sample6_detailAddress);
					model.addAttribute("extraAddress", sample6_extraAddress);
				} else {
					model.addAttribute("detailAddress", detailAddress);
				}
			}

			model.addAttribute("postcode", sample6_postcode);
			model.addAttribute("address", sample6_address);
		} else 
			userInfoDTO.setUser_address("");
		
		// 반 목록 출력
		List<ClassRoom> classList = mkhService.createdClass();
		// validation 에러처리 하기 위해 modelAttribute="userInfo"로 보내기 위해 new
		UserInfo userinfo = new UserInfo();
		model.addAttribute("userInfo", userinfo);
		model.addAttribute("classList", classList);
		
		// 수정 페이지에서 받은 값들
		System.out.println("user_id->"+userInfo.getUser_id());
		System.out.println("user_pw->"+userInfo.getUser_pw());
			
		if(!userInfo.getUser_id().equals(userInfoDTO.getUser_id()) || 
		   !userInfo.getUser_pw().equals(userInfoDTO.getUser_pw())) {
			System.out.println("아이디와 비밀번호 재확인 인증 실패");
			model.addAttribute("msg", "ID와 PW를 다시 확인해주세요.");
			return "forward:/mypage_check_pw";
		} else {
			// 재확인 인증 성공시 로직으로 검증
			userInfoDTO = mkhService.userLoginCheck(userInfo);
			if(userInfoDTO == null) {
				System.out.println("인증 실패");
				model.addAttribute("msg", "ID와 PW를 다시 확인해주세요.");
				return "forward:/mypage_check_pw";
			} else {
				System.out.println("인증 성공");
				session.setAttribute("userInfoDTO", userInfoDTO);
				return "mypage/mypage_update";
			}
		}
	}
	
	// 수정페이지 이미지첨부 + update
	@PostMapping(value = "mypage_update_result")
	public String mypageUpdateResult (@ModelAttribute("userInfo") @Valid UserInfo userInfo
									, BindingResult bindingResult
									, HttpServletRequest request
									, Model model
									, String sample6_postcode	  , String sample6_address
								    , String sample6_detailAddress, String sample6_extraAddress
									, @RequestParam(value = "file1", required = false)MultipartFile file1) throws IOException {
		System.out.println("MkhController mypageUpdateResult Start..");
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		if(bindingResult.hasErrors()) {
			System.out.println("validation 에러 발생");
			// 에러 발생해도 반 목록 출력
			List<ClassRoom> classList = mkhService.createdClass();
			model.addAttribute("classList", classList);
			
			return "mypage/mypage_update";
		}
		
		// 주소
		if(sample6_postcode.isEmpty() || sample6_address.isEmpty()) {
			System.out.println("주소값 NULL");
			userInfo.setUser_address("");
		} else {
			String user_address = sample6_postcode +"~"+ sample6_address +"~"+ sample6_detailAddress + sample6_extraAddress;
			System.out.println("user_address->"+ user_address);
			userInfo.setUser_address(user_address);
		}
		
		// 생일
//		if(userInfo.getUser_birth() != null) {
//			Date date = userInfo.getUser_birth();
//			System.out.println("userInfo.getUser_birth()->"+date);
//			userInfo.setUser_birth(date);
//		}
		
		// 이미지 첨부
		if(file1.getSize() > 0) {
			System.out.println("파일이 있다...!");
			System.out.println("mypageUpdateResult userInfo.getUser_id()->"+userInfo.getUser_id());
			String attach_path = "upload";	// 파일경로
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");		// 저장 위치 주소 지정 (webapp 아래 폴더)
			
			System.out.println("File Upload Post Start");
			
			log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
			log.info("size : " + file1.getSize());							// 파일 사이즈
			log.info("contextType : " + file1.getContentType());			// 파일 타입
			log.info("uploadPath : " + uploadPath);							// 파일 저장되는 주소
			
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);	// 저장되는 파일명
			log.info("Return savedName : " + savedName);
			
			// userInfo에 파일명과 파일경로 세팅
			userInfo.setAttach_name(savedName);
			userInfo.setAttach_path(attach_path);
		} else {
			System.out.println("파일이 변경이 없다면");
			System.out.println("userInfo.getAttach_name()->" + userInfoDTO.getAttach_name());
			System.out.println("userInfo.getAttach_path()->" + userInfoDTO.getAttach_path());
			String attach_name = userInfoDTO.getAttach_name();
			String attach_path = userInfoDTO.getAttach_path();

			userInfo.setAttach_name(attach_name);
			userInfo.setAttach_path(attach_path);
		}
		

		int result = mkhService.updateUser(userInfo);
		System.out.println("result->"+result);
		if(result == 1) {
			System.out.println("수정성공");
			// 이미지 update가 성공하면 세션정보 최신화
			// kjo 서비스 내에 findID메소드를 사용
			UserInfo us = uis.findbyuserId(userInfo);
			// 세션정보 최신화
			request.getSession().setAttribute("userInfo", us);
			return "redirect:/mypage_main";
		} else {
			System.out.println("수정실패");
			return "redirect:/mypage_update";
		}
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
		
		// 파일을 복사함.
		// bytes : 컨텐츠, target : 저장경로와 저장이름이 담겨있는 객체
		FileCopyUtils.copy(bytes, target);	// org.springframework.util.FileCopyUtils
		
		return savedName;
	}
	
	/* 환경설정 */
	@ResponseBody
	@RequestMapping(value = "user_env")
	public String userEnvUpdate(UserEnv userEnv, HttpServletRequest request) {
		System.out.println("MkhController userEnvUpdate Start..");
		
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		userEnv.setUser_id(userInfoDTO.getUser_id());
		
		System.out.println("userEnv.userEnv.getUser_id->"+userEnv.getUser_id());
		System.out.println("userEnv.getEnv_alarm_comm()->"+userEnv.getEnv_alarm_comm());
		System.out.println("userEnv.userEnv.getEnv_alarm_meeting()->"+userEnv.getEnv_alarm_meeting());
		
		if(userEnv.getEnv_alarm_mine() == null) {
			userEnv.setEnv_alarm_mine("Y");
		}
		
		int result = mkhService.updateEnv(userEnv);
		
		if(result > 0) {
			System.out.println("수정완료");
			return "1";
		}
		System.out.println("수정실패");
		return "0";
	}
	
	/* MYPOST - 내 글 모음*/
	@RequestMapping(value = "mypost_board_list")
	public String mypostBoardList(PrjBdData prjBdData, HttpServletRequest request, Model model, @RequestParam(defaultValue = "1") String currentPage) {
		System.out.println("MkhController mypostBoardList Start..");
	    System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
	    // userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    
	    prjBdData.setUser_id(userInfoDTO.getUser_id());
	    
		/* 내가 쓴 게시글  Count */
		int totalBDCount = mkhService.totalBDcount(prjBdData);
		System.out.println("totalBdCount->"+totalBDCount);
		model.addAttribute("totalBDCount", totalBDCount);
		
		/* 내가 쓴 게시글 List */
 		// paging 작업
 		Paging page = new Paging(totalBDCount, currentPage);
 		prjBdData.setStart(page.getStart());
 		prjBdData.setEnd(page.getEnd());
 		model.addAttribute("page", page);
 		
 		//검색 분류코드 가져오기
		Code code = new Code();
		code.setTable_name("MYPOST_BOARD");
		code.setField_name("MYPOST_CATEGORY");
		//-----------------------------------------------------
		List<Code> search_codelist = mkhService.codeList(code);
		//-----------------------------------------------------
		model.addAttribute("search", prjBdData.getSearch()); //검색필드
		model.addAttribute("keyword", prjBdData.getKeyword()); //검색어
		model.addAttribute("search_codelist", search_codelist); //검색 분류
 	
 		// Select ALL
 		List<PrjBdData> selectAll = mkhService.bdSelectAll(prjBdData);
		System.out.println("MkhController mypostBoardList bdSelectAll.size->"+selectAll.size());
		model.addAttribute("selectAll", selectAll);
		
//		System.out.println("app_id : " + selectAll.get(0).getApp_id());
//		System.out.println("app_name : " + selectAll.get(0).getApp_name());
//		System.out.println("Subject : " + selectAll.get(0).getSubject());
		
		return "/mypost/mypost_board_list";
	}
	
	// 내가 쓴 댓글
	@RequestMapping(value = "mypost_comment_list")
	public String mypostCommentList(@RequestParam(defaultValue = "1") String currentPage
									, HttpServletRequest request, Model model, PrjBdData prjBdData) {
		System.out.println("MkhController mypostCommentList Start..");
		// userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
	    
	    prjBdData.setUser_id(userInfoDTO.getUser_id());
	    // All Comment Count
	    int totalComt = mkhService.totalComt(prjBdData);
		System.out.println("totalComt->"+totalComt);
		model.addAttribute("totalComt", totalComt);

	  	/* 내가 쓴 댓글 출력  */
		// paging 작업
	  	Paging page = new Paging(totalComt, currentPage);
	  	prjBdData.setStart(page.getStart());
	  	prjBdData.setEnd(page.getEnd());
	  	model.addAttribute("page", page);
	  	
	  	//검색 분류코드 가져오기
  		Code code = new Code();
  		code.setTable_name("MYPOST_COMMENT");
  		code.setField_name("MYPOST_CATEGORY");
  		//-----------------------------------------------------
  		List<Code> search_codelist = mkhService.codeList(code);
  		//-----------------------------------------------------
  		model.addAttribute("search", prjBdData.getSearch()); //검색필드
  		model.addAttribute("keyword", prjBdData.getKeyword()); //검색어
  		model.addAttribute("search_codelist", search_codelist); //검색 분류
	    
 		List<BdDataComt> selectAllComt = mkhService.selectAllComt(prjBdData);
 		model.addAttribute("selectAllComt", selectAllComt);
	  	
		return "mypost/mypost_comment_list";
	}
	
	// 내가 추천한 게시글
	@RequestMapping(value = "mypost_good_list")
	public String mypostGoodList(HttpServletRequest request, Model model, String currentPage, PrjBdData prjBdData) {
		System.out.println("MkhController mypostGoodList Start..");
		// userInfo 세션값 받아와서 userInfoDTO로 사용
	    UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

		prjBdData.setUser_id(userInfoDTO.getUser_id());
	    // All Good Count
	    int totalGood = mkhService.totalGood(prjBdData);
		System.out.println("totalGood->"+totalGood);
		model.addAttribute("totalGood", totalGood);

	  	/* 내가 추천한 게시글 출력  */
		// paging 작업
	  	Paging page = new Paging(totalGood, currentPage);
	  	prjBdData.setStart(page.getStart());
	  	prjBdData.setEnd(page.getEnd());
	  	model.addAttribute("page", page);
	  	
	  	//검색 분류코드 가져오기
  		Code code = new Code();
  		code.setTable_name("MYPOST_BOARD");
  		code.setField_name("MYPOST_CATEGORY");
  		//-----------------------------------------------------
  		List<Code> search_codelist = mkhService.codeList(code);
  		//-----------------------------------------------------
  		model.addAttribute("search", prjBdData.getSearch()); //검색필드
  		model.addAttribute("keyword", prjBdData.getKeyword()); //검색어
  		model.addAttribute("search_codelist", search_codelist); //검색 분류
	    
 		List<BdDataGood> selectAllGood = mkhService.selectAllGood(prjBdData);
 		model.addAttribute("selectAllGood", selectAllGood);
		
		return "mypost/mypost_good_list";
	}
	
	/* 로그인 페이지 */
	
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
		
//		String auth_email = auth_email;
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
			} else if (auth_email.equals("")) {
				System.out.println("이메일 주소 없음");
				return "3";
			} else {
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
