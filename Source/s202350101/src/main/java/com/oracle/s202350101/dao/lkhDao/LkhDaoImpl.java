package com.oracle.s202350101.dao.lkhDao;


import java.util.*;

import com.oracle.s202350101.model.*;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LkhDaoImpl implements LkhDao {
	private final SqlSession sqlSession;



	//#######################################################################
	//############  	작업보드	그래프 그려주는 ajax  			     ############
	//#######################################################################
	// 도넛 그래프
	@Override
	public List<Integer> doughnut_chart(int project_id) {
		List<Integer> taskStatusList = null;
		try {
			//---------------------------------------------------------------------------
			taskStatusList = sqlSession.selectList("doughnut_chart", project_id);
		} catch (Exception e) {
			log.info("dao : doughnut_chart error Message -> {}", e.getMessage());
		}
		return taskStatusList;
	}

	@Override
	public PrjInfo project_day(int project_id) {
		PrjInfo prjInfo = null;
		try {
			prjInfo = sqlSession.selectOne("project_day", project_id);
		} catch (Exception e) {
			log.info("dao : project_day error Message -> {}", e.getMessage());
		}
		return prjInfo;
	}


	// 진척률 그래프
	@Override
	public List<Task> workload_chart(int project_id) {
		List<Task> taskUserWorkload = null;
		try {
			taskUserWorkload = sqlSession.selectList("workload_chart", project_id);
		} catch (Exception e) {
			log.info("dao : task_user_workload error Message -> {}", e.getMessage());
		}
		return taskUserWorkload;
	}

	@Override
	public List<PrjStep> project_step_select(int project_id) {
		List<PrjStep> stepList = null;
		try {
			stepList = sqlSession.selectList("project_step_select", project_id);
		} catch (Exception e) {
			log.info("dao : task_user_workload error Message -> {}", e.getMessage());
		}
		return stepList;
	}

	@Override
	public List<Task> project_step_chart(int project_id) {
		List<Task> projectStepList = null;
		try {
			projectStepList = sqlSession.selectList("project_step_chart", project_id);
		} catch (Exception e) {
			log.info("dao : project_step_chart error Message -> {}", e.getMessage());
		}
		return projectStepList;
	}


	//#######################################################################
	//############  	인원별 타임라온			 			     ############
	//#######################################################################
	//작업별 타임라인
	@Override
	public List<Task> task_timeline(int project_id, String timeline_type) {
		List<Task> timelineTask = null;
		try {
			//timelineTask = sqlSession.selectList("task_timeline",project_id);
			//by_step(단계별 오름차순), by_user(팀원별 오름차순), by_create(작성일 내림차순)
			timelineTask = sqlSession.selectList("task_timeline_"+timeline_type, project_id);
		} catch (Exception e) {
			log.info("dao :task_timeline error Message -> {}", e.getMessage());
		}
		return timelineTask;

	}


	//#######################################################################
	//############  	작업 목록  							     ############
	//#######################################################################
	public int task_count(Task task) {
		int taskCount = 0;
		try {
			taskCount = sqlSession.selectOne("task_count", task);
		} catch (Exception e) {
			log.info("dao : task_count error Message -> {}", e.getMessage());
		}
		return taskCount;
	}



	//  작업 리스트
	@Override
	public List<Task> task_list(Task task) {
		List<Task> taskList = null;
		try {
			taskList = sqlSession.selectList("task_list", task);

		} catch (Exception e) {
			log.info("dao :task_list error Message -> {}", e.getMessage());
		}
		return taskList;
	}



	//작업 리스트에 시간 순으로 정렬(내림,올림)
	@Override
	public List<Task> task_time_decs(Task task) {
		List<Task> taskList = null;
		try {
			taskList = sqlSession.selectList("task_time_decs", task);
		} catch (Exception e) {
			log.info("dao :task_time_decs error Message -> {}", e.getMessage());
		}
		return taskList;
	}

	@Override
	public List<Task> task_time_aces(Task task) {
		List<Task> taskList = null;
		try {
			taskList = sqlSession.selectList("task_time_aces", task);

			System.out.println("--=------------------------------------------");
			for (Task t :taskList){
				System.out.println(t.getTask_subject());
			}


		} catch (Exception e) {
			log.info("dao :task_time_aces error Message -> {}", e.getMessage());
		}
		return taskList;
	}
	////////




	//#######################################################################
	//############  	작업 상세화면				  			     ############
	//#######################################################################
	@Override
	public Task task_detail(int task_id, int project_id) {
		Task task = new Task();
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("task_id", task_id);
			params.put("project_id", project_id);
			task = sqlSession.selectOne("task_detail", params);
		} catch (Exception e) {
			log.info("dao :task_detail error Message -> {}", e.getMessage());
		}
		return task;
	}

	// 작업 상세화면에서 공동작업자들 보여주기
	public List<TaskSub> taskWorkerlist(TaskSub taskSub) {
		List<TaskSub> taskSubList = null;
		try {
			taskSubList = sqlSession.selectList("taskWorkerlist", taskSub);

		} catch (Exception e) {
			log.info("dao :taskWorkerlist error Message -> {}", e.getMessage());
		}
		return taskSubList;
	}

	// 작업 상세화면에서 첨부파일 리스트
	@Override
	public List<TaskAttach> task_attach_list(int task_id,int project_id) {
		List<TaskAttach> taskAttachList = null;
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("task_id", task_id);
			params.put("project_id", project_id);
			taskAttachList = sqlSession.selectList("task_attach_list",params);

		} catch (Exception e) {
			log.info("dao :task_attach_list error Message -> {}", e.getMessage());
		}
		return taskAttachList;
	}






	//#######################################################################
	//############  	// task create form 보여주기 get 			   ##########
	//#######################################################################

	@Override
	public List<PrjStep> project_step_list(int project_id) {
		List<PrjStep> prj_step_list = null;
		try {
			prj_step_list = sqlSession.selectList("task_create_form_step_list", project_id);

		} catch (Exception e) {
			log.info("dao :project_step_list error Message -> {}", e.getMessage());
		}
		return prj_step_list;
	}

	// task create form 에서 같이 할 작업자 리스트 보여주기
	@Override
	public List<UserInfo> task_create_form_worker_list(int project_id) {
		List<UserInfo> task_create_form_worker_list = null;
		try {
			task_create_form_worker_list = sqlSession.selectList("task_create_form_worker_list", project_id);

		} catch (Exception e) {
			log.info("dao :task_create_form_worker_list error Message -> {}", e.getMessage());
		}
		return task_create_form_worker_list;
	}




	//#######################################################################
	//############  	작업 버튼 눌럿을시 							   ##########
	//#######################################################################

	@Override
	public int task_create(Task task) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("task_create", task);
		} catch (Exception e) {
			// 로깅은 여기서 수행
			log.error("dao: task_create error Message -> {}", e.getMessage());
			// 예외를 상위로 전파
			throw new Exception("task_create 메서드에서 예외 발생", e);
		}
		return result;
	}
	@Override
	public int task_worker_create(@Param("list") List<TaskSub> taskSubList) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("task_worker_create", taskSubList);
		} catch (Exception e) {
			// 로깅은 여기서 수행
			log.error("dao: task_worker_create error Message -> {}", e.getMessage());
			// 예외를 상위로 전파
			throw new Exception("task_worker_create 메서드에서 예외 발생", e);
		}
		return result;
	}



	public  int	task_attach_max(int task_id){
		int result = 0;
		try {
			result = sqlSession.selectOne("taskAttach_max",task_id);
		} catch (Exception e) {
			log.info("dao :task_attach_max error Message -> {}", e.getMessage());
		}
		return result;
	}

	@Override
	public int task_attach_create(@Param("list") List<TaskAttach> taskAttachList) throws Exception {
		int result = 0;
		try {
			result = sqlSession.insert("taskAttach_create", taskAttachList);
		} catch (Exception e) {
			// 로깅은 여기서 수행
			log.error("dao: taskAttach_create error Message -> {}", e.getMessage());
			// 예외를 상위로 전파
			throw new Exception("task_attach_create 메서드에서 예외 발생", e);
		}
		return result;
	}



	@Override
	public int task_update(Task task) throws Exception{
		int result =0;
		try {
			result = sqlSession.update("task_update",task);
		}
		catch (Exception e) {
			log.info("dao :task_update error Message -> {}", e.getMessage());
			throw new Exception("task_update error Message ->", e);
		}
		return result;
	}

	@Override
	public int task_worker_init(int projectId,int taskId) throws Exception {
		int result = 0;
		try{				//update엿음
			Map<String, Integer> params = new HashMap<>();
			params.put("task_id", taskId);
			params.put("project_id", projectId);
			result = sqlSession.delete("task_worker_init",params);
		}catch (Exception e) {
			log.info("dao :task_worker_init error Message -> {}", e.getMessage());
			throw new Exception("dao :task_worker_init error Message ->", e);
		}
		return result;
	}



	@Override
	public int task_attach_update(List<TaskAttach> taskAttachList) throws Exception{
		int reuslt =0;
		try {				//update엿음
			reuslt = sqlSession.insert("task_attach_update",taskAttachList);
		}
		catch (Exception e) {
			log.info("dao :task_attach_update error Message -> {}", e.getMessage());
			throw new Exception("dao :task_attach_update error Message ->", e);
		}
		return reuslt;
	}


	@Override
	public int task_worker_update(List<TaskSub> taskSubList)  throws Exception{
		int reuslt =0;
		try {						//update엿음
			reuslt = sqlSession.insert("task_worker_update",taskSubList);
		}
		catch (Exception e) {
			log.info("dao :task_worker_update error Message -> {}", e.getMessage());
			throw new Exception("dao :task_worker_update error Message ->", e);
		}
		return reuslt;

	}

	public TaskAttach physical_file_delete(TaskAttach  taskAttach) throws Exception{
		TaskAttach resultTaskAttach = null;
		try {
			resultTaskAttach = sqlSession.selectOne("physical_file_delete",taskAttach);
		}catch (Exception e){
			log.info("dao :physical_file_delete error Message -> {}", e.getMessage());
			throw new Exception("dao :physical_file_delete Message ->{}", e);
		}

		return resultTaskAttach;
	}

	@Override
	public int task_attach_delete(TaskAttach taskAttach) throws Exception{
		int reuslt =0;
		try {
			reuslt = sqlSession.delete("task_attach_delete",taskAttach);
		}
		catch (Exception e) {
			log.info("dao :task_attach_deletee error Message -> {}", e.getMessage());
			throw new Exception("dao :task_attach_delete Message ->", e);
		}
		return reuslt;

	}


	//#######################################################################
	//############  	휴지통 기능 (목록, 삭제 , 복구 , 영구삭제		##########
	//#######################################################################
	@Override
	public List<Task> garbage_list(Task task) {
		List<Task> garbageList = null;
		try {
			garbageList = sqlSession.selectList("garbage_list", task);
			log.info(garbageList.get(0).getUser_name());

		} catch (Exception e) {
			log.info("dao :garbage_list error Message -> {}", e.getMessage());
		}
		return garbageList;

	}

	@Override
	public int garbage_count(int project_id) {
		int result = 0;
		try {
			result = sqlSession.selectOne("garbage_count", project_id);
		} catch (Exception e) {
			log.info("dao :garbage_count error Message -> {}", e.getMessage());
		}
		return result;
	}


	@Override
	public int task_garbage(Task task) {
		int result = 0;
		try {
			result = sqlSession.update("task_garbage", task);
		} catch (Exception e) {
			log.info("dao :task_garbage error Message -> {}", e.getMessage());
		}
		return result;
	}


	@Override
	public int task_restore(Task task) {
		int result = 0;
		try {
			result = sqlSession.update("task_restore", task);
		}catch (Exception e){
			log.info("dao :task_resotre error Message -> {}", e.getMessage());
		}
		return  result;
	}


	@Override
	public int task_delete(Task task) {
		int result = 0;
		try {
			result = sqlSession.update("task_delete", task);
		}catch (Exception e){
			log.info("dao :task_delete error Message -> {}", e.getMessage());
		}
		return  result;
	}




}
