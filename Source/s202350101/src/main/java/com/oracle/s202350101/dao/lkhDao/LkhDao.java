package com.oracle.s202350101.dao.lkhDao;

import java.util.List;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.TaskSub;
import com.oracle.s202350101.model.UserInfo;

public interface LkhDao {
	
	
	// 작업별 상태 count -> 도넛차트
	List<Integer> 		task_status_count(int project_id);
	//회원별 작업 진척도
	List<Task>			task_user_workload(int project_id);
	// 작업 리스트 보기
	List<Task>			task_list(int project_id);
	// 작업 타임라인보기
	List<Task>			task_timeline();




	// 현재 속한 팀프로젝트의 프로젝트 단계 보여주기  task create get form
	List<PrjStep> 		project_step_list(int project_id);
	// 현재 속한 팀프로젝트의  인원들보여주기
	List<UserInfo> 		task_create_form_worker_list(int project_id);

	// 작업 생성  task create post form
	int 				task_create(Task task);
	int					maxTaskid_select();
	int					task_worker_create(List<TaskSub> taskSubList);




	//작업 상세 내용
	Task				task_detail(int task_id, int project_id);
	List<TaskSub>		taskWorkerlist(int project_id, int task_id);
	
}
