package com.oracle.s202350101.dao.lkhDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.TaskSub;
import com.oracle.s202350101.model.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.controller.LkhController;
import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;


@Repository
@Slf4j
@RequiredArgsConstructor
public class LkhDaoImpl implements LkhDao {
	private final SqlSession sqlSession;

	@Override
	public List<Integer> task_status_count(int project_id) {
		List<Integer> taskStatusList= null;
		try {
			taskStatusList = sqlSession.selectList("taskStatus_doughnut",project_id);
		} catch (Exception e) {
			log.info("dao : task_status_count error Message -> {}",e.getMessage());
		}
		return taskStatusList;
	}


	@Override
	public List<Task> task_user_workload(int project_id) {
		List<Task> taskUserWorkload= null;
		try {
			taskUserWorkload= sqlSession.selectList("taskUserWorkload",project_id);
		} catch (Exception e) {
			log.info("dao : task_user_workload error Message -> {}",e.getMessage());
		}
		return taskUserWorkload;
	}

	@Override
	public List<Task> task_timeline() {
		List<Task> timelineTask= null;

		try {
			timelineTask = sqlSession.selectList("task_timeline");

		} catch (Exception e) {
			log.info("dao :task_timeline error Message -> {}",e.getMessage());
		}
		return timelineTask;

	}


	@Override
	public List<Task> task_list(int proejct_id) {
		List<Task> taskList= null;
		try {
			taskList = sqlSession.selectList("taskList",proejct_id);
		} catch (Exception e) {
			log.info("dao :task_list error Message -> {}",e.getMessage());
		}
		return taskList;
	
	
	}
	// task create view에서 보여주기 get
	@Override
	public List<PrjStep> project_step_list(int project_id) {
		List<PrjStep> prj_step_list =null;
		try {
			prj_step_list = sqlSession.selectList("task_create_form_step_list", project_id);

		} catch (Exception e) {
			log.info("dao :project_step_list error Message -> {}",e.getMessage());
		}
		return prj_step_list;


	}

	@Override
	public List<UserInfo> task_create_form_worker_list(int project_id) {
		List<UserInfo> task_create_form_worker_list =null;
		try {
			task_create_form_worker_list = sqlSession.selectList("task_create_form_worker_list", project_id);

		} catch (Exception e) {
			log.info("dao :task_create_form_worker_list error Message -> {}",e.getMessage());
		}
		return task_create_form_worker_list;
	}

	// task_create post 실행
	@Override
	public int task_create(Task task) {
		int result = 0;
		try {
			int i = sqlSession.insert("task_create",task);
		} catch (Exception e) {
			log.info("dao :task_create error Message -> {}",e.getMessage());
		}
		return result;
	}

	@Override
	public int maxTaskid_select() {
		int result = 0;
		try {
			result = sqlSession.selectOne("maxTaskid_select");
		} catch (Exception e) {
			log.info("dao :maxTaskid_select error Message -> {}",e.getMessage());
		}
		return result;
	}

	@Override
	public int task_worker_create(List<TaskSub> taskSubList) {
		int result = 0;
		try {
			int i = sqlSession.insert("task_worker_create",taskSubList);
		} catch (Exception e) {
			log.info("dao :task_worker_create error Message -> {}",e.getMessage());
		}
		return result;

	}


	@Override
	public Task task_detail(int task_id, int project_id) {
		Task task = new Task();
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("task_id", task_id);
			params.put("project_id", project_id);
			task = sqlSession.selectOne("task_detail", params);

		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return task;
	}


	@Override
	public List<TaskSub> taskWorkerlist(int project_id, int task_id) {

		List<TaskSub> taskWorkerlist= new ArrayList<>();
		try {
			Map<String, Integer> params = new HashMap<>();
			params.put("task_id", task_id);
			params.put("project_id", project_id);
			taskWorkerlist = sqlSession.selectList("taskWorkerlist", params);

		} catch (Exception e) {
			log.info("dao :taskWorkerlist error Message -> {}",e.getMessage());
		}
		return taskWorkerlist;

	}



	//project check


}
