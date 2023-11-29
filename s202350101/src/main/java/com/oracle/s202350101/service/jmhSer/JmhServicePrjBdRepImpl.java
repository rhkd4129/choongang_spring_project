package com.oracle.s202350101.service.jmhSer;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoPrjBdRep;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdRep;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  //생성자 없어도 되고 final변수 자동 인스턴스화
@Transactional
public class JmhServicePrjBdRepImpl implements JmhServicePrjBdRep {

	private final JmhDaoPrjBdRep jmhRepDao;

	//총건수
	@Override
	public int totalCount(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl totalCount START...");

		int totalCnt = 0;
		
		if(prjBdRep.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdRep.getSearch());
			if(!prjBdRep.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdRep.getKeyword());
				//검색 건수 가져오기
				//------------------------------------------
				totalCnt = jmhRepDao.searchCount(prjBdRep);
				//------------------------------------------
				System.out.println("JmhServicePrjBdRepImpl totalCount totalCnt->" + totalCnt);
				System.out.println("JmhServicePrjBdRepImpl totalCount END...");
				return totalCnt;
			}
		}
		//-----------------------------------------
		totalCnt = jmhRepDao.totalCount(prjBdRep);
		//-----------------------------------------
		System.out.println("JmhServicePrjBdRepImpl totalCount totalCnt->" + totalCnt);
		System.out.println("JmhServicePrjBdRepImpl totalCount END...");
		return totalCnt;
	}
	
	//목록
	@Override
	public List<PrjBdRep> boardList(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl boardList START...");
		
		List<PrjBdRep> prjBdRepList = null;
		
		if(prjBdRep.getKeyword() != null) {
			if(!prjBdRep.getKeyword().equals("")) {
				//--------------------------------------------
				prjBdRepList = jmhRepDao.searchList(prjBdRep);
				//--------------------------------------------
				System.out.println("JmhServicePrjBdRepImpl boardList > searchList END...");
				return prjBdRepList;
			}
		}		
		//-------------------------------------------
		prjBdRepList = jmhRepDao.boardList(prjBdRep);
		//-------------------------------------------		
		System.out.println("JmhServicePrjBdRepImpl boardList END...");
		return prjBdRepList;
	}

	//분류
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("JmhServicePrjBdRepImpl codeList START...");
		List<Code> reCodeList = null;
		//------------------------------------
		reCodeList = jmhRepDao.codeList(code);
		//------------------------------------
		System.out.println("JmhServicePrjBdRepImpl codeList END...");
		return reCodeList;
	}

	//등록
	@Override
	public int insertBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl insertBoard START...");
		int resultCount = 0;		
		Date sysdate = new Date();
		prjBdRep.setCreate_date(sysdate);		
		//--------------------------------------------
		resultCount = jmhRepDao.insertBoard(prjBdRep);
		//--------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl insertBoard resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl insertBoard END...");
		return resultCount;
	}

	//조회
	@Override
	public PrjBdRep selectBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl selectBoard START...");
		PrjBdRep selectPrjBdRep = null;		
		//-----------------------------------------------
		selectPrjBdRep = jmhRepDao.selectBoard(prjBdRep);
		//-----------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl selectBoard END...");
		return selectPrjBdRep;
	}

	//수정
	@Override
	public int updateBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl updateBoard START...");
		int resultCount = 0;				
		//--------------------------------------------
		resultCount = jmhRepDao.updateBoard(prjBdRep);
		//--------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl updateBoard resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl updateBoard END...");
		return resultCount;
	}

	//삭제
	@Override
	public int deleteBoard(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl deleteBoard START...");
		int resultCount = 0;				

		//문서의 댓글들 모두 삭제
		//----------------------------------------------------------------
		int resultCommentCount = jmhRepDao.deleteCommentBoard(prjBdRep);
		//----------------------------------------------------------------
		if(resultCommentCount > 0) {System.out.println("댓글 삭제완료:"+resultCommentCount);}

		//--------------------------------------------
		resultCount = jmhRepDao.deleteBoard(prjBdRep);
		//--------------------------------------------
		if(resultCount > 0) {System.out.println("문서 삭제완료:"+resultCount);}
		
		System.out.println("JmhServicePrjBdRepImpl deleteBoard resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl deleteBoard END...");
		return resultCount;
	}

	//댓글 등록
	@Override
	public int insertComment(BdRepComt bdRepComt) {
		System.out.println("JmhServicePrjBdRepImpl insertComment START...");
		int resultCount = 0;				
		//-----------------------------------------------
		resultCount = jmhRepDao.insertComment(bdRepComt);
		//-----------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl insertComment resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl insertComment END...");
		return resultCount;
	}

	//댓글 조회
	public List<BdRepComt> selectCommentList(BdRepComt bdRepComt) {
		System.out.println("JmhServicePrjBdRepImpl selectCommentList START...");
		List<BdRepComt> resultBdRepComtList = null;				
		//-----------------------------------------------------------
		resultBdRepComtList = jmhRepDao.selectCommentList(bdRepComt);
		//-----------------------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl selectCommentList resultBdRepComtList.size()->"+resultBdRepComtList.size());
		System.out.println("JmhServicePrjBdRepImpl selectCommentList END...");
		return resultBdRepComtList;
	}

	//댓글 삭제
	public int deleteComment(BdRepComt bdRepComt) {
		System.out.println("JmhServicePrjBdRepImpl deleteComment START...");
		int resultCount = 0;				
		//-----------------------------------------------
		resultCount = jmhRepDao.deleteComment(bdRepComt);
		//-----------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl deleteComment resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl deleteComment END...");
		return resultCount;
	}

	//댓글들 알림 플래그 일괄 업데이트(N개)
	@Override
	public int updateCommentAlarmFlag(PrjBdRep prjBdRep) {
		System.out.println("JmhServicePrjBdRepImpl updateCommentAlarmFlag START...");
		int resultCount = 0;				
		//-------------------------------------------------------
		resultCount = jmhRepDao.updateCommentAlarmFlag(prjBdRep);
		//-------------------------------------------------------
		System.out.println("JmhServicePrjBdRepImpl updateCommentAlarmFlag resultCount->"+resultCount);
		System.out.println("JmhServicePrjBdRepImpl updateCommentAlarmFlag END...");
		return resultCount;
	}

}
