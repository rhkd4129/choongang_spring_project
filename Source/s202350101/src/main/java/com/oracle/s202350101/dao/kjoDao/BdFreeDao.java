package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.BdFree;

import java.util.List;

public interface BdFreeDao {
//<!--모든 BdFree조회-->
    List<BdFree> findAllBdFree();

//<!--카테고리별 BdFree, 작성자 정보 조회-->
    List<BdFree> findBdFreeByCategory(BdFree bf);

//<!--카테고리별 BdFree, 작성자 정보 조회 및 페이징-->
    List<BdFree> pageBdFreeByCategoryAndPage(BdFree bf);

//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 페이징-->
    List<BdFree> findByCategorySearchAndPage(BdFree bf);

//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 개수-->
    int findByCategorySearch(BdFree bf);

    int del_bdf(List<String> doc_nos);
}
