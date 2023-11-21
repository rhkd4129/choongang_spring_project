package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;

public interface JmhDaoMain {

	List<BdFree> selectMainBdFree(BdFree bdFree);
	List<BdQna> selectMainBdQna(BdQna bdQna);

}
