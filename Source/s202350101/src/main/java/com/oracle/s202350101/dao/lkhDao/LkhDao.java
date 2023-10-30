package com.oracle.s202350101.dao.lkhDao;

import java.util.List;

import com.oracle.s202350101.model.Task;

public interface LkhDao {
	
	
	//이것도 깨질라나...
	List<Integer> 		task_status_count();
	
	List<Task>			task_user_workStatus();
	List<Task>			task_board();
	List<Task>			task_table();

	int 				task_create(Task task);
	
}
