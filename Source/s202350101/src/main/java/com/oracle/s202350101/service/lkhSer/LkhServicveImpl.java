package com.oracle.s202350101.service.lkhSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.TaskSub;
import com.oracle.s202350101.model.UserInfo;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.lkhDao.LkhDao;
import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;
import com.oracle.s202350101.model.Task;

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


	public List<Integer> task_status_count(int project_id) {
		List<Integer> taskStatusList = null;
		taskStatusList = lkhDao.task_status_count(project_id);
		return taskStatusList;
	}


	@Override
	public List<Task> task_user_workStatus(int project_id) {
		List<Task> taskUserWorkStatusList = null;
		taskUserWorkStatusList = lkhDao.task_user_workload(project_id);
		return taskUserWorkStatusList;
	}


	@Override
	public List<Task> task_list(int project_id) {
		List<Task> taskList = null;
		taskList = lkhDao.task_list(project_id);
		return taskList;
	}


	// 작업 상세 내용
	@Override
	public Task task_detail(int task_id, int project_id) {
		return lkhDao.task_detail(task_id, project_id);
	}
	
	//현재 누가 같이 작업하는지 보여줌
	
	
	
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

	

	// 현재 작업 maxid값 가져오기
	public int	maxTaskid_select(){
		return lkhDao.maxTaskid_select();
	}




	// 위 세개 메소드를 합쳐서 트랙잭션 처리

	

	//여러명의 작업자가 함께 하는 경우
//	@Override
//	public int task_worker_create(Task task, List<String> selectedWorkers) {
//		int result = 0;
//		List<TaskSub> taskSubList = new ArrayList<>();
//		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
//		try {
//			lkhDao.task_create(task);
//			int taskmaxId = lkhDao.maxTaskid_select();
//			for (String workId : selectedWorkers) {
//				TaskSub taskSub = new TaskSub();
//				taskSub.setTask_id(taskmaxId);
//				taskSub.setProject_id(task.getProject_id());
//				taskSub.setWorker_id(workId);
//				taskSubList.add(taskSub);
//				log.info("작업자 생성");
//			}
//			lkhDao.task_worker_create(taskSubList);
//			transactionManager.commit(txStatus);
//			result = 1;
//		} catch (Exception e) {
//			transactionManager.rollback(txStatus);
//			log.info("service :tran_task_create error Message -> {}", e.getMessage());
//			result = -1;
//		}
//		return result;
//	}
}
