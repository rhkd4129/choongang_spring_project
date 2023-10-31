package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.PrjInfoDao;
import com.oracle.s202350101.model.ClassRoom;
import com.oracle.s202350101.model.PrjInfo;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrjInfoServiceImpl implements PrjInfoService {

    private final PrjInfoDao PIdao;

    @Override
    public List<PrjInfo> findAll() {
        log.info("findbyclassuser start");
        List<PrjInfo> PIList = PIdao.findAll();
        return PIList;

    }

    @Override
    public List<PrjInfo> findPrjInfoById(ClassRoom cr) {
        log.info("findbyClassUserProject start");
        List<PrjInfo> PIList = PIdao.findPrjInfoById(cr);
        return PIList;
    }

    @Override
    public List<PrjInfo> findbyClassId(ClassRoom cr) {
        log.info("findbyClassUserProject start");
        List<PrjInfo> PIList = PIdao.findbyClassId(cr);
        return PIList;
    }
}
