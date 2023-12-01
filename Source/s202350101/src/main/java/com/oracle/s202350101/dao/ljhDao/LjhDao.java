package com.oracle.s202350101.dao.ljhDao;

import java.util.HashMap;
import java.util.List;

import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

public interface LjhDao {
	
	// 프로젝트 캘린더 & 회의록
	PrjInfo 			getProject(int project_id);				// 전체 프로젝트 기간 조회
	List<Meeting> 		getMeetingList(int project_id);			// 전체 회의 리스트 조회
	List<Meeting> 		getMeetingRead(int meeting_id);			// 선태한 회의록의 정보와 회의 참석자 정보 리스트로 조회
	List<Meeting> 		getMeetingReportList(int project_id);	// 회의록 등록 상태인 회의 조회 (meeting_status = 2,3)
	List<Meeting> 		getMtRpListPage(Meeting meeting);		// 회의록 페이징 작업용
	List<Meeting> 		getMeetingDate(int project_id);			// 회의록 미등록 상태인 회의 조회 (meeting_status = 1)
	List<PrjMemList> 	getPrjMember(int project_id);			// 프로젝트 전체 팀원 목록 조회
	int 				insertMeetingDate(Meeting meeting);		// 회의일정 등록 (meeting_status = 1)
	int 				updateMeetingReport(Meeting meeting);	// 회의록 수정
	int 				deleteMeetingMember(Meeting meeting);	// 기존 회의 참석자 삭제
	int 				insertMeetingMember(Meeting meeting);	// 회의 참석자 등록 (회의 수정 시 참석자 추가 - 기존 meeting_id 사용)
	int 				deleteMeetingReport(int meeting_id);	// 회의록 삭제
	int 				insertMember(Meeting mt);				// 회의 참석자 신규 등록 (회의 신규 등록 시 참석자 추가 - meeting_id max값 사용)
	int 				updateReport(Meeting meeting);			// 회의일정 -> 회의록으로 변경 (meeting_status = 3으로 변경)
	int 				insertMeetingReport(Meeting meeting);	// 회의록 등록 (meeting_status = 2)
	int 				updateMeetingDate(Meeting meeting);		// 회의일정 수정
	
	// 알림
	List<Meeting> 		getUserMeeting(HashMap<String, String> map);	// 접속한 회원이 참석자로 포함된 회의 조회
	List<PrjBdData> 	getBoardRep(HashMap<String, String> map);		// 접속한 회원이 작성한 글과 등록된 답글 조회 (alarm_flag = N만)
	List<PrjBdData> 	getBoardComt(HashMap<String, String> map);		// 접속한 회원이 작성한 글과 등록된 댓글 개수 조회 (alarm_flag = N만)
	List<PrjInfo> 		getPrjApprove(HashMap<String, String> map);		// 프로젝트 생성 승인 여부 조회 (팀장 계정 접속 시)
	List<PrjInfo> 		getNewPrj(HashMap<String, String> map);			// 미승인 상태인 프로젝트 신청 건수 조회 (관리자 계정 접속 시)
	


}
