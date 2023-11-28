package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoPrjInfo;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  //생성자 없어도 되고 final변수 자동 인스턴스화
@Transactional
public class JmhServicePrjInfoImpl implements JmhServicePrjInfo {

	private final JmhDaoPrjInfo jmhPrjInfoDao;

	@Override
	public PrjInfo selectOne(int project_id) {

		System.out.println("JmhServicePrjInfoImpl selectOne START...");
		
		PrjInfo prjInfo = null;
		
		//-----------------------------------------------------
		prjInfo = jmhPrjInfoDao.selectOne(project_id);
		//-----------------------------------------------------
		
		System.out.println("JmhServicePrjInfoImpl selectOne END...");
		return prjInfo;
	}

	@Override
	public List<PrjInfo> selectList(int project_status) {
	
		System.out.println("JmhServicePrjInfoImpl selectList START...");
		
		List<PrjInfo> prjInfoList = null;
		
		//-----------------------------------------------------
		prjInfoList = jmhPrjInfoDao.selectList(project_status);
		//-----------------------------------------------------
		
		System.out.println("JmhServicePrjInfoImpl selectList END...");
		return prjInfoList;
	}

	@Override
	public List<UserInfo> selectMemList(int project_id) {
		
		System.out.println("JmhServicePrjInfoImpl selectMemList START...");
		
		List<UserInfo> prjMemList = null;
		
		//-----------------------------------------------------
		prjMemList = jmhPrjInfoDao.selectMemList(project_id);
		//-----------------------------------------------------
		
		System.out.println("JmhServicePrjInfoImpl selectMemList END...");
		return prjMemList;
	}

	@Override
	public List<Task> selectTaskProgress(int project_id) {

		System.out.println("JmhServicePrjInfoImpl selectTaskProgress START...");
		
		List<Task> prjTaskProgressList = null;
		
		//-----------------------------------------------------------------
		prjTaskProgressList = jmhPrjInfoDao.selectTaskProgress(project_id);
		//-----------------------------------------------------------------
		
		System.out.println("JmhServicePrjInfoImpl selectTaskProgress END...");
		return prjTaskProgressList;
	}


}
