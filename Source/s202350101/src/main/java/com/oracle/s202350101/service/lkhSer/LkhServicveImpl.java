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

		List<PrjStep> stepList = lkhDao.project_step_select(project_id);
		// 현재 프로젝트에 있는 단계 리스트

		List<String> stepNameList = stepList.stream().map(m->m.getProject_s_name()).collect(Collectors.toList());
		//현재 프로젝트에 잇는 단계에 이름만 따로 갖고옴

		List<Task> stepTaskList =  lkhDao.project_step_chart(project_id);
		// 현재 프로젝트에 잇는 작업 리스트


		// 작업 리스트중에 진행중인 작업만 가져오기  -> 최종적으로 보낼 데이터
		List<Task> currentTaskList = stepTaskList.stream().filter(t->t.getTask_status().equals("1")).collect(Collectors.toList());



		// 우선 map을 선언후 키로 각 프로젝트 단계 이름을 지정
		// 값에는 빈리스트 지정
		Map<String, List<String>> mapData = new HashMap<>();
		stepNameList.stream().forEach(m->mapData.put(m, new ArrayList<>()));


		// map의 키를 순회(프로젝트 단계이름)하면서 작업들도 같이 순회 ->
		// 작업의 단계이름과 키의 단계이름이 같으면 해당 키의 값에 잇는 리스트에 추가함
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
		AjaxResponse data = new AjaxResponse();

		data.setMapData(mapData);
		data.setOnelist(currentTaskList);
		return data;
	}

	@Override
	public int task_count(Task task){
		return lkhDao.task_count(task);
	}


	@Override
	public List<Task> task_list(Task task) {
		List<Task> taskList = lkhDao.task_list(task);
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
	public List<Task> task_timeline(int project_id) {
		return lkhDao.task_timeline(project_id);
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
		
		log.info("task_create 서비스 시작");
		try {
			int taskResult = lkhDao.task_create(task);
			log.info("task_create 결과 : {}",taskResult);
			
			if (taskResult == 1 && task.getWorkerIdList() != null && !task.getWorkerIdList().isEmpty()) {
				log.info("공동작업자가 있다. 공동작업자 생성 시작");
				List<TaskSub> taskSubList = new ArrayList<>();
				for (String workId : task.getWorkerIdList()) {
					TaskSub taskSub = new TaskSub();
					taskSub.setProject_id(task.getProject_id());
					taskSub.setWorker_id(workId);
					taskSubList.add(taskSub);
				}
				int taskSubResult = lkhDao.task_worker_create(taskSubList);
				log.info("taskSub 결과  : {}",taskSubResult);
			}
			
			//작업 생성시 업로드할 파일이 존재할경우
			if (!multipartFileList.isEmpty() && multipartFileList.get(0).getSize()>0) {

				log.info("업로드할 파일이 하나이상 존재합니다.");
				int maxId =lkhDao.task_attach_max(task.getTask_id());
				log.info("지금현재 task_attach의 max값은 ? {}",maxId);


				List<TaskAttach> taskAttachList = new ArrayList<>();
				String attach_path = "upload";
				int i  = 1;
				for (MultipartFile file : multipartFileList) {
					TaskAttach taskAttach = new TaskAttach();
					taskAttach.setTask_id(task.getTask_id());
					taskAttach.setProject_id(task.getProject_id());
					taskAttach.setAttach_no(maxId+i);
					// 파일리스트에 3개가 들어모면 i+1을 더하는 형식으로 현재 max값에 +1 , +2  , +3 으로 pk생성하는방식
					String fileName = upload_file(file.getOriginalFilename(), file.getBytes(), uploadPath);
					taskAttach.setAttach_name(fileName);
					taskAttach.setAttach_path(attach_path);
					taskAttachList.add(taskAttach);
					i+=1;
					log.info("현재 저장될 taskAttach의 attach_no의 값은 ? -> {}",taskAttach.getAttach_no());
				}
				int taskAttachReuslt = lkhDao.task_attach_create(taskAttachList);
				// 다중 첨부파일 업로드 
				log.info("taskAttachCreate의 결과는 : {}",taskAttachReuslt);
			}

			transactionManager.commit(txStatus);
			log.info("커밋 성공하였습니다.");
		} catch (Exception e) {
			transactionManager.rollback(txStatus);
			log.info("service: createGroupTask, transactionManager error Message -> {}", e.getMessage());
		}
		return 1;
	}


	// 1.작업만 수정하는경우
	// 2.공동작업자는 수정할경우 기존에잇던 것을 다 delete처리하고 다시 insert함

	@Override
	public int task_update(Task task, List<MultipartFile> multipartFileList, String uploadPath,List<String> attachDeleteList) {
		log.info("task_create 서비스 시작");
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		try {
					//////////////		Task update		//////////////
			int taskResult = lkhDao.task_update(task);
			log.info("taskupdate Reuslt : {}",taskResult);

				//////////////		TaskSub테이블 update		//////////////


			//수정 폼에서 공동작업자를 아무도 선택하지 않았다면 해당 작업의 taskSub를 다 delete처리
			if(taskResult == 1 && task.getWorkerIdList() == null){
				int taskSbinit = lkhDao.task_worker_init(task.getProject_id(), task.getTask_id());
				log.info("작업자가 없는상태로 되돌리기 taskSbinit 결과  :{}",taskSbinit);
			}

			//공동작업자리스트에 하나라도 있다면.
			if (taskResult == 1 && task.getWorkerIdList() != null && !task.getWorkerIdList().isEmpty()) {

				List<TaskSub> taskSubList = new ArrayList<>();
				for (String workId : task.getWorkerIdList()) {
					TaskSub taskSub = new TaskSub();
					taskSub.setTask_id(task.getTask_id());
					taskSub.setProject_id(task.getProject_id());
					taskSub.setWorker_id(workId);
					taskSubList.add(taskSub);
					log.info("workerId ----- {}: ",workId);
				}
				// 공동작업자의 수정(update)는 기존에잇던 명단을 다  delete처리하고  새로 다시 insert한다는 개념
				// 필드내용을 업데이트하는게 아니라 아예 행을 새로 넣고 삭제하고 (insert, delete)해야 해서   
				int taskSbinit = lkhDao.task_worker_init(task.getProject_id(), task.getTask_id());
				int taskSbResult = lkhDao.task_worker_update(taskSubList);
				log.info("taskSbinit : {}",taskSbinit);
				log.info("taskSbResult : {}",taskSbResult);
			}


			//////////////		TaskAttach테이블 update		//////////////
			//삭제할 파일이 하나라도 존재한다면
			if (attachDeleteList != null && !attachDeleteList.isEmpty()) {
				for(String no :attachDeleteList)	{
					TaskAttach taskAttach = new TaskAttach();
					taskAttach.setTask_id(task.getTask_id());
					taskAttach.setProject_id(task.getProject_id());
					taskAttach.setAttach_no(Integer.parseInt(no));
					log.info("삭제될 파일의 no {}",no);

					//먼저 물리적 경로에 잇는 파일을 삭제하기위해 select해오기
					TaskAttach selectFile = lkhDao.physical_file_delete(taskAttach);
					log.info("selectFile.getTask_id() :{}", selectFile.getTask_id());
					log.info("selectFile.getAttach_no() :{}", selectFile.getAttach_no());

					//삭제할 파일의 path와 name갖고오기
					String deleteFile = uploadPath+selectFile.getAttach_name();
					log.info("삭제할 파일 이름 {}",deleteFile);
					upFileDelete(deleteFile);			 // 실제 물리 파일 삭제
					log.info("물리 파일 삭제 이제 db파일 삭제 할 차레");
					lkhDao.task_attach_delete(taskAttach); //DB상에서 삭제 완료
				}
			}

			// 추가적으로 파일을 업로드 해야한다면
			if (multipartFileList != null && !multipartFileList.isEmpty() && multipartFileList.get(0).getSize()>0 ) {
				// 파일 업로드
				List<TaskAttach> taskAttachList = new ArrayList<>();
				int maxId =lkhDao.task_attach_max(task.getTask_id());
				log.info("현재 작업에 대한 taskAttach의 max값은?  : {}",maxId);
				int i=1;
				String attach_path = "upload";
				for (MultipartFile file : multipartFileList) {
					TaskAttach taskAttach = new TaskAttach();
					taskAttach.setTask_id(task.getTask_id());
					taskAttach.setProject_id(task.getProject_id());
					taskAttach.setAttach_no(maxId+i);
					i+=1;
					String fileName = upload_file(file.getOriginalFilename(), file.getBytes(), uploadPath);
					taskAttach.setAttach_name(fileName);
					taskAttach.setAttach_path(attach_path);
					log.info("저장될 파일의정보 TaskID : {}   attachPk: {}",taskAttach.getTask_id(), taskAttach.getAttach_no());
					taskAttachList.add(taskAttach);
				}
				lkhDao.task_attach_update(taskAttachList);

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
		log.info("물리적 파일 삭제 시작");
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
