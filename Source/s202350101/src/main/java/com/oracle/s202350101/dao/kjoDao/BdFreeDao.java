package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.BdFree;

import java.util.List;

public interface BdFreeDao {
    List<BdFree> findAllBdFree();

    List<BdFree> findBdFreeByCategory(BdFree bf);
}
