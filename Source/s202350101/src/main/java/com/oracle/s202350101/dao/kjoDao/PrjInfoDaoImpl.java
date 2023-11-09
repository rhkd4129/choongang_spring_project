package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PrjInfoDaoImpl implements PrjInfoDao{

    private final SqlSession session;

    //  모든 프로젝트 조회
    @Override
    public List<PrjInfo> findAll() {
        log.info("findAll start");
        List<PrjInfo> PIList = null;
        try {
            PIList = session.selectList("findAllPrjInfo");
            //	결과	출력
            System.out.println(PIList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            e.printStackTrace();
        }

        return PIList;
    }
    //  강의실 별 프로젝트 조회
    @Override
    public List<PrjInfo> findbyClassId(ClassRoom cr) {
        log.info("findbyClassId start");
        List<PrjInfo> PIList = null;
        try {
            PIList = session.selectList("findbyClassIdPrjInfo", cr);
//            System.out.println(PIList.stream().collect(Collectors.toList()));

        }catch (Exception e) {
            e.printStackTrace();
        }

        return PIList;
    }
}
