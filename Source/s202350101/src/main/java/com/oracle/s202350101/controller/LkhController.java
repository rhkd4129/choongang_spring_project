package com.oracle.s202350101.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.service.lkhSer.LkhService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LkhController {
	private final LkhService lkhService;


	@GetMapping("Lee")
	public String Hello(Model model ) {
		log.info("say hello");
//		taskUserWorkStatusList = lkhService.task_user_workStatus();
		return "project/board/Hello";
	}


	@ResponseBody
	@GetMapping("dashboard")
	public List<Integer> dashboard() {
		log.info("dashboard Controller init");
		List<Integer> taskStatusList = new ArrayList<>();
		taskStatusList =  lkhService.task_status_count();
		for(Integer t : taskStatusList) {System.out.println(t);}
		return taskStatusList; 
	}
	@ResponseBody
	@GetMapping("dashboard_bar")
	public List<Task> dashboard_bar() {
		log.info("dashboard_bar Controller init");
		List<Task> taskUserWorkStatusList = new ArrayList<>();
		taskUserWorkStatusList =  lkhService.task_user_workStatus();
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



	@GetMapping("viewer_table")
	public String  viewer_table(Model model) {
		List<Task>  taskList =  lkhService.task_table();
		
		model.addAttribute("taskList", taskList);
		
		return "project/board/viewer_table";
			
	}
	@ResponseBody
	@GetMapping("view_status")
	public List<Task>  view_status() {return lkhService.task_table();}


	//이건되냐
	@GetMapping("task_detail")
	public String task_detail(int task_id, int project_id,Model model){
		Task task = lkhService.task_detail(task_id,project_id);
		model.addAttribute("task",task);
		return "project/board/task_detail";
	}


	@GetMapping("task_create_view")
	public String task_create_view(){

		return "project/board/taskInsertView";
	}
	@PostMapping("task_create")
	public String task_create(Model model   ){
		// 작업 ID 프로젝트 ID 회원 ID  휴지통 0

		return "redirect:viewer_table";
	}


	//@ResponseBody
	//@GetMapping("board_view")
	//public List<Task> board_view() {
	//	List<Task> boardList = new ArrayList<>();
	//	boardList = lkhService.task_user_workStatus();//
	//	return boardList;
	//}
	
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
