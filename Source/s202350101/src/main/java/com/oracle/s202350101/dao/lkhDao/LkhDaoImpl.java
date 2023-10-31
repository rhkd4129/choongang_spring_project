package com.oracle.s202350101.dao.lkhDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<Integer> task_status_count() {
		List<Integer> taskStatusList= null;
		try {
			taskStatusList = sqlSession.selectList("taskStatusList");		
		} catch (Exception e) {
			log.info("dao : task_status_count error Message -> {}",e.getMessage());
		}
		return taskStatusList;
	}

	
	@Override
	public List<Task> task_user_workStatus() {
		List<Task> taskUserWorkStatusList= null;
		try {
			taskUserWorkStatusList = sqlSession.selectList("taskUserWorkStatus");		
		} catch (Exception e) {
			log.info("dao : task_user_workStatus error Message -> {}",e.getMessage());
		}
		return taskUserWorkStatusList;
	}


	@Override
	public List<Task> task_board() {
		List<Task> boardList= null;
		try {
			boardList = sqlSession.selectList("boardTask");		
		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return boardList;
	
	}


	@Override
	public List<Task> task_table() {
		List<Task> boardTableList= null;
		try {
			boardTableList = sqlSession.selectList("boardTable");		
		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
		}
		return boardTableList;
	
	
	}

	@Override
	@PostMapping("create_task")
	public int task_create(Task task) {
		int result = 0;
		try {
			int i = sqlSession.insert("task_create",task);
		} catch (Exception e) {
			log.info("dao :task_board error Message -> {}",e.getMessage());
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


}
