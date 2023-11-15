package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.PrjBdData;

import java.util.List;

public interface PrjBdDataService {
    int 			totalCount();
    List<PrjBdData> boardList(PrjBdData prjBdData);

    List<PrjBdData> findByClassProjectId(PrjBdData prjBdData);
}
