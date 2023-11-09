package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.PrjInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class BdFreeDaoImpl implements BdFreeDao {

    private final SqlSession session;
//<!--모든 BdFree조회-->
    @Override
    public List<BdFree> findAllBdFree() {
        log.info("findAllBdFree start");
        List<BdFree> BFList = null;
        try {
            BFList = session.selectList("findAllBdFree");
            //	결과	출력
            System.out.println(BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            System.out.println("findAllBdFree Error -->>" + e.getMessage());
        }

        return BFList;
    }

    //<!--카테고리별 BdFree, 작성자 정보 조회-->
    @Override
    public List<BdFree> findBdFreeByCategory(BdFree bf) {
        log.info("findBdFreeByCategory start");
        List<BdFree> BFList = null;
        try {
            log.info("findBdFreeByCategory" + bf.getBd_category());
            BFList = session.selectList("findBdFreeByCategory",bf);
            //	결과	출력
            System.out.println(BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            System.out.println("findBdFreeByCategory Error -->>" + e.getMessage());
        }

        return BFList;
    }

    //<!--카테고리별 BdFree, 작성자 정보 조회 및 페이징-->
    @Override
    public List<BdFree> pageBdFreeByCategoryAndPage(BdFree bf) {
        log.info("pageBdFreeByCategoryAndPage start");
        List<BdFree> BFList = null;
        try {
            log.info("pageBdFreeByCategoryAndPage" + bf.getBd_category());
            BFList = session.selectList("pageBdFreeByCategoryAndPage",bf);
            //	결과	출력
            System.out.println("pageBdFreeByCategoryAndPage Result: "+BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            System.out.println("pageBdFreeByCategoryAndPage Error -->>" + e.getMessage());
        }

        return BFList;

    }

    //<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 페이징-->
    @Override
    public List<BdFree> findByCategorySearchAndPage(BdFree bf) {

        log.info("findByCategorySearchAndPage start");
        List<BdFree> BFList = null;
        try {
            log.info("findByCategorySearchAndPage" + bf.getBd_category());
            BFList = session.selectList("findByCategorySearchAndPage",bf);
            //	결과	출력
            System.out.println("findByCategorySearchAndPage Result: "+BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            System.out.println("findByCategorySearchAndPage Error -->>" + e.getMessage());
        }

        return BFList;
    }

    //<!--카테고리별 BdFree, 작성자 정보 조회 및 검색 개수-->
    @Override
    public int findByCategorySearch(BdFree bf) {

        log.info("findByCategorySearch start");
        int BFList = 0;
        try {
            log.info("findByCategorySearch" + bf.getBd_category());
            BFList = session.selectOne("findByCategorySearch",bf);
        }catch (Exception e) {
            System.out.println("findByCategorySearch Error -->>" + e.getMessage());
        }

        return BFList;

    }
}
