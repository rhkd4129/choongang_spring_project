package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoMain;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JmhServiceMainImpl implements JmhServiceMain {
	
	private final JmhDaoMain jmhMDao;
	
	@Override
	public List<BdFree> selectMainBdFree(BdFree bdFree) {
				
		System.out.println("JmhServiceMainImpl selectMainBdFree START...");
		List<BdFree> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainBdFree(bdFree);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainBdFree END...");
		return boardList;
	}

	@Override
	public List<BdQna> selectMainBdQna(BdQna bdQna) {
		
		System.out.println("JmhServiceMainImpl selectMainBdQna START...");
		List<BdQna> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainBdQna(bdQna);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainBdQna END...");
		return boardList;
	}

	@Override
	public List<PrjBdData> selectMainData(PrjBdData board) {
		
		System.out.println("JmhServiceMainImpl selectMainData START...");
		List<PrjBdData> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainData(board);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainData END...");
		return boardList;
	}

	@Override
	public List<PrjBdRep> selectMainReport(PrjBdRep board) {
		
		System.out.println("JmhServiceMainImpl selectMainReport START...");
		List<PrjBdRep> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainReport(board);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainReport END...");
		return boardList;
	}

	@Override
	public List<Meeting> selectMainMeeting(Meeting board) {
		
		System.out.println("JmhServiceMainImpl selectMainMeeting START...");
		List<Meeting> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainMeeting(board);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainMeeting END...");
		return boardList;
	}

	@Override
	public List<Task> selectMainTask(Task board) {

		System.out.println("JmhServiceMainImpl selectMainTask START...");
		List<Task> boardList = null;
		//----------------------------------------------------
		boardList = jmhMDao.selectMainTask(board);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainTask END...");
		return boardList;
	}

}
