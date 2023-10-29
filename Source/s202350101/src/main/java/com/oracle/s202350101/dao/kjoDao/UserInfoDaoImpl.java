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

	//	특정 강의실 전체 학생 조회
	@Override
	public List<UserInfo> findbyclassuser(int cl_id) {
		log.info("findbyclassuser start");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUser", cl_id);
			//	결과	출력
			System.out.println(UIList.stream().collect(Collectors.toList()));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return UIList;
	}

	//	특정 강의실 내 전체 학생 및 참여 프로젝트 조회
	@Override
	public List<UserInfo> findbyClassUserProject(int cl_id) {
		log.info("findbyClassUserProject start");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUserProject", cl_id);
			System.out.println(UIList.stream().collect(Collectors.toList()));

		}catch (Exception e) {
			e.printStackTrace();
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
			e.printStackTrace();
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
			e.printStackTrace();
		}
		return result;
	}


}
