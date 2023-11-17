package com.oracle.s202350101.service.jmhSer;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoPrjBdDataImpl;
import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  //생성자 없어도 되고 final변수 자동 인스턴스화
@Transactional
public class JmhServicePrjBdDataImpl implements JmhServicePrjBdData {

	private final JmhDaoPrjBdDataImpl jmhDataDao;

	//총건수
	@Override
	public int totalCount(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl totalCount START...");

		int totalCnt = 0;
		
		if(prjBdData.getDoc_group_list() != null) {
			System.out.println("★doc_group---->"+prjBdData.getDoc_group());
			System.out.println("★doc_group_list---->"+prjBdData.getDoc_group_list());
			if(prjBdData.getDoc_group_list().toUpperCase().equals("Y")) {
				// 알림에서 원글+답글 목록 열때
				// prj_board_data 조건에 해당하는 Count
				//------------------------------------------
				totalCnt = jmhDataDao.alarmCount(prjBdData);
				//------------------------------------------
				System.out.println("JmhServiceImpl totalCount totalCnt->" + totalCnt);
				System.out.println("JmhServiceImpl totalCount END...");
				return totalCnt;
			} 
		}
		if(prjBdData.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdData.getSearch());
			if(!prjBdData.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdData.getKeyword());
				//검색 건수 가져오기
				//------------------------------------------
				totalCnt = jmhDataDao.searchCount(prjBdData);
				//------------------------------------------
				System.out.println("JmhServiceImpl totalCount totalCnt->" + totalCnt);
				System.out.println("JmhServiceImpl totalCount END...");
				return totalCnt;
			}
		}
		//------------------------------------------
		totalCnt = jmhDataDao.totalCount(prjBdData);
		//------------------------------------------

		System.out.println("JmhServiceImpl totalCount totalCnt->" + totalCnt);
		System.out.println("JmhServiceImpl totalCount END...");
		return totalCnt;
	}

	//목록
	@Override
	public List<PrjBdData> boardList(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl boardList START...");
		
		List<PrjBdData> prjBdDataList = null;
		
		if(prjBdData.getDoc_group_list() != null) {
			if(prjBdData.getDoc_group_list().toUpperCase().equals("Y")) {
				// 알림에서 원글+답글 목록 열때
				// prj_board_data 조건에 해당하는 Count
				//----------------------------------------------
				prjBdDataList = jmhDataDao.alarmList(prjBdData);
				//----------------------------------------------
				System.out.println("JmhServiceImpl boardList > alarmList END...");
				return prjBdDataList;
			}
		}
		if(prjBdData.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdData.getSearch());
			if(!prjBdData.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdData.getKeyword());
				//-----------------------------------------------
				prjBdDataList = jmhDataDao.searchList(prjBdData);
				//-----------------------------------------------
				System.out.println("JmhServiceImpl boardList > searchList END...");
				return prjBdDataList;
			}
		}		
		//----------------------------------------------
		prjBdDataList = jmhDataDao.boardList(prjBdData);
		//----------------------------------------------
		
		System.out.println("JmhServiceImpl boardList END...");
		return prjBdDataList;
	}

	//분류
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("JmhServiceImpl codeList START...");
		List<Code> reCodeList = null;
		//-------------------------------------
		reCodeList = jmhDataDao.codeList(code);
		//-------------------------------------
		System.out.println("JmhServiceImpl codeList END...");
		return reCodeList;
	}

	//등록
	@Override
	public int insertBoard(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl insertBoard START...");
		int resultCount = 0;		
		Date sysdate = new Date();
		prjBdData.setCreate_date(sysdate);		
		//----------------------------------------------
		resultCount = jmhDataDao.insertBoard(prjBdData);
		//----------------------------------------------
		System.out.println("JmhServiceImpl insertBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl insertBoard END...");
		return resultCount;
	}

	//조회
	@Override
	public PrjBdData selectBoard(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl selectBoard START...");
		PrjBdData selectPrjBdData = null;		
		//--------------------------------------------------
		selectPrjBdData = jmhDataDao.selectBoard(prjBdData);
		//--------------------------------------------------
		System.out.println("JmhServiceImpl selectBoard END...");
		return selectPrjBdData;
	}

	//수정
	@Override
	public int updateBoard(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl updateBoard START...");
		int resultCount = 0;				
		//----------------------------------------------
		resultCount = jmhDataDao.updateBoard(prjBdData);
		//----------------------------------------------
		System.out.println("JmhServiceImpl updateBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateBoard END...");
		return resultCount;
	}

	//삭제
	@Override
	public int deleteBoard(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl deleteBoard START...");
		int resultCount = 0;
		int resultReplyCount = 0;
		int resultCommentCount = 0;

		//삭제할 문서 정보 가져오기
		//----------------------------------------------
		prjBdData = jmhDataDao.selectBoard(prjBdData);
		//----------------------------------------------
		
		//삭제할 하위 답글 정보 가져오기
		//-------------------------------------------------------------------
		List<PrjBdData> replyDocList = jmhDataDao.selectReplyList(prjBdData);
		//-------------------------------------------------------------------

		System.out.println("하위 답글 수:"+replyDocList.size());
		if(replyDocList.size() > 0) {
			for(PrjBdData replyDoc : replyDocList) {
				//-----------------------------------------------------------
				resultCommentCount = jmhDataDao.deleteCommentBoard(replyDoc);
				//-----------------------------------------------------------
				if(resultReplyCount > 0) {System.out.println("답글의 댓글 삭제완료:"+resultCommentCount);}
				//--------------------------------------------------
				resultReplyCount = jmhDataDao.deleteBoard(replyDoc);
				//--------------------------------------------------				
				if(resultReplyCount > 0) {System.out.println("답글 삭제완료:"+resultReplyCount);}
			}
		}
		
		//문서의 댓글들 모두 삭제
		//------------------------------------------------------------
		resultCommentCount = jmhDataDao.deleteCommentBoard(prjBdData);
		//------------------------------------------------------------
		if(resultCommentCount > 0) {System.out.println("댓글 삭제완료:"+resultCommentCount);}

		//----------------------------------------------
		resultCount = jmhDataDao.deleteBoard(prjBdData);
		//----------------------------------------------
		if(resultCount > 0) {System.out.println("문서 삭제완료:"+resultCount);}
		
		System.out.println("JmhServiceImpl deleteBoard resultCount->"+resultCount);
		System.out.println("JmhServiceImpl deleteBoard END...");
		return resultCount;
	}

	//조회수
	@Override
	public int readCount(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl readCount START...");
		int resultCount = 0;				
		//--------------------------------------------
		resultCount = jmhDataDao.readCount(prjBdData);
		//--------------------------------------------
		System.out.println("JmhServiceImpl readCount resultCount->"+resultCount);
		System.out.println("JmhServiceImpl readCount END...");
		return resultCount;
	}

	//추천여부 확인
	@Override
	public BdDataGood checkGoodList(BdDataGood bdDataGood) {
		System.out.println("JmhServiceImpl checkGoodList START...");
		BdDataGood resultBdDataGood = null;				
		//------------------------------------------------------
		resultBdDataGood = jmhDataDao.checkGoodList(bdDataGood);
		//------------------------------------------------------
		if(resultBdDataGood != null) {
			System.out.println("JmhServiceImpl checkGoodList bdDataGood->"+resultBdDataGood.getUser_id());
		}
		System.out.println("JmhServiceImpl checkGoodList END...");
		return resultBdDataGood;
	}

	//추천목록에 추가
	@Override
	public int insertGoodList(BdDataGood bdDataGood) {
		System.out.println("JmhServiceImpl insertGoodList START...");
		int resultCount = 0;				
		//-------------------------------------------------
		resultCount = jmhDataDao.insertGoodList(bdDataGood);
		//-------------------------------------------------
		System.out.println("JmhServiceImpl insertGoodList resultCount->"+resultCount);
		System.out.println("JmhServiceImpl insertGoodList END...");
		return resultCount;
	}

	//추천목록 조회
	@Override
	public List<BdDataGood> selectGoodList(BdDataGood bdDataGood) {
		System.out.println("JmhServiceImpl selectGoodList START...");
		List<BdDataGood> resultBdDataGoodList = null;				
		//-----------------------------------------------------------
		resultBdDataGoodList = jmhDataDao.selectGoodList(bdDataGood);
		//-----------------------------------------------------------
		if(resultBdDataGoodList.size() > 0) {
			System.out.println("JmhServiceImpl selectGoodList bdDataGood->"+resultBdDataGoodList.get(0).getUser_id());
		}
		System.out.println("JmhServiceImpl selectGoodList END...");
		return resultBdDataGoodList;
	}

	//추천수 저장
	@Override
	public int updateGoodCount(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl updateGoodCount START...");
		int resultCount = 0;				
		//--------------------------------------------------
		resultCount = jmhDataDao.updateGoodCount(prjBdData);
		//--------------------------------------------------
		System.out.println("JmhServiceImpl updateGoodCount resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateGoodCount END...");
		return resultCount;
	}

	//답글 계층 처리
	@Override
	public int updateOtherReply(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl updateOtherReply START...");
		int resultCount = 0;				
		//---------------------------------------------------
		resultCount = jmhDataDao.updateOtherReply(prjBdData);
		//---------------------------------------------------
		System.out.println("JmhServiceImpl updateOtherReply resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateOtherReply END...");
		return resultCount;
	}

	//댓글 등록
	@Override
	public int insertComment(BdDataComt bdDataComt) {
		System.out.println("JmhServiceImpl insertComment START...");
		int resultCount = 0;				
		//---------------------------------------------------
		resultCount = jmhDataDao.insertComment(bdDataComt);
		//---------------------------------------------------
		System.out.println("JmhServiceImpl insertComment resultCount->"+resultCount);
		System.out.println("JmhServiceImpl insertComment END...");
		return resultCount;
	}

	//댓글 조회
	@Override
	public List<BdDataComt> selectCommentList(BdDataComt bdDataComt) {
		System.out.println("JmhServiceImpl selectCommentList START...");
		List<BdDataComt> resultBdDataComtList = null;				
		//--------------------------------------------------------
		resultBdDataComtList = jmhDataDao.selectCommentList(bdDataComt);
		//--------------------------------------------------------
		System.out.println("JmhServiceImpl selectCommentList resultBdDataComtList.size()->"+resultBdDataComtList.size());
		System.out.println("JmhServiceImpl selectCommentList END...");
		return resultBdDataComtList;
	}

	//댓글 삭제
	@Override
	public int deleteComment(BdDataComt bdDataComt) {
		System.out.println("JmhServiceImpl deleteComment START...");
		int resultCount = 0;				
		//---------------------------------------------------
		resultCount = jmhDataDao.deleteComment(bdDataComt);
		//---------------------------------------------------
		System.out.println("JmhServiceImpl deleteComment resultCount->"+resultCount);
		System.out.println("JmhServiceImpl deleteComment END...");
		return resultCount;
	}

	//답글 알림 플래그 업데이트(1개)
	@Override
	public int updateReplyAlarmFlag(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl updateReplyAlarmFlag START...");
		int resultCount = 0;				
		//-------------------------------------------------------
		resultCount = jmhDataDao.updateReplyAlarmFlag(prjBdData);
		//-------------------------------------------------------
		System.out.println("JmhServiceImpl updateReplyAlarmFlag resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateReplyAlarmFlag END...");
		return resultCount;
	}

	//댓글들 알림 플래그 일괄 업데이트(N개)
	@Override
	public int updateCommentAlarmFlag(PrjBdData prjBdData) {
		System.out.println("JmhServiceImpl updateCommentAlarmFlag START...");
		int resultCount = 0;				
		//---------------------------------------------------------
		resultCount = jmhDataDao.updateCommentAlarmFlag(prjBdData);
		//---------------------------------------------------------
		System.out.println("JmhServiceImpl updateCommentAlarmFlag resultCount->"+resultCount);
		System.out.println("JmhServiceImpl updateCommentAlarmFlag END...");
		return resultCount;
	}

}
