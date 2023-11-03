package com.oracle.s202350101.service.lkhSer;

import java.util.List;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.TaskSub;
import com.oracle.s202350101.model.UserInfo;

public interface LkhService {

	// 작업별 상태 count -> 도넛차트
	List<Integer> 		task_status_count(int project_id);
	List<Task>			task_user_workStatus(int project_id);
	List<Task>			task_list(int project_id);
	Task				task_detail(int task_id, int project_id);
	List<Task>			task_timeline();

	List<PrjStep>     project_step_list(int project_id);
	List<UserInfo>  task_create_form_worker_list(int project_id);


	int 			task_create(Task task);
	int				maxTaskid_select();
//	int				task_worker_create(List<String> taskSubList);


}
