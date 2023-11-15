package com.oracle.s202350101.service.hijSer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.oracle.s202350101.dao.hijDao.HijDao;
import com.oracle.s202350101.model.HijPrjStep;
import com.oracle.s202350101.model.HijRequestDto;
import com.oracle.s202350101.model.HijRequestPrjDto;
import com.oracle.s202350101.model.HijSearchRequestDto;
import com.oracle.s202350101.model.HijSearchResponseDto;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;
import com.oracle.s202350101.model.PrjStep;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class HijServiceImpl implements HijService {
	
	private final HijDao hd;
	private final PlatformTransactionManager transactionManager;
	
// 1. admin 프로젝트 관리
	
//------------------------------------------------------------------------------------------------------------------	
	// prj_info 전체 갯수
	@Override
	public int totalCount() {
		System.out.println("HijServiceImple totalCount START");
		int totalCount = 0;
		//----------------------------------
		totalCount = hd.totalCount();
		//----------------------------------
		return totalCount;
	}	
//------------------------------------------------------------------------------------------------------------------	
	// 프로젝트 생성 승인 -> 프로젝트 리스트
	@Override
	public List<PrjInfo> approveList(PrjInfo prjInfo) {
		System.out.println("HijServiceImple approveList START");
		//------------------------------------------------------
		List<PrjInfo> approveList = hd.approveList(prjInfo);
		//------------------------------------------------------
		return approveList;
	}	
//----------------------------------------------------------------------------------------------	
// 프로젝트 생성 승인 상태 바꿔줌
	@Override
	public int app_ok(HijRequestDto hijrequest) {
		System.out.println("HijServiceImple app_ok START");
		int resultCount = 0;
		
		List<String> project_ids = hijrequest.getProject_id();    //  Project_id 리스트 저장
		List<String> project_approves = hijrequest.getProject_approve();    //  Project_approve 리스트 저장
		
		HijRequestPrjDto hijRequestPrjDto = new HijRequestPrjDto();
		
		for(int i = 0; i < project_ids.size(); i++) {
			hijRequestPrjDto.setProject_id(Integer.parseInt(project_ids.get(i)));
			hijRequestPrjDto.setProject_approve(Integer.parseInt(project_approves.get(i)));
			System.out.println("확인 1: " + project_ids.get(i));
			System.out.println("확인 2: " + project_approves.get(i));
			
			List<PrjMemList> okMemberList = null;
			List<PrjStep> defaultList = null;
			
			try { 
				//----------------------------------------------------------------------------------------
				resultCount = hd.updatePrjInfoProjectApprove(hijRequestPrjDto); //관리자 페이지 승인여부 : 승인완료 
				//----------------------------------------------------------------------------------------
				if(resultCount > 0) { 
					System.out.println("승인처리완료 : " + resultCount);
					//----------------------------------------------------------------------------------------
					okMemberList = hd.selectPrjInfoMemberList(hijRequestPrjDto); // 프로젝트 멤버 조회
					//----------------------------------------------------------------------------------------
					if(okMemberList.size() > 0) {
						System.out.println("확인11 : " + okMemberList.size());
						//----------------------------------------------------------------------------------------
						resultCount = hd.updateUserInfoProjectId(okMemberList);	//user_info project_id 값 추가
						//----------------------------------------------------------------------------------------
						System.out.println("resultCOunt 확인용!!! :" +resultCount );
						if(resultCount != 0) { 
							System.out.println("확인 22 " + resultCount);
							//----------------------------------------------------------------------------------------
							defaultList = hd.selectDefualtStep(defaultList); //단계 기본값 조회 (prj_step project_id=0)
							//----------------------------------------------------------------------------------------
							System.out.println("defaultList 사이즈 : " + defaultList.size());
							for(int j=0; j<defaultList.size(); j++) {
								defaultList.get(j).setProject_id(hijRequestPrjDto.getProject_id());	// 현재 사용하고자 하는 project_id 가져옴
								System.out.println("project_id 확인 : " +defaultList.get(j).getProject_id());
							}
							if(defaultList.size() > 0 ) {
								//----------------------------------------------------------------------------------------
								resultCount = hd.insertDefualtStep(defaultList); // 단계 기본값 조회한것을 새로운 project 단계 생성에 넣어줌 
								//----------------------------------------------------------------------------------------
							}
						}
					} 
				} 
			}catch (Exception e) {
				System.out.println("HijDaoImpl app_ok Exception e : " + e.getMessage()); 
			}
		}	
		return resultCount; 
	}

//------------------------------------------------------------------------------------------------------------------	
	// 프로젝트 삭제

	@Override
	public int app_del(HijRequestDto hijrequest) {
		System.out.println("HijServiceImple app_del START");
		int resultCount = 0;
		
		List<String> project_ids = hijrequest.getProject_id();
		List<String> del_statuss = hijrequest.getDel_status();
		
		HijRequestPrjDto hijRequestPrjDto = new HijRequestPrjDto();
		
		for(int i = 0; i < project_ids.size(); i++) {
			
			hijRequestPrjDto.setProject_id(Integer.parseInt(project_ids.get(i)));
			hijRequestPrjDto.setDel_status(Integer.parseInt(del_statuss.get(i)));
				
			List<PrjMemList> delMemberList = null;
			try {
				//-----------------------------------------------------------------------------------
				delMemberList = hd.selectPrjInfoMemberList(hijRequestPrjDto); //멤버리스트 가져옴
				//-----------------------------------------------------------------------------------
				System.out.println("멤버목록 가져옴"+ delMemberList.get(0).getUser_id());
				System.out.println("delMemberList : " + delMemberList.size());
				if(delMemberList.size() > 0) {
					//-----------------------------------------------------------------------------------
					resultCount = hd.updateNullUserProjectId(delMemberList); // user_info의 project_id null로 바꿔줌
					//-----------------------------------------------------------------------------------
					System.out.println("확인 11 : " + resultCount );
					if(resultCount != 0 ) {
						//-----------------------------------------------------------------------------------
						resultCount = hd.deletePrjInfoMemberList(delMemberList); // 멤버리스트 삭제
						//-----------------------------------------------------------------------------------
						if(resultCount != 0) { 
							//-----------------------------------------------------------------------------------
							resultCount = hd.updatePrjInfoDelStatus(hijRequestPrjDto); // 프로젝트 관리의 삭제여부에 삭제 띄어줌
							//-----------------------------------------------------------------------------------
						}
					} 
				}		
			} catch (Exception e) {
				System.out.println("HijServiceImpl app_del Exception e : " + e.getMessage()); 
			}
		}
		return resultCount;
	}
	
	
//--------------------------------------------------------------------------------------	
// 2. 프로젝트 생성
//------------------------------------------------------------------------------------------------------------------		
	// user_id랑 같은 class_id를 가진학생 리스트
	@Override
	public List<UserInfo> listName(String user_id) {
		List<UserInfo> nameList = null;
		System.out.println("HijServiceImple listName START");
		//-----------------------------------------------------
		nameList = hd.listName(user_id);
		//-----------------------------------------------------
		return nameList;
	}	
//------------------------------------------------------------------------------------------------------------------	
	// 프로젝트 생성 수행
	@Override
	public int reqCreate(PrjInfo prjInfo) {	
		
		int createReq = 0;
		int createMem = 0;
			
		String[] prjMems = prjInfo.getMember_user_id().split(","); //체크된 멤버 배열로 저장
		
		TransactionStatus txStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			//----------------------------------------------------
			createReq = hd.reqCreate(prjInfo);
			//----------------------------------------------------
			System.out.println("HijServiceImple reqCreate START");
			
			// 팀장 아이디 prj_mem_list 추가
			PrjInfo pi = new PrjInfo();
			pi.setMember_user_id(prjInfo.getProject_manager_id());
			pi.setProject_id(prjInfo.getProject_id());	
			//---------------------------------------------------
			createMem += hd.memCreate(pi); //prj_mem_list에 팀원 추가
			//---------------------------------------------------
			// prj_mem_list에 넣는 작업
			for(int i=0; i<prjMems.length; i++) {
				pi.setMember_user_id(prjMems[i]);
				pi.setProject_id(prjInfo.getProject_id());
				//----------------------------------------------
				createMem += hd.memCreate(pi); //prj_mem_list에 팀원 추가
				//----------------------------------------------
			}
			transactionManager.commit(txStatus);
			} catch (Exception e) {
				transactionManager.rollback(txStatus);
				System.out.println("HijServiceImple reqCreate Exception : " + e.getMessage());
			}
		return createReq;
	}
