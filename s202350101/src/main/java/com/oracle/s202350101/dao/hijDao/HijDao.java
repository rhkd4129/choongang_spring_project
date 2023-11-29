package com.oracle.s202350101.dao.hijDao;

import java.util.List;

import com.oracle.s202350101.model.HijPrjStep;
import com.oracle.s202350101.model.HijRequestPrjDto;
import com.oracle.s202350101.model.HijSearchRequestDto;
import com.oracle.s202350101.model.HijSearchResponseDto;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;


public interface HijDao {
	
//------------------------------------------------------------------------------------
// 1. admin 프로젝트 관리
//--------------------------------------------------------------------------------------	
	int totalCount();
	List<PrjInfo> approveList(PrjInfo prjInfo);		// 프로젝트 생성 승인 리스트 
	// 프로젝트 생성승인
	int updatePrjInfoProjectApprove(HijRequestPrjDto hijRequestPrjDto); 			// 관리자 페이지 project_approve=2
	List<PrjMemList> selectPrjInfoMemberList(HijRequestPrjDto hijRequestPrjDto);	// 프로젝트 멤버 조회 
	int updateUserInfoProjectId(List<PrjMemList> okMemberList); 					// user_info에 project_id 해당 id로 넣어줌
	List<PrjStep> selectDefualtStep(List<PrjStep> defaultList); 					// 단계 기본값 조회 (prj_step project_id=0)
	int insertDefualtStep(List<PrjStep> defaultList); 								//단계 기본값 조회한것을 새로운 project 단계 생성에 넣어줌
	// 프로젝트 삭제
	int updateNullUserProjectId(List<PrjMemList> delMemberList);	// 회원정보에 프로젝트id초기화
	int deletePrjInfoMemberList(List<PrjMemList> delMemberList);	// 멤버목록에서 삭제상태로 수정
	int updatePrjInfoDelStatus(HijRequestPrjDto hijRequestPrjDto);	// 프로젝트 정보에 del_status=1
//--------------------------------------------------------------------------------------		
// 2. 프로젝트 생성
//--------------------------------------------------------------------------------------
	List<UserInfo> listName(String user_id);	// user_id랑 같은 class_id를 가진학생 리스트
	int reqCreate(PrjInfo prjInfo);				// 프로젝트 생성
	int memCreate(PrjInfo pi);					// prj_mem_list에 팀원 추가
//--------------------------------------------------------------------------------------
// 3. 프로젝트 단계 프로파일
//--------------------------------------------------------------------------------------
	List<PrjMemList> listMember(int project_id);				// 프로젝트 멤버 리스트
	PrjInfo stepList(int project_id); 							// 프로젝트 조회 
	List<PrjStep> listTitle(int project_id);					// 프로젝트 단계조회
	int prjStatus(PrjInfo prjInfo);								// 프로젝트 진행 상태	
	int reqEdit(PrjInfo prjInfo);								// 프로젝트 정보 수정
	int memReCreate(PrjMemList pi);								// 프로젝트 정보 수정 팀원 리스트
	int insertStep(PrjStep prjStep);							// 프로젝트 단계 추가 수행
	int prjOrder(List<HijPrjStep> hijPrjStepList);				// 단계 선택
	PrjStep detailStep(int project_id, int project_step_seq);	// 프로젝트 단계 수정 조회
	int updateStep(PrjStep prjStep);							// 프로젝트 단계 수정 수행
	int deleteStep(int project_id, int project_step_seq);  		// 프로젝트 단계 삭제
	List<HijSearchResponseDto> searchAll(HijSearchRequestDto hijSearchRequestDto); // 통합검색
	int updateAlarmCount(PrjInfo prjInfo);						// 알람
	

	
	
	
	
	

	
	

	
	
	
	
	
	



}
