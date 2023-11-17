package com.oracle.s202350101.service.hijSer;

import java.util.List;

import com.oracle.s202350101.model.HijPrjStep;
import com.oracle.s202350101.model.HijRequestDto;
import com.oracle.s202350101.model.HijSearchRequestDto;
import com.oracle.s202350101.model.HijSearchResponseDto;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;


public interface HijService {

//--------------------------------------------------------------------------------------	
// 1. admin 프로젝트 관리
//--------------------------------------------------------------------------------------	
	int totalCount();												// Prj_info 전체 목록 리스트 갯수
	List<PrjInfo> approveList(PrjInfo prjInfo);						// Prj_info 전체 리스트
	int app_ok(HijRequestDto hijrequest);							// 프로젝트 생성 승인처리
	int app_del(HijRequestDto hijrequest);							// 프로젝트 삭제
//--------------------------------------------------------------------------------------	
// 2. 프로젝트 생성
//--------------------------------------------------------------------------------------	
	List<UserInfo> listName(String user_id);						// user_id랑 같은 class_id를 가진학생 리스트	
	int reqCreate(PrjInfo prjInfo);									// 프로젝트 생성 수행
//--------------------------------------------------------------------------------------	
// 3. 프로젝트 단계 프로파일	
//--------------------------------------------------------------------------------------
	List<PrjMemList> listMember(int project_id);					// 프로젝트 멤버 리스트
	PrjInfo listStep(int project_id); 								// 프로젝트 조회 
	List<PrjStep> titleList(int project_id);						// 프로젝트 단계조회
	int prjStatus(PrjInfo prjInfo);									// 프로젝트 상태조회
	int reqEdit(PrjInfo prjInfo);									// 프로젝트 정보 수정 수행
	int insertStep(PrjStep prjStep);								// 프로젝트 단계 추가 수행
	int prjOrder( List<HijPrjStep> hijPrjStepList);							// 단계 선택
	PrjStep detailStep(int project_id, int project_step_seq);		// 프로젝트 단계 수정 조회
	int updateStep(PrjStep prjStep);								// 프로젝트 단계 수정 수행
	int deleteStep(int project_id, int project_step_seq);     		// 프로젝트 단계 삭제
	List<HijSearchResponseDto> searchAll(HijSearchRequestDto hijSearchRequestDto); // 통합검색
	int updateAlarmCount(PrjInfo prjInfo);							// 알람
		
	
	
	
	
	
	
	
	
	

}
