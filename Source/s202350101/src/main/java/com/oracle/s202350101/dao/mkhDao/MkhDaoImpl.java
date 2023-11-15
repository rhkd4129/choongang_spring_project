package com.oracle.s202350101.dao.mkhDao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.ClassRoom;
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
	public int totalBDcount(UserInfo userInfo) {
		int totalBDCount = 0;
		System.out.println("MkhDaoImpl totalBDcount start...");
		try {
			totalBDCount = session.selectOne("totalBDListCount", userInfo);
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
		
//		String user_id = prjBdData.getUser_id();
//		int start = prjBdData.getStart();
//		int end = prjBdData.getEnd();
//		
//		System.out.println(user_id);
//		System.out.println(start);
//		System.out.println(end);
		
		try {
			selectAll = session.selectList("mkh_bdSelectAll", prjBdData);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl bdSelectAll Exception->" +e.getMessage());
		}
		return selectAll;
	}
	
	@Override
	public List<BdQna> bdQnaList(UserInfo userInfo) {
		List<BdQna> qnaList = null;
		System.out.println("MkhDaoImpl bdQnaList start...");
		try {
			qnaList = session.selectList("QnaListSelect", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl bdQnaList Exception->" +e.getMessage());
		}
		return qnaList;
	}

	@Override
	public List<BdFree> bdFreeList(UserInfo userInfo) {
		List<BdFree> freeList = null;
		System.out.println("MkhDaoImpl bdFreeList start...");
		try {
			freeList = session.selectList("FreeListSelect", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl bdFreeList Exception->" +e.getMessage());
		}
		return freeList;
	}

	@Override
	public List<PrjBdData> prjDataList(UserInfo userInfo) {
		List<PrjBdData> dataPrjList = null;
		System.out.println("MkhDaoImpl PrjDataList start...");
		try {
			dataPrjList = session.selectList("DataListSelect", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl PrjDataList Exception->" +e.getMessage());
		}
		return dataPrjList;
	}

	@Override
	public List<PrjBdRep> prjRepList(UserInfo userInfo) {
		List<PrjBdRep> RepPrjList = null;
		System.out.println("MkhDaoImpl PrjRepList start...");
		try {
			RepPrjList = session.selectList("RepListSelect", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl PrjRepList Exception->" +e.getMessage());
		}
		return RepPrjList;
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
	public int totalQna(UserInfo userInfo) {
		int totalBdQna = 0;
		System.out.println("MkhDaoImpl totalQna start...");
		try {
			totalBdQna = session.selectOne("totalQnaCount", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalQna Exception->" +e.getMessage());
		}

		return totalBdQna;
	}

	@Override
	public int totalFree(UserInfo userInfo) {
		int totalBdFree = 0;
		System.out.println("MkhDaoImpl totalFree start...");
		try {
			totalBdFree = session.selectOne("totalFreeCount", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalFree Exception->" +e.getMessage());
		}

		return totalBdFree;
	}

	@Override
	public int totalDtPj(UserInfo userInfo) {
		int totalDtPrj = 0;
		System.out.println("MkhDaoImpl totalDtPj start...");
		try {
			totalDtPrj = session.selectOne("totalDtPjCount", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalDtPj Exception->" +e.getMessage());
		}

		return totalDtPrj;
	}

	@Override
	public int totalRepPj(UserInfo userInfo) {
		int totalRepPrj = 0;
		System.out.println("MkhDaoImpl totalRepPj start...");
		try {
			totalRepPrj = session.selectOne("totalRepPjCount", userInfo);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl totalRepPj Exception->" +e.getMessage());
		}

		return totalRepPrj;
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
	public List<BdQna> qnaGood(UserInfo userInfoDTO) {
		List<BdQna> qnaGood = null;
		System.out.println("MkhDaoImpl qnaGood start...");
		try {
			qnaGood = session.selectList("qnaGoodSelect", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl qnaGood Exception->" +e.getMessage());
		}
		return qnaGood;
	}

	@Override
	public List<BdFree> freeGood(UserInfo userInfoDTO) {
		List<BdFree> freeGood = null;
		System.out.println("MkhDaoImpl freeGood start...");
		try {
			freeGood = session.selectList("freeGoodSelect", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl freeGood Exception->" +e.getMessage());
		}
		return freeGood;
	}

	@Override
	public List<PrjBdData> prjDataGood(UserInfo userInfoDTO) {
		List<PrjBdData> prjDataGood = null;
		System.out.println("MkhDaoImpl prjDataGood start...");
		try {
			prjDataGood = session.selectList("prjDataGoodSelect", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl prjDataGood Exception->" +e.getMessage());
		}
		return prjDataGood;
	}

	@Override
	public List<BdFreeComt> freeComt(UserInfo userInfoDTO) {
		List<BdFreeComt> freeComt = null;
		System.out.println("MkhDaoImpl freeComt start...");
		try {
			freeComt = session.selectList("mkhFreeComt", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl freeComt Exception->" +e.getMessage());
		}
		return freeComt;
	}

	@Override
	public List<BdDataComt> dataComt(UserInfo userInfoDTO) {
		List<BdDataComt> dataComt = null;
		System.out.println("MkhDaoImpl dataComt start...");
		try {
			dataComt = session.selectList("mkhDataComt", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl dataComt Exception->" +e.getMessage());
		}
		return dataComt;
	}

	@Override
	public List<BdRepComt> repComt(UserInfo userInfoDTO) {
		List<BdRepComt> repComt = null;
		System.out.println("MkhDaoImpl repComt start...");
		try {
			repComt = session.selectList("mkhRepComt", userInfoDTO);
		} catch (Exception e) {
			System.out.println("MkhDaoImpl repComt Exception->" +e.getMessage());
		}
		return repComt;
	}

	@Override
	public int updateEnv(UserEnv userEnv) {
		int result = 0;
		System.out.println("MkhDaoImpl updateEnv start...");
		try {
			result = session.update("mkh_updateEnv", userEnv);
			
		} catch (Exception e) {
			System.out.println("MkhDaoImpl updateEnv Exception->" +e.getMessage());
		}
		return result;
	}

}
