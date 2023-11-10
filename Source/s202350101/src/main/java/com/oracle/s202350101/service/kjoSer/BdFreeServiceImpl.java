package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.BdFreeDao;
import com.oracle.s202350101.model.BdFree;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BdFreeServiceImpl implements BdFreeService {

    private final BdFreeDao BFdao;
    //<!--모든 BdFree조회-->
    @Override
    public List<BdFree> findAllBdFree() {
        return BFdao.findAllBdFree();
    }
//<!--카테고리별 BdFree, 작성자 정보 조회-->

    @Override
    public List<BdFree> findBdFreeByCategory(BdFree bf) {
        return BFdao.findBdFreeByCategory(bf);
    }
//<!--카테고리별 BdFree, 작성자 정보 조회 및 페이징-->

    @Override
    public List<BdFree> pageBdFreeByCategoryAndPage(BdFree bf) {
        return BFdao.pageBdFreeByCategoryAndPage(bf);
    }
//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 페이징-->

    @Override
    public List<BdFree> findByCategorySearchAndPage(BdFree bf) {
        return BFdao.findByCategorySearchAndPage(bf);
    }
//<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 개수-->

    @Override
    public int findByCategorySearch(BdFree bf) {
        return BFdao.findByCategorySearch(bf);
    }


}
