package com.oracle.s202350101.dao.mkhDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.PrjBdRep;
import com.oracle.s202350101.model.UserEnv;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MkhDaoImpl implements MkhDao {
	
	private final SqlSession session;

	@Override
	public int insertUserInfo(UserInfo userInfo) {
		int result = 0;
		System.out.println("MkhDaoImpl insert Start...");
		try {
			result = session.insert("InsertUserInfo", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl insert Exception->" +e.getMessage());
		}
		return result;
	}

	@Override
	public List<ClassRoom> createdClass() {
		List<ClassRoom> classList = null;
		System.out.println("MkhDaoImpl createdClass start...");
		try {
			// ClassRoom의 class_id와 class_room_num만 select
			classList = session.selectList("createdClassSelect");
		} catch (Exception e) {
			System.out.println("MkhDaoImpl createdClass Exception->" +e.getMessage());
		}
		return classList;
	}


	@Override
	public int totalBDcount(PrjBdData prjBdData) {
		int totalBDCount = 0;
		System.out.println("MkhDaoImpl totalBDcount start...");
		try {
			totalBDCount = session.selectOne("mkhTotalBDListCount", prjBdData);
			System.out.println("MkhDaoImpl totalBDcount totalCnt->"+totalBDCount);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalBDcount Exception->" +e.getMessage());
		}

		return totalBDCount;
	}
	
	@Override
	public UserInfo userLoginCheck(UserInfo userInfo) {
		UserInfo userConfirm = null;
		System.out.println("MkhDaoImpl userLoginCheck start...");
		try {
			userConfirm = session.selectOne("userLoginConfirm", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl userLogin Exception->"+e.getMessage());
		}
		
		return userConfirm;
	}
	
	// board 전체 select
	@Override
	public List<PrjBdData> bdSelectAll(PrjBdData prjBdData) {
		List<PrjBdData> selectAll = null;
		System.out.println("MkhDaoImpl bdSelectAll start...");
		
		try {
			selectAll = session.selectList("mkhBdSelectAll", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl bdSelectAll Exception->" +e.getMessage());
		}
		return selectAll;
	}

	@Override
	public UserInfo confirm(String user_id) {
		System.out.println("MkhDaoImpl confirm start...");
		UserInfo userInfo = new UserInfo();
		try {
			userInfo = session.selectOne("IdConfirm", user_id);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl confirm Exception->"+e.getMessage());
		}
		return userInfo;
	}

	@Override
	public int updatePw(Map<String, String> map) {
		int result = 0;
		System.out.println("MkhDaoImpl updatePw start...");
		try {
			result = session.update("pwUpdate", map);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl updatePw Exception->" +e.getMessage());
		}

		return result;
	}

	@Override
	public UserInfo userFindId(UserInfo userInfo) {
		UserInfo userInfoDto = null;
		System.out.println("MkhDaoImpl userFindId start...");
		try {
			userInfoDto = session.selectOne("userFindId", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl userFindId Exception->" +e.getMessage());
		}

		return userInfoDto;
	}

	@Override
	public UserEnv selectEnv(String user_id) {
		UserEnv userEnv = null;
		System.out.println("MkhDaoImpl selectEnv start...");
		try {
			userEnv = session.selectOne("selectUserEnv", user_id);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl selectEnv Exception->" +e.getMessage());
		}
		
		return userEnv;
	}

	@Override
	public ClassRoom selectClass(String user_id) {
		ClassRoom classRoom = null;
		System.out.println("MkhDaoImpl selectClass start...");
		try {
			classRoom = session.selectOne("selectUserClass", user_id);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl selectClass Exception->" +e.getMessage());
		}

		return classRoom;
	}

	@Override
	public int updateUser(UserInfo userInfo) {
		int result = 0;
		System.out.println("MkhDaoImpl updateUser start...");
		System.out.println("MkhDaoImpl updateUser userInfo->"+userInfo);
		try {
			result = session.update("userInfoUpdate", userInfo);
			
		} catch (Exception e) {
			System.out.println("MkhDaoImpl updateUser Exception->" +e.getMessage());
		}
		return result;
	}

	@Override
	public int updateEnv(UserEnv userEnv) {
		int result = 0;
		System.out.println("MkhDaoImpl updateEnv start...");
		try {
			result = session.update("mkhUpdateEnv", userEnv);
			
		} catch (Exception e) {
			System.out.println("MkhDaoImpl updateEnv Exception->" +e.getMessage());
		}
		return result;
	}

	@Override
	public int totalComt(PrjBdData prjBdData) {
		int totalComt = 0;
		System.out.println("MkhDaoImpl totalComt start...");
		try {
			totalComt = session.selectOne("mkhTotalComt", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalComt Exception->" +e.getMessage());
		}

		return totalComt;
	}

	@Override
	public List<BdDataComt> selectAllComt(PrjBdData prjBdData) {
		List<BdDataComt> selectAllComt = null;
		System.out.println("MkhDaoImpl selectAllComt start...");
		try {
			selectAllComt = session.selectList("mkhSelectAllComt", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl selectAllComt Exception->" +e.getMessage());
		}
		return selectAllComt;
	}

	@Override
	public int totalGood(PrjBdData prjBdData) {
		int totalGood = 0;
		System.out.println("MkhDaoImpl totalGood start...");
		try {
			totalGood = session.selectOne("mkhTotalGood", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalGood Exception->" +e.getMessage());
		}

		return totalGood;
	}

	@Override
	public List<BdDataGood> selectAllGood(PrjBdData prjBdData) {
		List<BdDataGood> selectAllGood = null;
		System.out.println("MkhDaoImpl selectAllGood start...");
		
		try {
			selectAllGood = session.selectList("mkhSelectAllGood", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl selectAllGood Exception->" +e.getMessage());
		}
		return selectAllGood;
	}

	@Override
	public List<Code> codeList(Code code) {
		System.out.println("MkhDaoImpl codeList START...");
		List<Code> reCodeList = null;		
		try {
			//------------------------------------------------------------
			reCodeList = session.selectList("mkhPrjBdDataCodeList", code);
			//------------------------------------------------------------
			System.out.println("reCodeList.size()->"+reCodeList.size());
			if(reCodeList.size() > 0) {
				//성공
				System.out.println("MkhDaoImpl codeList code->"+reCodeList.get(0).getCate_code());
				System.out.println("MkhDaoImpl codeList name->"+reCodeList.get(0).getCate_name());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("MkhDaoImpl codeList Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl codeList END...");
		return reCodeList;
	}
	
	//검색 문서 건수
	@Override
	public int searchDBCount(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchDBCount START...");
		int searchCnt = 0;				
		try {
			//----------------------------------------------------------------------
			searchCnt = session.selectOne("mkhBDListSearchCount", prjBdData);
			//----------------------------------------------------------------------
			System.out.println("MkhDaoImpl searchDBCount searchCnt->"+searchCnt);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchDBCount Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchDBCount END...");
		return searchCnt;
	}
	
	@Override
	public int searchGoodCount(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchGoodCount START...");
		int searchCnt = 0;				
		try {
			//----------------------------------------------------------------------
			searchCnt = session.selectOne("mkhGoodListSearchCount", prjBdData);
			//----------------------------------------------------------------------
			System.out.println("MkhDaoImpl searchGoodCount searchCnt->"+searchCnt);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchGoodCount Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchGoodCount END...");
		return searchCnt;
	}
	
	@Override
	public int searchComtCount(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchComtCount START...");
		int searchCnt = 0;				
		try {
			//----------------------------------------------------------------------
			searchCnt = session.selectOne("mkhComtListSearchCount", prjBdData);
			//----------------------------------------------------------------------
			System.out.println("MkhDaoImpl searchComtCount searchCnt->"+searchCnt);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchComtCount Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchComtCount END...");
		return searchCnt;
	}
	

	@Override
	public List<PrjBdData> searchBDList(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchList START...");
		List<PrjBdData> searchAllList = null;		
		try {
			//----------------------------------------------------------------------
			searchAllList = session.selectList("mkhSearchAllList", prjBdData);
			//----------------------------------------------------------------------
			if(searchAllList != null) {
				System.out.println("MkhDaoImpl searchList prjBdDataList.get(0).getSubject()->"+((PrjBdData) searchAllList.get(0)).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchList Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchList END...");
		return searchAllList;
	}

	@Override
	public List<BdDataGood> searchGoodList(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchList START...");
		List<BdDataGood> searchAllList = null;		
		try {
			//----------------------------------------------------------------------
			searchAllList = session.selectList("mkhSearchGoodList", prjBdData);
			//----------------------------------------------------------------------
			if(searchAllList != null) {
				System.out.println("MkhDaoImpl searchGoodList prjBdDataList.get(0).getSubject()->"+((BdDataGood) searchAllList.get(0)).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchGoodList Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchGoodList END...");
		return searchAllList;
	}

	@Override
	public List<BdDataComt> searchComtList(PrjBdData prjBdData) {
		System.out.println("MkhDaoImpl searchComtList START...");
		List<BdDataComt> searchComtList = null;		
		try {
			//----------------------------------------------------------------------
			searchComtList = session.selectList("mkhSearchComtList", prjBdData);
			//----------------------------------------------------------------------
			if(searchComtList != null) {
				System.out.println("MkhDaoImpl searchComtList.get(0)).getComment_context()->"+((BdDataComt) searchComtList.get(0)).getComment_context());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("MkhDaoImpl searchComtList Exception->"+e.getMessage());
		}
		System.out.println("MkhDaoImpl searchComtList END...");
		return searchComtList;
	}


}
