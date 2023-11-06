package com.oracle.s202350101.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.*;
import com.oracle.s202350101.service.Paging;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.service.lkhSer.LkhService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	private final LkhService lkhService;
	@GetMapping("dashboard_home")
	public String Hello(HttpServletRequest request, Model model ) {
		UserInfo user = (UserInfo) request.getSession().getAttribute("userInfo");
		return "project/board/dashboard_home";
	}

	@ResponseBody
	@GetMapping("bar")
	public PrjInfo bar(HttpServletRequest request ,Model model){
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		return lkhService.project_day(projectId);
	}

	@GetMapping("task_board")
	public String board(HttpServletRequest request , Model model, String currentPage){
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

		return "project/board/taskBoard";

	}

	@ResponseBody
	@GetMapping("dashboard_doughnut")
	public List<Integer> dashboard(HttpServletRequest request) {
		UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");
		int id = userInfo.getProject_id();
		log.info("dashboard Controller init");
		List<Integer> taskStatusList = new ArrayList<>();
		taskStatusList =  lkhService.doughnut_chart(id);
		for(Integer t : taskStatusList) {System.out.println(t);}
		return taskStatusList; 
	}

	//진척률

	// 프로젝트에 속한 인원들과
	//
	@ResponseBody
	@GetMapping("dashboard_bar")
	public List<Task> dashboard_bar(HttpServletRequest request) {
		UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");
		int id = userInfo.getProject_id();
		log.info("dashboard_bar Controller init");
		List<Task> taskUserWorkStatusList = new ArrayList<>();
		taskUserWorkStatusList =  lkhService.Workload_chart(id);
		return taskUserWorkStatusList; 
	}



	@GetMapping("task_timeline_view")
	public String task_timeline_view(){
		return "project/board/task_timeline";
	}

	@ResponseBody
	@GetMapping("task_timeline")
	public List<Task> task_timeline(){
		List<Task> timeLine = lkhService.task_timeline();
		return timeLine;
	}




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
		return "project/board/taskList";
	}


	@GetMapping("task_time_desc")
	@ResponseBody
	public  AjaxResponse task_time_desc(HttpServletRequest request,String currentPage){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		log.info("내림");
		int projectId = userInfo.getProject_id();
		Task task =  new Task();
		task.setProject_id(projectId);
		int taskCount = lkhService.task_count(projectId);
		Paging   page = new Paging(taskCount, currentPage);
		task.setStart(page.getStart());
		task.setEnd(page.getEnd());
		List<Task>  taskList =  lkhService.task_time_decs(task);
		AjaxResponse data = new AjaxResponse();
		data.setOnelist(taskList);
		data.setOneObject(page);
		
		
		for(Task a: taskList) {
			System.out.println(a.getTask_end_itme());
		}
		return data;
	}

	@GetMapping("task_time_acsc")
	@ResponseBody
	public  AjaxResponse task_time_aces(HttpServletRequest request,String currentPage){
		log.info("올림");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		Task task =  new Task();
		task.setProject_id(projectId);
		int taskCount = lkhService.task_count(projectId);
		Paging   page = new Paging(taskCount, currentPage);
		task.setStart(page.getStart());
		task.setEnd(page.getEnd());
		List<Task>  taskList =  lkhService.task_time_aces(task);
		for(Task a: taskList) {
			System.out.println(a.getTask_end_itme());
		}
		AjaxResponse data = new AjaxResponse();
		data.setOnelist(taskList);
		data.setOneObject(page);
		return data;
	}




	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id,Model model){
		Task task = lkhService.task_detail(task_id,project_id);
		log.info(task.getUser_name());
		TaskSub taskSub = new TaskSub();
		taskSub.setProject_id(project_id);
		taskSub.setTask_id(task_id);
		List<TaskSub> taskSubList =lkhService.taskWorkerlist(taskSub);

		model.addAttribute("taskSubList",taskSubList);
		model.addAttribute("task",task);
		return "project/board/taskDeatil";
	}



	@GetMapping("task_create_view")
	public String task_create_view(HttpServletRequest request ,Model model){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		log.info("projectId : {}",projectId);
		List<PrjStep>  prjStepList = lkhService.project_step_list(projectId);
		List<UserInfo>  task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);
		model.addAttribute("prjStepList",prjStepList);
		model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);
		return "project/board/taskInsertView";
	}

	@PostMapping("task_create")
	public String task_create( @RequestParam(value = "worker" ,required = false) List<String> selectedWorkers, @ModelAttribute Task task
							  , RedirectAttributes redirectAttributes, HttpServletRequest request){
		//접속한 유저 id를 가져와서 설정
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");

		task.setUser_id(userInfo.getUser_id());
		task.setProject_id(userInfo.getProject_id());

		//작업 생성후 그 작업 pk를 갖고온다
		log.info("task_id: " + task.getTask_id());
		log.info("project_id: " + task.getProject_id());
		log.info("project_step_seq: " + task.getProject_step_seq());
		log.info("user_id: " + task.getUser_id());
		log.info("task_subject: " + task.getTask_subject());
		log.info("task_content: " + task.getTask_content());
		log.info("task_stat_time: " + task.getTask_stat_time());
		log.info("task_end_itme: " + task.getTask_end_itme());
		log.info("task_priority: " + task.getTask_priority());
		log.info("task_status: " + task.getTask_status());


		//작업자 목록이 있으면
		if (selectedWorkers != null) {
			lkhService.createGroupTask(selectedWorkers,task);
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



	// 휴지통 보여주기
	@GetMapping("garbage_list")
	public String task_garbage(HttpServletRequest request ,Model model ,String currentPage ){

		log.info("task_garbage ctr");
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");

		Task task = new Task();
		task.setProject_id(userInfo.getProject_id());


		List<Task> garbageList = lkhService.garbage_list(task);
		log.info(String.valueOf(garbageList.size()));
		Paging   page = new Paging(garbageList.size(), currentPage);
		task.setStart(page.getStart());
		task.setEnd(page.getEnd());

		model.addAttribute("",garbageList);
		model.addAttribute("taskCount",garbageList.size());
		model.addAttribute("page",page);
		return "project/board/garbageList";
	}
	//휴지통 삭제 버튼
	@PostMapping("task_garbage")
	@ResponseBody
	public int task_update(int task_id){
		log.info("휴지통 이동하기 비동기 함수 진입점 ");
		return lkhService.task_garbage(task_id);

	}
}

//		if(task.getGarbage()  != null && task.getProject_s_name() != null){
//			bindingResult.reject("total",new Object[]{10000, });
//		}
// 에러가 있으면 다시 뒤로
//		if(bindingResult.hasErrors()){
//			log.info("errer={}",bindingResult);
////			model.addAttribute("msg","BindingResult 입력 실패 확인해 보세요");
//			return "project/board/taskInsertView";
//		}