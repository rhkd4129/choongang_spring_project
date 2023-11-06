package com.oracle.s202350101.dao.lkhDao;

import java.util.List;

import com.oracle.s202350101.model.*;

public interface LkhDao {
	
	//DashBoard Home   도넛 그래프, 진척률 막대그래프


	// 작업별 상태 count -> 도넛차트
	List<Integer> 		doughnut_chart(int project_id);
	//회원별 작업 진척도
	List<Task>			Workload_chart(int project_id);

	PrjInfo				project_day(int project_id);
	// 해당 프로젝트의 작업의 총 개수보기
	int					task_count(int project_id);


	// 작업 리스트 보기
	List<Task>			task_list(Task task);
	List<Task>			task_time_decs(Task task);
	List<Task>			task_time_aces(Task task);

	//작업 상세 내용
	Task				task_detail(int task_id, int project_id);


	//해당 작업의 같이하는 사람들 리스트
	List<TaskSub> 		taskWorkerlist(TaskSub taskSub);

	// 작업 타임라인보기
	List<Task>			task_timeline();




	// 현재 속한 팀프로젝트의 프로젝트 단계 보여주기  task create get form
	List<PrjStep> 		project_step_list(int project_id);
	// 현재 속한 팀프로젝트의  인원들보여주기
	List<UserInfo> 		task_create_form_worker_list(int project_id);

	// 작업 생성  task create post form
	int 				task_create(Task task);
	int					task_worker_create(List<TaskSub> taskSubList);



	List<Task> 			garbage_list(Task task);

	//휴지통으로 이동시키기(임시삭제 )
	int					task_garbage(int task_id);



	
}
