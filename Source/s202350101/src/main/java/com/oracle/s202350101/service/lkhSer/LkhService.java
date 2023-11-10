package com.oracle.s202350101.service.lkhSer;

import java.io.IOException;
import java.util.List;

import com.oracle.s202350101.model.*;

public interface LkhService {

	// 작업별 상태 count -> 도넛차트
	List<Integer> 			doughnut_chart(int project_id);

	List<Task> 				workload_chart(int project_id);

	PrjInfo 				project_day(int project_id);

	List<Task> 				task_time_decs(Task task);

	List<Task> 				task_time_aces(Task task);

	// 해당 프로젝트 작업 수
	int 					task_count(int project_id);

	List<Task>				task_list(Task Task);

	Task 					task_detail(int task_id, int project_id);

	List<TaskSub>			taskWorkerlist(TaskSub taskSub);

	List<Task> 				task_timeline();

	List<PrjStep> 			project_step_list(int project_id);

	List<UserInfo> 			task_create_form_worker_list(int project_id);


	// 작업생성
	int 						task_create(Task task);

	// createGroupTask 트랜잭션 처리:task_create,task_worker_create dao 함수가 2개들어가있다
	int 						createGroupTask(List<String> workerList, Task task);

	//휴지통으로 이동
	int 						task_garbage(Task task);

	//휴지통 목록
	List<Task> 					garbage_list(Task task);

	int 						garbage_count(int project_id);

	int 						task_delete(Task task);


	int 						task_restore(Task task);


	String 						upload_file(String originalName, byte[] fileData, String uploadPath) throws IOException;
	int 						 upFileDelete(String deleteFileName)   throws Exception;
}