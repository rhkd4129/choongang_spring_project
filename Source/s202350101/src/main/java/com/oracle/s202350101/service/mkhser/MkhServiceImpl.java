package com.oracle.s202350101.service.mkhser;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.mkhDao.MkhDao;
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

@Service
@RequiredArgsConstructor
public class MkhServiceImpl implements MkhService {
	
	private final MkhDao	mkhdao;
//--------------------------------------------------------------------------------------	
	@Override
	public UserInfo userLoginCheck(UserInfo userInfoDTO) {
		System.out.println("MkhServiceImpl userLoginCheck Start..");
		//-------------------------------------------------
		UserInfo userInfo = mkhdao.userLoginCheck(userInfoDTO);
		//-------------------------------------------------
		return userInfo;
	}
//--------------------------------------------------------------------------------------
	@Override
	public List<ClassRoom> createdClass() {
		List<ClassRoom> classList = null;
		System.out.println("MkhServiceImpl createdClass Start...");
		//-------------------------------------------------
		classList = mkhdao.createdClass();
		//-------------------------------------------------
		System.out.println("MkhServiceImpl classList.size()->" +classList.size());
		
		return classList;
	}
//--------------------------------------------------------------------------------------	
	@Override
	public UserInfo confirm(String user_id) {
		System.out.println("MkhServiceImpl confirm Start...");
		//-------------------------------------------------
		UserInfo userInfo = mkhdao.confirm(user_id);
		//-------------------------------------------------
		
		return userInfo;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int insertUserInfo(UserInfo userInfo) {
		int result = 0;
		System.out.println("MkhServiceImpl insert Start...");
		//-------------------------------------------------
		result = mkhdao.insertUserInfo(userInfo);
		//-------------------------------------------------
		return result;
	}
//--------------------------------------------------------------------------------------
	@Override
	public UserEnv selectEnv(String user_id) {
		System.out.println("MkhServiceImpl selectEnv Start...");
		//-------------------------------------------------
		UserEnv userEnv = mkhdao.selectEnv(user_id);
		//-------------------------------------------------
		return userEnv;
	}
//--------------------------------------------------------------------------------------
	@Override
	public ClassRoom selectClass(String user_id) {
		System.out.println("MkhServiceImpl selectClass Start...");
		//-------------------------------------------------
		ClassRoom classRoom = mkhdao.selectClass(user_id);
		//-------------------------------------------------	
		return classRoom;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int updateUser(UserInfo userInfo) {
		System.out.println("MkhServiceImpl updateUser Start...");
		//-------------------------------------------------	
		int result = mkhdao.updateUser(userInfo);
		//-------------------------------------------------	
		return result;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int updateEnv(UserEnv userEnv) {
		System.out.println("MkhServiceImpl updateEnv Start...");
		//-------------------------------------------------	
		int result = mkhdao.updateEnv(userEnv);
		//-------------------------------------------------	
		return result;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int totalBDcount(PrjBdData prjBdData) {
		System.out.println("MkhServiceImpl totalBDcount Start...");
		int totalCnt = 0;
		
		if(prjBdData.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdData.getSearch());
			if(!prjBdData.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdData.getKeyword());
				//검색 건수 가져오기
				//------------------------------------------
				totalCnt = mkhdao.searchDBCount(prjBdData);
				//------------------------------------------
				System.out.println("MkhServiceImpl totalBDcount totalCnt->" + totalCnt);
				System.out.println("MkhServiceImpl totalBDcount END...");
				return totalCnt;
			}
		}
		//------------------------------------------
		totalCnt = mkhdao.totalBDcount(prjBdData);
		//------------------------------------------

		System.out.println("MkhServiceImpl totalBDcount totalCnt->" + totalCnt);
		System.out.println("MkhServiceImpl totalBDcount END...");
		

		return totalCnt;
	}
//--------------------------------------------------------------------------------------	
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("MkhServiceImpl codeList START...");
		List<Code> reCodeList = null;
		//-------------------------------------
		reCodeList = mkhdao.codeList(code);
		//-------------------------------------
		System.out.println("MkhServiceImpl codeList END...");
		return reCodeList;
	}
//--------------------------------------------------------------------------------------
	@Override
	public List<PrjBdData> bdSelectAll(PrjBdData prjBdData) {
		List<PrjBdData> selectAll = null;
		System.out.println("MkhServiceImpl bdSelectAll Start...");
		if(prjBdData.getKeyword() != null) {
			if(!prjBdData.getKeyword().equals("")) {
				//-----------------------------------------------
				selectAll = mkhdao.searchBDList(prjBdData);
				//-----------------------------------------------
				System.out.println("MkhServiceImpl boardList > searchList END...");
				return selectAll;
			}
		}
		selectAll = mkhdao.bdSelectAll(prjBdData);
		System.out.println("MkhServiceImpl bdSelectAll.size()->" +selectAll.size());
		
		return selectAll;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int totalComt(PrjBdData prjBdData) {
		System.out.println("MkhServiceImpl totalComt Start...");
		int totalComt = 0;
		
		if(prjBdData.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdData.getSearch());
			if(!prjBdData.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdData.getKeyword());
				//댓글 검색 건수 가져오기
				//------------------------------------------
				totalComt = mkhdao.searchComtCount(prjBdData);
				//------------------------------------------
				System.out.println("MkhServiceImpl searchComtCount ->" + totalComt);
				System.out.println("MkhServiceImpl searchComtCount END...");
				return totalComt;
			}
		}
		//------------------------------------------
		totalComt = mkhdao.totalComt(prjBdData);
		//------------------------------------------

		System.out.println("MkhServiceImpl totalCount totalCnt->" + totalComt);
		System.out.println("MkhServiceImpl totalCount END...");
		
		return totalComt;
	}
//--------------------------------------------------------------------------------------
	@Override
	public List<BdDataComt> selectAllComt(PrjBdData prjBdData) {
		
		List<BdDataComt> selectAllComt = null;
		System.out.println("MkhServiceImpl selectAllComt Start...");
		if(prjBdData.getKeyword() != null) {
			if(!prjBdData.getKeyword().equals("")) {
				//-----------------------------------------------
				selectAllComt = mkhdao.searchComtList(prjBdData);
				//-----------------------------------------------
				System.out.println("MkhServiceImpl boardList > searchList END...");
				return selectAllComt;
			}
		}
		//-----------------------------------------------
		selectAllComt = mkhdao.selectAllComt(prjBdData);
		//-----------------------------------------------
		System.out.println("MkhServiceImpl bdSelectAll.size()->" +selectAllComt.size());
		
		return selectAllComt;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int totalGood(PrjBdData prjBdData) {
		System.out.println("MkhServiceImpl totalGood Start...");
		int totalGood = 0;
		
		if(prjBdData.getKeyword() != null) {
			System.out.println("★검색 Search---->"+prjBdData.getSearch());
			if(!prjBdData.getKeyword().equals("")) {
				System.out.println("★검색 SearchKeyword---->"+prjBdData.getKeyword());
				// 내가 추천한 게시글 검색 건수 가져오기
				//------------------------------------------
				totalGood = mkhdao.searchGoodCount(prjBdData);
				//------------------------------------------
				System.out.println("MkhServiceImpl searchGoodCount ->" + totalGood);
				System.out.println("MkhServiceImpl searchGoodCount END...");
				return totalGood;
			}
		}
		//------------------------------------------
		totalGood = mkhdao.totalGood(prjBdData);
		//------------------------------------------
		System.out.println("MkhServiceImpl totalCount totalCnt->" + totalGood);
		System.out.println("MkhServiceImpl totalCount END...");

		return totalGood;
	}
//--------------------------------------------------------------------------------------
	@Override
	public List<BdDataGood> selectAllGood(PrjBdData prjBdData) {
		List<BdDataGood> selectAllGood = null;
		System.out.println("MkhServiceImpl selectAllGood Start...");
		if(prjBdData.getKeyword() != null) {
			if(!prjBdData.getKeyword().equals("")) {
				//------------------------------------------
				selectAllGood = mkhdao.searchGoodList(prjBdData);
				//------------------------------------------
				return selectAllGood;
			}
		}
		//------------------------------------------
		selectAllGood = mkhdao.selectAllGood(prjBdData);
		//------------------------------------------
		System.out.println("MkhServiceImpl selectAllGood.size()->" +selectAllGood.size());
		
		return selectAllGood;
	}	
//--------------------------------------------------------------------------------------
	@Override
	public UserInfo userFindId(UserInfo userInfo) {
		System.out.println("MkhServiceImpl userFindId Start...");
		//------------------------------------------
		UserInfo userInfoDto = mkhdao.userFindId(userInfo);
		//------------------------------------------
		return userInfoDto;
	}
//--------------------------------------------------------------------------------------
	@Override
	public int updatePw(Map<String, String> map) {
		System.out.println("MkhServiceImpl updatePw Start...");
		//------------------------------------------
		int result = mkhdao.updatePw(map);
		//------------------------------------------

		return result;
	}
//--------------------------------------------------------------------------------------
	
}
