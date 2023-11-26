package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.KjoRequestDto;

import java.util.List;

public interface BdFreeService {
//<!--카테고리별 BdFree, 작성자 정보 조회-->
    List<BdFree> findBdFreeByCategory(BdFree bf);
//<!--카테고리별 BdFree, 작성자 정보 조회 및 페이징-->
    List<BdFree> pageBdFreeByCategoryAndPage(BdFree bf);
//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 개수-->
    int findByCategorySearch(BdFree bf);
//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 페이징-->
    List<BdFree> findByCategorySearchAndPage(BdFree bf);
//<!--게시글 id로 삭제-->
    int del_bdf(KjoRequestDto kjorequest);
//<!--모든 BdFree조회-->
    List<BdFree> findAllBdFree();

}
