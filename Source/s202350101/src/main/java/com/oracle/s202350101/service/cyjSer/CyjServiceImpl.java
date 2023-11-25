package com.oracle.s202350101.service.cyjSer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oracle.s202350101.dao.cyjDao.CyjDao;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CyjServiceImpl implements CyjService {

	private final CyjDao cd;


	// 총 갯수
	@Override
	public int totalBdFree() {
		System.out.println("CyjServiceImpl totalBdFree Start");
		int totalBdFree = cd.totalBdFree();
		System.out.println("CyjServiceImpl totalBdFree-> " + totalBdFree);
		
		return totalBdFree;
	}

	// 추천수 가장 높은 row 3개 
	@Override
	public List<BdFree> goodRow(BdFree bdFree) {
		System.out.println("CyjServiceImpl goodRow Start");
		List<BdFree> goodList = cd.goodList(bdFree);
		System.out.println("CyjServiceImpl goodList-> " + goodList);
		
		return goodList;
	}

	// 전체 리스트
	@Override
	public List<BdFree> listBdFree(BdFree bdFree) {
		System.out.println("CyjServiceImpl listBdFree start");
		List<BdFree> listBdFree = cd.listBdFree(bdFree);
		System.out.println("CyjServiceImpl listBdFree-> " + listBdFree);
		
		return listBdFree;
	}

// ------------------------------------------------------------------
	
	// 새 글 입력 
	@Override
	public int insertBdFree(BdFree bdFree) {
		System.out.println("CyjServiceImpl insertBdFree start");
		int insertBdFree = cd.insertBdFree(bdFree);
		System.out.println("CyjServiceImpl insertBdFree-> " + insertBdFree);
		
		return insertBdFree;
	}
		
// -------------------------------------------------------------
		
	// 상세페이지
	@Override
	public BdFree bdFreeContent(int doc_no) {
		System.out.println("CyjServiceImpl bdFreeContent start");
		BdFree bdFreeContent = cd.bdFreeContent(doc_no);
		System.out.println("CyjServiceImpl bdFreeContent-> " + bdFreeContent);
		
		return bdFreeContent;
	}
	
	// 조회수 
	@Override
	public int bdCount(int doc_no) {
		System.out.println("CyjServiceImpl bdCount start");
		int bdCount = cd.bdCount(doc_no);
		System.out.println("CyjServiceImpl bdCount-> " + bdCount);
		
		return bdCount;
	}
		
// -------------------------------------------------------------

	// 수정
	@Override
	public int bdFreeUpdate2(BdFree bdFree) {
		System.out.println("CyjServiceImpl bdFreeUpdate2 start");
		int bdFreeUpdate2 = cd.bdFreeUpdate2(bdFree);
		System.out.println("CyjServiceImpl bdFreeUpdate2-> " + bdFreeUpdate2);
		
		return bdFreeUpdate2;
	}

// -------------------------------------------------------------

	// 삭제
	@Override
	public int boardDelete(int doc_no) {
		System.out.println("CyjServiceImpl boardDelete start");
		int boardDelete = cd.boardDelete(doc_no);
		System.out.println("CyjServiceImpl boardDelete-> " + boardDelete);
		
		return boardDelete;
	}

// -------------------------------------------------------------

	// 1. 추천자 목록에 있는지 확인 --> 중복 확인
	@Override
	public int goodConfirm(BdFreeGood bdFreeGood) {
		System.out.println("CyjServiceImpl goodComfim start");
		int goodConfirm = cd.goodConfirm(bdFreeGood);
		System.out.println("CyjServiceImpl goodConfirm-> " + goodConfirm);
		
		return goodConfirm;
	}
	
	// 2. 수행 후 추천기록이 없으면 BdFreeGood 테이블에 추천 추가
	@Override
	public int notifyGoodInsert(BdFreeGood bdFreeGood) {
		System.out.println("CyjServiceImpl notifyGoodInsert start");
		int notifyGoodInsert = cd.notifyGoodInsert(bdFreeGood);
		System.out.println("CyjServiceImpl notifyGoodInsert-> " + notifyGoodInsert);
		
		return notifyGoodInsert;
	}
	
	// 3. bd_free 테이블에 good_count를 업데이트
	@Override
	public int notifyGoodUpdate(BdFreeGood bdFreeGood) {
		System.out.println("CyjServiceImpl notifyGoodUpdate start");
		int notifyGoodUpdate = cd.notifyGoodUpdate(bdFreeGood);
		System.out.println("CyjServiceImpl notifyGoodUpdate-> " + notifyGoodUpdate);
		
		return notifyGoodUpdate;
	}
	
	// 4. 추천 select
	@Override
	public int notifyGoodSelect(BdFree bdFree) {
		System.out.println("CyjServiceImpl notifyGoodSelect start");
		int notifyGoodSelect = cd.notifyGoodSelect(bdFree);
		System.out.println("CyjServiceImpl notifyGoodSelect-> " + notifyGoodSelect);
		
		return notifyGoodSelect;
	}
	
// ------------------------------------------------------------------------	
// ------------------------- 이벤트 게시판 ------------------------------------

	// 이벤트_총 갯수  
	@Override
	public int eventCount() {
		System.out.println("CyjServiceImpl eventCount start");
		int eventTotal = cd.eventTotal();
		System.out.println("CyjServiceImpl eventTotal-> " + eventTotal);
		
		return eventTotal;
	}

	// 이벤트_전체 리스트 
	@Override
	public List<BdFree> eventList(BdFree bdFree) {
		System.out.println("CyjServiceImpl eventList start");
		List<BdFree> listEvent = cd.listEvent(bdFree);
		System.out.println("CyjServiceImpl listEvent-> " + listEvent);
		
		return listEvent;
	}
	
	// 이벤트_추천수 가장 높은 row 3개 
	@Override
	public List<BdFree> eventCount(BdFree bdFree) {
		System.out.println("CyjServiceImpl eventCount start");
		List<BdFree> bList = cd.eventGood(bdFree);
		System.out.println("CyjServiceImpl bList.size()-> " + bList.size());
		
		return bList;
	}

// ------------------------------------------------------------------------	

	// 이벤트_상세 
	@Override
	public BdFree eventContent(int doc_no) {
		System.out.println("CyjServiceImpl eventContent start");
		BdFree content = cd.eventContent(doc_no);
		System.out.println("CyjServiceImpl content-> " + content);
		
		return content;
	}
	
	// 이벤트_조회수 
	@Override
	public int eventBdCount(int doc_no) {
		System.out.println("CyjServiceImpl eventBdCount start");
		int eventCount = cd.eventCount(doc_no);
		System.out.println("CyjServiceImpl eventCount-> " + eventCount);
		
		return eventCount;
	}
	
	// 이벤트_댓글입력  
	@Override
	public int comtInsert(BdFreeComt bdFreeComt) {
		System.out.println("CyjServiceImpl ajaxComt start");
		int comtInsert = cd.comtInsert(bdFreeComt);
		System.out.println("CyjServiceImpl comtInsert-> " + comtInsert);
		
		return comtInsert;
	}
	
	// 이벤트_댓글리스트
	@Override
	public List<BdFreeComt> eventComt(BdFreeComt bdFreeComt) {
		System.out.println("CyjServiceImpl eventComt start");
		List<BdFreeComt> comt = cd.eventComt(bdFreeComt);
		System.out.println("CyjServiceImpl comt-> " + comt);
		
		return comt;
	}	
	
	// 이벤트_해당 게시글에 대한 댓글 총 갯수
	@Override
	public int eventComtCount(int doc_no) {
		System.out.println("CyjServiceImpl eventComtCount start");
		int eventComtCount = cd.eventComtCount(doc_no);
		System.out.println("CyjServiceImpl eventComtCount-> " + eventComtCount);
		
		return eventComtCount;
	}
	
	// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
	@Override
	public int cyUpdateCommentAlarmFlag(BdFree eventContent) {
		System.out.println("CyjServiceImpl cyUpdateCommentAlarmFlag start");
		int cyUpdateCommentAlarmFlag = cd.cyUpdateCommentAlarmFlag(eventContent);
		System.out.println("CyjServiceImpl cyUpdateCommentAlarmFlag-> " + cyUpdateCommentAlarmFlag);
		
		return cyUpdateCommentAlarmFlag;
	}
	
// ------------------------------------------------------------------------	

	// 이벤트_새 글 입력
	@Override
	public int eventInsert(BdFree bdFree) {
		System.out.println("CyjServiceImpl eventInsert start");
		int eventInsert = cd.eventInsert(bdFree);
		System.out.println("CyjServiceImpl eventInsert-> " + eventInsert);
		
		return eventInsert;
	}

// ------------------------------------------------------------------------	
	
	// 이벤트_수정
	@Override
	public int eventUpdate(BdFree bdFree) {
		System.out.println("CyjServiceImpl eventUpdate start");
		int eventUpdate = cd.eventUpdate(bdFree);
		System.out.println("CyjServiceImpl eventUpdate-> " + eventUpdate);
		
		return eventUpdate;
	}

// ------------------------------------------------------------------------	

	// 이벤트_삭제
	@Override
	public int eventDelete(int doc_no) {
		System.out.println("CyjServiceImpl eventDelete start");
		int delete = cd.eventDelete(doc_no);
		System.out.println("CyjServiceImpl delete-> " + delete);
		
		return delete;
	}

// ------------------------------------------------------------------------	
// ------------------------- 자유 게시판 ------------------------------------

	// 자유_총 갯수
	@Override
	public int freeTotal() {
		System.out.println("CyjServiceImpl freeTotal start");
		int freeTotal = cd.freeTotal();
		System.out.println("CyjServiceImpl freeTotal-> " + freeTotal);
		
		return freeTotal;
	}

	// 자유_추천수 가장 높은 row 3개 
	@Override
	public List<BdFree> freeRow() {
		System.out.println("CyjServiceImpl freeRow start");
		List<BdFree> freeList = cd.freeList();
		System.out.println("CyjServiceImpl freeList-> " + freeList);
		
		return freeList;
	}

	// 자유_전체 리스트
	@Override
	public List<BdFree> freeList(BdFree bdFree) {
		System.out.println("CyjServiceImpl freeList start");
		List<BdFree> freeTotalList = cd.freeTotalList(bdFree);
		System.out.println("CyjServiceImpl freeTotalList.size()-> " + freeTotalList.size());
		
		return freeTotalList;
	}
	
// ------------------------------------------------------------------------	

	// 자유_상세
	@Override
	public BdFree freeContent(int doc_no) {
		System.out.println("CyjServiceImpl freeContent start");
		BdFree freeContent = cd.freeContent(doc_no);
		System.out.println("CyjServiceImpl freeContent-> " + freeContent);
		
		return freeContent;
	}

	// 자유_조회수 증가
	@Override
	public int freeCount(int doc_no) {
		System.out.println("CyjServiceImpl freeCount start");
		int freeCount = cd.freeCount(doc_no);
		System.out.println("CyjServiceImpl freeCount-> " + freeCount);
		
		return freeCount;
	}

	// 자유_댓글리스트
	@Override
	public List<BdFreeComt> freeComtList(int doc_no) {
		System.out.println("CyjServiceImpl freeComtList start");
		List<BdFreeComt> freeComtList = cd.freeComtList(doc_no);
		System.out.println("CyjServiceImpl freeComtList-> " + freeComtList);
		
		return freeComtList;
	}
	
// ------------------------------------------------------------------------	
	
	// 자유_새 글 입력 
	@Override
	public int freeInsert(BdFree bdFree) {
		System.out.println("CyjServiceImpl freeInsert start");
		int freeInsert = cd.freeInsert(bdFree);
		System.out.println("CyjServiceImpl freeInsert-> " + freeInsert);
		
		return freeInsert;
	}
	
// ------------------------------------------------------------------------		

	// 자유_수정
	@Override
	public int freeUpdate(BdFree bdFree) {
		System.out.println("CyjServiceImpl freeUpdate start");
		int freeUpdate = cd.bdFreeUpdate(bdFree);
		System.out.println("CyjServiceImpl freeUpdate-> " + freeUpdate);
		
		return freeUpdate;
	}

// ------------------------------------------------------------------------		

	// 자유_게시글 삭제
	@Override
	public int freeDelete(int doc_no) {
		System.out.println("CyjServiceImpl freeDelete start");
		int delete = cd.freeDelete(doc_no);
		System.out.println("CyjServiceImpl delete-> " + delete);
		
		return delete;
	}
	
// ------------------------------------------------------------------------		
	
	// 자유_댓글 삭제
	@Override
	public int freeComtDelete(BdFreeComt bdFreeComt) {
		System.out.println("CyjServiceImpl freeComtDelete start");
		int comtDelete = cd.freeComtDelete(bdFreeComt);
		System.out.println("CyjServiceImpl comtDelete-> " + comtDelete);
		
		return comtDelete;
	}

// ------------------------------------------------------------------------	
// ------------------------- qna 게시판 ------------------------------------

	// qna_count (전체 or 분류검색) 
	@Override 
	public int qnaSelectCount(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaTotalCount start");
		
		int totalCnt = 0;
		
		if(bdQna.getDoc_group_list() != null) {
			System.out.println("★doc_group---->"+bdQna.getDoc_group());
			System.out.println("★doc_group_list---->"+bdQna.getDoc_group_list());
			if(bdQna.getDoc_group_list().toUpperCase().equals("Y")) {
				// 알림에서 원글+답글 목록 열때
				// prj_board_data 조건에 해당하는 Count
				//------------------------------------------
				totalCnt = cd.alarmCount(bdQna);
				//------------------------------------------
				System.out.println("JmhServiceImpl totalCount totalCnt->" + totalCnt);
				System.out.println("JmhServiceImpl totalCount END...");
				return totalCnt;
			} 
		}
		if(bdQna.getKeyword() != null) {
			if(!bdQna.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->" + bdQna.getKeyword()); 
				totalCnt = cd.searchCount(bdQna);  // 검색 건수 가져오기 
				System.out.println("CyjServiceImpl qnaSelectCount totalCnt->" + totalCnt);
				return totalCnt;
			}
		}
		totalCnt = cd.qnaSelectCount(bdQna);   // 전체 검색 건수
		System.out.println("CyjServiceImpl qnaSelectCount totalCnt-> " + totalCnt);
		
		return totalCnt; 
	}
	
	// qna_추천수 가장 높은 row 3개
	@Override
	public List<BdQna> qnaRow() {
		System.out.println("CyjServiceImpl qnaRow start");
		List<BdQna> qnaList = cd.qnaList();
		System.out.println("CyjServiceImpl qnaList-> " + qnaList);
		
		return qnaList;
	}

	// qna_전제 리스트 
	@Override
	public List<BdQna> qnaList(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaList start");
		
		List<BdQna> qnaSelectList = null;
		
		if(bdQna.getDoc_group_list() != null) {
			if(bdQna.getDoc_group_list().toUpperCase().equals("Y")) {
				// 알림에서 원글+답글 목록 열때
				// prj_board_data 조건에 해당하는 Count
				qnaSelectList = cd.alarmList(bdQna);
				System.out.println("CyjServiceImpl boardList > alarmList END...");
				return qnaSelectList;
			}
		}
		if(bdQna.getKeyword() != null) {
			if(!bdQna.getKeyword().equals("")) {
				System.out.println("★검색 keyword----> " + bdQna.getKeyword());
				qnaSelectList = cd.searchList(bdQna); // 분류별 검색 리스트
				return qnaSelectList;
			}
		}
		// 전체 리스트 
		qnaSelectList = cd.qnaTotalList(bdQna);
		System.out.println("CyjServiceImpl qnaSelectList-> " + qnaSelectList);
		return qnaSelectList;
	}

	// qna_검색 분류 코드 가져오기
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("CyjServiceImpl codeList start");
		List<Code> codeList = cd.codeList(code);
		System.out.println("CyjServiceImpl codeList-> " + codeList);
		
		return codeList;
	}
	
