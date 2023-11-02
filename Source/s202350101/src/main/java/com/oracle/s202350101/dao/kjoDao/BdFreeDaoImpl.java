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
    @Override
    public List<BdFree> findAllBdFree() {
        log.info("findAll start");
        List<BdFree> BFList = null;
        try {
            BFList = session.selectList("findAllBdFree");
            //	결과	출력
            System.out.println(BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            e.printStackTrace();
        }

        return BFList;
    }

    @Override
    public List<BdFree> findBdFreeByCategory(BdFree bf) {
        log.info("findAll start");
        List<BdFree> BFList = null;
        try {
            log.info("ctat" + bf.getBd_category());
            BFList = session.selectList("findBdFreeByCategory",bf);
            //	결과	출력
            System.out.println(BFList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            e.printStackTrace();
        }

        return BFList;
    }
}
