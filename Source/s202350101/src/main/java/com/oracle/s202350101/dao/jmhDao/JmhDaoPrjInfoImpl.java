package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JmhDaoPrjInfoImpl implements JmhDaoPrjInfo {

	//Mybatis DB 연동
	private final SqlSession session;
	
	@Override
	public List<PrjInfo> selectList(int project_status) {
		
		System.out.println("JmhDaoPrjInfo selectList START...");
		List<PrjInfo> prjInfoList = null;		
		try {
			//-----------------------------------------------------------------------
			prjInfoList = session.selectList("jmhPrjInfoStatusList", project_status);
			//-----------------------------------------------------------------------
			if(prjInfoList != null) {
				System.out.println("JmhDaoPrjInfo selectList prjInfoList.get(0).getProject_name()->"+((PrjInfo) prjInfoList.get(0)).getProject_name());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfo selectList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfo selectList END...");
		return prjInfoList;
	}

	@Override
	public List<PrjMemList> selectMemList(int project_id) {
		
		System.out.println("JmhDaoPrjInfo selectMemList START...");
		List<PrjMemList> prjMemList = null;		
		try {
			//-----------------------------------------------------------------------
			prjMemList = session.selectList("jmhPrjMemList", project_id);
			//-----------------------------------------------------------------------
			if(prjMemList != null) {
				System.out.println("JmhDaoPrjInfo selectMemList prjMemList.get(0).getProject_id()->"+((PrjMemList) prjMemList.get(0)).getProject_id());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfo selectMemList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfo selectList END...");
		return prjMemList;
	}

}
