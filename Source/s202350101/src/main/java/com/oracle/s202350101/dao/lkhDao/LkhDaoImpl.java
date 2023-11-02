package com.oracle.s202350101.dao.lkhDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.PrjStep;
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
			log.info("dao : task_user_workStatus error Message -> {}",e.getMessage());
		}
		return taskUserWorkload;
	}



	@Override
	public List<Task> task_list(int proejct_id) {
		List<Task> taskList= null;
		try {
			taskList = sqlSession.selectList("taskList",proejct_id);
		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return taskList;
	
	
	}

	@Override
	public int task_create(Task task) {
		int result = 0;
		try {
			int i = sqlSession.insert("task_create_form",task);
		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return result;
	}

	@Override
	public List<PrjStep> project_step_list(int project_id) {
		List<PrjStep> prj_step_list =null;
		try {
			prj_step_list = sqlSession.selectList("task_create_form", project_id);

		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return prj_step_list;


	}

	@Override
	public List<UserInfo> task_create_form_worker_list(int project_id) {
		List<UserInfo> task_create_form_worker_list =null;
		try {
			task_create_form_worker_list = sqlSession.selectList("task_create_form_worker_list", project_id);

		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return task_create_form_worker_list;
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
	public List<Task> task_timeline() {
		List<Task> timelineTask= null;

		try {
			timelineTask = sqlSession.selectList("task_timeline");

		} catch (Exception e) {
			log.info("dao :task_timeline error Message -> {}",e.getMessage());
		}
		return timelineTask;

	}




	//project check


}
