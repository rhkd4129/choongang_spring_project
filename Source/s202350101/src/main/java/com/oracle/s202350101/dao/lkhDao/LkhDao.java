package com.oracle.s202350101.dao.lkhDao;

import java.util.List;
import java.util.Optional;

import com.oracle.s202350101.model.*;

public interface LkhDao {
	
	//DashBoard Home   도넛 그래프, 진척률 막대그래프
	// 작업별 상태 count -> 도넛차트
	List<Integer> 		doughnut_chart(int project_id);
	//회원별 작업 진척도
	List<Task>			workload_chart(int project_id);

	List<PrjStep>	    project_step_select(int project_id);
	List<Task>	   	 	project_step_chart(int project_id);

	PrjInfo				project_day(int project_id);
	// 해당 프로젝트의 작업의 총 개수보기
	int					task_count(Task task);


	// 작업 리스트 보기
	List<Task>			task_list(Task task);
	List<Task>			task_search(Task task);
	List<Task>			task_time_decs(Task task);
	List<Task>			task_time_aces(Task task);

	// 작업 타임라인보기
	List<Task>			task_timeline();


	// ----------------------작업 상세 내용 ----------------------//
	Task				task_detail(int task_id, int project_id);
	//작업 상세 내역에서 첨부파일 리스트 보여주기
	List<TaskAttach>	task_attach_list(int task_id, int project_id);


	//해당 작업의 같이하는 사람들 리스트
	List<TaskSub> 		taskWorkerlist(TaskSub taskSub);




	// 현재 속한 팀프로젝트의 프로젝트 단계 보여주기(생성폼에서 보여주는 거 )
	List<PrjStep> 		project_step_list(int project_id);
	// 현재 속한 팀프로젝트의  인원들보여주기
	List<UserInfo> 		task_create_form_worker_list(int project_id);


	//----------------------  작업 생성   ----------------------//
	int 				task_create(Task task);
	int					task_worker_create(List<TaskSub> taskSubList);
	int					task_attach_max();
	int					task_attach_create(List<TaskAttach> taskAttachList);
	//----------------------  작업 수정  task  ----------------------//

	int					task_update(Task task);

	int					task_worker_init(int projectId,int taskId);
	int					task_worker_update(List<TaskSub> taskSubList);
	int					task_attach_update(List<TaskAttach> taskAttachList);

	// ----------------- 휴지통관련 ,삭제관련 ------------- ///
	//휴지통 목록
	List<Task> 			garbage_list(Task task);

	//휴지통으로 이동시키기
	int					task_garbage(Task task);

	// 휴지통에 잇는 작업 개수 반환
	int					garbage_count(int project_id);

	// 휴지통에서도 삭제(영구삭제)
	int					task_delete(Task task);

	// 휴지통에서 복구시키기
	int					task_restore(Task task);



	
}
