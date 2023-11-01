package com.oracle.s202350101.service.lkhSer;

import java.util.List;

import com.oracle.s202350101.model.Task;
public interface LkhService {
	List<Integer> 		task_status_count();
	List<Task>			task_user_workStatus();
	List<Task>			task_board();
	List<Task>			task_table();

	Task				task_detail(int task_id, int project_id);

	List<Task>			task_timeline();
	
}
