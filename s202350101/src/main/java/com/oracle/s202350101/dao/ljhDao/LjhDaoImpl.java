package com.oracle.s202350101.dao.ljhDao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LjhDaoImpl implements LjhDao {

	private final SqlSession session;

//-------------------------------------------------------------------------------------------------------
// 프로젝트 캘린더 & 회의록
//-------------------------------------------------------------------------------------------------------
	// 전체 프로젝트 기간 조회
	@Override
	public PrjInfo getProject(int project_id) {
		PrjInfo prjInfo = new PrjInfo();
		System.out.println("LjhDaoImpl getProject Start");
		
		try {
			//---------------------------------------------------------------
			prjInfo = session.selectOne("ljhPrjInfoSelOne", project_id);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl getProject prjInfo.getProject_name() : " + prjInfo.getProject_name());
			System.out.println("LjhDaoImpl getProject prjInfo.getProject_startdate() : " + prjInfo.getProject_startdate());
			System.out.println("LjhDaoImpl getProject prjInfo.getProject_enddate() : " + prjInfo.getProject_enddate());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getProject Exception : " + e.getMessage());
		}
		
		return prjInfo;
	}

	// 전체 회의 리스트 조회
	@Override
	public List<Meeting> getMeetingList(int project_id) {
		List<Meeting> meetingList = null;
		System.out.println("LjhDaoImpl getMeetingList Start");
		
		try {
			//---------------------------------------------------------------
			meetingList = session.selectList("ljhMeetingList", project_id);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl getMeetingList meetingDateList.size() :" + meetingList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getMeetingList Exception : " + e.getMessage());
		}
		
		return meetingList;
	}

	// 선태한 회의록의 정보와 회의 참석자 정보 리스트로 조회
	@Override
	public List<Meeting> getMeetingRead(int meeting_id) {
		List<Meeting> meetingRead = null;
		System.out.println("LjhDaoImpl getMeetingRead Start");
		
		try {
			//---------------------------------------------------------------
			meetingRead = session.selectList("ljhMeetingRead", meeting_id);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl getMeetingRead meetingRead.size() -> " + meetingRead.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getMeetingRead Exception : " + e.getMessage());
		}
		
		return meetingRead;
	}
	
	// 회의록 등록 상태인 회의 조회 (meeting_status = 2,3)
	@Override
	public List<Meeting> getMeetingReportList(int project_id) {
		List<Meeting> meetingReportList = null;
		System.out.println("LjhDaoImpl getMeetingReportList Start");
		
		try {
			//-----------------------------------------------------------------------------
			meetingReportList = session.selectList("ljhMeetingReportList", project_id);
			//-----------------------------------------------------------------------------
			System.out.println("LjhDaoImpl getMeetingReportList meetingList.size() -> " + meetingReportList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getMeetingReportList Exception : " + e.getMessage());
		}
		
		return meetingReportList;
	}

	// 회의록 페이징 작업용
	@Override
	public List<Meeting> getMtRpListPage(Meeting meeting) {
		System.out.println("LjhDaoImpl getMtRpListPage Start");
		List<Meeting> mtList = null;
		
		try {
			//---------------------------------------------------------
			mtList = session.selectList("ljhMtRpListPage", meeting);
			//---------------------------------------------------------
			System.out.println("LjhDaoImpl getMtRpListPage mtList.size() -> " + mtList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getMtRpListPage Exception : " + e.getMessage());
		}
		
		return mtList;
	}
	
	// 회의록 미등록 상태인 회의 조회 (meeting_status = 1)
	@Override
	public List<Meeting> getMeetingDate(int project_id) {
		System.out.println("LjhDaoImpl getMeetingDate Start");
		List<Meeting> meetingDate = null;
		
		try {
			//---------------------------------------------------------------
			meetingDate = session.selectList("ljhMeetingDate", project_id);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl getMeetingDate meetingDate.size() -> " + meetingDate.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getMeetingDate Exception : " + e.getMessage());
		}
		
		return meetingDate;
	}
	
	// 프로젝트 전체 팀원 목록 조회
	@Override
	public List<PrjMemList> getPrjMember(int project_id) {
		List<PrjMemList> prjMemList = null;
		System.out.println("LjhDaoImpl getPrjMember Start");
		
		try {
			//---------------------------------------------------------------
			prjMemList = session.selectList("ljhPrjMemList", project_id);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl getPrjMember prjMemList.size() -> " + prjMemList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getPrjMember Exception : " + e.getMessage());
		}
		
		return prjMemList;
	}

	// 회의일정 등록 (meeting_status = 1)
	@Override
	public int insertMeetingDate(Meeting meeting) {
		int insertResult = 0;
		System.out.println("LjhDaoImpl insertMeetingDate Start");
		
		try {
			//---------------------------------------------------------------
			insertResult = session.insert("ljhMeetingInsert", meeting);
			//---------------------------------------------------------------
			System.out.println("LjhDaoImpl insertMeetingDate insertResult -> " + insertResult);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl insertMeetingDate Exception : " + e.getMessage());
		}
		
		return insertResult;
	}

	// 회의록 수정
	@Override
	public int updateMeetingReport(Meeting meeting) {
		int updateResult = 0;
		System.out.println("LjhDaoImpl updateMeetingReport Start");
		
		try {
			//-----------------------------------------------------------------
			updateResult = session.update("ljhMeetingReportUpdate", meeting);
			//-----------------------------------------------------------------
			System.out.println("LjhDaoImpl updateMeetingReport updateResult -> " + updateResult);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl updateMeetingReport Exception : " + e.getMessage());
		}
		
		return updateResult;
	}

	// 기존 회의 참석자 삭제
	@Override
	public int deleteMeetingMember(Meeting meeting) {
		int deleteResult = 0;
		System.out.println("LjhDaoImpl deleteMeetingMember Start");
		
		try {
			//-----------------------------------------------------------------
			deleteResult = session.delete("ljhDeleteMeetingMember", meeting);
			//-----------------------------------------------------------------
			System.out.println("LjhDaoImpl deleteMeetingMember deleteResult -> " + deleteResult);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl deleteMeetingMember Exception : " + e.getMessage());
		}
		
		return deleteResult;
	}

	// 회의 참석자 등록 (회의 수정 시 참석자 추가 - 기존 meeting_id 사용)
	@Override
	public int insertMeetingMember(Meeting meeting) {
		int insertResult = 0;
		System.out.println("LjhDaoImpl insertMeetingMember Start");
		
		try {
			//-----------------------------------------------------------------
			insertResult = session.insert("ljhInsertMeetingMember", meeting);
			//-----------------------------------------------------------------
			System.out.println("LjhDaoImpl insertMeetingMember insertResult -> " + insertResult);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl insertMeetingMember Exception : " + e.getMessage());
		}
		
		return insertResult;
	}

	// 회의록 삭제
	@Override
	public int deleteMeetingReport(int meeting_id) {
		int deleteResult = 0;
		System.out.println("LjhDaoImpl deleteMeetingReport Start");
		
		try {
			//---------------------------------------------------------------------
			deleteResult = session.delete("ljhDeleteMeetingReport", meeting_id);
			//---------------------------------------------------------------------
			System.out.println("LjhDaoImpl deleteMeetingReport deleteResult -> " + deleteResult);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl deleteMeetingReport Exception : " + e.getMessage());
		}
		
		return deleteResult;
	}

	// 회의 참석자 신규 등록 (회의 신규 등록 시 참석자 추가 - meeting_id max값 사용)
	@Override
	public int insertMember(Meeting mt) {
		int result = 0;
		System.out.println("LjhDaoImpl insertMember Start");
		
		try {
			//----------------------------------------------------
			result = session.insert("ljhInsertMember", mt);
			//----------------------------------------------------
			System.out.println("LjhDaoImpl insertMember result -> " + result);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl insertMember Exception : " + e.getMessage());
		}
		
		return result;
	}
	
	// 회의일정 -> 회의록으로 변경 (meeting_status = 3으로 변경)
	@Override
	public int updateReport(Meeting meeting) {
		System.out.println("LjhDaoImpl updateReport Start");
		
		int result = 0;
		
		try {
			//------------------------------------------------------
			result = session.update("ljhReportUpdate", meeting);
			//------------------------------------------------------
			System.out.println("LjhDaoImpl updateReport result -> " + result);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl updateReport Exception : " + e.getMessage());
		}
		
		return result;
	}

	// 회의록 등록 (meeting_status = 2)
	@Override
	public int insertMeetingReport(Meeting meeting) {
		System.out.println("LjhDaoImpl insertMeetingReport Start");
		int result = 0;
		
		try {
			//------------------------------------------------------
			result = session.update("ljhReportInsert", meeting);
			//------------------------------------------------------
			System.out.println("LjhDaoImpl updateReport result -> " + result);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl updateReport Exception : " + e.getMessage());
		}
		
		return result;
	}

	// 회의일정 수정
	@Override
	public int updateMeetingDate(Meeting meeting) {
		System.out.println("LjhDaoImpl updateMeetingDate Start");
		int result = 0;
		
		try {
			//---------------------------------------------------------
			result = session.update("ljhMeetingDateUpdate", meeting);
			//---------------------------------------------------------
			System.out.println("LjhDaoImpl updateMeetingDate result -> " + result);
		} catch (Exception e) {
			System.out.println("LjhDaoImpl updateMeetingDate Exception : " + e.getMessage());
		}
		
		return result;
	}
	
	
