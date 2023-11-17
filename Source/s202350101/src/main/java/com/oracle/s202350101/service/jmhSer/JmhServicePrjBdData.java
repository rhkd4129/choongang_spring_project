package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;

public interface JmhServicePrjBdData {
	int 			totalCount(PrjBdData prjBdData);
	List<PrjBdData> boardList(PrjBdData prjBdData);
	List<Code> 		codeList(Code code);
	int 			insertBoard(PrjBdData prjBdData);
	PrjBdData 		selectBoard(PrjBdData prjBdData);
	int 			updateBoard(PrjBdData prjBdData);
	int 			deleteBoard(PrjBdData prjBdData);
	int 			readCount(PrjBdData prjBdData);
	BdDataGood		checkGoodList(BdDataGood bdDataGood);
	int 			insertGoodList(BdDataGood bdDataGood);
	List<BdDataGood> selectGoodList(BdDataGood bdDataGood);
	int 			updateGoodCount(PrjBdData prjBdData);
	int 			updateOtherReply(PrjBdData prjBdData);
	int 			insertComment(BdDataComt bdDataComt);
	List<BdDataComt> selectCommentList(BdDataComt bdDataComt);
	int 			deleteComment(BdDataComt bdDataComt);
	int 			updateReplyAlarmFlag(PrjBdData prjBdData);
	int 			updateCommentAlarmFlag(PrjBdData prjBdData);
}
