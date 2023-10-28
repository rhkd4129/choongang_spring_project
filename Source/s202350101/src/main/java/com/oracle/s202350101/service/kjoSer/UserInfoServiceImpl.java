package com.oracle.s202350101.service.kjoSer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oracle.s202350101.dao.kjoDao.UserInfoDao;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserInfoServiceImpl implements UserInfoService{

	private final UserInfoDao UIdao;

	@Override
	public List<UserInfo> findbyclassuser(int cl_id) {
		log.info("findbyclassuser start");
		List<UserInfo> UIList = UIdao.findbyclassuser(cl_id);
		return UIList;
	}

	@Override
	public List<UserInfo> findbyClassUserProject(int cl_Id) {
		log.info("findbyClassUserProject start");
		List<UserInfo> UIList = UIdao.findbyClassUserProject(cl_Id);
		return UIList;

	}

}
