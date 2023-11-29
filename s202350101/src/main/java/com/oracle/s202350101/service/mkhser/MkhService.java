package com.oracle.s202350101.service.mkhser;

import java.util.List;
import java.util.Map;

import com.oracle.s202350101.model.BdDataComt;
import com.oracle.s202350101.model.BdDataGood;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;
import com.oracle.s202350101.model.UserEnv;
import com.oracle.s202350101.model.UserInfo;

public interface MkhService {

	int                insertUserInfo(UserInfo userInfo);
	List<ClassRoom>    createdClass();
	UserInfo           userLoginCheck(UserInfo userInfo);
	UserInfo           confirm(String user_id);
	int                totalBDcount(PrjBdData prjBdData);
	int 			   updatePw(Map<String, String> map);
	UserInfo           userFindId(UserInfo userInfo);
	UserEnv            selectEnv(String user_id);
	ClassRoom          selectClass(String user_id);
	int                updateUser(UserInfo userInfo);
	int                updateEnv(UserEnv userEnv);
	List<PrjBdData>    bdSelectAll(PrjBdData prjBdData);
	int                totalComt(PrjBdData prjBdData);
	List<BdDataComt>   selectAllComt(PrjBdData prjBdData);
	int                totalGood(PrjBdData prjBdData);
	List<BdDataGood>   selectAllGood(PrjBdData prjBdData);
	List<Code>         codeList(Code code);
	
}
