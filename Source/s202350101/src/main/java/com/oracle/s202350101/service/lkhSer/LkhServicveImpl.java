package com.oracle.s202350101.service.lkhSer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.*;
import org.aspectj.weaver.loadtime.Aj;
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
	public AjaxResponse 	project_step_chart(int project_id) {
		List<Task> stepTaskList = null;
		List<PrjStep> stepList = null;
		stepList = lkhDao.project_step_select(project_id);
		//프로젝트 단계 리스트
		List<String> stepNameList = stepList.stream().map(m->m.getProject_s_name()).collect(Collectors.toList());
		//프로젝트 작업 리스트 
		stepTaskList =  lkhDao.project_step_chart(project_id);

		AjaxResponse data = new AjaxResponse();
		Map<String, List<String>> mapData = new HashMap<>();

		// 맵에 값 추가
		
		// 우선 map을 선언후 키로 각 프로젝트 단계이름을 지정 하고 값에는 빈리스트 생성
		
		stepNameList.stream().forEach(m->mapData.put(m, new ArrayList<>()));
		
		// 프로젝트 단게이름을 순회하고 작업들도 순회하면서 서로 단계이름이 같으면 해당 키에 있는 값 리스트에 추가함
		for (String key : mapData.keySet()) {
			for (Task t : stepTaskList) {
				if (t.getProject_s_name().equals(key)) {
					// 해당하는 키에 맞는 값 목록 가져오기
					List<String> values = mapData.get(key);
					if (values != null) {
						// 값을 List<String>에 추가
						values.add(t.getTask_subject());
					}
				}
			}
		}
		// 최종적으로 키에는 단계이름 값에는 그 단계에 해당하는 작업들이 들어잇는 map이 완성됨
		data.setMapData(mapData);
		// 맵 값 출력
		for (Map.Entry<String, List<String>> entry : mapData.entrySet()) {
			String key = entry.getKey();
			List<String> values = entry.getValue();
			System.out.print(key + ": ");
			for (String value : values) {
				System.out.print(value + " ");
			}
			System.out.println();
		}
		return data;
	}

	@Override
	public int task_count(int project_id,Optional<String>  search){
		return lkhDao.task_count(project_id, search);
	}


	@Override
	public List<Task> task_list(Task task) {
		List<Task> taskList = null;
		taskList = lkhDao.task_list(task);
		return taskList;
	}

	public List<Task> task_search(Task task) {
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
			if (!multipartFileList.isEmpty()) {
				log.info("파일이 있다!!!!");
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
			if(taskResult == 1 && task.getWorkerIdList() == null){
				int taskSbinit = lkhDao.task_worker_init(task.getProject_id(), task.getTask_id());
				log.info("작업자가 없는상태로 되돌리기 taskSbinit :{}",taskSbinit);
			}
			if (taskResult == 1 && task.getWorkerIdList() != null && !task.getWorkerIdList().isEmpty()) {
				// 작업자 업데이트
				List<TaskSub> taskSubList = new ArrayList<>();
				for (String workId : task.getWorkerIdList()) {
					TaskSub taskSub = new TaskSub();
					taskSub.setTask_id(task.getTask_id());
					taskSub.setProject_id(task.getProject_id());
					taskSub.setWorker_id(workId);
					taskSubList.add(taskSub);
					log.info("proejct_id ----- {}   task_id {}: ",task.getProject_id(),task.getTask_id());
					log.info("workerId ----- {}: ",workId);

				}
				int taskSbinit = lkhDao.task_worker_init(task.getProject_id(), task.getTask_id());
				log.info("taskSbinit : {}",taskSbinit);
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
			log.info("transactionManager.commit(txStatus) --> 성공!!!!!!!!!");
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