//------------------------------------------------------------------------------------------------------------------	
// 3. 프로젝트 단계 프로파일
//------------------------------------------------------------------------------------------------------------------		
	// 프로젝트 멤버 리스트
	@Override
	public List<PrjMemList> listMember(int project_id) {
		List<PrjMemList> memberList = null;
		System.out.println("HijServiceImple listMember START");
		//-----------------------------------------------------
		memberList = hd.listMember(project_id);
		//-----------------------------------------------------
		System.out.println("HijServiceImple listMember memberList.size : " + memberList.size());
		return memberList;
	}
//------------------------------------------------------------------------------------------------------------------
	// 프로젝트 조회 
	@Override
	public PrjInfo listStep(int project_id) {
		PrjInfo prjInfo = null;
		System.out.println("HijServiceImple listStep START");
		//-----------------------------------------------------
		prjInfo = hd.stepList(project_id);
		//-----------------------------------------------------
		return prjInfo;
	}
//------------------------------------------------------------------------------------------------------------------	
	// 프로젝트 단계조회
	@Override
	public List<PrjStep> titleList(int project_id) {
		List<PrjStep> listTitle = null;
		System.out.println("HijServiceImple titleList START");
		//-----------------------------------------------------
		listTitle = hd.listTitle(project_id);
		//-----------------------------------------------------
		System.out.println("HijServiceImple titleList  listTitle.size : " + listTitle.size());
		return listTitle;
	}
