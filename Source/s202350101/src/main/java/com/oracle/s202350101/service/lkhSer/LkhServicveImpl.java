package com.oracle.s202350101.service.lkhSer;

import java.util.List;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.lkhDao.LkhDao;
import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class LkhServicveImpl implements LkhService {

	private final LkhDao lkhDao;
	@Override
	
	
	public List<Integer> task_status_count(int project_id) {
		List<Integer> taskStatusList= null;
		taskStatusList = lkhDao.task_status_count(project_id);
		return taskStatusList;
	}
	
	
	@Override
	public List<Task> task_user_workStatus(int project_id) {
		List<Task> taskUserWorkStatusList= null;
		taskUserWorkStatusList = lkhDao.task_user_workload(project_id);
		return taskUserWorkStatusList;
	}



	@Override
	public List<Task> task_list(int project_id) {
		List<Task> taskList= null;
		taskList = lkhDao.task_list(project_id);
		return taskList;
	}


	// 작업 상세 내용
	@Override
	public Task task_detail(int task_id, int project_id) {
		return lkhDao.task_detail(task_id,project_id);
	}

	
	//프로젝트 타임라이ㅏㄴ
	@Override
	public List<Task> task_timeline() {
		return  lkhDao.task_timeline();
	}

	
	//프로젝트 단계리스트 보여주기
	@Override
	public List<PrjStep> project_step_list(int project_id) {
		return lkhDao.project_step_list(project_id);
	}

	//프로젝트 인원 보여주기
	@Override
	public List<UserInfo> task_create_form_worker_list(int project_id) {
		return lkhDao.task_create_form_worker_list(project_id);
	}

	@Override
	public int task_create(Task task) {
		return lkhDao.task_create(task);
	}



}
