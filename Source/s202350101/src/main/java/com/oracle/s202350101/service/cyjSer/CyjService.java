package com.oracle.s202350101.service.cyjSer;

import java.util.List;

import javax.validation.Valid;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;

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
	int               eventCount();
	List<BdFree>      eventList(BdFree bdFree);
	BdFree            eventContent(int doc_no);
	int               eventBdCount(int doc_no);
	int               eventInsert(BdFree bdFree);
	int               eventUpdate(BdFree bdFree);
	int               eventDelete(int doc_no);
	List<BdFreeComt>  eventComt(int doc_no);
	int               ajaxComt(BdFreeComt bdFreeComt);
	List<BdFree>      eventCount(BdFree bdFree);
	List<BdFreeComt>  eventSelect(BdFreeComt bdFreeComt);
	
	// 자유
	int               freeTotal();
	List<BdFree>      freeRow();
	List<BdFree>      freeList(BdFree bdFree);
	BdFree            freeContent(int doc_no);
	int               freeCount(int doc_no);
	List<BdFree>      freeComtList(int doc_no);
	int               freeInsert(BdFree bdFree);
	int               ajaxFreeComt(BdFreeComt bdFreeComt);
	List<BdFreeComt>  freeComtList(BdFreeComt bdFreeComt);
	int               freeUpdate(BdFree bdFree);
	int               freeDelete(int doc_no);
	
	// qna
	int               qnaTotalCount();
	List<BdQna>       qnaRow();
	List<BdQna>       qnaList(BdQna bdQna);
	int               qnaInsert(BdQna bdQna);
	BdQna             qnaContent(int doc_no);
	int               qnaCount(int doc_no);
	int               qnaUpdate(BdQna bdQna);
	
	
	// qna 추천
	int              qnaConfirm(BdQnaGood bdQnaGood);
	int              qnaGoodInsert(BdQnaGood bdQnaGood);
	int              qnaGoodUpdate(BdQnaGood bdQnaGood);
	int              qnaGoodSelect(BdQna bdQna);
	



	
	
	
	 
	



	

}