//------------------------------------------------------------------------------------------------------------------		
	// 프로젝트 상태 확인
	@Override
	public int prjStatus(PrjInfo prjInfo) {
		int result = 0;
		System.out.println("HijServiceImple prjStatus START");
		//-----------------------------------------------------
		result = hd.prjStatus(prjInfo);
		//-----------------------------------------------------
		return result;
	}
//------------------------------------------------------------------------------------------------------------------

	// 프로젝트 정보 단계 프로파일 프로젝트 정보 수정
	@Override
	public int reqEdit(PrjInfo prjInfo) {
		System.out.println("HijServiceImple reqEdit START");
		int resultCount = 0;
		int reCreateMem = 0;
		List<PrjMemList> delMemberList = null;
		List<PrjMemList> okMemberList = new ArrayList<PrjMemList>() ;
		System.out.println("prjInfo.getMember_user_id() ---> : " + prjInfo.getMember_user_id());
		String[] prjMems = prjInfo.getMember_user_id().split(",");
				
		try {
			//---------------------------------
			resultCount = hd.reqEdit(prjInfo);
			//---------------------------------
			
			if(resultCount != 0) {
				HijRequestPrjDto hijRequestPrjDto = new HijRequestPrjDto();
				hijRequestPrjDto.setProject_id(prjInfo.getProject_id());
				System.out.println("테스트 : " + prjInfo.getProject_id());
				
				//-------------------------------------------------------------------------
				delMemberList = hd.selectPrjInfoMemberList(hijRequestPrjDto); //1. 멤버리스트 가져옴
				//-------------------------------------------------------------------------
				System.out.println("delMemberList");
				System.out.println("테스트 사이즈 2 : " + delMemberList.size());
				for(int k = 0; k<delMemberList.size(); k++) {
					System.out.println("멤버목록 가져옴 "+k+":"+ delMemberList.get(k).getUser_id());
					System.out.println("멤버목록 프로젝트 아이디"+k+":" + delMemberList.get(k).getProject_id());
				}
				if(delMemberList.size() > 0) {
					
					//-------------------------------------------------------
					resultCount = hd.updateNullUserProjectId(delMemberList);
					//-------------------------------------------------------
					if(resultCount !=0) {
						System.out.println("updateNullUserProjectId 성공 : " +resultCount );
						//-------------------------------------------------------------------------
						resultCount = hd.deletePrjInfoMemberList(delMemberList); //2. 멤버리스트 삭제
						//-------------------------------------------------------------------------
						System.out.println("resultCount 확인 deletePrjInfoMemberList*******: " + resultCount);
						if(resultCount != 0) {
						
							PrjMemList pi = new PrjMemList();
								  
							pi.setUser_id(prjInfo.getProject_manager_id());
							pi.setProject_id(prjInfo.getProject_id());	
							System.out.println("프로젝트 id 확인 :" +prjInfo.getProject_id() );
							//---------------------------------
							reCreateMem += hd.memReCreate(pi); 
							//---------------------------------
							okMemberList.add(pi);
								
							// prj_mem_list에 넣는 작업
							for(int i=0; i<prjMems.length; i++) {
								pi = new PrjMemList();
								pi.setUser_id(prjMems[i]);
								pi.setProject_id(prjInfo.getProject_id());
									
								//---------------------------------
								reCreateMem += hd.memReCreate(pi); 
								//---------------------------------
								okMemberList.add(pi);
							}							 							  
							//-----------------------------------------------------
							resultCount = hd.updateUserInfoProjectId(okMemberList);	//5.						 
							//-----------------------------------------------------
						}
					}
				}				
			}			
		} catch (Exception e) {
			System.out.println("HijServiceImple reqCreate Exception : " + e.getMessage());
		}
		return resultCount;
	}