// ------------------------------------------------------------------------	

	// qna_상세
	@Override
	public BdQna qnaContent(int doc_no) {
		System.out.println("CyjServiceImpl qnaContent start");
		BdQna qnaContent = cd.qnaContent(doc_no);
		System.out.println("CyjServiceImpl qnaContent-> " + qnaContent);
		
		return qnaContent;
	}

	// qna_조회수	
	@Override
	public int qnaCount(int doc_no) {
		System.out.println("CyjServiceImpl qnaCount start");
		int qnaCount = cd.qnaCount(doc_no);
		System.out.println("CyjServiceImpl qnaCount-> " + qnaCount);
		
		return qnaCount;
	}

	// 현재 로그인 사용자가 답글의 부모글 작성자인 경우 답글 조회시 alarm_flag='Y'로 변경처리
	@Override
	public int cyUpdateReplyAlarmFlag(BdQna qnaContent) {
		System.out.println("CyjServiceImpl cyUpdateReplyAlarmFlag start");
		int resultCount = 0;	
		resultCount = cd.cyUpdateReplyAlarmFlag(qnaContent);
		System.out.println("CyjServiceImpl cyUpdateReplyAlarmFlag resultCount-> " + resultCount);
		System.out.println("CyjServiceImpl cyUpdateReplyAlarmFlag END...");
	
		return resultCount;
	}
