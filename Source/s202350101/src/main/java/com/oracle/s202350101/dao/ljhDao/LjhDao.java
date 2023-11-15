package com.oracle.s202350101.dao.ljhDao;

import java.util.HashMap;
import java.util.List;

import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

public interface LjhDao {

	PrjInfo 			getProject(int project_id);
	List<Meeting> 		getMeetingList(int project_id);
	List<Meeting> 		getMeetingRead(int meeting_id);
	List<Meeting> 		getMeetingReportList(int project_id);
	List<Meeting> 		getMeetingDate(int project_id);
	List<PrjMemList> 	getPrjMember(int project_id);
	int 				insertMeetingDate(Meeting meeting);
	int 				updateMeetingReport(Meeting meeting);
	int 				deleteMeetingMember(Meeting meeting);
	int 				insertMeetingMember(Meeting meeting);
	int 				deleteMeetingReport(int meeting_id);
	int 				insertMember(Meeting mt);
	int 				updateReport(Meeting meeting);
	int 				insertMeetingReport(Meeting meeting);
	List<Meeting> 		getUserMeeting(HashMap<String, String> map);
	List<PrjBdData> 	getBoardRep(HashMap<String, String> map);
	List<PrjBdData> 	getBoardComt(HashMap<String, String> map);
	List<PrjInfo> 		getPrjApprove(HashMap<String, String> map);


}
