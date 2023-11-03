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
import org.springframework.web.bind.annotation.*;

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
		int projectId = userInfo.getProject_id();
		List<Task>  taskList =  lkhService.task_list(projectId);
		
		model.addAttribute("taskList", taskList);
		
		return "project/board/taskList";
	}


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
		log.info("task_crate_ctr start !!!");

		//접속한 유저 id를 가져와서 설정
		UserInfo userInfo =(UserInfo) request.getSession().getAttribute("userInfo");
		String user_id = userInfo.getUser_id();
		task.setUser_id(user_id);
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


		for (String worker : selectedWorkers) {
			log.info("worker:{}",worker);
		}
		//작업자 목록이 있으면
		if (selectedWorkers != null) {
			System.out.println("작업자 목록이 있다");
//			lkhService.task_worker_create(task,selectedWorkers);
		}
		else{
			int i  = lkhService.task_create(task);

			

		}
//		redirectAttributes.addAttribute("taskMaxId",taskMaxId);
		redirectAttributes.addAttribute("status",true);
		return "redirect:task_list";
	}



	// 휴지통 보여주기
	@GetMapping("task_garbage_view")
	public String task_garbage(Model model){
		return "project/board/task_garbage_view";
	}
	//휴지통 삭제 버튼
	@PostMapping("task_garbage")
	@GetMapping("task_update")
	public String task_update(Model model){
		return "project/board/task_update_view";
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