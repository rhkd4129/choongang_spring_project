package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.Task;

public interface JmhDaoMain {

	List<BdFree> selectMainBdFree(BdFree bdFree);
	List<BdQna> selectMainBdQna(BdQna bdQna);
	List<PrjBdData> selectMainData(PrjBdData board);
	List<PrjBdRep> selectMainReport(PrjBdRep board);
	List<Meeting> selectMainMeeting(Meeting board);
	List<Task> selectMainTask(Task board);

}
