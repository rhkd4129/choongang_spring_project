package com.oracle.s202350101.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	// 대시보드 홈
	@GetMapping("dashboard")
	public String dashboard(HttpServletRequest request, Model model ) {
		log.info("board_view ctr start");
		UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");

		return "project/task/dashboard";
	}



	// 작업 시간 그래프
	@GetMapping("task_timeline")
	public String task_timeline(){
		return "project/task/taskTimeline";
	}


	//작업 리스트
	@GetMapping("task_list")
	public String  task_list(HttpServletRequest request,  Model model,String currentPage
	,@RequestParam(required = false) String search  ) {
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();

		Task task =  new Task();
		task.setProject_id(projectId);
		List<Task> taskList = null;
		int taskCount = 0;

		if(search == null){
			taskCount = lkhService.task_count(projectId,Optional.empty());
			Paging   page = new Paging(taskCount, currentPage);
			task.setStart(page.getStart());
			task.setEnd(page.getEnd());
			taskList =  lkhService.task_list(task);
			model.addAttribute("page",page);
			log.info("검색어가 없습");
		}
		else{
			log.info("search  -> :{}",search);
			taskCount = lkhService.task_count(projectId, Optional.of(search));
			Paging   page = new Paging(taskCount, currentPage);
			task.setSearch(search);
			task.setStart(page.getStart());
			task.setEnd(page.getEnd());
			taskList =  lkhService.task_list(task);
			model.addAttribute("page",page);
		}



		model.addAttribute("taskList", taskList);
		model.addAttribute("taskCount",taskCount);
		return "project/task/taskList";
	}
	@GetMapping("task_search")
	public String task_search(HttpServletRequest request,  Model model,String currentPage , @RequestParam(required = false) String search ){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();

		Task task =  new Task();
		task.setProject_id(projectId);
		task.setSearch(search);



		return "project/task/taskSearch";
	}


	// 작업 상세 화면
	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id,Model model){
		log.info("task_create_view ctr taskId : {}",task_id);


		//현재 task 정보
		Task task = lkhService.task_detail(task_id,project_id);
		// 현재 task의 잇는 첨부파일들
		List<TaskAttach> taskAttachList =  lkhService.task_attach_list(task_id,project_id);
		// 현재 task의 잇는 공동 작업자들
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(project_id);
		taskSub.setTask_id(task_id);
		List<TaskSub> taskSubList =lkhService.taskWorkerlist(taskSub);

		model.addAttribute("taskAttachList",taskAttachList);
		model.addAttribute("taskSubList",taskSubList);
		model.addAttribute("task",task);
		return "project/task/taskDeatil";
	}



	// 작업 생성 폼 페이지
	@GetMapping("task_create_form")
	public String task_create_form(HttpServletRequest request ,Model model){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		Task task = new Task();
		log.info("task_create_view ctr projectId : {}",projectId);
		List<PrjStep>  prjStepList = lkhService.project_step_list(projectId);
		List<UserInfo>  task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
		// 현재 프로젝트에 속한 프로젝트 단계 리스트와 프로젝트 인원들도 같이 선택해서 입력해야함
		model.addAttribute("prjStepList",prjStepList);
		model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
		model.addAttribute("task", task);
		return "project/task/taskInsertForm";
	}


	//		if(task.getGarbage()  != null && task.getProject_s_name() != null){bindingResult.reject("total",new Object[]{10000, });}
	// 작업 생성 Post @RequestParam(value = "worker" ,required = false) List<String> selectedWorkers,
	@PostMapping("task_create")
	public String task_create(
			@Validated @ModelAttribute Task task, BindingResult bindingResult,
			@RequestParam(value = "file1", required = false) List<MultipartFile> multipartFileList,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) throws IOException {

		// 1 .컨트롤러 시작
		log.info("1. task_create ctr");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int  projectId = userInfo.getProject_id();
		String  userId = userInfo.getUser_id();
		log.info("2. userId = {}  proejctId = {}",userId,projectId);
		// 2 .컨트롤러 진입 후 접속한 사용자 정보까지 받아오기


		// 3. 폼 유효성 검증
		if(bindingResult.hasErrors()){
			log.info("3 validation .errer : {}",bindingResult);
			// 유효성 검사 에러가 있을 때 폼에 다시 입력한 값을 유지하기 위해 모델 속성 추가
			List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
			List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
			model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
			model.addAttribute("prjStepList",prjStepList);
			//  4. 에러가 있다면 다시 폼을 보여주기
			log.info("4 .validation error view return ");
			return "project/task/taskInsertForm";
		}

		task.setUser_id(userId);
		task.setProject_id(projectId);
		log.info("5. task informaion injection ");
		// 5  .task 생성시 필요한 작성자와 프로젝트 번호 주입
		// 저장 위치
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		// task_create => 트랜잭션 처리 기본 task, 공동작업자, 파일 3개의 dao메서드가 실행됨\

		int result = lkhService.task_create(task,multipartFileList,uploadPath);
		// 6.트랜잭션처리 까지 완료된 상태
		log.info("6. taskCreate success");
		redirectAttributes.addAttribute("status",true);
		return "redirect:task_list";
	}



	// 작업 수정 폼
	@GetMapping ("task_update_form")
	public String task_update_form(HttpServletRequest request ,Model model ,
								   int taskId, int projectId){
		log.info("task_update_form ctr");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		
		// 수정하기전 Task에 대한 정보
		Task task = lkhService.task_detail(taskId,projectId);
		// 현재 작업이 속한 프로젝트의 단계
		List<PrjStep>  prjStepList = lkhService.project_step_list(projectId);
		// 현재 프토젝트의 속한  인원들의 user_id와 user_name을가져옴
		List<UserInfo>  task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
		// 원래 작업자들의 명단을 가져옴
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(projectId);
		taskSub.setTask_id(taskId);
		List<TaskSub> taskSubList =lkhService.taskWorkerlist(taskSub);
		List<TaskAttach> taskAttachList =  lkhService.task_attach_list(taskId,projectId);


		model.addAttribute("taskAttachList",taskAttachList);
		model.addAttribute("prjStepList",prjStepList);
		model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
		model.addAttribute("taskSubList",taskSubList);
		model.addAttribute("task", task);

		return "project/task/taskUpdateForm";
	}

	@PostMapping("task_update")
	public String task_update(
			@Validated @ModelAttribute Task task, BindingResult bindingResult,
			@RequestParam(value = "file1", required = false) List<MultipartFile> multipartFileList,
			RedirectAttributes redirectAttributes, HttpServletRequest request, Model model) throws IOException {

		// 1 .컨트롤러 시작
		log.info("1. task_create ctr");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int  projectId = userInfo.getProject_id();
		String  userId = userInfo.getUser_id();
		// 3. 폼 유효성 검증
		if(bindingResult.hasErrors()){
			log.info("3 validation .errer : {}",bindingResult);
			// 유효성 검사 에러가 있을 때 폼에 다시 입력한 값을 유지하기 위해 모델 속성 추가
			List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
			List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
			TaskSub taskSub = new TaskSub();
			taskSub.setProject_id(projectId);
			taskSub.setTask_id(task.getTask_id());
			List<TaskSub> taskSubList =lkhService.taskWorkerlist(taskSub);
			List<TaskAttach> taskAttachList =  lkhService.task_attach_list(task.getTask_id(),projectId);


			model.addAttribute("taskAttachList",taskAttachList);
			model.addAttribute("prjStepList",prjStepList);
			model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
			model.addAttribute("taskSubList",taskSubList);
			model.addAttribute("task", task);
			//  4. 에러가 있다면 다시 폼을 보여주기
			log.info("4 .validation error view return ");
			return "project/task/taskUpdateForm";

		}
		// 저장 위치
		String uploadPath = request.getSession().getServletContext().getRealPath("/upload/");

		int result = lkhService.task_update(task,multipartFileList,uploadPath);
		// 6.트랜잭션처리 까지 완료된 상태
		log.info("6. taskCreate success");
		redirectAttributes.addAttribute("status",true);
		return "redirect:task_list";
	}


	// 휴지통 보여주기
	@GetMapping("garbage_list")
	public String task_garbage(HttpServletRequest request ,Model model ,String currentPage ){
		log.info("task_garbage ctr");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");

		Task task = new Task();
		task.setProject_id(userInfo.getProject_id());
		int taskCount   = lkhService.garbage_count(userInfo.getProject_id());
		Paging page = new  Paging(taskCount,currentPage);
		task.setStart(page.getStart());
		task.setEnd(page.getEnd());
		List<Task> garbageList = lkhService.garbage_list(task);
		log.info(String.valueOf(garbageList.size()));

		model.addAttribute("currentUserId",userInfo.getUser_id());
		model.addAttribute("garbageList",garbageList);
		model.addAttribute("taskCount",taskCount);
		model.addAttribute("page",page);
		return "project/task/garbageList";
	}
//	@GetMapping("/download/{fileName}")
//	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) throws IOException {
//		UrlResource resource = new UrlResource();
//		String ENC

//		return ResponseEntity.ok()
//				.contentType(MediaType.APPLICATION_OCTET_STREAM)
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.() + "\"")
//				.body(file);
//	}


}