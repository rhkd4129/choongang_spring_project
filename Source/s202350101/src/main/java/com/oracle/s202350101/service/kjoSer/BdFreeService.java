package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.BdFree;

import java.util.List;

public interface BdFreeService {
    List<BdFree> findAllBdFree();
    List<BdFree> findBdFreeByCategory(BdFree bf);


}