//------------------------------------------------------------------------------------------------------------------		
	// 프로젝트 단계 추가 수행
	@Override
	public int insertStep(PrjStep prjStep) {
		int stepInsert = 0;
		System.out.println("HijServiceImple insertStep START");
		//-----------------------------------------------------
		stepInsert = hd.insertStep(prjStep);
		//-----------------------------------------------------
		return stepInsert;
	}
//------------------------------------------------------------------------------------------------------------------	
	// 단계 선택 **************************************
	@Override
	public int prjOrder( List<HijPrjStep> hijPrjStepList) {
		int resultCount = 0;
		System.out.println("HijServiceImple prjOrder START");
		//-------------------------------------------------
		resultCount = hd.prjOrder(hijPrjStepList);
		//-------------------------------------------------
		return resultCount;
	}

//------------------------------------------------------------------------------------------------------------------		
	// 프로젝트 단계 수정 조회
	@Override
	public PrjStep detailStep(int project_id, int project_step_seq) {
		PrjStep prjStep = null;
		System.out.println("HijServiceImple detailStep START");
		//-----------------------------------------------------
		prjStep = hd.detailStep(project_id, project_step_seq);
		//-----------------------------------------------------
		return prjStep;
	}
//------------------------------------------------------------------------------------------------------------------		
	// 프로젝트 단계 수정 수행
	@Override
	public int updateStep(PrjStep prjStep) {
		int updateCount = 0;
		System.out.println("HijServiceImple updateStep START");
		//-----------------------------------------------------
		updateCount = hd.updateStep(prjStep);
		//-----------------------------------------------------
		return updateCount;
	}
//------------------------------------------------------------------------------------------------------------------	
	// 프로젝트 단계 삭제
	@Override
	public int deleteStep(int project_id, int project_step_seq) {
		int result=0;
		System.out.println("HijServiceImple deleteStep START");
		//-----------------------------------------------------
		result = hd.deleteStep(project_id, project_step_seq);
		//-----------------------------------------------------
		return result;
	}
//------------------------------------------------------------------------------------------------------------------
	@Override
	public List<HijSearchResponseDto> searchAll(HijSearchRequestDto hijSearchRequestDto) {
		List<HijSearchResponseDto> 	hijSearchResponseDtoList = null;  // 1개 table 검색해온 결과 List
		List<HijSearchResponseDto> 	hijSearchResponsMerge = new ArrayList<HijSearchResponseDto>(); // 취합 list(bd_free + bd_QNA)
		System.out.println("HijServiceImple searchAll START");
		
		// 대상 table 추가 하려면 여기다 넣음
		List<String> tableList = new ArrayList<String>();
		tableList.add("BD_FREE");
		tableList.add("BD_QNA");
		
		// table 하나씩 검색
		for(String table : tableList) {
			hijSearchRequestDto.setTablename(table);
			hijSearchResponseDtoList = hd.searchAll(hijSearchRequestDto);
			hijSearchResponsMerge.addAll(hijSearchResponseDtoList);
		}
		
		return hijSearchResponsMerge;
	}



}	

