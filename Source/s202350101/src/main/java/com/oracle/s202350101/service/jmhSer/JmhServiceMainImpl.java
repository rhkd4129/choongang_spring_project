package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoMain;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class JmhServiceMainImpl implements JmhServiceMain {
	
	private final JmhDaoMain jmhMDao;
	
	@Override
	public List<BdFree> selectMainBdFree(BdFree bdFree) {
				
		System.out.println("JmhServiceMainImpl selectMainBdFree START...");
		List<BdFree> mainBoardList = null;
		//----------------------------------------------------
		mainBoardList = jmhMDao.selectMainBdFree(bdFree);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainBdFree END...");
		return mainBoardList;
	}

	@Override
	public List<BdQna> selectMainBdQna(BdQna bdQna) {
		
		System.out.println("JmhServiceMainImpl selectMainBdQna START...");
		List<BdQna> mainBoardList = null;
		//----------------------------------------------------
		mainBoardList = jmhMDao.selectMainBdQna(bdQna);
		//----------------------------------------------------
		System.out.println("JmhServiceMainImpl selectMainBdQna END...");
		return mainBoardList;
	}

}