// ------------------------------------------------------------------------	

	// qna_수정
	@Override
	public int qnaUpdate(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaUpdate start");
		int qnaUpdate = cd.qnaUpdate(bdQna);
		System.out.println("CyjServiceImpl qnaUpdate-> " + qnaUpdate);
		
		return qnaUpdate;
	}

// ------------------------------------------------------------------------	

	// qna_추천 1.중복체크
	@Override
	public int qnaConfirm(BdQnaGood bdQnaGood) {
		System.out.println("CyjServiceImpl qnaConfirm start");
		int qnaConfrim = cd.qnaConfrim(bdQnaGood);
		System.out.println("CyjServiceImpl qnaConfrim-> " + qnaConfrim);
		
		return qnaConfrim;
	}

	// qna_추천 2. insert 
	@Override
	public int qnaGoodInsert(BdQnaGood bdQnaGood) {
		System.out.println("CyjServiceImpl qnaGoodInsert start");
		int qnaGoodInsert = cd.qnaGoodInsert(bdQnaGood);
		System.out.println("CyjServiceImpl qnaGoodInsert-> " + qnaGoodInsert);
		
		return qnaGoodInsert;
	}

	// qna_추천 3. update
	@Override
	public int qnaGoodUpdate(BdQnaGood bdQnaGood) {
		System.out.println("CyjServiceImpl qnaGoodUpdate start");
		int qnaGoodUpdate = cd.qnaGoodUpdate(bdQnaGood);
		System.out.println("CyjServiceImpl qnaGoodUpdate-> " + qnaGoodUpdate);
		
		return qnaGoodUpdate;
	}

	// qna_추천 4. select
	@Override
	public int qnaGoodSelect(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaGoodSelect start");
		int qnaGoodSelect = cd.qnaGoodSelect(bdQna);
		System.out.println("CyjServiceImpl qnaGoodSelect-> " + qnaGoodSelect);
		
		return qnaGoodSelect;
	}

