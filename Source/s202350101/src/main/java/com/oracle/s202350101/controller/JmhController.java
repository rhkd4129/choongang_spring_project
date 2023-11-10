package com.oracle.s202350101.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.model.Paging;
import com.oracle.s202350101.service.jmhSer.JmhServicePrjBdDataImpl;
import com.oracle.s202350101.service.jmhSer.JmhServicePrjBdRepImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class JmhController {
	
	private final JmhServicePrjBdDataImpl jmhDataSer;
	private final JmhServicePrjBdRepImpl jmhRepSer;

	//#######################################################################
	//############  프로젝트 프로젝트 공지/자료 게시판 prj_board_data_OOO  ############
	//#######################################################################

	
	//프로젝트 공지/자료 게시판 목록
	@RequestMapping(value = "prj_board_data_list")
	public String prjBoardDataList(PrjBdData prjBdData, String currentPage, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 목록(prj_board_data_list) START-----");
		
		// prj_board_data 전체 Count
		//---------------------------------------
		int totalCount = jmhDataSer.totalCount();
		//---------------------------------------
		
		//Page 작업
		Paging 	page = new Paging(totalCount, currentPage);
		
		//Parameter emp --> Page만 추가 Setting
		prjBdData.setStart(page.getStart()); 	//시작시 1
		prjBdData.setEnd(page.getEnd());		//시작시 10
		
		//--------------------------------------------------------------
		List<PrjBdData> prjBdDataList = jmhDataSer.boardList(prjBdData);
		//--------------------------------------------------------------
		
		model.addAttribute("boardList", prjBdDataList); 
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		
		System.out.println("-----프로젝트 공지/자료 게시판 목록(prj_board_data_list) END-----");
		
		return "/project/board/prj_board_data_list";
	}

	//프로젝트 공지/자료 게시판 - 작성화면
	@RequestMapping(value = "prj_board_data_write")
	public String prjBoardDataWrite(Model model, HttpServletRequest request) {
		System.out.println("-----프로젝트 공지/자료 게시판 작성(prj_board_data_write) START-----");
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		//오늘날짜
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("오늘날짜->" + df.format(date));
		String todayDate = df.format(date);
		
		//분류코드 가져오기
		Code code = new Code();
		code.setTable_name("PRJ_BD_DATA");
		code.setField_name("BD_CATEGORY");
		//----------------------------------------------------------
		List<Code> bd_category_codelist = jmhDataSer.codeList(code);
		//----------------------------------------------------------
		System.out.println("bd_category_codelist-->"+bd_category_codelist);
		if(bd_category_codelist == null) {
			model.addAttribute("errorMsg", "분류가져오기 실패");
			return "error";
		}
		
		PrjBdData prjBdData = new PrjBdData();
		
		String parent_doc_no = request.getParameter("parent_doc_no");
		String project_id = request.getParameter("project_id");
		
		if(parent_doc_no != null && !parent_doc_no.equals("")) {
			//답변작성인 경우
			System.out.println("parent_doc_no:"+parent_doc_no);
			
			prjBdData.setDoc_no(Integer.parseInt(parent_doc_no));
			prjBdData.setProject_id(Integer.parseInt(project_id));
			//------------------------------------------------------------
			PrjBdData selectPrjBdData = jmhDataSer.selectBoard(prjBdData); //원글
			//------------------------------------------------------------
			
			model.addAttribute("doc_group", selectPrjBdData.getDoc_group());
			model.addAttribute("doc_step", selectPrjBdData.getDoc_step());
			model.addAttribute("doc_indent", selectPrjBdData.getDoc_indent());
			model.addAttribute("subject", "[답변] " + selectPrjBdData.getSubject());
			model.addAttribute("parent_doc_no", parent_doc_no);
			model.addAttribute("parent_doc_user_id", selectPrjBdData.getUser_id());
			model.addAttribute("parent_doc_subject", selectPrjBdData.getSubject());
		}else {
			model.addAttribute("doc_group", "0");
			model.addAttribute("doc_step", "0");
			model.addAttribute("doc_indent", "0");
			model.addAttribute("subject", "");
			model.addAttribute("parent_doc_no", "0");
			model.addAttribute("parent_doc_user_id", "");
			model.addAttribute("parent_doc_subject", "");
		}
		model.addAttribute("project_id", project_id);
		model.addAttribute("todayDate", todayDate); //작성일
		model.addAttribute("bd_category_codelist", bd_category_codelist); //분류
		model.addAttribute("userInfoDTO", userInfoDTO); //로그인사용자 정보
		
		System.out.println("-----프로젝트 공지/자료 게시판 작성(prj_board_data_write) END-----");
		
		return "/project/board/prj_board_data_write";
	}
	
	//신규등록
	@RequestMapping(value = "prj_board_data_insert", method = RequestMethod.POST)
	public String prjBoardDataInsert(PrjBdData prjBdData, HttpServletRequest request, MultipartFile file1, Model model)	throws IOException, Exception {
		
		System.out.println("-----프로젝트 공지/자료 게시판 등록(prj_board_data_insert) START-----");
		
		if(file1.getSize() > 0) {
			//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
			
			System.out.println("prjBoardDataInsert POST START...");
			log.info("uploadPath: " 	+ uploadPath);
			log.info("originalName:"	+ file1.getOriginalFilename());
			log.info("size: " 			+ file1.getSize());
			log.info("contentType: " 	+ file1.getContentType());
			
			//---------------------------------------------------------------------------------------
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			log.info("savedName: " 	+ savedName);
			//---------------------------------------------------------------------------------------
			
			// Service --> DB CRUD
			prjBdData.setAttach_name(file1.getOriginalFilename());
			prjBdData.setAttach_path(savedName);			
		}
		
		//답변등록의 경우, 기존 답변들 Doc_Step처리
		int parent_doc_no = prjBdData.getParent_doc_no();
		int project_id = prjBdData.getProject_id();
		if(parent_doc_no > 0 && project_id > 0) {
			//답변작성인 경우
			//------------------------------------------------------
			int replyCount = jmhDataSer.updateOtherReply(prjBdData);
			//------------------------------------------------------
			prjBdData.setDoc_step(prjBdData.getDoc_step()+1);
			prjBdData.setDoc_indent(prjBdData.getDoc_indent()+1);
		}
		
		//--------------------------------------------------
		int resultCount = jmhDataSer.insertBoard(prjBdData);
		//--------------------------------------------------
		
		model.addAttribute("action", "insert"); //수행(작성)
		model.addAttribute("status", "success"); //상태(성공)
		model.addAttribute("prjBdData", prjBdData);
		model.addAttribute("redirect", "prj_board_data_list"); //목록으로 이동

		System.out.println("-----프로젝트 공지/자료 게시판 등록(prj_board_data_insert) END-----");
		
		return "forward:/submit_control";
	}

	//내부 메소드 private으로
	private String uploadFile(String originalName, byte[] fileData, String uploadPath) throws IOException {
		
		// Universally Unique Identified (UUID) 유일한 식별자
		UUID uid = UUID.randomUUID();
		
		// requestPath = requestPath + "/resources/image";
		System.out.println("uploadPath->"+uploadPath);
		
		//Directory생성
		File fileDirectory = new File(uploadPath);
		
		if(!fileDirectory.exists()) {
			//신규 폴더(Directory) 생성 : 폴더가 없으면 새로 자동 생성해줌.
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성: "+uploadPath);
		}
		
		String savedName = uid.toString() + "_" + originalName;
		
		log.info("savedName: "+savedName);
		
		File target = new File(uploadPath, savedName);
		
		// File target = new File(requestPath, savedName);
		// File UpLoad ----> uploadPath / UUID+_+originalName
		
		//실제 업로드 순간
		FileCopyUtils.copy(fileData, target); //org.springframework.util.FileCopyUtils
			
		return savedName;
	}
	
	@RequestMapping(value="uploadFileDelete", method = RequestMethod.GET)
	public String uploadFileDelete(String attach_path, HttpServletRequest request, Model model) 
			throws Exception {
		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String deleteFile = uploadPath + attach_path;
		
		log.info("deleteFile: " + deleteFile);
		System.out.println("uploadFileDelete GET Start");
		
		int delResult = upFileDelete(deleteFile);
		
		log.info("deleteFile result-> "+delResult);
		
		model.addAttribute("deleteFile", deleteFile);
		model.addAttribute("delResult", delResult);
		
		return "uploadResult";
		
	}

	private int upFileDelete(String deleteFileName) throws Exception {
		int result = 0;
		
		log.info("upFileDelete result-> " + deleteFileName);
		
		File file = new File(deleteFileName);
		
		if(file.exists()) {
			
			if(file.delete()) {
				
				System.out.println("파일삭제 성공");
				result = 1;
			}
			else {
				System.out.println("파일삭제 실패");
				result = 0;
			}
		}
		else {
			System.out.println("삭제할 파일이 존재하지 않습니다.");
			result = -1;
		}
		return result;
	}
		
	//프로젝트 공지/자료 게시판 - 조회화면
	@RequestMapping(value = "prj_board_data_read")
	public String prjBoardDataRead(PrjBdData prjBdData, HttpServletRequest request, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 조회(prj_board_data_read) START-----");
		
		System.out.println("doc_no->"+prjBdData.getDoc_no());
		System.out.println("project_id->"+prjBdData.getProject_id());

		//-----------------------------------------------------------
		int resultCount = jmhDataSer.readCount(prjBdData); //조회수 증가
		//-----------------------------------------------------------
		
		//------------------------------------------------------------
		PrjBdData selectPrjBdData = jmhDataSer.selectBoard(prjBdData);
		//------------------------------------------------------------
		System.out.println("subject->"+selectPrjBdData.getSubject());
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		model.addAttribute("userInfoDTO", userInfoDTO); //로그인사용자 정보
		model.addAttribute("board", selectPrjBdData);
		
		System.out.println("-----프로젝트 공지/자료 게시판 조회(prj_board_data_read) END-----");
		
		return "/project/board/prj_board_data_read";
	}

	//수정화면 열기
	@RequestMapping(value = "prj_board_data_edit")
	public String prjBoardDataEdit(PrjBdData prjBdData, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 수정화면 열기(prj_board_data_edit) START-----");
		
		System.out.println("doc_no->"+prjBdData.getDoc_no());
		System.out.println("project_id->"+prjBdData.getProject_id());
		
		//------------------------------------------------------------
		PrjBdData selectPrjBdData = jmhDataSer.selectBoard(prjBdData);
		//------------------------------------------------------------
		System.out.println("subject->"+selectPrjBdData.getSubject());
		
		//분류코드 가져오기
		Code code = new Code();
		code.setTable_name("PRJ_BD_DATA");
		code.setField_name("BD_CATEGORY");
		//----------------------------------------------------------
		List<Code> bd_category_codelist = jmhDataSer.codeList(code);
		//----------------------------------------------------------
		System.out.println("bd_category_codelist-->"+bd_category_codelist);
		if(bd_category_codelist == null) {
			model.addAttribute("errorMsg", "분류가져오기 실패");
			return "error";
		}
	
		model.addAttribute("bd_category_codelist", bd_category_codelist); //분류
		model.addAttribute("board", selectPrjBdData);
		
		System.out.println("-----프로젝트 공지/자료 게시판 수정화면 열기(prj_board_data_edit) END-----");

		return "/project/board/prj_board_data_edit";
	}
	
	//수정 저장
	@RequestMapping(value = "prj_board_data_update", method = RequestMethod.POST)
	public String prjBoardDataUpdate(PrjBdData prjBdData, HttpServletRequest request, MultipartFile file1, Model model)	throws IOException, Exception {
		
		System.out.println("-----프로젝트 공지/자료 게시판 수정(prj_board_data_update) START-----");
		
		//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		String before_attach_delete_flag = prjBdData.getAttach_delete_flag();
		String before_attach_path = prjBdData.getAttach_path();
		
		System.out.println("before_attach_delete_flag->"+before_attach_delete_flag);
		System.out.println("before_attach_path->"+before_attach_path);
		
		if(before_attach_delete_flag.equals("D") && !before_attach_path.isEmpty()) {
			//기존 첨부파일이 있고 삭제플래그가 D인 경우 삭제하기 -> 문서정보 업데이트 후 삭제
			prjBdData.setAttach_name("");
			prjBdData.setAttach_path("");
		}
		
		if(file1.getSize() > 0) {			
			
			System.out.println("FileUpload START...");
			log.info("uploadPath: " 	+ uploadPath);
			log.info("originalName:"	+ file1.getOriginalFilename());
			log.info("size: " 			+ file1.getSize());
			log.info("contentType: " 	+ file1.getContentType());
			
			//---------------------------------------------------------------------------------------
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			log.info("savedName: " 	+ savedName);
			//---------------------------------------------------------------------------------------
			
			// Service --> DB CRUD
			prjBdData.setAttach_name(file1.getOriginalFilename());
			prjBdData.setAttach_path(savedName);
		}
		
		//--------------------------------------------------
		int resultCount = jmhDataSer.updateBoard(prjBdData);
		//--------------------------------------------------
		if(resultCount > 0) {
			if(before_attach_delete_flag.equals("D") && !before_attach_path.isEmpty()) {
			
				//수정이 정상수행 되었을때 기존파일 삭제처리
				String deleteFile = uploadPath + before_attach_path;
				
				log.info("deleteFile: " + deleteFile);
				
				//---------------------------------------
				int delResult = upFileDelete(deleteFile);
				//---------------------------------------
			}
			model.addAttribute("action", "update"); //수행(수정)
			model.addAttribute("status", "success"); //상태(성공)
			model.addAttribute("prjBdData", prjBdData);
			model.addAttribute("redirect", "prj_board_data_list"); //목록으로 이동
		}else {
			model.addAttribute("action", "update"); //수행(수정)
			model.addAttribute("status", "error"); //수행실패
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 수정(prj_board_data_update) END-----");
		
		return "forward:/submit_control";
	}
	
	@RequestMapping(value = "/submit_control")
	public String mainPage(Model model) {
		return "submit_control";
	}
	
	//문서 삭제
	@ResponseBody
	@RequestMapping(value = "prj_board_data_delete")
	public String prjBoardDataDelete(PrjBdData prjBdData, HttpServletRequest request, Model model) throws IOException, Exception {
		
		System.out.println("-----프로젝트 공지/자료 게시판 삭제(prj_ board_data_delete) START-----");
		
		//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String before_attach_path = prjBdData.getAttach_path();
		
		//--------------------------------------------------
		int resultCount = jmhDataSer.deleteBoard(prjBdData);
		//--------------------------------------------------
		System.out.println("JmhController resultCount->"+resultCount);
		if(resultCount > 0) {
			if(!before_attach_path.isEmpty()) {
				//문서삭제가 정상수행 되었을때 첨부파일 삭제처리
				String deleteFile = uploadPath + before_attach_path;
				
				log.info("deleteFile: " + deleteFile);
				
				//---------------------------------------
				int delResult = upFileDelete(deleteFile);
				//---------------------------------------			
			}
		}else {
			return "error";
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 삭제(prj_board_data_delete) END-----");
		
		return "success";
	}
	
	//추천 수행
	@ResponseBody
	@RequestMapping(value = "prj_board_data_good")
	public String prjBoardDataGood(PrjBdData prjBdData, Model model, HttpServletRequest request) {
		System.out.println("-----프로젝트 공지/자료 게시판 추천(prj_board_data_good) START-----");
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		System.out.println("doc_no->"+prjBdData.getDoc_no());
		System.out.println("project_id->"+prjBdData.getProject_id());
		System.out.println("user_id->"+userInfoDTO.getUser_id()); //현재 로그인 사용자ID

		BdDataGood bdDataGood = new BdDataGood();
		bdDataGood.setDoc_no(prjBdData.getDoc_no());
		bdDataGood.setProject_id(prjBdData.getProject_id());
		bdDataGood.setUser_id(userInfoDTO.getUser_id());
		//------------------------------------------------------------------------------
		BdDataGood returnBdDataGood = jmhDataSer.checkGoodList(bdDataGood); //추천여부 확인
		//------------------------------------------------------------------------------
		if(returnBdDataGood != null) {
			//이미 추천한 경우
			System.out.println("이미 추천한 경우");
			return "duplicated";
		}else {
			//----------------------------------------------------------------------
			int resultInsert = jmhDataSer.insertGoodList(bdDataGood); //추천목록에 추가
			//----------------------------------------------------------------------
			System.out.println("resultInsert 추천목록에 추가->"+resultInsert);
			//-----------------------------------------------------------------
			int resultCount = jmhDataSer.updateGoodCount(prjBdData); //추천수 저장
			//-----------------------------------------------------------------
			System.out.println("resultCount 추천수 저장->"+resultCount);
		}				
		
		return "success"; //추천완료
		
/*		//------------------------------------------------------------
		PrjBdData selectPrjBdData = jmhDataSer.selectBoard(prjBdData);
		//------------------------------------------------------------
		System.out.println("subject->"+selectPrjBdData.getSubject());
		
		model.addAttribute("board", selectPrjBdData);
		
		System.out.println("-----프로젝트 공지/자료 게시판 추천(prj_board_data_good) END-----");		
		return "/project/board/prj_board_data_read";*/
	}

	//추천자목록 조회
	@PostMapping(value = "prj_board_data_good_list")
	@ResponseBody
	public List<BdDataGood> prjBoardDataGood(@RequestBody BdDataGood bdDataGood, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 추천자 조회(prj_board_data_good_list) START-----");
		List<BdDataGood> resultBdDataGoodList = null;
		
		System.out.println("doc_no->"+bdDataGood.getDoc_no());
		System.out.println("project_id->"+bdDataGood.getProject_id());

		//------------------------------------------------------------------------
		resultBdDataGoodList = jmhDataSer.selectGoodList(bdDataGood); //추천여부 확인
		//------------------------------------------------------------------------
		if(resultBdDataGoodList.size() > 0) {
			//추천자 있을때
			System.out.println("추천자 수 : "+resultBdDataGoodList.size());
		}else {  
			System.out.println("추천자 수 : 0");
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 추천자 조회(prj_board_data_good_list) END-----");		
		return resultBdDataGoodList;
	}

	//댓글 등록
	@PostMapping(value = "prj_board_data_insert_comment")
	@ResponseBody
	public String prjBoardDataInsertComment(@RequestBody BdDataComt bdDataComt, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 등록(prj_board_data_insert_comment) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdDataComt.getDoc_no());
		System.out.println("project_id->"+bdDataComt.getProject_id());
		System.out.println("user_id->"+bdDataComt.getUser_id());
		System.out.println("comment_context->"+bdDataComt.getComment_context());
		bdDataComt.setAlarm_flag("N");
		
		//---------------------------------------------------------------
		int resultCount = jmhDataSer.insertComment(bdDataComt); //댓글 등록
		//---------------------------------------------------------------
		if(resultCount > 0) {
			//성공
			resultStatus = "success";
		}else {
			//실패
			resultStatus = "fail";
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 등록(prj_board_data_insert_comment) END-----");
		return resultStatus;
	}
	
	//댓글 조회
	@PostMapping(value = "prj_board_data_comment_list")
	@ResponseBody
	public List<BdDataComt> prjBoardDataCommentList(@RequestBody BdDataComt bdDataComt, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 목록(prj_board_data_comment_list) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdDataComt.getDoc_no());
		System.out.println("project_id->"+bdDataComt.getProject_id());
		
		//-----------------------------------------------------------------------------------------
		List<BdDataComt> bdDataComtList = jmhDataSer.selectCommentList(bdDataComt); //댓글 목록 가져오기
		//-----------------------------------------------------------------------------------------
		if(bdDataComtList.size() > 0) {
			//추천자 있을때
			System.out.println("댓글 수 : "+bdDataComtList.size());
		}else {  
			System.out.println("댓글 수 : 0");
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 목록(prj_board_data_comment_list) END-----");		
		return bdDataComtList;
	}

	//댓글 삭제
	@PostMapping(value = "prj_board_data_delete_comment")
	@ResponseBody
	public String prjBoardDataDeleteComment(@RequestBody BdDataComt bdDataComt, Model model) {
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 삭제(prj_board_data_delete_comment) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdDataComt.getDoc_no());
		System.out.println("project_id->"+bdDataComt.getProject_id());
		System.out.println("comment_doc_no->"+bdDataComt.getComment_doc_no());
		System.out.println("user_id->"+bdDataComt.getUser_id());
				
		//---------------------------------------------------------------
		int resultCount = jmhDataSer.deleteComment(bdDataComt); //댓글 삭제
		//---------------------------------------------------------------
		if(resultCount > 0) {
			//성공
			resultStatus = "success";
		}else {
			//실패
			resultStatus = "fail";
		}
		
		System.out.println("-----프로젝트 공지/자료 게시판 댓글 삭제(prj_board_data_delete_comment) END-----");
		return resultStatus;
	}

	
	//##################################################################
	//############  프로젝트 업무보고 게시판 prj_board_report_OOO  ############
	//##################################################################
	
	//프로젝트 업무보고 게시판 목록
	@RequestMapping(value = "prj_board_report_list")
	public String prjBoardRepList(PrjBdRep prjBdRep, String currentPage, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 목록(prj_board_report_list) START-----");
		
		// prj_board_report 전체 Count
		//--------------------------------------
		int totalCount = jmhRepSer.totalCount();
		//--------------------------------------
		
		//Page 작업
		Paging 	page = new Paging(totalCount, currentPage);
		
		//Parameter emp --> Page만 추가 Setting
		prjBdRep.setStart(page.getStart()); 	//시작시 1
		prjBdRep.setEnd(page.getEnd());		//시작시 10
		
		//-----------------------------------------------------------
		List<PrjBdRep> prjBdRepList = jmhRepSer.boardList(prjBdRep);
		//-----------------------------------------------------------
		
		model.addAttribute("boardList", prjBdRepList); 
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("page", page);
		
		System.out.println("-----프로젝트 업무보고 게시판 목록(prj_board_report_list) END-----");
		
		return "/project/board/prj_board_report_list";
	}

	//프로젝트 업무보고 게시판 - 작성화면
	@RequestMapping(value = "prj_board_report_write")
	public String prjBoardRepWrite(Model model, HttpServletRequest request) {
		System.out.println("-----프로젝트 업무보고 게시판 작성(prj_board_report_write) START-----");
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		//오늘날짜
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("오늘날짜->" + df.format(date));
		String todayDate = df.format(date);
		
		//분류코드 가져오기
		Code code = new Code();
		code.setTable_name("PRJ_BD_REP");
		code.setField_name("BD_CATEGORY");
		//---------------------------------------------------------
		List<Code> bd_category_codelist = jmhRepSer.codeList(code);
		//---------------------------------------------------------
		System.out.println("bd_category_codelist-->"+bd_category_codelist);
		if(bd_category_codelist == null) {
			model.addAttribute("errorMsg", "분류가져오기 실패");
			return "error";
		}
		
		model.addAttribute("todayDate", todayDate); //작성일
		model.addAttribute("bd_category_codelist", bd_category_codelist); //분류
		model.addAttribute("userInfoDTO", userInfoDTO); //로그인사용자 정보
		
		System.out.println("-----프로젝트 업무보고 게시판 작성(prj_board_report_write) END-----");
		
		return "/project/board/prj_board_report_write";
	}
	
	//신규등록
	@RequestMapping(value = "prj_board_report_insert", method = RequestMethod.POST)
	public String prjBoardRepInsert(PrjBdRep prjBdRep, HttpServletRequest request, MultipartFile file1, Model model)	throws IOException, Exception {
		
		System.out.println("-----프로젝트 업무보고 게시판 등록(prj_board_report_insert) START-----");
		
		if(file1.getSize() > 0) {
			//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
			String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
			
			System.out.println("prjBoardRepInsert POST START...");
			log.info("uploadPath: " 	+ uploadPath);
			log.info("originalName:"	+ file1.getOriginalFilename());
			log.info("size: " 			+ file1.getSize());
			log.info("contentType: " 	+ file1.getContentType());
			
			//---------------------------------------------------------------------------------------
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			log.info("savedName: " 	+ savedName);
			//---------------------------------------------------------------------------------------
			
			// Service --> DB CRUD
			prjBdRep.setAttach_name(file1.getOriginalFilename());
			prjBdRep.setAttach_path(savedName);			
		}
				
		//------------------------------------------------
		int resultCount = jmhRepSer.insertBoard(prjBdRep);
		//------------------------------------------------
				
		model.addAttribute("action", "insert"); //수행(작성)
		model.addAttribute("status", "success"); //상태(성공)
		model.addAttribute("prjBdData", prjBdRep);
		model.addAttribute("redirect", "prj_board_report_list"); //목록으로 이동

		System.out.println("-----프로젝트 업무보고 게시판 등록(prj_board_report_insert) END-----");
		
		return "forward:/submit_control";
	}

		
	//프로젝트 업무보고 게시판 - 조회화면
	@RequestMapping(value = "prj_board_report_read")
	public String prjBoardRepRead(PrjBdRep prjBdRep, HttpServletRequest request, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 조회(prj_board_report_read) START-----");
		
		System.out.println("doc_no->"+prjBdRep.getDoc_no());
		System.out.println("project_id->"+prjBdRep.getProject_id());

		//---------------------------------------------------------
		PrjBdRep selectPrjBdRep = jmhRepSer.selectBoard(prjBdRep);
		//---------------------------------------------------------
		System.out.println("subject->"+selectPrjBdRep.getSubject());
		
		System.out.println("session.userInfo->"+request.getSession().getAttribute("userInfo"));
		UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");
		
		model.addAttribute("userInfoDTO", userInfoDTO); //로그인사용자 정보
		model.addAttribute("board", selectPrjBdRep);
		
		System.out.println("-----프로젝트 업무보고 게시판 조회(prj_board_report_read) END-----");
		
		return "/project/board/prj_board_report_read";
	}

	//수정화면 열기
	@RequestMapping(value = "prj_board_report_edit")
	public String prjBoardRepEdit(PrjBdRep prjBdRep, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 수정화면 열기(prj_board_report_edit) START-----");
		
		System.out.println("doc_no->"+prjBdRep.getDoc_no());
		System.out.println("project_id->"+prjBdRep.getProject_id());
		
		//---------------------------------------------------------
		PrjBdRep selectPrjBdRep = jmhRepSer.selectBoard(prjBdRep);
		//---------------------------------------------------------
		System.out.println("subject->"+selectPrjBdRep.getSubject());
		
		//분류코드 가져오기
		Code code = new Code();
		code.setTable_name("PRJ_BD_REP");
		code.setField_name("BD_CATEGORY");
		//---------------------------------------------------------
		List<Code> bd_category_codelist = jmhRepSer.codeList(code);
		//---------------------------------------------------------
		System.out.println("bd_category_codelist-->"+bd_category_codelist);
		if(bd_category_codelist == null) {
			model.addAttribute("errorMsg", "분류가져오기 실패");
			return "error";
		}
	
		model.addAttribute("bd_category_codelist", bd_category_codelist); //분류
		model.addAttribute("board", selectPrjBdRep);
		
		System.out.println("-----프로젝트 업무보고 게시판 수정화면 열기(prj_board_report_edit) END-----");

		return "/project/board/prj_board_report_edit";
	}
	
	//수정 저장
	@RequestMapping(value = "prj_board_report_update", method = RequestMethod.POST)
	public String prjBoardRepUpdate(PrjBdRep prjBdRep, HttpServletRequest request, MultipartFile file1, Model model)	throws IOException, Exception {
		
		System.out.println("-----프로젝트 업무보고 게시판 수정(prj_board_report_update) START-----");
		
		//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		String before_attach_delete_flag = prjBdRep.getAttach_delete_flag();
		String before_attach_path = prjBdRep.getAttach_path();
		
		System.out.println("before_attach_delete_flag->"+before_attach_delete_flag);
		System.out.println("before_attach_path->"+before_attach_path);
		
		if(before_attach_delete_flag.equals("D") && !before_attach_path.isEmpty()) {
			//기존 첨부파일이 있고 삭제플래그가 D인 경우 삭제하기 -> 문서정보 업데이트 후 삭제
			prjBdRep.setAttach_name("");
			prjBdRep.setAttach_path("");
		}
		
		if(file1.getSize() > 0) {			
			
			System.out.println("FileUpload START...");
			log.info("uploadPath: " 	+ uploadPath);
			log.info("originalName:"	+ file1.getOriginalFilename());
			log.info("size: " 			+ file1.getSize());
			log.info("contentType: " 	+ file1.getContentType());
			
			//---------------------------------------------------------------------------------------
			String savedName = uploadFile(file1.getOriginalFilename(), file1.getBytes(), uploadPath);
			log.info("savedName: " 	+ savedName);
			//---------------------------------------------------------------------------------------
			
			// Service --> DB CRUD
			prjBdRep.setAttach_name(file1.getOriginalFilename());
			prjBdRep.setAttach_path(savedName);
		}
		
		//-----------------------------------------------
		int resultCount = jmhRepSer.updateBoard(prjBdRep);
		//-----------------------------------------------
		if(resultCount > 0) {
			if(before_attach_delete_flag.equals("D") && !before_attach_path.isEmpty()) {
			
				//수정이 정상수행 되었을때 기존파일 삭제처리
				String deleteFile = uploadPath + before_attach_path;
				
				log.info("deleteFile: " + deleteFile);
				
				//---------------------------------------
				int delResult = upFileDelete(deleteFile);
				//---------------------------------------
			}			
			model.addAttribute("action", "update"); //수행(수정)
			model.addAttribute("status", "success"); //상태(성공)
			model.addAttribute("prjBdData", prjBdRep);
			model.addAttribute("redirect", "prj_board_report_list"); //목록으로 이동
		}else {
			model.addAttribute("action", "update"); //수행(수정)
			model.addAttribute("status", "error"); //수행실패
		}

		System.out.println("-----프로젝트 업무보고 게시판 수정(prj_board_report_update) END-----");
		
		return "forward:/submit_control";
	}
	
	//문서 삭제
	@ResponseBody
	@RequestMapping(value = "prj_board_report_delete")
	public String prjBoardRepDelete(PrjBdRep prjBdRep, HttpServletRequest request, Model model) throws IOException, Exception {
		
		System.out.println("-----프로젝트 업무보고 게시판 삭제(prj_board_report_delete) START-----");
		
		//Servlet 상속 받지 못했을 때 realPath 불러오는 방법		
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		String before_attach_path = prjBdRep.getAttach_path();
		
		//-----------------------------------------------
		int resultCount = jmhRepSer.deleteBoard(prjBdRep);
		//-----------------------------------------------
		System.out.println("JmhController resultCount->"+resultCount);
		if(resultCount > 0) {
			if(!before_attach_path.isEmpty()) {
				//문서삭제가 정상수행 되었을때 첨부파일 삭제처리
				String deleteFile = uploadPath + before_attach_path;
				
				log.info("deleteFile: " + deleteFile);
				
				//---------------------------------------
				int delResult = upFileDelete(deleteFile);
				//---------------------------------------			
			}
		}else {
			return "error";
		}
		
		System.out.println("-----프로젝트 업무보고 게시판 삭제(prj_board_report_delete) END-----");
		
		return "success";
	}
	

	//댓글 등록
	@PostMapping(value = "prj_board_report_insert_comment")
	@ResponseBody
	public String prjBoardRepInsertComment(@RequestBody BdRepComt bdRepComt, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 댓글 등록(prj_board_report_insert_comment) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdRepComt.getDoc_no());
		System.out.println("project_id->"+bdRepComt.getProject_id());
		System.out.println("user_id->"+bdRepComt.getUser_id());
		System.out.println("comment_context->"+bdRepComt.getComment_context());
		bdRepComt.setAlarm_flag("N");
		
		//---------------------------------------------------------------
		int resultCount = jmhRepSer.insertComment(bdRepComt); //댓글 등록
		//---------------------------------------------------------------
		if(resultCount > 0) {
			//성공
			resultStatus = "success";
		}else {
			//실패
			resultStatus = "fail";
		}
		
		System.out.println("-----프로젝트 업무보고 게시판 댓글 등록(prj_board_report_insert_comment) END-----");
		return resultStatus;
	}
	
	//댓글 조회
	@PostMapping(value = "prj_board_report_comment_list")
	@ResponseBody
	public List<BdRepComt> prjBoardRepCommentList(@RequestBody BdRepComt bdRepComt, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 댓글 목록(prj_board_report_comment_list) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdRepComt.getDoc_no());
		System.out.println("project_id->"+bdRepComt.getProject_id());
		
		//-----------------------------------------------------------------------------------------
		List<BdRepComt> bdRepComtList = jmhRepSer.selectCommentList(bdRepComt); //댓글 목록 가져오기
		//-----------------------------------------------------------------------------------------
		if(bdRepComtList.size() > 0) {
			//추천자 있을때
			System.out.println("댓글 수 : "+bdRepComtList.size());
		}else {  
			System.out.println("댓글 수 : 0");
		}
		
		System.out.println("-----프로젝트 업무보고 게시판 댓글 목록(prj_board_report_comment_list) END-----");		
		return bdRepComtList;
	}

	//댓글 삭제
	@PostMapping(value = "prj_board_report_delete_comment")
	@ResponseBody
	public String prjBoardRepDeleteComment(@RequestBody BdRepComt bdRepComt, Model model) {
		System.out.println("-----프로젝트 업무보고 게시판 댓글 삭제(prj_board_report_delete_comment) START-----");
		
		String resultStatus = null;
		
		System.out.println("doc_no->"+bdRepComt.getDoc_no());
		System.out.println("project_id->"+bdRepComt.getProject_id());
		System.out.println("comment_doc_no->"+bdRepComt.getComment_doc_no());
		System.out.println("user_id->"+bdRepComt.getUser_id());
				
		//---------------------------------------------------------------
		int resultCount = jmhRepSer.deleteComment(bdRepComt); //댓글 삭제
		//---------------------------------------------------------------
		if(resultCount > 0) {
			//성공
			resultStatus = "success";
		}else {
			//실패
			resultStatus = "fail";
		}
		
		System.out.println("-----프로젝트 업무보고 게시판 댓글 삭제(prj_board_report_delete_comment) END-----");
		return resultStatus;
	}

	
	//##################################################################
	
	//String->JSON으로 파싱 함수
	private static JSONObject jsonToObjectParser(String jsonStr) {
		JSONParser parser = new JSONParser();
		JSONObject jsonObj = null;
		try {
			//-------------------------------------------
			jsonObj = (JSONObject) parser.parse(jsonStr);
			//-------------------------------------------
		} catch ( ParseException e) {
			e.printStackTrace();
		}
		return jsonObj;
	}	
}
