package com.oracle.s202350101.service.cyjSer;

import java.util.List;

import javax.validation.Valid;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.Todo;

public interface CyjService {

	// 공지/event/자유    추천
	int               goodConfirm(BdFreeGood bdFreeGood);
	int 			  notifyGoodInsert(BdFreeGood bdFreeGood);	
	int 			  notifyGoodUpdate(BdFreeGood bdFreeGood);
	int 			  notifyGoodSelect(BdFree bdFree);
	
	// 공지
	int               totalBdFree();
	List<BdFree>      listBdFree(BdFree bdFree);
	int               insertBdFree(BdFree bdFree);
	BdFree            bdFreeContent(int doc_no);
	int            	  bdFreeUpdate2(BdFree bdFree);
	int               bdCount(int doc_no);
	int               boardDelete(int doc_no);
	List<BdFree>      goodRow(BdFree bdFree);
	
	// event
	int 			  cyUpdateCommentAlarmFlag(BdFree eventContent);  // 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
	int               eventCount();
	List<BdFree>      eventList(BdFree bdFree);
	BdFree            eventContent(int doc_no);
	int               eventBdCount(int doc_no);
	int               eventInsert(BdFree bdFree);
	int               eventUpdate(BdFree bdFree);
	int               eventDelete(int doc_no);
	List<BdFreeComt>  eventComt(BdFreeComt bdFreeComt);  // 페이징 작업땜에 객체로 바꿈 
 	int               comtInsert(BdFreeComt bdFreeComt);  
	List<BdFree>      eventCount(BdFree bdFree);
	int 			  eventComtCount(int doc_no);  // 해당 게시글에 대한 댓글 총 갯수
	
	// 자유
	int               freeTotal();
	List<BdFree>      freeRow();
	List<BdFree>      freeList(BdFree bdFree);
	BdFree            freeContent(int doc_no);
	int               freeCount(int doc_no);
	List<BdFreeComt>      freeComtList(BdFreeComt bdFreeComt);
	int               freeInsert(BdFree bdFree);
	int               freeUpdate(BdFree bdFree);
	int               freeDelete(int doc_no);
	int 			  freeComtDelete(BdFreeComt bdFreeComt);
	
	// qna
	int 			  cyUpdateReplyAlarmFlag(BdQna qnaContent); // 현재 로그인 사용자가 답글의 부모글 작성자인 경우 답글 조회시 alarm_flag='Y'로 변경처리

	int				  qnaCount(int doc_no);
	List<BdQna>       qnaRow();
	List<BdQna>       qnaList(BdQna bdQna);
	int               qnaSelectCount(BdQna bdQna);
	int 			  qnaInsert(BdQna bdQna);
	BdQna             qnaContent(int doc_no);
	int               qnaUpdate(BdQna bdQna);
	BdQna             selectBdQna(BdQna bdQna);	
	int 			  qnaReply(BdQna bdQna);
	List<Code> 		  codeList(Code code);
	
	// qna 추천
	int               qnaConfirm(BdQnaGood bdQnaGood);
	int               qnaGoodInsert(BdQnaGood bdQnaGood);
	int               qnaGoodUpdate(BdQnaGood bdQnaGood);
	int               qnaGoodSelect(BdQna bdQna);
	int               qnaDelete(int doc_no);
	
	
	
	

	

	
	

	



	



	
	
	
	 
	



	

}
