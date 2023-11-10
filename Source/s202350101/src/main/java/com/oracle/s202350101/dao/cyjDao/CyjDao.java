package com.oracle.s202350101.dao.cyjDao;

import java.util.List;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;

public interface CyjDao {

	int             totalBdFree();
	List<BdFree>    listBdFree(BdFree bdFree);
	int             insertBdFree(BdFree bdFree);
	BdFree          bdFreeContent(int doc_no);
	int             bdFreeUpdate2(BdFree bdFree);
	int             bdCount(int doc_no);
	int             boardDelete(int doc_no);
	int             goodCount(int doc_no);
	int             goodCountView(int doc_no);
	List<BdFree>    goodList(BdFree bdFree);
	
	
	// event 
	int                eventTotal();
	List<BdFree>       listEvent(BdFree bdFree);
	BdFree             eventContent(int doc_no);
	int                eventCount(int doc_no);
	int                eventInsert(BdFree bdFree);
	int                eventUpdate(BdFree bdFree);
	int                eventDelete(int doc_no);
	List<BdFreeComt>   eventComt(int doc_no);
	int                ajaxComt(BdFreeComt bdFreeComt);
	List<BdFree>       eventGood(BdFree bdFree);
	List<BdFreeComt>   eventSelect(BdFreeComt bdFreeComt);
	
	
	// 자유
	int                freeTotal();
	List<BdFree>       freeList();
	List<BdFree>       freeTotalList(BdFree bdFree);
	BdFree             freeContent(int doc_no);
	int                freeCount(int doc_no);
	List<BdFree>       freeComtList(int doc_no);
	int                freeCountUp(int doc_no);
	int                countGet(int doc_no);
	int                freeInsert(BdFree bdFree);
	int                ajaxFreeComt(BdFreeComt bdFreeComt);
	List<BdFreeComt>   freeSelect(BdFreeComt bdFreeComt);
	int                bdFreeUpdate(BdFree bdFree);
	int                freeDelete(int doc_no);
	


	
	

}
