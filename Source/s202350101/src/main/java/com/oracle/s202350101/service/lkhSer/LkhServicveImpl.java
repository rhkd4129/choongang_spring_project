package com.oracle.s202350101.service.lkhSer;

import java.util.List;

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
	
	
	public List<Integer> task_status_count() {
		List<Integer> taskStatusList= null;
		taskStatusList = lkhDao.task_status_count();
		return taskStatusList;
	}
	
	
	@Override
	public List<Task> task_user_workStatus() {
		List<Task> taskUserWorkStatusList= null;
		taskUserWorkStatusList = lkhDao.task_user_workStatus();
		return taskUserWorkStatusList;
	}


	@Override
	public List<Task> task_board() {
		List<Task> boardList= null;
		boardList = lkhDao.task_board();
		return boardList;
	}


	@Override
	public List<Task> task_table() {
		List<Task> taskList= null;
		taskList = lkhDao.task_table();
		return taskList;
	}

}
