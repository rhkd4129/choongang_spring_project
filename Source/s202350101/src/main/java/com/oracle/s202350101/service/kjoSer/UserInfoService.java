package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import com.oracle.s202350101.controller.KjoController;
import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.UserInfo;

public interface UserInfoService {

	List<UserInfo> findbyclassuser(int cl_id);

	List<UserInfo> findbyClassUserProject(int clId);

    int auth_modify(KjoRequestDto kjorequest);
}