//-------------------------------------------------------------------------------------------------------
// 알림
//-------------------------------------------------------------------------------------------------------
	// 접속한 회원이 참석자로 포함된 회의 조회
	@Override
	public List<Meeting> getUserMeeting(HashMap<String, String> map) {
		System.out.println("LjhDaoImpl getUserMeeting Start");
		List<Meeting> meetingList = null;
		
		try {
			//---------------------------------------------------------
			meetingList = session.selectList("ljhUserMeeting", map);
			//---------------------------------------------------------
			System.out.println("LjhDaoImpl getUserMeeting meetingList.size() -> " + meetingList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getUserMeeting Exception : " + e.getMessage());
		}
		
		return meetingList;
	}

	// 접속한 회원이 작성한 글과 등록된 답글 조회 (alarm_flag = N만)
	@Override
	public List<PrjBdData> getBoardRep(HashMap<String, String> map) {
		System.out.println("LjhDaoImpl getBoardRep Start");
		List<PrjBdData> boardRep = null;
		
		try {
			//----------------------------------------------------
			boardRep = session.selectList("ljhBoardRep", map);
			//----------------------------------------------------
			System.out.println("LjhDaoImpl getBoardRep boardRep.size() -> " + boardRep.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getBoardRep Exception : " + e.getMessage());
		}
		
		return boardRep;
	}

	// 접속한 회원이 작성한 글과 등록된 댓글 개수 조회 (alarm_flag = N만)
	@Override
	public List<PrjBdData> getBoardComt(HashMap<String, String> map) {
		System.out.println("LjhDaoImpl getBoardComt Start");
		List<PrjBdData> boardComt = null;
		
		try {
			//------------------------------------------------------
			boardComt = session.selectList("ljhBoardComt", map);
			//------------------------------------------------------
			System.out.println("LjhDaoImpl getBoardComt boardComt.size() -> " + boardComt.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getBoardComt Exception : " + e.getMessage());
		}
		
		return boardComt;
	}

	// 프로젝트 생성 승인 여부 조회 (팀장 계정 접속 시)
	@Override
	public List<PrjInfo> getPrjApprove(HashMap<String, String> map) {
		System.out.println("LjhDaoImpl getPrjApprove Start");
		List<PrjInfo> prjApprove = null;
		
		try {
			//---------------------------------------------------------
			prjApprove = session.selectList("ljhPrjApprove", map);
			//---------------------------------------------------------
			System.out.println("LjhDaoImpl getPrjApprove prjApprove.size() -> " + prjApprove.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getPrjApprove Exception : " + e.getMessage());
		}
		
		return prjApprove;
	}

	// 미승인 상태인 프로젝트 신청 건수 조회 (관리자 계정 접속 시)
	@Override
	public List<PrjInfo> getNewPrj(HashMap<String, String> map) {
		System.out.println("LjhDaoImpl getNewPrj Start");
		List<PrjInfo> newPrjList = null;
		
		try {
			//----------------------------------------------------
			newPrjList = session.selectList("ljhNewPrj", map);
			//----------------------------------------------------
			System.out.println("LjhDaoImpl getNewPrj newPrjList.size() -> " + newPrjList.size());
		} catch (Exception e) {
			System.out.println("LjhDaoImpl getNewPrj Exception : " + e.getMessage());
		}
		
		return newPrjList;
	}

}