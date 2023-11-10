package com.oracle.s202350101.controller;

import java.util.ArrayList;
import java.util.List;
import com.oracle.s202350101.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.oracle.s202350101.service.lkhSer.LkhService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	private final LkhService lkhService;

	// 대시보드 홈
	@GetMapping("dashboard")
	public String dashboard(HttpServletRequest request, Model model ) {
		log.info("board_view ctr start");
		UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");

		return "project/task/dashboard";
	}


	// 프로젝트 상태별로 보여주기
	@GetMapping("task_board")
	public String task_board(HttpServletRequest request , Model model, String currentPage){
		log.info("board_view ctr start");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		Task task =  new Task();
		task.setProject_id(projectId);
		task.setStart(1);
		task.setEnd(999);
		int taskCount = lkhService.task_count(projectId);
		List<Task>  taskList =  lkhService.task_list(task);

		// 작업 리스트를 받은 후 작업 상태별로 나누어서 model에 등록
		List<Task> taskStatus0 = new ArrayList<Task>();
		List<Task> taskStatus1 = new ArrayList<Task>();
		List<Task> taskStatus2 = new ArrayList<Task>();
		for (Task t : taskList) {
			switch (t.getTask_status()) {
				case "0":
					taskStatus0.add(t);
					break;
				case "1":
					taskStatus1.add(t);
					break;
				case "2":
					taskStatus2.add(t);
					break;
			}
		}
		model.addAttribute("taskStatus0",taskStatus0);
		model.addAttribute("taskStatus1",taskStatus1);
		model.addAttribute("taskStatus2",taskStatus2);
		model.addAttribute("taskCount",taskCount);
		return "project/task/taskBoard";
	}


	// 작업 시간 그래프
	@GetMapping("task_timeline")
	public String task_timeline(){
		return "project/task/taskTimeline";
	}


	//작업 리스트
	@GetMapping("task_list")
	public String  task_list(HttpServletRequest request,  Model model,String currentPage) {
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();

		Task task =  new Task();
		task.setProject_id(projectId);

		int taskCount = lkhService.task_count(projectId);
		Paging   page = new Paging(taskCount, currentPage);

		task.setStart(page.getStart());
		task.setEnd(page.getEnd());
		List<Task>  taskList =  lkhService.task_list(task);

		model.addAttribute("taskList", taskList);
		model.addAttribute("taskCount",taskCount);
		model.addAttribute("page",page);
		return "project/task/taskList";
	}

	// 작업 상세 화면
	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id,Model model){
		log.info("task_create_view ctr task_id : {}",task_id);
		Task task = lkhService.task_detail(task_id,project_id);
		log.info(task.getUser_name());
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(project_id);
		taskSub.setTask_id(task_id);
		List<TaskSub> taskSubList =lkhService.taskWorkerlist(taskSub);

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
			RedirectAttributes redirectAttributes, HttpServletRequest request,Model model) {
		// 컨트롤러 내용
		log.info("task_create ctr");

		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int  projectId = userInfo.getProject_id();
		String  userId = userInfo.getUser_id();

		log.info("userId = {}  proejctId = {}",userId,projectId);

		if(bindingResult.hasErrors()){
			log.info("errer 있다 : {}",bindingResult);

			// 유효성 검사 에러가 있을 때 폼에 다시 입력한 값을 유지하기 위해 모델 속성 추가
			List<PrjStep> prjStepList = lkhService.project_step_list(projectId);
			List<UserInfo> task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
			model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
			model.addAttribute("prjStepList",prjStepList);
			// 에러가 잇다면 다시 입력하도록 리턴
			return "project/task/taskInsertForm";
		}

		task.setUser_id(userId);
		task.setProject_id(projectId);

		//작업자 목록이 있으면
		if (task.getWorkerIdList() != null) {
			lkhService.createGroupTask(task.getWorkerIdList(),task);
			System.out.println("작업자 목록이 있다");
		}
		else {
			System.out.println("작업자가 없다 ");
			int i = lkhService.task_create(task);
		}
		//없으면 그냥 작업만 생성해주기
		redirectAttributes.addAttribute("status",true);
		return "redirect:task_list";
	}



	// 작업 수정 폼
	@GetMapping ("task_update_form")
	public String task_update_form(HttpServletRequest request ,Model model){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		log.info("task_update_form ctr projectId : {}",projectId);
		return "project/task/taskUpdateForm";


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


}