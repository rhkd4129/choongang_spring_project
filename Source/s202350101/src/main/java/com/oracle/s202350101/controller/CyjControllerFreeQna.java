package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.cyjSer.CyjService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CyjControllerFreeQna {
	
	private final CyjService cs;
	
// ------------------------- 자유 게시판 ------------------------------------

	// 자유_리스트
	@RequestMapping(value = "board_free")
	public String boardFree(BdFree bdFree, String currentPage, Model model) {
		System.out.println("CyjControllerQna board_free Start----------------------");
		
		// 총 갯수
		int freeTotal = cs.freeTotal();
		System.out.println("자유 게시판 freeTotal-> " + freeTotal);
		model.addAttribute("freeTotal", freeTotal);
		
		// 추천수 가장 높은 row 3개
		List<BdFree> freeRow = cs.freeRow();
		System.out.println("자유 추천수 가장 높은 row.size()-> " + freeRow.size());
		model.addAttribute("freeRow", freeRow);
		
		// paging 작업
		Paging page = new Paging(freeTotal, currentPage);  // 전달인자 
		bdFree.setStart(page.getStart());
		bdFree.setEnd(page.getEnd());
		model.addAttribute("page", page);
		
		// 전체 리스트
		List<BdFree> freeList = cs.freeList(bdFree);
		System.out.println("자유 게시판 freeList.size()-> " + freeList.size());
		model.addAttribute("freeList", freeList);
		
		return "board/board_free/board_free_list";
	}
	
// ------------------------------------------------------------------

	// 자유_상세 페이지
	@GetMapping(value = "free_content")
	public String freeContent(HttpServletRequest request, int doc_no, Model model) {
		System.out.println("CyjControllerQna free_content Start----------------------");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지 접속자 loginId-> " + loginId);
		
		// 자유_상세
		BdFree freeContent = cs.freeContent(doc_no);
		System.out.println("CyjControllerQna freeContent-> " + freeContent);
		model.addAttribute("freeContent", freeContent);
		
		// 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(freeContent.getUser_id())) {
			result = 1;
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// 자유_조회수
		int freeCount = cs.freeCount(doc_no);
		System.out.println("CyjControllerQna freeCount-> " + freeCount);
		model.addAttribute("freeContent", freeContent);
		
		// 자유_댓글리스트
		List<BdFree> freeComtList = cs.freeComtList(doc_no);
		System.out.println("CyjControllerQna freeComtList-> " + freeComtList);
		model.addAttribute("freeComtList", freeComtList);
		
		return "board/board_free/board_free_content";
	}
	
	// 자유_댓글
	@ResponseBody
	@RequestMapping(value = "ajaxFreeComt")
	public List<BdFreeComt> ajaxfreeComt(BdFreeComt bdFreeComt){
		System.out.println("CyjControllerQna ajaxFreeComt Start----------------------");
		
		// 댓글 입력
		int ajaxComtInsert = cs.ajaxFreeComt(bdFreeComt);
		System.out.println("CyjControllerQna ajaxComtInsert-> " + ajaxComtInsert);
		
		// 입력한 댓글 갖고 옴
		List<BdFreeComt> freeComtList = cs.freeComtList(bdFreeComt);
		System.out.println("CyjControllerQna freeComtList-> " + freeComtList);
		
		return freeComtList;
	}
	
// ------------------------------------------------------------------
	
	// 자유_추천
	@ResponseBody
	@RequestMapping(value = "ajaxFreeGoodCount")
	public String freeGoodCount(HttpServletRequest request, int doc_no) {
		System.out.println("CyjControllerQna ajaxFreeGoodCount Start----------------------");
		
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
		int freeConfirm = cs.goodConfirm(bdFreeGood);
		System.out.println("CyjControllerQna freeConfirm-> " + freeConfirm);
		
		String result = "";
		BdFree bdFree = new BdFree();
		bdFree.setDoc_no(doc_no);
		
		if (freeConfirm == 1) {		// 중복 O
			result = "duplication";
		} else {					// 중복 X
			// 2. 추천 insert
			int freeGoodInsert = cs.notifyGoodInsert(bdFreeGood);
			System.out.println("CyjControllerQna freeGoodInsert-> " + freeGoodInsert);
			
			if (freeGoodInsert == 1) {
				// 3. 추천 update
				int freeGoodUpdate = cs.notifyGoodUpdate(bdFreeGood);
				System.out.println("CyjControllerQna freeGoodUpdate-> " + freeGoodUpdate);
				// 4. 추천 select
				int freeGoodSelect = cs.notifyGoodSelect(bdFree);
				System.out.println("CyjControllerQna freeGoodSelect-> " + freeGoodSelect);
				result = String.valueOf(freeGoodSelect);
			} else {
				result = "error";  // error
			}
		}
		return result;
	}
	
// ------------------------------------------------------------------

	// 자유_새 글 입력하기 위한 페이지 이동             
	@RequestMapping(value = "free_insert_from")
	public String freeInsert(Model model) {
		System.out.println("CyjControllerQna free_insert_from Start----------------------");
		
		BdFree bdFree = new BdFree();
		model.addAttribute("bdFree", bdFree);
		
		return "board/board_free/board_free_insert";
	}
	
	// 새 글 입력 
	@PostMapping(value = "free_insert")
	public String freeInsert(@Valid @ModelAttribute BdFree bdFree, BindingResult bindingResult
							 ,@RequestParam(value = "file1", required = false) MultipartFile file1
							 ,HttpServletRequest request, Model model) throws IOException {
		System.out.println("CyjControllerQna free_insert Start----------------------");
		
		if (bindingResult.hasErrors()) {
			System.out.println("CyjControllerQna free_insert hasErrors");
			return "board/board_free/board_free_insert";
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
		
		// loginId : 새 글 작성에는 접속자이자 작성자 
		String loginId = userInfoDTO.getUser_id();
		System.out.println(request.getSession().getAttribute("loginId"));
		bdFree.setUser_id(loginId);
		
		if(!file1.isEmpty()) {
			log.info("파일이 존재합니다  ");
			bdFree.setAttach_path(attach_path);
			bdFree.setAttach_name(saveName);
		}	
		
		int freeInsert = cs.freeInsert(bdFree);
		System.out.println("free free_insert 새 글 입력-> " + freeInsert);
		
		model.addAttribute("freeInsert", freeInsert);
		
		return "redirect:/board_free";
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
	
// ------------------------------------------------------------------

	// 자유_수정: 상세 정보 get 
	@GetMapping(value = "free_update")
	public String freeUpdate(int doc_no, Model model) {
		System.out.println("CyjControllerQna free_update Start----------------------");
		
		BdFree content = cs.freeContent(doc_no);
		System.out.println("CyjControllerQna content-> " + content);
		
		model.addAttribute("content", content);
		
		return "board/board_free/board_free_update";
	}
	
	// 수정
	@PostMapping(value = "free_update2")
	public String freeUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjControllerQna free_update2 Start----------------------");
		
		int freeUpdate = cs.freeUpdate(bdFree);
		System.out.println("CyjControllerQna freeUpdate-> " + freeUpdate);
		
		model.addAttribute("freeUpdate", freeUpdate);
		
		redirectAttributes.addAttribute("doc_no", bdFree.getDoc_no());
		
		return "redirect:/free_content?doc_no={doc_no}";
	}
		
// ------------------------------------------------------------------

	// 자유_삭제 
	@ResponseBody
	@RequestMapping(value = "freeDelete")
	public int ajaxFreeDelete(int doc_no) {
		System.out.println("CyjControllerQna ajaxFreeDelete Start----------------------");
		
		int ajaxFreeDelete = cs.freeDelete(doc_no);
		System.out.println("CyjControllerQna ajaxFreeDelete-> " + ajaxFreeDelete);
		
		return ajaxFreeDelete;
	}
	
// ----------------------------------------------------------------------		
// ------------------------- qna 게시판 ------------------------------------
	
	// QNA_리스트
	@RequestMapping(value = "board_qna")
	public String boardQna(BdQna bdQna, String currentPage, Model model) {
		System.out.println("CyjControllerQna board_qna Start----------------------");
		
		// 갯수 (전체 or 분류검색)
		int qnaTotalCount = cs.qnaSelectCount(bdQna);
		System.out.println("CyjControllerQna qnaTotalCount-> " + qnaTotalCount);
		model.addAttribute("qnaTotalCount", qnaTotalCount);
		
		// 추천수 가장 높은 row 3개
		List<BdQna> qnaRow = cs.qnaRow();
		System.out.println("CyjControllerQna qnaRow.size()-> " + qnaRow.size());
		model.addAttribute("qnaRow", qnaRow);
		
		// paging 작업
		Paging page = new Paging(qnaTotalCount, currentPage);
		bdQna.setStart(page.getStart());
		bdQna.setEnd(page.getEnd());
		model.addAttribute("page", page);
		
		// 리스트 (전체 or 분류검색)
		List<BdQna> qnaList = cs.qnaList(bdQna);
		System.out.println("CyjControllerQna qnaList.size()-> " + qnaList.size());
		model.addAttribute("qnaList", qnaList);
		
		// 검색 분류 코드 가져오기
		Code code = new Code();
		code.setTable_name("BD_QNA");
		code.setField_name("BD_CATEGORY");
		 
		// qna_검색 분류 코드 가져오기
		List<Code> codeList = cs.codeList(code); 
		System.out.println("CyjControllerQna codeList-> " + codeList);
		model.addAttribute("codeList", codeList);
		
		return "board/board_qna/board_qna_list";
	}
	
// ----------------------------------------------------------------------		

	// QNA_원글 작성하기 위한 페이지 이동
	@RequestMapping(value = "qna_insert_form")
	public String qnaInsertForm(HttpServletRequest request, Model model) {
		System.out.println("CyjControllerQna qna_insert_form Start----------------------");
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		BdQna bdQna = new BdQna();		
		model.addAttribute("doc_group", "0");
		model.addAttribute("doc_step", "0");
		model.addAttribute("doc_indent", "0");
		model.addAttribute("subject", "");
		model.addAttribute("parent_doc_no", "0");
		model.addAttribute("parent_doc_user_id", "");
		model.addAttribute("bd_category", "");	
		model.addAttribute("userInfoDTO", userInfoDTO); // 로그인 사용자 정보 
		
		// 답변등록의 경우, 기존 답변들 Doc_Step처리
		int parent_doc_no = bdQna.getParent_doc_no();
		if (parent_doc_no > 0) {
			// 답변 순서 조절
			int qnaReply = cs.qnaReply(bdQna);
			bdQna.setDoc_step(bdQna.getDoc_no() + 1);
			bdQna.setDoc_indent(bdQna.getDoc_indent() + 1);
		}
		return "board/board_qna/board_qna_insert";
	}
	
	// 원글 작성
	@PostMapping(value = "qna_insert")
	public String qnaInsert(@Valid @ModelAttribute BdQna bdQna, BindingResult bindingResult
						   ,@RequestParam(value = "file1", required = false) MultipartFile file1
						   ,HttpServletRequest request , Model model) throws IOException {
		System.out.println("CyjControllerQna qna_insert Start----------------------");

		// 폼 에러검사 해야함 
		// !!!!!
		
		// file Upload
		String attach_path = "upload"; 	  // 실제 파일이 저장되는 폴더명, uploadPath의 이름과 동일하게 해야 오류 X
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/"); // 저장 위치 지정 
		
		System.out.println("CyjController File Upload Post Start");
		
		log.info("originalName : " + file1.getOriginalFilename());		// 원본 파일명
		log.info("size : "         + file1.getSize());					// 파일 사이즈
		log.info("contextType : "  + file1.getContentType());			// 파일 타입
		log.info("uploadPath : "   + uploadPath);						// 파일 저장되는 주소
		
		String saveName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);  // 저장되는 파일명 
		log.info("saveName: " + saveName);
			
		// userInfo 정보 
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 새 글 작성에는 접속자이자 작성자 
		String loginId = userInfoDTO.getUser_id();
		bdQna.setUser_id(loginId);
		System.out.println(request.getSession().getAttribute("loginId"));
		
		if(!file1.isEmpty()) {
			log.info("파일이 존재합니다  ");
			bdQna.setAttach_path(attach_path);
			bdQna.setAttach_name(saveName);
		}	
		
		// 새 글 입력 (원글)
		int qnaInsert = cs.qnaInsert(bdQna);
		System.out.println("CyjControllerQna qnaInsert-> " + qnaInsert);
		model.addAttribute("qnaInsert", qnaInsert);
		
		return "redirect:/board_qna";
	}
	
// ----------------------------------------------------------------------		

	// 상세 페이지
	@GetMapping(value = "qna_content")
	public String qnaContent(HttpServletRequest request, int doc_no, Model model) {
		System.out.println("CyjControllerQna qna_content");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		model.addAttribute("userInfoDTO", userInfoDTO);
		
		// loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
		String loginId = userInfoDTO.getUser_id();
		System.out.println("상세페이지 접속자 loginId-> " + loginId);
		
		// qna_상세
		BdQna qnaContent = cs.qnaContent(doc_no);
		System.out.println("CyjControllerQna qnaContent-> " + qnaContent);
		model.addAttribute("qnaContent", qnaContent);
		
		// 접속자와 작성자 비교 --> 같으면 1, 다르면 0
		int result = 0;
		if (loginId.equals(qnaContent.getUser_id())) {
			result = 1;
		} else {
			result = 0;
		}
		model.addAttribute("result", result);
		
		// qna_조회수
		int qnaCount = cs.qnaCount(doc_no);
		System.out.println("CyjControllerQna qnaCount-> " + qnaCount);
		model.addAttribute("qnaCount", qnaCount);
		
		return "board/board_qna/board_qna_content";
	}
	
	// 상세페이지 내부 (답글)
	@PostMapping(value = "qna_content_replyInsert")
	public String qnaContentReplyInsert(BdQna bdQna, Model model) {
		
		System.out.println("CyjControllerQna subject: "  + bdQna.getSubject());
		System.out.println("CyjControllerQna doc_body: " + bdQna.getDoc_body());
		
		// 기존 답변들 Doc_Step 처리
		int parent_doc_no = bdQna.getParent_doc_no();
		if (parent_doc_no > 0) {
			// 답변 순서 조절
			int qnaReply = cs.qnaReply(bdQna);
			bdQna.setDoc_step(bdQna.getDoc_no() + 1);
			bdQna.setDoc_indent(bdQna.getDoc_indent() + 1);
		}
		
		// 새 글 작성 (답글)
		int qnaInsert = cs.qnaInsert(bdQna);
		System.out.println("CyjControllerQna qnaInsert-> " + qnaInsert);
		model.addAttribute("qnaInsert", qnaInsert);
		
		return "redirect:/board_qna";
	}
	
// ----------------------------------------------------------------------		
	
	// qna_추천
	@ResponseBody
	@RequestMapping(value = "ajaxQnaGoodCount")
	public String qnaGoodCount(HttpServletRequest request, int doc_no) {
		System.out.println("CyjControllerQna ajaxQnaGoodCount Start");
		
		System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		// loginId : 추천에서 접속자
		String loginId = userInfoDTO.getUser_id();
		System.out.println("추천 접속자 loginId-> " + loginId);
		
		BdQnaGood bdQnaGood = new BdQnaGood();
		bdQnaGood.setDoc_no(doc_no);
		bdQnaGood.setUser_id(loginId);
		System.out.println(bdQnaGood.getDoc_no());
		System.out.println(bdQnaGood.getUser_id());
		
		// 1. 추천자 목록에 있는지 중복 체크
		int qnaConfirm = cs.qnaConfirm(bdQnaGood);
		System.out.println("CyjControllerQna qnaConfirm-> " + qnaConfirm);
		
		String result = "";
		BdQna bdQna = new BdQna();
		bdQna.setDoc_no(doc_no);
		
		if (qnaConfirm == 1) {  	  // 중복 O
			result = "duplication";
		} else {					  // 중복 X
			// 2. 추천 insert
			int qnaGoodInsert = cs.qnaGoodInsert(bdQnaGood);
			System.out.println("CyjControllerQna qnaGoodInsert-> " + qnaGoodInsert);
			
			if (qnaGoodInsert == 1) {
				// 3. 추천 update
				int qnaGoodUpdate = cs.qnaGoodUpdate(bdQnaGood);
				System.out.println("CyjControllerQna qnaGoodUpdate-> " + qnaGoodUpdate);
				// 4. 추천 select 
				int qnaGoodSelect = cs.qnaGoodSelect(bdQna);
				System.out.println("CyjControllerQna qnaGoodSelect-> " + qnaGoodSelect);
				result = String.valueOf(qnaGoodSelect);
			} else {
				result = "error";  // error
			}
		}
		return result;
	}
	
// ----------------------------------------------------------------------		

	// qna_수정: 상세 정보 get
	@GetMapping(value = "qna_update")
	public String qnaUpdate(int doc_no, Model model) {
		System.out.println("CyjControllerQna qna_update Start");
		
		BdQna content = cs.qnaContent(doc_no);
		System.out.println("CyjControllerQna content-> " + content);
		model.addAttribute("content", content);
		
		return "board/board_qna/board_qna_update";
	}
	
	// qna_수정
	@PostMapping(value = "qna_update2")
	public String qnaUpdate2(BdQna bdQna, Model model, RedirectAttributes redirectAttributes) {
		System.out.println("CyjControllerQna qna_update2 Start");
		
		int qnaUpdate = cs.qnaUpdate(bdQna);
		System.out.println("CyjControllerQna qnaUpdate-> " + qnaUpdate);
		model.addAttribute("qnaUpdate", qnaUpdate);
		
		redirectAttributes.addAttribute("doc_no",bdQna.getDoc_no());
		
		return "redirect:/qna_content?doc_no={doc_no}";
	}
	
// ----------------------------------------------------------------------		
		
	// qna_삭제
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
