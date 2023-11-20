package com.oracle.s202350101.dao.kjoDao;

import java.util.List;
import java.util.stream.Collectors;

import com.oracle.s202350101.model.KjoRequestDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
@RequiredArgsConstructor
public class UserInfoDaoImpl implements UserInfoDao{

	private final SqlSession session;

//<!--사용자 ID로 사용자 조회-->
	@Override
	public UserInfo findbyuserId(UserInfo ui) {
		log.info("findbyuserId start ID : " + ui.getUser_id());
		try {
			ui = session.selectOne("findbyuserId", ui);
		} catch (Exception e) {
			log.info("findbyuserId Error : " + e.getMessage());
		}
		return ui;
	}

	//	특정 강의실 전체 학생 조회
	@Override
	public List<UserInfo> findbyclassuser(UserInfo ui) {
		log.info("findbyclassuser START");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUser", ui);
//			System.out.println(UIList.stream().collect(Collectors.toList()));

		}catch (Exception e) {
			log.info("findbyclassuser ERROR : {}",e.getMessage());
		}
		
		return UIList;
	}

	//	특정 강의실 내 전체 학생 및 참여 프로젝트 조회
	@Override
	public List<UserInfo> findbyClassUserProject(int cl_id) {
		log.info("findbyClassUserProject START");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUserProject", cl_id);
//			System.out.println(UIList.stream().collect(Collectors.toList()));

		}catch (Exception e) {
			log.info("findbyClassUserProject ERROR : {}",e.getMessage());
		}
		return UIList;
	}


	//	학생들	Manager권한	수정
	@Override
	public int auth_modify_manager(List<String> userManager) {
		log.info("auth_modify_manager start");
		int result = 0;
		try {
			result= session.update("auth_modify_manager", userManager);

		}catch (Exception e) {
			log.info("auth_modify_manager ERROR : {}",e.getMessage());
		}
		return result;

	}

	//	학생들	Student권한	수정
	@Override
	public int auth_modify_student(List<String> userStudent) {
		log.info("auth_modify_student start");
		int result = 0;
		try {
			result= session.update("auth_modify_student", userStudent);

		}catch (Exception e) {
			log.info("auth_modify_student ERROR : {}",e.getMessage());
		}
		return result;
	}

//<!--어드민 제외 사용자 정보, 사용자 참여 프로젝트 조회-->
	@Override
	public List<UserInfo> pageUserInfo(UserInfo userInfo) {

		log.info("pageUserInfo start");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("pageUserInfo", userInfo);
//			System.out.println(UIList.stream().collect(Collectors.toList()));

		}catch (Exception e) {
			log.info("pageUserInfo ERROR : {}",e.getMessage());
		}
		return UIList;
	}
//<!--특정 강의실 내 어드민 제외 사용자 조회 & 채팅 사용-->
	@Override
	public List<UserInfo> findbyClassUserAndChatEnv(UserInfo ui) {
		log.info("findbyClassUserAndChatEnv start");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUserAndChatEnv", ui);
//			System.out.println(UIList.stream().collect(Collectors.toList()));

		}catch (Exception e) {
			log.info("findbyClassUserAndChatEnv ERROR : {}",e.getMessage());
		}

		return UIList;
	}
}
