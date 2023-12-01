package com.oracle.s202350101.dao.lkhDao;

import java.util.List;
import java.util.Optional;

import com.oracle.s202350101.model.*;

public interface LkhDao {

	// DashBoard Home   도넛 그래프, 진척률 막대그래프
	// 작업별 상태 count -> 도넛차트
	List<Integer> 		doughnut_chart(int project_id);
	//회원별 작업 진척도
	List<Task>			workload_chart(int project_id);

	PrjInfo				project_day(int project_id);

	List<PrjStep>	    project_step_select(int project_id);
	List<Task>	   	 	project_step_chart(int project_id);



	// 해당 프로젝트의 작업의 총 개수보기
	int					task_count(Task task);
	// 작업 리스트 보기
	List<Task>			task_list(Task task);

	List<Task>			task_time_decs(Task task);
	List<Task>			task_time_aces(Task task);
	// 작업 타임라인보기
	List<Task>			task_timeline(int project_id, String timeline_type);


	// ----------------------작업 상세 내용 ----------------------//
	Task				task_detail(int task_id, int project_id);
	//작업 상세 내역에서 첨부파일 리스트 보여주기
	List<TaskAttach>	task_attach_list(int task_id, int project_id);

	//해당 작업의 같이하는 사람들 리스트
	List<TaskSub> 		taskWorkerlist(TaskSub taskSub);
	// ----------------------------------------------------------------//



	//--------------------------------  작업 생성   ---------------------------------//
	// 1 . 새작업 폼에서 현재 프로젝트의 단계와 참여하는 인원 보여주는 메서드들 		GET
	List<PrjStep> 		project_step_list(int project_id);
	// 현재 속한 팀프로젝트의  인원들보여주기
	List<UserInfo> 		task_create_form_worker_list(int project_id);


	// 2.작성하기 눌럿을떄  작업테이블의 생성과 공동작업자 생성  첨부파일테이블의 생성 4개의 dao가 순차실행 -> 트랜잭션 처리 	POST
	int 				task_create(Task task) throws Exception;
	int					task_worker_create(List<TaskSub> taskSubList) throws Exception;
	int					task_attach_max(int task_id);
	int					task_attach_create(List<TaskAttach> taskAttachList) throws Exception;
	//-----------------------------------------------------------------------------------------//




	//----------------------  작업 수정  task  ----------------------//

	int					task_update(Task task)throws Exception;
	int					task_worker_init(int projectId,int taskId)throws Exception;
	int					task_worker_update(List<TaskSub> taskSubList)throws Exception;
	int					task_attach_update(List<TaskAttach> taskAttachList)throws Exception ;


	// 수정할때 첨부파일 삭제 시  물리적 파일 삭제 후 DB에서 파일 삭제
	TaskAttach 				physical_file_delete(TaskAttach  taskAttach) throws Exception;
	int					task_attach_delete(TaskAttach taskAttach) throws Exception;

	// --------------------------------------------------------- ///




	// ----------------- 휴지통관련 ,삭제관련 --------------------------------- ///
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