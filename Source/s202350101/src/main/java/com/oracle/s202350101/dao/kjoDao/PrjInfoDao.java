package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;

import java.util.List;

public interface PrjInfoDao {
    //  모든 프로젝트 조회
    List<PrjInfo> findAll();
    //  강의실 별 프로젝트 조회
    List<PrjInfo> findbyClassId(ClassRoom cr);
}
