package com.oracle.s202350101.dao.kjoDao;

import java.util.List;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.UserInfo;

public interface UserInfoDao {

	List<UserInfo> findbyclassuser(int cl_id);
	List<UserInfo> findbyClassUserProject(int cl_id);
	int auth_modify_manager(List<String> userManager);

	int auth_modify_student(List<String> userStudent);
}
