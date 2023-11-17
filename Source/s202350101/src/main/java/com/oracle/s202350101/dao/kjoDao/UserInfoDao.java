package com.oracle.s202350101.dao.kjoDao;

import java.util.List;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.UserInfo;

public interface UserInfoDao {

//<!--사용자 ID로 사용자 조회-->
	UserInfo findbyuserId(UserInfo ui);
//	특정 강의실 전체 학생 조회
	List<UserInfo> findbyclassuser(UserInfo ui);
//	특정 강의실 내 전체 학생 및 참여 프로젝트 조회
	List<UserInfo> findbyClassUserProject(int cl_id);
//	학생들	Manager권한	수정
	int auth_modify_manager(List<String> userManager);
//	학생들	Student권한	수정
	int auth_modify_student(List<String> userStudent);
//<!--어드민 제외 사용자 정보, 사용자 참여 프로젝트 조회-->
    List<UserInfo> pageUserInfo(UserInfo userInfo);

//<!--특정 강의실 내 어드민 제외 사용자 조회 & 채팅 사용-->
	List<UserInfo> findbyClassUserAndChatEnv(UserInfo userInfo);
}
