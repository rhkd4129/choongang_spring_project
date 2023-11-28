package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.UserInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JmhDaoPrjInfoImpl implements JmhDaoPrjInfo {

	//Mybatis DB 연동
	private final SqlSession session;
	
	@Override
	public PrjInfo selectOne(int project_id) {

		System.out.println("JmhDaoPrjInfoImpl selectList START...");
		PrjInfo prjInfo = null;		
		try {
			//-------------------------------------------------------
			prjInfo = session.selectOne("jmhPrjInfoOne", project_id);
			//-------------------------------------------------------
			if(prjInfo != null) {
				System.out.println("JmhDaoPrjInfoImpl selectOne prjInfo.get(0).getProject_name()->"+prjInfo.getProject_name());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfoImpl selectOne Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfoImpl selectOne END...");
		return prjInfo;
	}

	@Override
	public List<PrjInfo> selectList(int project_status) {
		
		System.out.println("JmhDaoPrjInfoImpl selectList START...");
		List<PrjInfo> prjInfoList = null;		
		try {
			//-----------------------------------------------------------------------
			prjInfoList = session.selectList("jmhPrjInfoStatusList", project_status);
			//-----------------------------------------------------------------------
			if(prjInfoList != null) {
				System.out.println("JmhDaoPrjInfoImpl selectList prjInfoList.get(0).getProject_name()->"+((PrjInfo) prjInfoList.get(0)).getProject_name());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfoImpl selectList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfoImpl selectList END...");
		return prjInfoList;
	}

	@Override
	public List<UserInfo> selectMemList(int project_id) {
		
		System.out.println("JmhDaoPrjInfoImpl selectMemList START...");
		List<UserInfo> prjMemList = null;		
		try {
			//-----------------------------------------------------------------------
			prjMemList = session.selectList("jmhPrjMemList", project_id);
			//-----------------------------------------------------------------------
			if(prjMemList != null) {
				System.out.println("JmhDaoPrjInfoImpl selectMemList prjMemList.get(0).getProject_id()->"+((UserInfo) prjMemList.get(0)).getProject_id());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfoImpl selectMemList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfoImpl selectList END...");
		return prjMemList;
	}

	@Override
	public List<Task> selectTaskProgress(int project_id) {

		System.out.println("JmhDaoPrjInfoImpl selectTaskProgress START...");
		List<Task> prjTaskProgressList = null;		
		try {
			//-----------------------------------------------------------------------
			prjTaskProgressList = session.selectList("jmhTaskProgress", project_id);
			//-----------------------------------------------------------------------
			if(prjTaskProgressList != null) {
				System.out.println("JmhDaoPrjInfoImpl selectTaskProgress prjTaskProgressList.get(0).getProject_id()->"+((Task) prjTaskProgressList.get(0)).getProject_id());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjInfoImpl selectTaskProgress Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjInfoImpl selectTaskProgress END...");
		return prjTaskProgressList;

	}

}
