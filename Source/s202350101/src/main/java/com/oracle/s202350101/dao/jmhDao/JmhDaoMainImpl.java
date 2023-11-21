package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JmhDaoMainImpl implements JmhDaoMain {

	private final SqlSession session;

	@Override
	public List<BdFree> selectMainBdFree(BdFree bdFree) {
		
		System.out.println("JmhDaoMainImpl selectMainBdFree START...");
		List<BdFree> mainBoardList = null;		
		try {
			//---------------------------------------------------------------
			mainBoardList = session.selectList("jmhMainBdFreeList", bdFree);
			//---------------------------------------------------------------
			System.out.println("mainBoardList.size()->"+mainBoardList.size());
			if(mainBoardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainBdFree subject->"+mainBoardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainBdFree Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainBdFree END...");
		return mainBoardList;
	}

	@Override
	public List<BdQna> selectMainBdQna(BdQna bdQna) {
		
		System.out.println("JmhDaoMainImpl selectMainBdQna START...");
		List<BdQna> mainBoardList = null;		
		try {
			//---------------------------------------------------------------
			mainBoardList = session.selectList("jmhMainBdQnaList", bdQna);
			//---------------------------------------------------------------
			System.out.println("mainBoardList.size()->"+mainBoardList.size());
			if(mainBoardList.size() > 0) {
				//성공
				System.out.println("JmhDaoMainImpl selectMainBdQna subject->"+mainBoardList.get(0).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoMainImpl selectMainBdQna Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoMainImpl selectMainBdQna END...");
		return mainBoardList;
	}
	
}