// ------------------------------------------------------------------------	

	// qna_새 글 입력하기 위한 상세 
	@Override
	public BdQna selectBdQna(BdQna bdQna) {
		System.out.println("CyjServiceImpl selectBdQna start");
		BdQna selectBdQna = cd.selectBdQna(bdQna);
		System.out.println("CyjServiceImpl selectBdQna-> " + selectBdQna);
		
		return selectBdQna;
	}
	
	// qna_새 글 입력 
	@Override
	public int qnaInsert(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaInsert start");
		int qnaInsert = cd.qnaInsert(bdQna);
		System.out.println("CyjServiceImpl qnaInsert-> " + qnaInsert);
		
		return qnaInsert;
	}

// ------------------------------------------------------------------------	

	// qna_답변 순서 조절 
	@Override
	public int qnaReply(BdQna bdQna) {
		System.out.println("CyjServiceImpl qnaReply start");
		int qnaReply = cd.qnaReply(bdQna);
		System.out.println("CyjServiceImpl qnaReply-> " + qnaReply);
		
		return qnaReply;
	}

// ------------------------------------------------------------------------	

	// qna_삭제
	@Override
	public int qnaDelete(int doc_no) {
		System.out.println("CyjServiceImpl qnaDelete start");
		int qnaDelete = cd.qnaDelete(doc_no);
		System.out.println("CyjServiceImpl qnaDelete-> " + qnaDelete);
		
		return qnaDelete;
	}









	




	
	
	
	
	
	
	
	



	
	

}
