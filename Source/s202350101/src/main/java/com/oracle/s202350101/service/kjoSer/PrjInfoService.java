package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;

import java.util.List;

public interface PrjInfoService {
    List<PrjInfo> findAll();
    List<PrjInfo> findPrjInfoById(ClassRoom cr);

    List<PrjInfo> findbyClassId(ClassRoom cr);
}
