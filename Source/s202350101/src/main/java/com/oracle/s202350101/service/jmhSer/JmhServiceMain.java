package com.oracle.s202350101.service.jmhSer;

import java.util.List;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdQna;

public interface JmhServiceMain {

	List<BdFree> selectMainBdFree(BdFree bdFree);
	List<BdQna> selectMainBdQna(BdQna bdQna);

}
