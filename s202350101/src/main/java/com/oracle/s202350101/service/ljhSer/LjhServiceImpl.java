package com.oracle.s202350101.service.ljhSer;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.s202350101.dao.ljhDao.LjhDao;
import com.oracle.s202350101.model.Meeting;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LjhServiceImpl implements LjhService {

	private final PlatformTransactionManager transactionManager;
	private final LjhDao	ljhd;
	
//-------------------------------------------------------------------------------------------------------
// 프로젝트 캘린더 & 회의록
//-------------------------------------------------------------------------------------------------------
	// 전체 프로젝트 기간 조회
	@Override
	public PrjInfo getProject(int project_id) {
		System.out.println("LjhServiceImpl getProject Start");
		
		//------------------------------------------------------
		PrjInfo prjInfo = ljhd.getProject(project_id);
		//------------------------------------------------------
		
		return prjInfo;
	}

	// 전체 회의 리스트 조회 (status 1, 2, 3 모두)
	@Override
	public List<Meeting> getMeetingList(int project_id) {
		System.out.println("LjhServiceImpl getMeetingList Start");

		//--------------------------------------------------------------
		List<Meeting> meetingList = ljhd.getMeetingList(project_id);
		//--------------------------------------------------------------
		
		return meetingList;
	}
	
	// 선태한 회의록의 정보와 회의 참석자 정보 리스트로 조회
	@Override
	public List<Meeting> getMeetingRead(int meeting_id) {
		System.out.println("LjhServiceImpl getMeetingRead Start");
		
		//--------------------------------------------------------------
		List<Meeting> meetingRead = ljhd.getMeetingRead(meeting_id);
		//--------------------------------------------------------------
		
		return meetingRead;
	}

	// 회의록 등록 상태인 회의 조회 (meeting_status = 2, 3)
	@Override
	public List<Meeting> getMeetingReportList(int project_id) {
		System.out.println("LjhServiceImpl getMeetingReportList Start");
		
		//--------------------------------------------------------------------------
		List<Meeting> meetingReportList = ljhd.getMeetingReportList(project_id);
		//--------------------------------------------------------------------------
		
		return meetingReportList;
	}

	// 회의록 페이징 작업용
	@Override
	public List<Meeting> getMtRpListPage(Meeting meeting) {
		System.out.println("LjhServiceImpl getMtRpListPage Start");
		
		//--------------------------------------------------------------
		List<Meeting> mtList = ljhd.getMtRpListPage(meeting);
		//--------------------------------------------------------------
		
		return mtList;
	}
	
	// 회의록 미등록 상태인 회의 조회 (meeting_status = 1)
	@Override
	public List<Meeting> getMeetingDate(int project_id) {
		System.out.println("LjhServiceImpl getMeetingDate Start");
		
		//--------------------------------------------------------------
		List<Meeting> meetingDateList = ljhd.getMeetingDate(project_id);
		//--------------------------------------------------------------
		
		return meetingDateList;
	}
	
	// 프로젝트 전체 팀원 목록 조회
	@Override
	public List<PrjMemList> getPrjMember(int project_id) {
		System.out.println("LjhServiceImpl getPrjMember Start");
		
		//--------------------------------------------------------------
		List<PrjMemList> prjMemList = ljhd.getPrjMember(project_id);
		//--------------------------------------------------------------
		
		return prjMemList;
	}
	
	// 회의일정 등록 (meeting_status = 1)
	@Override
	public int insertMeetingDate(Meeting meeting) {
		System.out.println("LjhServiceImpl insertMeetingDate Start");
		int insertResult = 0;
		
		//--------------------------------------------------------------
		insertResult = ljhd.insertMeetingDate(meeting);
		//--------------------------------------------------------------
		
		return insertResult;
	}

	// 회의록 수정
	@Override
	public int meetingReportUpdate(Meeting meeting) {
		
		System.out.println("LjhServiceImpl meetingReportUpdate Start");
		
		String[] meetMems = meeting.getMeetuser_id().split(",");	// string으로 넘어온 체크된 참석자 ,로 구분해 잘라서 배열로 저장 
		
		System.out.println("LjhServiceImpl meetingReportUpdate meetMems -> " + meetMems);

		int updateResult = 0;
		int deleteResult = 0;
		int insertResult = 0;

		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());		// 트랜잭션 처리
		
		try {

			//---------------------------------------------------
			updateResult = ljhd.updateMeetingReport(meeting);	// 회의록 수정
			//---------------------------------------------------
			System.out.println("meetingReportUpdate updateResult -> " + updateResult);
			
			//---------------------------------------------------
			deleteResult = ljhd.deleteMeetingMember(meeting);	// 기존 회의 참석자 삭제
			//---------------------------------------------------
			System.out.println("meetingReportUpdate deleteResult -> " + deleteResult);
			
			for (int i = 0; i<meetMems.length; i++) {			// 체크된 참석자수 만큼 MEETING_MEMBER TBL insert 작업 수행
				Meeting mt = new Meeting(); 
				mt.setMeetuser_id(meetMems[i]);
				mt.setMeeting_id(meeting.getMeeting_id());
				mt.setProject_id(meeting.getProject_id());
				
				//---------------------------------------------------
				insertResult += ljhd.insertMeetingMember(mt);	// 회의 참석자 신규 등록
				//---------------------------------------------------
				System.out.println("meetingReportUpdate insertResult -> " + insertResult);
			}

			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl meetingReportUpdate Exception -> " + e.getMessage());
		}

		int totalResult = updateResult+deleteResult+insertResult;
		
		return totalResult;
		
	}
	
	// 회의록 수정 (미사용)
	@Override
	public int updateMeetingReport(Meeting meeting) {
		System.out.println("LjhServiceImpl updateMeetingReport Start");
		int updateResult = 0;
		updateResult = ljhd.updateMeetingReport(meeting);
		
		return updateResult;
	}

	// 기존 회의 참석자 삭제 (미사용)
	@Override
	public int deleteMeetingMember(Meeting meeting) {
		System.out.println("LjhServiceImpl deleteMeetingMember Start");
		
		int deleteResult = 0;
		
		//---------------------------------------------------
		deleteResult = ljhd.deleteMeetingMember(meeting);
		//---------------------------------------------------
		
		return deleteResult;
	}

	// 회의 참석자 등록 (미사용)
	@Override
	public int insertMeetingMember(Meeting meeting) {
		System.out.println("LjhServiceImpl insertMeetingMember Start");
		
		int insertResult = 0;
		
		//---------------------------------------------------
		insertResult = ljhd.insertMeetingMember(meeting);
		//---------------------------------------------------
		
		return insertResult;
	}
	
	// 회의록 삭제
	@Override
	public int deleteMeetingReport(Meeting meeting) {
		System.out.println("LjhServiceImpl deleteMeetingReport Start");
		
		int memberDelete = 0;
		int meetingDelete = 0;
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());		// 트랜잭션 처리
		
		try {
			//-----------------------------------------------------------------------------
			memberDelete = ljhd.deleteMeetingMember(meeting);		// MEETING_MEMBER TBL delete
			//-----------------------------------------------------------------------------
			System.out.println("deleteMeetingReport memberDelete -> " + memberDelete);
			
			//--------------------------------------------------------------------------------------
			meetingDelete = ljhd.deleteMeetingReport(meeting.getMeeting_id());		// MEETING TBL delete
			//--------------------------------------------------------------------------------------
			System.out.println("deleteMeetingReport meetingDelete -> " + meetingDelete);
			
			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl deleteMeetingReport Exception -> " + e.getMessage());
		}
		
		int totalResult = meetingDelete+memberDelete;
		
		return totalResult;
	}
	
	// 회의일정 등록 (meeting_status = 1)
	@Override
	public int insertMeeting(Meeting meeting) {
		System.out.println("LjhServiceImpl insertMeeting Start");
		
		String[] meetMems = meeting.getMeetuser_id().split(",");	// string으로 넘어온 체크된 참석자 ,로 구분해 잘라서 배열로 저장 
		
		System.out.println("LjhServiceImpl insertMeeting meetMems.length -> " + meetMems.length);
		
		// 회의일정 등록 (meeting TBL)
		int meetingInsert = 0;
		// 참석자 등록
		int memberInsert = 0;
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition()); 	// 트랜잭션 처리
		
		try {
			//---------------------------------------------------------
			meetingInsert = ljhd.insertMeetingDate(meeting);	// 회의일정 등록
			//---------------------------------------------------------
			
			for (int i = 0; i<meetMems.length; i++) {		// 체크된 참석자수 만큼 MEETING_MEMBER TBL insert 작업 수행
				Meeting mt = new Meeting(); 
				mt.setMeetuser_id(meetMems[i]);
				mt.setMeeting_id(meeting.getMeeting_id());
				mt.setProject_id(meeting.getProject_id());
				
				//---------------------------------------------------------
				memberInsert += ljhd.insertMember(mt);		// 회의 참석자 등록
				//---------------------------------------------------------
				System.out.println("insertMeeting insertResult -> " + memberInsert);
			}

			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl insertMeeting Exception -> " + e.getMessage());
		}

		int totalResult = meetingInsert + memberInsert;
		
		return totalResult;
	}
	
	// 회의일정 -> 회의록 등록 (meeting_status = 1 -> 3)
	@Override
	public int insertMeetingReport(Meeting meeting) {
		System.out.println("LjhServiceImpl insertMeetingReport Start");
		
		String[] meetMems = meeting.getMeetuser_id().split(",");	// string으로 넘어온 체크된 참석자 ,로 구분해 잘라서 배열로 저장 
		
		System.out.println("LjhServiceImpl insertMeetingReport meetMems -> " + meetMems);

		int delMemResult = 0;		// 기존 참석자 삭제
		int inMemResult = 0;		// 참석자 새로 등록
		int updateResult = 0;		// 회의록 등록 (meeting_status = 3으로 변경)
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());		// 트랜잭션 처리
		
		try {
			//------------------------------------------------------------
			updateResult = ljhd.updateReport(meeting);			// 회의일정 -> 회의록으로 변경 (meeting_status = 3으로 변경)
			//------------------------------------------------------------
			System.out.println("insertMeetingReport updateResult -> " + updateResult);
			
			//------------------------------------------------------------
			delMemResult = ljhd.deleteMeetingMember(meeting);	// 기존 참석자 삭제
			//------------------------------------------------------------
			System.out.println("insertMeetingReport delMemResult -> " + delMemResult);
			
			for (int i = 0; i<meetMems.length; i++) {			// 체크된 참석자수 만큼 MEETING_MEMBER TBL insert 작업 수행
				Meeting mt = new Meeting(); 
				mt.setMeetuser_id(meetMems[i]);
				mt.setMeeting_id(meeting.getMeeting_id());
				mt.setProject_id(meeting.getProject_id());
				
				//------------------------------------------------------------
				inMemResult += ljhd.insertMeetingMember(mt);	// 참석자 새로 등록
				//------------------------------------------------------------
				System.out.println("insertMeetingReport inMemResult -> " + inMemResult);
			}

			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl meetingReportUpdate Exception -> " + e.getMessage());
		}

		int totalResult = updateResult+inMemResult+delMemResult;
		
		return totalResult;
		
	}

	// 회의록 등록 (meeting_status = 2)
	@Override
	public int insertReport(Meeting meeting) {
		System.out.println("LjhServiceImpl insertReport Start");
		
		String[] meetMems = meeting.getMeetuser_id().split(",");	// string으로 넘어온 체크된 참석자 ,로 구분해 잘라서 배열로 저장 
		
		System.out.println("LjhServiceImpl insertReport meetMems.length -> " + meetMems.length);
		
		// 회의일정 등록 (meeting TBL)
		int meetingInsert = 0;
		// 참석자 등록
		int memberInsert = 0;
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());		// 트랜잭션 처리
		
		try {
			//---------------------------------------------------------
			meetingInsert = ljhd.insertMeetingReport(meeting);		// 회의록 등록
			//---------------------------------------------------------
			
			for (int i = 0; i<meetMems.length; i++) {		// 체크된 참석자수 만큼 MEETING_MEMBER TBL insert 작업 수행
				Meeting mt = new Meeting(); 
				mt.setMeetuser_id(meetMems[i]);
				mt.setMeeting_id(meeting.getMeeting_id());
				mt.setProject_id(meeting.getProject_id());
				
				//---------------------------------------------------------
				memberInsert += ljhd.insertMember(mt);		// 회의 참석자 신규 등록
				//---------------------------------------------------------
				System.out.println("insertReport insertResult -> " + memberInsert);
			}

			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl insertReport Exception -> " + e.getMessage());
		}

		int totalResult = meetingInsert + memberInsert;
		
		return totalResult;
	}
	
	// 회의일정 수정
	@Override
	public int updateMeetingDate(Meeting meeting) {
		System.out.println("LjhServiceImpl updateMeetingDate Start");
		
		String[] meetMems = meeting.getMeetuser_id().split(",");	// string으로 넘어온 체크된 참석자 ,로 구분해 잘라서 배열로 저장 
		
		System.out.println("LjhServiceImpl updateMeetingDate meetMems -> " + meetMems);
		
		int updateResult = 0;
		int deleteResult = 0;
		int insertResult = 0;
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());		// 트랜잭션 처리
		
		try {
			//---------------------------------------------------------
			updateResult = ljhd.updateMeetingDate(meeting);		// 회의일정 수정
			//---------------------------------------------------------
			System.out.println("updateMeetingDate updateResult -> " + updateResult);
			
			//---------------------------------------------------------
			deleteResult = ljhd.deleteMeetingMember(meeting);	// 기존 회의 참석자 삭제
			//---------------------------------------------------------
			System.out.println("updateMeetingDate deleteResult -> " + deleteResult);
			
			for (int i = 0; i < meetMems.length; i++) {			// 체크된 참석자수 만큼 MEETING_MEMBER TBL insert 작업 수행
				Meeting mt = new Meeting();
				mt.setMeetuser_id(meetMems[i]);
				mt.setMeeting_id(meeting.getMeeting_id());
				mt.setProject_id(meeting.getProject_id());
				
				//---------------------------------------------------------
				insertResult += ljhd.insertMeetingMember(mt);	// 회의 참석자 등록
				//---------------------------------------------------------
				System.out.println("updateMeetingDate insertResult -> " + insertResult);
			}
			
			transactionManager.commit(txStatus);		// 이상 없으면 커밋
		} catch (Exception e) {
			transactionManager.rollback(txStatus);		// 에러 발생 시 롤백
			System.out.println("LjhServiceImpl updateMeetingDate Exception -> " + e.getMessage());
		}
		
		int totalResult = updateResult+insertResult+deleteResult;
		
		return totalResult;
	}
	
	
