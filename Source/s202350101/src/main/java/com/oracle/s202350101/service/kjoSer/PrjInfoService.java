package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;

import java.util.List;

public interface PrjInfoService {
    //  강의실 별 프로젝트 조회
    List<PrjInfo>               findbyClassId(ClassRoom cr);
//  모든 프로젝트 조회
    List<PrjInfo>               findAll();


}
