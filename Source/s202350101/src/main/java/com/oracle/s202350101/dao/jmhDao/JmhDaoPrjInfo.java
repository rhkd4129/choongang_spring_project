package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.Task;
import com.oracle.s202350101.model.UserInfo;

public interface JmhDaoPrjInfo {
	PrjInfo selectOne(int project_id);
	List<PrjInfo> selectList(int project_status);
	List<UserInfo> selectMemList(int project_id);
	List<Task> selectTaskProgress(int project_id);
}