//-------------------------------------------------------------------------------------------------------
// 알림
//-------------------------------------------------------------------------------------------------------
	// 접속한 회원이 참석자로 포함된 회의 조회
	@Override
	public List<Meeting> getUserMeeting(HashMap<String, String> map) {
		System.out.println("LjhServiceImpl getUserMeeting Start");
		List<Meeting> meetingList = null;
		
		//---------------------------------------------
		meetingList = ljhd.getUserMeeting(map);
		//---------------------------------------------
		
		return meetingList;
	}

	// 접속한 회원이 작성한 글과 등록된 답글 조회 (alarm_flag = N만)
	@Override
	public List<PrjBdData> getBoardRep(HashMap<String, String> map) {
		System.out.println("LjhServiceImpl getBoardRep Start");
		List<PrjBdData> boardRep = null;
		
		//---------------------------------------------
		boardRep = ljhd.getBoardRep(map);
		//---------------------------------------------
		
		return boardRep;
	}

	// 접속한 회원이 작성한 글과 등록된 댓글 개수 조회 (alarm_flag = N만)
	@Override
	public List<PrjBdData> getBoardComt(HashMap<String, String> map) {
		System.out.println("LjhServiceImpl getBoardComt Start");
		List<PrjBdData> boardComt = null;
		
		//---------------------------------------------
		boardComt = ljhd.getBoardComt(map);
		//---------------------------------------------
		
		return boardComt;
	}
	
	// 프로젝트 생성 승인 여부 조회 (팀장 계정 접속 시)
	@Override
	public List<PrjInfo> getPrjApprove(HashMap<String, String> map) {
		System.out.println("LjhServiceImpl getPrjApprove Start");
		List<PrjInfo> prjApprove = null;
		
		//---------------------------------------------
		prjApprove = ljhd.getPrjApprove(map);
		//---------------------------------------------
		
		return prjApprove;
	}

	// 미승인 상태인 프로젝트 신청 건수 조회 (관리자 계정 접속 시)
	@Override
	public List<PrjInfo> getNewPrj(HashMap<String, String> map) {
		System.out.println("LjhServiceImpl getNewPrj Start");
		List<PrjInfo> newPrjList = null;
		
		//---------------------------------------------
		newPrjList = ljhd.getNewPrj(map);
		//---------------------------------------------
		
		return newPrjList;
	}




	
}