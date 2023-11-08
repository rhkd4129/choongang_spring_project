package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import com.oracle.s202350101.controller.KjoController;
import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.UserInfo;

public interface UserInfoService {

	UserInfo findbyuserId(UserInfo userInfo);
	List<UserInfo> findbyclassuser(UserInfo ui);

	List<UserInfo> findbyClassUserProject(int clId);

    int auth_modify(KjoRequestDto kjorequest);

	List<UserInfo> pageUserInfo(UserInfo userInfo);
}
