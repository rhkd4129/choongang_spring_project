package com.oracle.s202350101.dao.kjoDao;

import java.util.List;
import java.util.stream.Collectors;

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
	
	@Override
	public List<UserInfo> findbyclassuser(int cl_id) {
		log.info("findbyclassuser start");
		List<UserInfo> UIList = null;
		try {
			UIList = session.selectList("findbyClassUser", cl_id);
			System.out.println(UIList.stream().collect(Collectors.toList()));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return UIList;
	}

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


}
