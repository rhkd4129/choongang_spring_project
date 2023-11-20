package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import com.oracle.s202350101.controller.KjoController;
import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.KjoResponse;
import com.oracle.s202350101.model.UserInfo;

public interface UserInfoService {

//<!--사용자 ID로 사용자 조회-->
	UserInfo findbyuserId(UserInfo userInfo);
//	특정 강의실 전체 학생 조회
	List<UserInfo> findbyclassuser(UserInfo ui);
//	특정 강의실 내 전체 학생 및 참여 프로젝트 조회
	List<UserInfo> findbyClassUserProject(int clId);
//	학생들 권한 수정
    int auth_modify(KjoRequestDto kjorequest);
//<!--어드민 제외 사용자 정보, 사용자 참여 프로젝트 조회-->
	List<UserInfo> pageUserInfo(UserInfo userInfo);

//<!--어드민 제외 사용자 정보, 사용자 참여 프로젝트 조회-->
	KjoResponse pageUserInfov2(UserInfo userInfo, String currentPage);

//<!--특정 강의실 내 어드민 제외 사용자 조회 & 채팅 사용-->
	List<UserInfo> findbyClassUserAndChatEnv(UserInfo userInfo);


	List<UserInfo> findAllUser();
}
