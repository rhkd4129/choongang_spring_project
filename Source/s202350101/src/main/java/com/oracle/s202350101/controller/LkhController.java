package com.oracle.s202350101.controller;

import java.util.ArrayList;
import java.util.List;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.TaskSub;
import com.oracle.s202350101.model.UserInfo;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.model.Task;
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
	@GetMapping("dashboard_doughnut")
	public List<Integer> dashboard(HttpServletRequest request) {
		UserInfo userInfo  = (UserInfo) request.getSession().getAttribute("userInfo");
		int id = userInfo.getProject_id();
		log.info("dashboard Controller init");
		List<Integer> taskStatusList = new ArrayList<>();
		taskStatusList =  lkhService.task_status_count(id);
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
		taskUserWorkStatusList =  lkhService.task_user_workStatus(id);
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
	public String  viewer_table(HttpServletRequest request,  Model model) {
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int id = userInfo.getProject_id();
		List<Task>  taskList =  lkhService.task_list(id);
		
		model.addAttribute("taskList", taskList);
		
		return "project/board/taskList";
			
	}
//	@ResponseBody
//	@GetMapping("view_status")
//	public List<Task>  view_status(HttpServletRequest request) {
//		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
//		int id = userInfo.getProject_id();
//		return lkhService.task_table(id);
//
//
//	}


	//이건되냐
	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id,Model model){
		Task task = lkhService.task_detail(task_id,project_id);
		model.addAttribute("task",task);
		return "project/board/task_detail";
	}


	@GetMapping("task_create_view")
	public String task_create_view(HttpServletRequest request ,Model model){
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		int projectId = userInfo.getProject_id();
		log.info("projectId{}",projectId);

		List<PrjStep>  prjStepList = lkhService.project_step_list(projectId);
		List<UserInfo>  task_create_form_worker_list = lkhService.task_create_form_worker_list(projectId);

		model.addAttribute("prjStepList",prjStepList);
		model.addAttribute("task_create_form_worker_list",task_create_form_worker_list);

		return "project/board/taskInsertView";
	}
	@PostMapping("task_create")
	public String task_create(@Validated  @ModelAttribute Task task, TaskSub taskSub
			, BindingResult bindingResult, RedirectAttributes redirectAttributes,
							  HttpServletRequest request){
//		if(task.getGarbage()  != null && task.getProject_s_name() != null){
//			bindingResult.reject("total",new Object[]{10000, });
//		}



		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		String user_id = userInfo.getUser_id();


		if(bindingResult.hasErrors()){
			log.info("errer={}",bindingResult);
//			model.addAttribute("msg","BindingResult 입력 실패 확인해 보세요");
			return "project/board/taskInsertView";
		}
		task.setUser_id(user_id);
		lkhService.task_create(task);
		redirectAttributes.addAttribute("taskId",task.getTask_id());
		redirectAttributes.addAttribute("status",true);


		return "redirect:task_detail?task_id={taskId}";
	}

	
	@GetMapping("task_garbage_view")
	public String task_garbage(Model model){

		return "project/board/task_garbage_view";
	}

	@PostMapping("task_garbage")
	@GetMapping("task_update")
	public String task_update(Model model){
		return "project/board/task_update_view";
	}
} 
