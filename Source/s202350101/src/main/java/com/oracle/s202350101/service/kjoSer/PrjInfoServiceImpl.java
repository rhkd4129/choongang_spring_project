package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.PrjInfoDao;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PrjInfoServiceImpl implements PrjInfoService {
    private final PrjInfoDao PIdao;
    @Override
    public List<PrjInfo> findbyClassId(ClassRoom cr) {
//  강의실 별 프로젝트 조회
        log.info("findbyClassUserProject start");
        List<PrjInfo> PIList = PIdao.findbyClassId(cr);
        return PIList;
    }
    @Override
    public List<PrjInfo> findAll() {
//  모든 프로젝트 조회
        log.info("findbyclassuser start");
        List<PrjInfo> PIList = PIdao.findAll();
        return PIList;

    }

}
