package com.oracle.s202350101.dao.cyjDao;

import java.util.List;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;
import com.oracle.s202350101.model.Code;

public interface CyjDao {

	// notify/event/free  추천
	int 				goodConfirm(BdFreeGood bdFreeGood);
	int 				notifyGoodInsert(BdFreeGood bdFreeGood);	
	int 				notifyGoodUpdate(BdFreeGood bdFreeGood);
	int 				notifyGoodSelect(BdFree bdFree);
	
	// notify
	int           	    totalBdFree();
	List<BdFree>    	listBdFree(BdFree bdFree);
	int             	insertBdFree(BdFree bdFree);
	BdFree          	bdFreeContent(int doc_no);
	int             	bdFreeUpdate2(BdFree bdFree);
	int             	bdCount(int doc_no);
	int             	boardDelete(int doc_no);
	List<BdFree>    	goodList(BdFree bdFree);
	
	// event
	int 			   cyUpdateCommentAlarmFlag(BdFree eventContent);  // 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
	int                eventTotal();
	List<BdFree>       listEvent(BdFree bdFree);
	BdFree             eventContent(int doc_no);
	int                eventCount(int doc_no);
	int                eventInsert(BdFree bdFree);
	int                eventUpdate(BdFree bdFree);
	int                eventDelete(int doc_no);
	List<BdFreeComt>   eventComt(BdFreeComt bdFreeComt);  // 페이징 작업땜에 객체로 바꿈 
	int                comtInsert(BdFreeComt bdFreeComt);  
	List<BdFree>       eventGood(BdFree bdFree);
	int 			   eventComtCount(int doc_no);  // 해당 게시글에 대한 댓글 총 갯수
	
	// free
	int                freeTotal();
	List<BdFree>       freeList();
	List<BdFree>       freeTotalList(BdFree bdFree);
	BdFree             freeContent(int doc_no);
	int                freeCount(int doc_no);
	List<BdFreeComt>       freeComtList(BdFreeComt bdFreeComt);
	int                freeInsert(BdFree bdFree);
	int                bdFreeUpdate(BdFree bdFree);
	int                freeDelete(int doc_no);
	int				   freeComtDelete(BdFreeComt bdFreeComt);
	
	// qna
	int 			   cyUpdateReplyAlarmFlag(BdQna qnaContent);  // 현재 로그인 사용자가 답글의 부모글 작성자인 경우 답글 조회시 alarm_flag='Y'로 변경처리
	int                qnaSelectCount(BdQna bdQna);
	List<BdQna>        qnaList();
	List<BdQna>        qnaTotalList(BdQna bdQna);
	int                qnaInsert(BdQna bdQna);
	BdQna              qnaContent(int doc_no);
	int                qnaCount(int doc_no);
	int                qnaUpdate(BdQna bdQna);
	BdQna 			   selectBdQna(BdQna bdQna);
	int 			   qnaReply(BdQna bdQna);
	List<Code>         codeList(Code code);
	List<BdQna> 	   searchList(BdQna bdQna);
	int 			   searchCount(BdQna bdQna);   // select 한 검색 건수 
	
	// qna 추천
	List<BdQna> 	   alarmList(BdQna bdQna);   // 알림 목록
	int                alarmCount(BdQna bdQna);  // 답글 목록 열때 count
	int				   qnaConfrim(BdQnaGood bdQnaGood);
	int                qnaGoodInsert(BdQnaGood bdQnaGood);
	int                qnaGoodUpdate(BdQnaGood bdQnaGood);
	int                qnaGoodSelect(BdQna bdQna);
	int                qnaDelete(int doc_no);

	

	

	

	

	
	

	
	
	


	
	

}
