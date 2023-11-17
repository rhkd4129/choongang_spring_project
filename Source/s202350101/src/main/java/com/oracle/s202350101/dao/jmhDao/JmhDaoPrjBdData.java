package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;

public interface JmhDaoPrjBdData {
	int 			totalCount(PrjBdData prjBdData);
	int 			alarmCount(PrjBdData prjBdData);
	int 			searchCount(PrjBdData prjBdData);
	List<PrjBdData>	boardList(PrjBdData prjBdData);
	List<PrjBdData>	alarmList(PrjBdData prjBdData);
	List<PrjBdData> searchList(PrjBdData prjBdData);
	List<Code> 		codeList(Code code);
	int 			insertBoard(PrjBdData prjBdData);
	PrjBdData 		selectBoard(PrjBdData prjBdData);
	int 			updateBoard(PrjBdData prjBdData);
	int 			deleteBoard(PrjBdData prjBdData);
	int 			deleteCommentBoard(PrjBdData prjBdData);
	List<PrjBdData> selectReplyList(PrjBdData prjBdData);
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
