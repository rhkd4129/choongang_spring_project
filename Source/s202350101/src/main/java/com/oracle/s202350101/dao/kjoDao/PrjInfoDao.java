package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;

import java.util.List;

public interface PrjInfoDao {
    List<PrjInfo> findAll();
    List<PrjInfo> findPrjInfoById(ClassRoom cr);


    List<PrjInfo> findbyClassId(ClassRoom cr);
}
