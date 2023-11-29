package com.oracle.s202350101.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.*;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.oracle.s202350101.service.lkhSer.LkhService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	private final LkhService lkhService;
	//git config --global core.autocrlf true

	//#######################################################################
	//############      통합검색 팝업 조회  /prj_task_read/         ############
	//#######################################################################

	@GetMapping("task_read")
	public String prjTaskRead(HttpServletRequest request, Model model) {
		log.info("prj_task_read Controller init");
		UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
		return "project/task/task_read";
	}

	//#######################################################################
	//############      프로젝트 작업 보드  /dashboard/         ############
	//#######################################################################

	@GetMapping("dashboard")
	public String dashboard(HttpServletRequest request, Model model) {
		log.info("dashboard Controller init");
		UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
		return "project/task/dashboard";
	}



	//#######################################################################
	//############  프로젝트 타임 라인 화면  /task_timeline/  ############
	//#######################################################################
	@GetMapping("task_timeline")
	public String task_timeline() {
		log.info("task_timeline Controller init");
		return "project/task/taskTimeline";
	}


	//#######################################################################
	//############  		작업 목록 화면 /task_list/ 			 ############
	//#######################################################################
	@GetMapping("task_list")
	public String task_list(HttpServletRequest request, Model model, String currentPage
			, @RequestParam(required = false) String keyword
			, @RequestParam(required = false) String keyword_division) {
		log.info("task_list Controller init");


		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		int projectId = userInfo.getProject_id();
		Task task = new Task();
		List<Task> taskList = null;
		int taskCount = 0;


		task.setProject_id(projectId);
		log.info("task list str project_id: {} search:{}  keyword_division {}", projectId, keyword, keyword_division);
		if (keyword == null) {
			log.info("검색어가 없는경우");

			//-----------------------------------------------------
			taskCount = lkhService.task_count(task);
			//-----------------------------------------------------

			Paging page = new Paging(taskCount, currentPage);
			task.setStart(page.getStart());
			task.setEnd(page.getEnd());
			taskList = lkhService.task_list(task);
			model.addAttribute("page", page);

		} else {
			log.info("검색어가 있는경우");
			task.setKeyword_division(keyword_division);
			task.setKeyword(keyword);

			//-----------------------------------------------------
			taskCount = lkhService.task_count(task);
			//-----------------------------------------------------

			Paging page = new Paging(taskCount, currentPage);

			task.setStart(page.getStart());
			task.setEnd(page.getEnd());

			//-----------------------------------------------------
			taskList = lkhService.task_list(task);
			//-----------------------------------------------------
			log.info("검색 개수 {}", taskCount);



			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("keyword_division", keyword_division);
		}

		model.addAttribute("taskList", taskList);
		model.addAttribute("taskCount", taskCount);
		return "project/task/taskList";
	}


	//#######################################################################
	//#######  작업 상세 화면  /task_detail/{task_id}/{project_id}     #########
	//#######################################################################
	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id, Model model) {
		log.info("task_detail Controller init");


		//현재 task 정보
		//-----------------------------------------------------
		Task task = lkhService.task_detail(task_id, project_id);
		//-----------------------------------------------------


		// 현재 task의 잇는 첨부파일들
		//-----------------------------------------------------------------------------------
		List<TaskAttach> taskAttachList = lkhService.task_attach_list(task_id, project_id);
		//-----------------------------------------------------------------------------------

		// 현재 task의 잇는 공동 작업자들
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(project_id);
		taskSub.setTask_id(task_id);
		//----------------------------------------------------------------
		List<TaskSub> taskSubList = lkhService.taskWorkerlist(taskSub);
		//----------------------------------------------------------------


		model.addAttribute("taskAttachList", taskAttachList);
		model.addAttribute("taskSubList", taskSubList);
		model.addAttribute("task", task);
		return "project/task/taskDeatil";
	}


	//#######################################################################
	//############    작업 생성 폼 화면    /task_create_form/       ############
	//#######################################################################
	@GetMapping("task_create_form")
	public String task_create_form(HttpServletRequest request, Model model) {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);
		int projectId = userInfo.getProject_id();
		log.info("task_create_view ctr projectId : {} userId: {}", projectId, userInfo.getUser_id());


		//오늘날짜
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String todayDate = df.format(date);
		model.addAttribute("todayDate", todayDate); //작성일


		Task task = new Task(); //이건 view화면에 에러 체크할때 필요해서 선언한 거

		// 작업 생성시 프로젝트 단계를 select 해오는 쿼리와    현재 프로젝트 멤버들을 select 해오는 쿼리
		//--------------------------------------------------------------------------------------------
		List<PrjStep> prjStepList = lkhService.project_step_list(projectId);

		List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
		//--------------------------------------------------------------------------------------------

		//지금 접속한 유저는 곧 작업의 담당자이므로 현재 프로젝트멤버들을 가져온 쿼리에서 현재 접속자는 필터링한다.
		List<UserInfo> filterWorkList = task_create_form_worker_list.stream().filter(user -> !user.getUser_id().equals(userInfo.getUser_id())).collect(Collectors.toList());
		// - >  task_create_form_worker_list를 하나씩 순회하면서 지금 접속중인 id과 쿼리해온 결과에 같은 id 잇으면 뺀다 (접속자가 lkh123 현재 프로젝트 인원들 중에 lkh123 을 뺌

		model.addAttribute("prjStepList", prjStepList);
		model.addAttribute("task_create_form_worker_list", filterWorkList);
		model.addAttribute("task", task);
		return "project/task/taskInsertForm";
	}

	//#######################################################################
	//############  작업 버튼 누를시 post     /task_create/          ###########
	//#######################################################################
	@PostMapping("task_create")
	public String task_create(
			@Validated @ModelAttribute Task task, BindingResult bindingResult,
			@RequestParam(value = "file1", required = false) List<MultipartFile> multipartFileList,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) throws IOException {

		// 1 .컨트롤러 시작
		log.info("1. task_create ctr");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		model.addAttribute("userInfo", userInfo);

		int projectId = userInfo.getProject_id();
		String userId = userInfo.getUser_id();
		log.info("2. userId = {}  proejctId = {}", userId, projectId);
		// 2 .컨트롤러 진입 후 접속한 사용자 정보까지 받아오기

		// 3. 폼 유효성 검증
		if(multipartFileList.size() >= 6 ){
			bindingResult.reject("fileOverCount","파일은 최대 6개까지 업로드 가능합니다");
		}

		if (bindingResult.hasErrors()) {
			log.info("3 validation .errer : {}", bindingResult);

			//--------------------------------------------------------------------------------------------
			List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
			List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
			//--------------------------------------------------------------------------------------------

			// 유효성 검사 에러가 있을 때 폼에 다시 입력한 값을 유지하기 위해 모델 속성 추가
			model.addAttribute("task_create_form_worker_list", task_create_form_worker_list);
			model.addAttribute("prjStepList", prjStepList);


			//  4. 에러가 있다면 다시 폼을 보여주기
			log.info("4 .validation error view return ");
			return "project/task/taskInsertForm";
		}

		// 5  .task 생성시 필요한 작성자와 프로젝트 번호 주입
		task.setUser_id(userId);
		task.setProject_id(projectId);
		log.info("5. task informaion injection ");

		// 저장 위치
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		// task_create => 트랜잭션 처리 기본 task, 공동작업자, 파일첨부등 여러개의 dao메서드가 실행됨
		//--------------------------------------------------------------------------------------------
		int result = lkhService.task_create(task, multipartFileList, uploadPath);
		//--------------------------------------------------------------------------------------------
		if (result == 1) {
			redirectAttributes.addAttribute("status", true);
		} else {
			redirectAttributes.addAttribute("status", false);
		}
		// 6.트랜잭션처리 까지 완료된 상태
		log.info("6. taskCreate success");

		return "redirect:task_list";
	}


	//#######################################################################
	//############      작업 업데이트 폼 화면   task_update_form     ############
	//#######################################################################
	@GetMapping("task_update_form")
	public String task_update_form(HttpServletRequest request, Model model,
								   int taskId, int projectId) {
		log.info("task_update_form ctr");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		// 수정하기전 Task에 대한 정보
		//--------------------------------------------------------------------------------------------
		Task task = lkhService.task_detail(taskId, projectId);
		//--------------------------------------------------------------------------------------------

		// 현재 작업이 속한 프로젝트의 단계
		//--------------------------------------------------------------------------------------------
		List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
		//--------------------------------------------------------------------------------------------


		// 현재 프토젝트의 속한  인원들의 user_id와 user_name을가져옴
		//--------------------------------------------------------------------------------------------
		List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
		//--------------------------------------------------------------------------------------------

		List<UserInfo> filterWorkList = task_create_form_worker_list.stream().filter(user -> !user.getUser_id().equals(userInfo.getUser_id())).collect(Collectors.toList());
		// - >  task_create_form_worker_list를 하나씩 순회하면서 지금 접속중인 id과 쿼리해온 결과에 같은 id 잇으면 뺀다 (접속자가 lkh123 현재 프로젝트 인원들 중에 lkh123 을 뺌
		// 원래 작업자들의 명단을 가져옴
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(projectId);
		taskSub.setTask_id(taskId);

		//--------------------------------------------------------------------------------------------
		List<TaskSub> taskSubList = lkhService.taskWorkerlist(taskSub);
		List<TaskAttach> taskAttachList = lkhService.task_attach_list(taskId, projectId);
		//--------------------------------------------------------------------------------------------

		model.addAttribute("taskAttachList", taskAttachList);
		model.addAttribute("prjStepList", prjStepList);
		model.addAttribute("task_create_form_worker_list", filterWorkList);
		model.addAttribute("taskSubList", taskSubList);
		model.addAttribute("task", task);

		return "project/task/taskUpdateForm";
	}


	//#######################################################################
	//############  작업 수정 버튼 누를 시 post    /task_update/     ############
	//#######################################################################
	@PostMapping("task_update")
	public String task_update(
			@Validated @ModelAttribute Task task, BindingResult bindingResult,
			@RequestParam(value = "file1", required = false) List<MultipartFile> multipartFileList,
			@RequestParam(value = "attach_delete_no", required = false) List<String> attachDeleteList,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) throws IOException {

		// 1 .컨트롤러 시작
		log.info("1. task_create ctr");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		String userId = userInfo.getUser_id();


		// 3. 폼 유효성 검증
		// 커스텀 오류
		if(multipartFileList.size() >= 6 ){
			bindingResult.reject("fileOverCount","파일은 최대 6개까지 업로드 가능합니다");
		}

		if (bindingResult.hasErrors()) {
			log.info("3 validation .errer : {}", bindingResult);
			// 유효성 검사 에러가 있을 때 폼에 다시 입력한 값을 유지하기 위해 모델 속성 추가
			TaskSub taskSub = new TaskSub();
			taskSub.setProject_id(projectId);
			taskSub.setTask_id(task.getTask_id());


			// 먼저 프로젝트 단계목록 리스트   ,   현재 프로젝트 인원들      , 이미 선택된 같이 하는 작업자 인원들     , 첨부파일 들
			//--------------------------------------------------------------------------------------------
			List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
			List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
			List<TaskSub> taskSubList = lkhService.taskWorkerlist(taskSub);
			List<TaskAttach> taskAttachList = lkhService.task_attach_list(task.getTask_id(), projectId);
			//--------------------------------------------------------------------------------------------


			model.addAttribute("task", task);
			model.addAttribute("prjStepList", prjStepList);
			model.addAttribute("task_create_form_worker_list", task_create_form_worker_list);
			model.addAttribute("taskSubList", taskSubList);
			model.addAttribute("taskAttachList", taskAttachList);

			//  4. 에러가 있다면 다시 폼을 보여주기
			log.info("4 .validation error view return ");
			return "project/task/taskUpdateForm";
		}
		// 저장 위치
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");
		//--------------------------------------------------------------------------------------------
		int result = lkhService.task_update(task, multipartFileList, uploadPath, attachDeleteList);
		//--------------------------------------------------------------------------------------------
		// 6.트랜잭션처리 까지 완료된 상태
		log.info("6. taskCreate success");
		redirectAttributes.addAttribute("update_status", true);
		return "redirect:task_list";
	}




	//#######################################################################
	//############  		휴지통 목록  /garbage_list/  			 ############
	//#######################################################################
	@GetMapping("garbage_list")
	public String task_garbage(HttpServletRequest request, Model model, String currentPage) {
		log.info("task_garbage ctr");
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");

		Task task = new Task();
		task.setProject_id(userInfo.getProject_id());

		// 휴지통에 잇는 작업 개수
		//--------------------------------------------------------------------------------------------
		int taskCount = lkhService.garbage_count(userInfo.getProject_id());
		//--------------------------------------------------------------------------------------------


		// 휴지통 목록
		//--------------------------------------------------------------------------------------------
		List<Task> garbageList = lkhService.garbage_list(task);
		//--------------------------------------------------------------------------------------------

		Paging page = new Paging(taskCount, currentPage);
		task.setStart(page.getStart());
		task.setEnd(page.getEnd());

		model.addAttribute("currentUserId", userInfo.getUser_id());
		model.addAttribute("garbageList", garbageList);
		model.addAttribute("taskCount", taskCount);
		model.addAttribute("page", page);
		return "project/task/garbageList";
	}
}