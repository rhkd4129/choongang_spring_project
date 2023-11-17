package com.oracle.s202350101.dao.hijDao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.HijPrjStep;
import com.oracle.s202350101.model.HijRequestDto;
import com.oracle.s202350101.model.HijRequestPrjDto;
import com.oracle.s202350101.model.HijSearchRequestDto;
import com.oracle.s202350101.model.HijSearchResponseDto;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class HijDaoImpl implements HijDao {

	private final SqlSession session;
	
	
// 1. admin 프로젝트 관리	
//--------------------------------------------------------------------------------------		
    // 프로젝트 갯수
	@Override
	public int totalCount() {
		int totalCount = 0;
		System.out.println("HijDaoImpl totalCount START"); 
		try {
			totalCount = session.selectOne("ijTotalCount");
		} catch (Exception e) {
			System.out.println("HijDaoImpl totalCount Exception e : " + e.getMessage()); 
		}
		return totalCount;
	}	
//--------------------------------------------------------------------------------------	
	// 프로젝트 관리 리스트
	@Override
	public List<PrjInfo> approveList(PrjInfo prjInfo) {
		List<PrjInfo> approveList = null;
		System.out.println("HijDaoImpl approveList START");
		try {
			approveList = session.selectList("ijApproveList", prjInfo);
		} catch (Exception e) {
			System.out.println("HijDaoImpl approveList Exception e : " + e.getMessage());
		}
		return approveList;
	}
//--------------------------------------------------------------------------------------		  
	//관리자 페이지 project_approve=2
	@Override
	public int updatePrjInfoProjectApprove(HijRequestPrjDto hijRequestPrjDto) {
		int resultCount = 0;
		System.out.println("HijDaoImpl updatePrjInfoProjectApprove START");
		try {
			resultCount = session.update("ijAppOk", hijRequestPrjDto); //관리자 페이지 승인여부 : 승인완료 
		} catch (Exception e) {
			System.out.println("HijDaoImpl updatePrjInfoProjectApprove Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------	
	//멤버목록 가져오기
	@Override
	public List<PrjMemList> selectPrjInfoMemberList(HijRequestPrjDto hijRequestPrjDto) {

		System.out.println("HijDaoImpl selectPrjInfoMemberList START");
		List<PrjMemList> delMemberList = null;
		
		try {
			delMemberList = session.selectList("ijAppOkGetUser", hijRequestPrjDto); //멤버리스트 가져옴
		} catch (Exception e) {
			System.out.println("HijDaoImpl selectPrjInfoMemberList Exception e : " + e.getMessage()); 
		}
		return delMemberList;
	}	
//--------------------------------------------------------------------------------------	
	//user_info에 project_id 해당 id로 넣어줌
	@Override
	public int updateUserInfoProjectId(List<PrjMemList> okMemberList) {
		int resultCount=0;
		System.out.println("HijDaoImpl updateUserInfoProjectId START");		
		try {
			resultCount = session.update("ijAppOkUpdateUser", okMemberList);	//user_info project_id 값 추가
		} catch (Exception e) {
			System.out.println("HijDaoImpl updateUserInfoProjectId Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------	
	// 단계 기본값 조회 (prj_step project_id=0)
	@Override
	public List<PrjStep> selectDefualtStep(List<PrjStep> defaultList) {
		System.out.println("HijDaoImpl selectDefualtStep START");
		try {
			defaultList = session.selectList("ijDefaultList"); //단계 기본값 조회 (prj_step project_id=0)
		} catch (Exception e) {
			System.out.println("HijDaoImpl selectDefualtStep Exception e : " + e.getMessage()); 
		}
		return defaultList;
	}
//--------------------------------------------------------------------------------------	
	// 단계 기본값 조회한것을 새로운 project 단계 생성에 넣어줌
	@Override
	public int insertDefualtStep(List<PrjStep> defaultList) {
		int resultCount = 0;
		System.out.println("HijDaoImpl insertDefualtStep START");
		
		try {
			 resultCount = session.insert("ijAppDefaultStep", defaultList); // 단계 기본값 조회한것을 새로운 project 단계 생성에 넣어줌
		} catch (Exception e) {
			System.out.println("HijDaoImpl insertDefualtStep Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------	
	//회원정보에 프로젝트id초기화
	@Override
	public int updateNullUserProjectId(List<PrjMemList> delMemberList) {

		System.out.println("HijDaoImpl updateNullUserProjectId START");
		int resultCount = 0;
		
		try {
			resultCount = session.update("ijAppDelUpdateUser", delMemberList); // user_info의 project_id null로 바꿔줌
		} catch (Exception e) {
			System.out.println("HijDaoImpl updateNullUserProjectId Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}	
//--------------------------------------------------------------------------------------	
	// 프로젝트 삭제시 멤버목록에서 삭제
	@Override
	public int deletePrjInfoMemberList(List<PrjMemList> delMemberList) {
		
		System.out.println("HijDaoImpl deletePrjInfoMemberList START");
		int resultCount = 0;
		
		try {
			resultCount = session.delete("ijAppDelUser", delMemberList); // 멤버리스트 삭제
		} catch (Exception e) {
			System.out.println("HijDaoImpl deletePrjInfoMemberList Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------	
	//멤버목록에서 삭제상태로 수정
	@Override
	public int updatePrjInfoDelStatus(HijRequestPrjDto hijRequestPrjDto) {
		
		System.out.println("HijDaoImpl updatePrjInfoDelStatus START");
		int resultCount = 0;
		
		try {
			resultCount = session.update("ijAppDel", hijRequestPrjDto); // 프로젝트 관리의 삭제여부에 삭제 띄어줌
		} catch (Exception e) {
			System.out.println("HijDaoImpl updatePrjInfoDelStatus Exception e : " + e.getMessage()); 
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------		
// 2. 프로젝트 생성	
//--------------------------------------------------------------------------------------	
	// user_id랑 같은 class_id를 가진학생 리스트
	@Override
	public List<UserInfo> listName(String user_id) {
		List<UserInfo> listName = null;
		System.out.println("HijDaoImpl listName START");
		try {
			listName = session.selectList("ijlistName", user_id);
			System.out.println("HijDaoImpl listName listName.size : " + listName.size());
		} catch (Exception e) {
			System.out.println("HijDaoImpl listName Exception e : " + e.getMessage());
		}
		return listName;
	}
//--------------------------------------------------------------------------------------	
	//	프로젝트 생성
	@Override
	public int reqCreate(PrjInfo prjInfo) {
		int reqCreate = 0;
		System.out.println("HijDaoImpl reqCreate START");
		try {
			reqCreate = session.insert("ijReqCreate", prjInfo);
		} catch (Exception e) {
			System.out.println("HijDaoImpl listName Exception e : " + e.getMessage());
		}
		return reqCreate;
	}
//--------------------------------------------------------------------------------------	
	//prj_mem_list에 팀원 추가
	@Override
	public int memCreate(PrjInfo pi) {
		int memCreate = 0;
		System.out.println("HijDaoImpl memCreate START");
		try {
			memCreate = session.insert("ijMemCreate", pi);
		} catch (Exception e) {
			System.out.println("HijDaoImpl memCreate Exception e : " + e.getMessage());
		}
		return memCreate;
	}
//--------------------------------------------------------------------------------------
// 3. 프로젝트 단계 프로파일
//--------------------------------------------------------------------------------------
	// 프로젝트 멤버 리스트
	@Override
	public List<PrjMemList> listMember(int project_id) {
		List<PrjMemList> memberList = null;
		System.out.println("HijDaoImpl listMember START");
		try {
			memberList = session.selectList("ijMemberList", project_id );
			System.out.println("HijDaoImpl listMember memberList.size():" + memberList.size());
		} catch (Exception e) {
			System.out.println("HijDaoImpl listMember Exception e : " + e.getMessage());
		}
		return memberList;
	}
//--------------------------------------------------------------------------------------
	// 프로젝트 조회
	@Override
	public PrjInfo stepList(int project_id) {
		PrjInfo prjInfo = null;
		System.out.println("HijDaoImpl stepList START");
		try {
			System.out.println("project id 확인 : " +project_id);
			prjInfo = session.selectOne("ijstepList", project_id);
			System.out.println("HijDaoImpl stepList prjInfo.getproject_id : " + prjInfo.getProject_id());
			System.out.println("HijDaoImpl stepList prjInfo.getProject_approve_name : " + prjInfo.getProject_approve_name());
		} catch (Exception e) {
			System.out.println("HijDaoImpl stepList Exception e : " + e.getMessage());
		}
		return prjInfo;
	}
//--------------------------------------------------------------------------------------	
	// 프로젝트 단계조회
	@Override
	public List<PrjStep> listTitle(int project_id) {
		List<PrjStep> titleList = null;
		System.out.println("HijDaoImpl listTitle START");
		try {
			titleList = session.selectList("ijTitleList", project_id);
			System.out.println("HijDaoImpl listTitle titleList.size : " + titleList.size());
		} catch (Exception e) {
			System.out.println("HijDaoImpl listTitle Exception e : " + e.getMessage());
		}
		return titleList;
	}
//--------------------------------------------------------------------------------------
	// 프로젝트 진행 상태
	@Override
	public int prjStatus(PrjInfo prjInfo) {
		int result=0;
		try {
			result=session.update("ijPrjStatus", prjInfo);
		}catch (Exception e) {
			System.out.println("HijDaoImpl prjStatus Exception e : " + e.getMessage());
		}
		return result; 
	}	
//--------------------------------------------------------------------------------------	
	// 프로젝트 정보 수정
	@Override
	public int reqEdit(PrjInfo prjInfo) {
		int resultCount = 0;
		System.out.println("HijDaoImpl reqEdit START");
		try {
			resultCount = session.update("ijReqEdit", prjInfo);
		} catch (Exception e) {
			System.out.println("HijDaoImpl reqEdit Exception e : " + e.getMessage());
		}
		return resultCount;
	}
//--------------------------------------------------------------------------------------	
	// 프로젝트 정보 수정 팀원 리스트
	@Override
	public int memReCreate(PrjMemList pi) {
		int reMemCreate = 0;
		System.out.println("HijDaoImpl reMemCreate START");
		try {
			reMemCreate = session.insert("ijMemReCreate", pi);
		} catch (Exception e) {
			System.out.println("HijDaoImpl reMemCreate Exception e : " + e.getMessage());
		}
		return reMemCreate;
		}
//--------------------------------------------------------------------------------------	
	// 프로젝트 단계 추가 수행
	@Override
	public int insertStep(PrjStep prjStep) {
		int stepInsert = 0;
		System.out.println("HijDaoImpl insertStep START");
		try {
			stepInsert = session.insert("ijInsertStep", prjStep);
		} catch (Exception e) {
			System.out.println("HijDaoImpl insertStep Exception e : " + e.getMessage());
		}
		return stepInsert;
	}
	
//------------------------------------------------------------------------------------------------------------------	
	// 단계 선택
	@Override
	public int prjOrder(List<HijPrjStep> hijPrjStepList) {
		int result =0;
		System.out.println("HijDaoImpl prjOrder START");
		try {
			result = session.update("ijPrjOrder", hijPrjStepList);
		} catch (Exception e) {
			System.out.println("HijDaoImpl prjOrder Exception e : " + e.getMessage());
		}
		return result;
	}
//--------------------------------------------------------------------------------------	
	// 프로젝트 단계 수정 조회
	@Override
	public PrjStep detailStep(int project_id, int project_step_seq) {
		PrjStep prjStep = null;
		System.out.println("HijDaoImpl detailStep START");
		
		try {
			PrjStep paramPrjStep = new PrjStep();
			paramPrjStep.setProject_id(project_id);
			paramPrjStep.setProject_step_seq(project_step_seq);
			prjStep = session.selectOne("ijDetailStep", paramPrjStep);
			System.out.println("HijDaoImpl detailStep : " + prjStep.getProject_s_name() );
		} catch (Exception e) {
			System.out.println("HijDaoImpl detailStep Exception e : " + e.getMessage());
		}
		return prjStep;
	}
//--------------------------------------------------------------------------------------	
	// 프로젝트 단계 수정 수행
	@Override
	public int updateStep(PrjStep prjStep) {
		int updateCount = 0;
		System.out.println("HijDaoImpl updateStep START");
		try{
			System.out.println("project_s_name"+ prjStep.getProject_s_name());
			System.out.println("project_s_context"+ prjStep.getProject_s_context());
			System.out.println("project_id"+ prjStep.getProject_id());
			System.out.println("project_step_seq()"+ prjStep.getProject_step_seq());
			System.out.println("prjStep.getProject_order()"+prjStep.getProject_order());
			updateCount = session.update("ijUpdateStep", prjStep);
			System.out.println("dao updateCount" + updateCount);
		}catch (Exception e) {
			System.out.println("HijDaoImpl updateStep Exception e : " + e.getMessage());
		}
		return updateCount;
	}	
//--------------------------------------------------------------------------------------	
	// 프로젝트 단계 삭제
	@Override
	public int deleteStep(int project_id, int project_step_seq) {
		int result = 0;
		System.out.println("HijDaoImpl deleteStep START");
		System.out.println("11111" + project_id);
		System.out.println("22222" + project_step_seq);
		try {
			PrjStep paramPrjStep = new PrjStep();
			paramPrjStep.setProject_id(project_id);
			paramPrjStep.setProject_step_seq(project_step_seq);
			result = session.delete("ijDeleteStep", paramPrjStep);
		} catch (Exception e) {
			System.out.println("HijDaoImpl deleteStep Exception e : " + e.getMessage());
		}
		return result;
	}

//--------------------------------------------------------------------------------------
	// 통합검색
	@Override
	public List<HijSearchResponseDto> searchAll(HijSearchRequestDto hijSearchRequestDto) {
		System.out.println("HijDaoImpl searchAll START");
		List<HijSearchResponseDto> hijSearchResponseDtoList = null;
		try {
			hijSearchResponseDtoList = session.selectList("ijSearchList", hijSearchRequestDto);
			System.out.println("HijDaoImpl searchAll hijSearchResponseDtoList.size : " + hijSearchResponseDtoList.size());
		} catch (Exception e) {
			System.out.println("HijDaoImpl searchAll Exception e : " + e.getMessage());
		}
		return hijSearchResponseDtoList;
	}
	
//--------------------------------------------------------------------------------------		
	// 알람
	@Override
	public int updateAlarmCount(PrjInfo prjInfo) {
		int resultCount = 0;
		System.out.println("HijDaoImpl updateAlarmCount START");
		try {
			resultCount = session.update("ijUpdateAlarm", prjInfo);
		} catch (Exception e) {
			System.out.println("HijDaoImpl searchAll Exception e : " + e.getMessage());
		}
		return resultCount;
	}

}



