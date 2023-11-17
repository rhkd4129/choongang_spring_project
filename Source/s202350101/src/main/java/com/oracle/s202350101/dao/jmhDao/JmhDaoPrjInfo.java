package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.PrjMemList;

public interface JmhDaoPrjInfo {
	List<PrjInfo> selectList(int project_status);
	List<PrjMemList> selectMemList(int project_id);
}
