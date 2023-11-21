package com.oracle.s202350101.service.lkhSer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.oracle.s202350101.model.*;
import org.springframework.web.multipart.MultipartFile;

public interface LkhService {

	// 작업별 상태 count -> 도넛차트
	List<Integer> 			doughnut_chart(int project_id);

	List<Task> 				workload_chart(int project_id);

	PrjInfo 				project_day(int project_id);

	AjaxResponse			project_step_chart(int project_id);

	List<Task> 				task_time_decs(Task task);

	List<Task> 				task_time_aces(Task task);

	// 해당 프로젝트 작업 수
	int 					task_count(Task task);

	List<Task>				task_list(Task Task);

	Task 					task_detail(int task_id, int project_id);
	List<TaskAttach>		task_attach_list(int task_id, int project_id);

	List<TaskSub>			taskWorkerlist(TaskSub taskSub);

	List<Task> 				task_timeline(int project_id);

	List<PrjStep> 			project_step_list(int project_id);

	List<UserInfo> 			task_create_form_worker_list(int project_id);


	// 작업생성 DAO메서드 여러개 포함되어있음 -> 트랜잭션처리
	int 						task_create(Task task, List<MultipartFile> multipartFileList,String uploadPath);

	// 작업생성 DAO메서드 여러개 포함되어있음 -> 트랜잭션처리
	int							task_update(Task task, List<MultipartFile> multipartFileList,String uploadPath,List<String> attachDeleteList);

	//휴지통으로 이동
	int 						task_garbage(Task task);

	//휴지통 목록
	List<Task> 					garbage_list(Task task);

	int 						garbage_count(int project_id);

	int 						task_delete(Task task);


	int 						task_restore(Task task);


	String 						upload_file(String originalName, byte[] fileData, String uploadPath) throws IOException;
	int 						upFileDelete(String deleteFileName)   throws Exception;
}