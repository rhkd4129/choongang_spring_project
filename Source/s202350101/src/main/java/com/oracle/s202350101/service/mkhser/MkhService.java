package com.oracle.s202350101.service.mkhser;

import java.util.List;
import java.util.Map;

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

public interface MkhService {

	int                insertUserInfo(UserInfo userInfo);
	List<ClassRoom>    createdClass();
	UserInfo           userLoginCheck(UserInfo userInfo);
	List<BdQna>        bdQnaList(UserInfo userInfo);
	List<BdFree> 	   bdFreeList(UserInfo userInfo);
	List<PrjBdData>    prjDataList(UserInfo userInfo);
	List<PrjBdRep>     prjRepList(UserInfo userInfo);
	UserInfo           confirm(String user_id);
	int                totalBDcount(UserInfo userInfo);
	int 			   totalQna(UserInfo userInfo);
	int 			   totalFree(UserInfo userInfo);
	int 			   totalDtPj(UserInfo userInfo);
	int 			   totalRepPj(UserInfo userInfo);
	int 			   updatePw(Map<String, String> map);
	UserInfo           userFindId(UserInfo userInfo);
	UserEnv            selectEnv(String user_id);
	ClassRoom          selectClass(String user_id);
	int                updateUser(UserInfo userInfo);
	List<BdQna> 	   qnaGood(UserInfo userInfoDTO);
	List<BdFree> 	   freeGood(UserInfo userInfoDTO);
	List<PrjBdData>    prjDataGood(UserInfo userInfoDTO);
	List<BdFreeComt>   freeComt(UserInfo userInfoDTO);
	List<BdDataComt>   dataComt(UserInfo userInfoDTO);
	List<BdRepComt>    repComt(UserInfo userInfoDTO);

}
