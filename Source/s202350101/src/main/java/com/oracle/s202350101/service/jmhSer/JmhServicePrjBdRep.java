package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;

public interface JmhServicePrjBdRep {
	int 			totalCount(PrjBdRep prjBdRep);
	List<PrjBdRep>  boardList(PrjBdRep prjBdRep);
	List<Code> 		codeList(Code code);
	int 			insertBoard(PrjBdRep prjBdRep);
	PrjBdRep 		selectBoard(PrjBdRep prjBdRep);
	int 			updateBoard(PrjBdRep prjBdRep);
	int 			deleteBoard(PrjBdRep prjBdRep);
	int 			insertComment(BdRepComt bdRepComt);
	List<BdRepComt> selectCommentList(BdRepComt bdRepComt);
	int 			deleteComment(BdRepComt bdRepComt);
	int 			updateCommentAlarmFlag(PrjBdRep prjBdRep);
}
