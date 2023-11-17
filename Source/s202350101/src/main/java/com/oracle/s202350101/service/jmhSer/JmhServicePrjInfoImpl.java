package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.s202350101.dao.jmhDao.JmhDaoPrjInfoImpl;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor  //생성자 없어도 되고 final변수 자동 인스턴스화
@Transactional
public class JmhServicePrjInfoImpl implements JmhServicePrjInfo {

	private final JmhDaoPrjInfoImpl jmhPrjInfoDao;
	
	@Override
	public List<PrjInfo> selectList(int project_status) {
	
		System.out.println("JmhServiceImpl selectList START...");
		
		List<PrjInfo> prjInfoList = null;
		
		//-----------------------------------------------------
		prjInfoList = jmhPrjInfoDao.selectList(project_status);
		//-----------------------------------------------------
		
		System.out.println("JmhServiceImpl selectList END...");
		return prjInfoList;
	}

	@Override
	public List<PrjMemList> selectMemList(int project_id) {
		
		System.out.println("JmhServiceImpl selectMemList START...");
		
		List<PrjMemList> prjMemList = null;
		
		//-----------------------------------------------------
		prjMemList = jmhPrjInfoDao.selectMemList(project_id);
		//-----------------------------------------------------
		
		System.out.println("JmhServiceImpl selectMemList END...");
		return prjMemList;
	}

}
