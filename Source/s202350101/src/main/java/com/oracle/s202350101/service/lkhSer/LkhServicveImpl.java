package com.oracle.s202350101.service.lkhSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.*;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.lkhDao.LkhDao;
import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Service
@Slf4j
@RequiredArgsConstructor
public class LkhServicveImpl implements LkhService {

	private final LkhDao lkhDao;
	private final PlatformTransactionManager transactionManager;

	@Override
	public List<Integer> doughnut_chart(int project_id) {
		List<Integer> taskStatusList = null;
		taskStatusList = lkhDao.doughnut_chart(project_id);
		return taskStatusList;
	}


	@Override
	public List<Task> workload_chart(int project_id) {
		List<Task> taskUserWorkStatusList = null;
		taskUserWorkStatusList = lkhDao.workload_chart(project_id);
		return taskUserWorkStatusList;
	}

	@Override
	public PrjInfo project_day(int project_id) {
		PrjInfo prjInfo = null;
		prjInfo = lkhDao.project_day(project_id);
		return prjInfo;
	}

	@Override
	public int task_count(int project_id) {
		return lkhDao.task_count(project_id);

	}


	@Override
	public List<Task> task_list(Task task) {
		List<Task> taskList = null;
		taskList = lkhDao.task_list(task);
		return taskList;
	}

	@Override
	public List<Task> task_time_decs(Task task) {
		List<Task> taskList = null;
		taskList = lkhDao.task_time_decs(task);
		return taskList;
	}

	@Override
	public List<Task> task_time_aces(Task task) {
		List<Task> taskList = null;
		taskList = lkhDao.task_time_aces(task);
		return taskList;
	}

	// 작업 상세 내용
	@Override
	public Task task_detail(int task_id, int project_id) {
		return lkhDao.task_detail(task_id, project_id);
	}


	@Override
	public List<TaskSub> taskWorkerlist(TaskSub taskSub) {
		return lkhDao.taskWorkerlist(taskSub);
	}

	//프로젝트 타임라이ㅏㄴ
	@Override
	public List<Task> task_timeline() {
		return lkhDao.task_timeline();
	}

	//task create get form view
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

	// task crate post
	@Override
	public int task_create(Task task) {
		return lkhDao.task_create(task);
	}

	@Override
	public int createGroupTask(List<String> workerList, Task task
	) {
		int result = 0;
		List<TaskSub> taskSubList = new ArrayList<>();

		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			lkhDao.task_create(task);
			for (String workId : workerList) {
				TaskSub taskSub = new TaskSub();
				taskSub.setProject_id(task.getProject_id());
				taskSub.setWorker_id(workId);
				taskSubList.add(taskSub);
				log.info("작업자 생성");
			}
			lkhDao.task_worker_create(taskSubList);
			transactionManager.commit(txStatus);
			result = 1;
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			log.info("service :createGroupTask error Message -> {}", e.getMessage());
			result = -1;
		}
		return result;
	}


	//휴지통
	@Override
	public int task_garbage(Task task) {
		return lkhDao.task_garbage(task);
	}

	@Override
	public List<Task> garbage_list(Task task) {
		return lkhDao.garbage_list(task);
	}

	@Override
	public int garbage_count(int project_id) {
		return lkhDao.garbage_count(project_id);
	}

	@Override
	public int task_delete(Task task){
		return lkhDao.task_delete(task);
	}

	@Override
	public int task_restore(Task task) {
		return lkhDao.task_restore(task);
	}
}

