package com.oracle.s202350101.service.lkhSer;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.oracle.s202350101.model.*;
import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.lkhDao.LkhDao;
import com.oracle.s202350101.dao.lkhDao.LkhDaoImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;


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
	public List<TaskAttach> task_attach_list(int task_id, int project_id) {
		return lkhDao.task_attach_list(task_id,project_id);
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


	// 3개의 dao메서드 포함 => 트랜잭션 처리
	@Override
	public int task_create(Task task, List<MultipartFile> multipartFileList, String uploadPath) {
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			int taskResult = lkhDao.task_create(task);

			if (taskResult == 1 && task.getWorkerIdList() != null && !task.getWorkerIdList().isEmpty()) {
				List<TaskSub> taskSubList = new ArrayList<>();
				for (String workId : task.getWorkerIdList()) {
					TaskSub taskSub = new TaskSub();
					taskSub.setProject_id(task.getProject_id());
					taskSub.setWorker_id(workId);
					taskSubList.add(taskSub);
				}
				int taskSubResult = lkhDao.task_worker_create(taskSubList);
			}

			// 파일 처리 부분
			if (multipartFileList != null && !multipartFileList.isEmpty()) {
				List<TaskAttach> taskAttachList = new ArrayList<>();
				String attach_path = "upload";
				for (MultipartFile file : multipartFileList) {
					TaskAttach taskAttach = new TaskAttach();
					taskAttach.setTask_id(task.getTask_id());
					taskAttach.setProject_id(task.getProject_id());
					String fileName = upload_file(file.getOriginalFilename(), file.getBytes(), uploadPath);
					taskAttach.setAttach_name(fileName);
					taskAttach.setAttach_path(attach_path);
					taskAttachList.add(taskAttach);
				}
				lkhDao.task_attach_create(taskAttachList);
			}

			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			log.info("service: createGroupTask, transactionManager error Message -> {}", e.getMessage());
		}
		return 1;
	}

	@Override
	public int task_attach_create(List<TaskAttach> taskAttachList) {
		return lkhDao.task_attach_create(taskAttachList);
	}

	@Override
	public int task_update(Task task, List<MultipartFile> multipartFileList, String uploadPath) {
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
			int taskResult = lkhDao.task_update(task);
			log.info("taskReuslt : {}",taskResult);
			if (taskResult == 1 && task.getWorkerIdList() != null && !task.getWorkerIdList().isEmpty()) {

				// 작업자 업데이트
				List<TaskSub> taskSubList = new ArrayList<>();
				for (String workId : task.getWorkerIdList()) {
					TaskSub taskSub = new TaskSub();
					taskSub.setProject_id(task.getProject_id());
					taskSub.setWorker_id(workId);
					taskSubList.add(taskSub);
				}

				int taskSbResult = lkhDao.task_worker_update(taskSubList);
				log.info("taskSbResult : {}",taskSbResult);
			}

			if (multipartFileList != null && !multipartFileList.isEmpty()) {
				// 파일 업로드
				List<TaskAttach> taskAttachList = new ArrayList<>();
				String attach_path = "upload";
				for (MultipartFile file : multipartFileList) {
					TaskAttach taskAttach = new TaskAttach();
					taskAttach.setTask_id(task.getTask_id());
					taskAttach.setProject_id(task.getProject_id());
					String fileName = upload_file(file.getOriginalFilename(), file.getBytes(), uploadPath);
					taskAttach.setAttach_name(fileName);
					taskAttach.setAttach_path(attach_path);
					taskAttachList.add(taskAttach);
				}

				// 파일 업데이트
				if (!taskAttachList.isEmpty()) {
					lkhDao.task_attach_update(taskAttachList);
				}
			}

			transactionManager.commit(txStatus);
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			log.error("service : task_update transactionManager error Message -> {}", e.getMessage());
			return -1; // 실패했을 경우를 알리기 위해 다른 값을 반환하도록 설정 가능
		}
		return 1;
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

	@Override
	public String upload_file(String originalName, byte[] fileData, String uploadPath) throws IOException {

		UUID uid = UUID.randomUUID();
		// requestPath = requestPath + "/resources/image";
		System.out.println("uploadPath->"+uploadPath);
		// Directory 생성
		File fileDirectory = new File(uploadPath);
		if (!fileDirectory.exists()) {
			// 신규 폴더(Directory) 생성
			fileDirectory.mkdirs();
			System.out.println("업로드용 폴더 생성 : " + uploadPath);
		}

		String savedName = uid.toString() + "_" + originalName;
		log.info("savedName: " + savedName);
		File target = new File(uploadPath, savedName);
		//	    File target = new File(requestPath, savedName);
		// File UpLoad   --->  uploadPath / UUID+_+originalName
		FileCopyUtils.copy(fileData, target);   // org.springframework.util.FileCopyUtils
		return  savedName;
	}

	@Override
	public int upFileDelete(String deleteFileName) throws Exception {
		int result =0;
		log.info("upFileDelete result-> " + deleteFileName);
		File file = new File(deleteFileName);
		if( file.exists() ){
			if(file.delete()){
				System.out.println("파일삭제 성공");
				result = 1;
			}
			else{
				System.out.println("파일삭제 실패");
				result = 0;
			}
		}
		else{
			System.out.println("삭제할 파일이 존재하지 않습니다.");
			result = -1;
		}
		return result;
	}


}