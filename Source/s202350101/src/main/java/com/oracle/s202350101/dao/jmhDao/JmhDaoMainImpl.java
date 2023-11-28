package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.Task;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JmhDaoMainImpl implements JmhDaoMain {

	private final SqlSession session;

	@Override
	public List<BdFree> selectMainBdFree(BdFree bdFree) {
		
		System.out.println("JmhDaoMainImpl selectMainBdFree START...");
		List<BdFree> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainBdFreeList", bdFree);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainBdFree subject->"+boardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainBdFree Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainBdFree END...");
		return boardList;
	}

	@Override
	public List<BdQna> selectMainBdQna(BdQna bdQna) {
		
		System.out.println("JmhDaoMainImpl selectMainBdQna START...");
		List<BdQna> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainBdQnaList", bdQna);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainBdQna subject->"+boardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainBdQna Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainBdQna END...");
		return boardList;
	}

	@Override
	public List<PrjBdData> selectMainData(PrjBdData board) {
		
		System.out.println("JmhDaoMainImpl selectMainData START...");
		List<PrjBdData> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainDataList", board);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainData subject->"+boardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainData Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainData END...");
		return boardList;
	}

	@Override
	public List<PrjBdRep> selectMainReport(PrjBdRep board) {

		
		System.out.println("JmhDaoMainImpl selectMainReport START...");
		List<PrjBdRep> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainReportList", board);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainReport subject->"+boardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainReport Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainReport END...");
		return boardList;
	}

	@Override
	public List<Meeting> selectMainMeeting(Meeting board) {
		
		System.out.println("JmhDaoMainImpl selectMainMeeting START...");
		List<Meeting> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainMeetingList", board);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainMeeting subject->"+boardList.get(0).getMeeting_title());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainMeeting Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainMeeting END...");
		return boardList;
	}

	@Override
	public List<Task> selectMainTask(Task board) {
		System.out.println("JmhDaoMainImpl selectMainTask START...");
		List<Task> boardList = null;		
		try {
			//---------------------------------------------------------------
			boardList = session.selectList("jmhMainTaskList", board);
			//---------------------------------------------------------------
			System.out.println("boardList.size()->"+boardList.size());
			if(boardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainTask subject->"+boardList.get(0).getTask_subject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainTask Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainTask END...");
		return boardList;
	}
	
}
