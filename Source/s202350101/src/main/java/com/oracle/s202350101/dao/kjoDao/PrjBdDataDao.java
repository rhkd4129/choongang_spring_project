package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.PrjBdData;

import java.util.List;

public interface PrjBdDataDao {
    int totalCount();

    List<PrjBdData> boardList(PrjBdData prjBdData);

    List<PrjBdData> findByClassProjectId(PrjBdData prjBdData);
}
