package com.oracle.s202350101.service.jmhSer;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoPrjBdRepImpl;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdRep;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  //생성자 없어도 되고 final변수 자동 인스턴스화
@Transactional
public class JmhServicePrjBdRepImpl implements JmhServicePrjBdRep {

	private final JmhDaoPrjBdRepImpl jmhRepDao;

	//총건수
	@Override
	public int totalCount() {
		System.out.println("JmhServiceImpl totalCount START...");		
		//---------------------------------
		int totalCnt = jmhRepDao.totalCount();
		//---------------------------------		
		System.out.println("JmhServiceImpl totalCount totalCnt->" + totalCnt);
		System.out.println("JmhServiceImpl totalCount END...");
		return totalCnt;
	}
	
	//목록
	@Override
	public List<PrjBdRep> boardList(PrjBdRep prjBdRep) {
		System.out.println("JmhServiceImpl boardList START...");
		List<PrjBdRep> prjBdRepList = null;
		//------------------------------------------
		prjBdRepList = jmhRepDao.boardList(prjBdRep);
		//------------------------------------------		
		System.out.println("JmhServiceImpl boardList END...");
		return prjBdRepList;
	}

	//분류
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("JmhServiceImpl codeList START...");
		List<Code> reCodeList = null;
		//---------------------------------
		reCodeList = jmhRepDao.codeList(code);
		//---------------------------------
		System.out.println("JmhServiceImpl codeList END...");
		return reCodeList;
	}

	//등록
	@Override
	public int insertBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServiceImpl insertBoard START...");
		int resultCount = 0;		
		Date sysdate = new Date();
		prjBdRep.setCreate_date(sysdate);		
		//------------------------------------------
		resultCount = jmhRepDao.insertBoard(prjBdRep);
		//------------------------------------------
		System.out.println("JmhServiceImpl insertBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl insertBoard END...");
		return resultCount;
	}

	//조회
	@Override
	public PrjBdRep selectBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServiceImpl selectBoard START...");
		PrjBdRep selectPrjBdRep = null;		
		//----------------------------------------------
		selectPrjBdRep = jmhRepDao.selectBoard(prjBdRep);
		//----------------------------------------------		
		System.out.println("JmhServiceImpl selectBoard END...");
		return selectPrjBdRep;
	}

	//수정
	@Override
	public int updateBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServiceImpl updateBoard START...");
		int resultCount = 0;				
		//------------------------------------------
		resultCount = jmhRepDao.updateBoard(prjBdRep);
		//------------------------------------------
		System.out.println("JmhServiceImpl updateBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateBoard END...");
		return resultCount;
	}

	//삭제
	@Override
	public int deleteBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServiceImpl deleteBoard START...");
		int resultCount = 0;				
		//------------------------------------------
		resultCount = jmhRepDao.deleteBoard(prjBdRep);
		//------------------------------------------
		System.out.println("JmhServiceImpl deleteBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl deleteBoard END...");
		return resultCount;
	}

	//댓글 등록
	@Override
	public int insertComment(BdRepComt bdRepComt) {
		System.out.println("JmhServiceImpl insertComment START...");
		int resultCount = 0;				
		//---------------------------------------------------
		resultCount = jmhRepDao.insertComment(bdRepComt);
		//---------------------------------------------------
		System.out.println("JmhServiceImpl insertComment resultCount->"+resultCount);
		System.out.println("JmhServiceImpl insertComment END...");
		return resultCount;
	}

	//댓글 조회
	public List<BdRepComt> selectCommentList(BdRepComt bdRepComt) {
		System.out.println("JmhServiceImpl selectCommentList START...");
		List<BdRepComt> resultBdRepComtList = null;				
		//--------------------------------------------------------
		resultBdRepComtList = jmhRepDao.selectCommentList(bdRepComt);
		//--------------------------------------------------------
		System.out.println("JmhServiceImpl selectCommentList resultBdRepComtList.size()->"+resultBdRepComtList.size());
		System.out.println("JmhServiceImpl selectCommentList END...");
		return resultBdRepComtList;
	}

	//댓글 삭제
	public int deleteComment(BdRepComt bdRepComt) {
		System.out.println("JmhServiceImpl deleteComment START...");
		int resultCount = 0;				
		//---------------------------------------------------
		resultCount = jmhRepDao.deleteComment(bdRepComt);
		//---------------------------------------------------
		System.out.println("JmhServiceImpl deleteComment resultCount->"+resultCount);
		System.out.println("JmhServiceImpl deleteComment END...");
		return resultCount;
	}

}
