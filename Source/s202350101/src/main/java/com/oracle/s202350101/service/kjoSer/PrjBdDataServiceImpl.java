package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.PrjBdDataDao;
import com.oracle.s202350101.model.PrjBdData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrjBdDataServiceImpl implements PrjBdDataService {
    private final PrjBdDataDao PBDdao;

    @Override
    public int totalCount() {
        log.info("totalCount start");
        int totalCnt = PBDdao.totalCount();
        log.info("totalCount : {}", totalCnt);
        return totalCnt;
    }

    @Override
    public List<PrjBdData> boardList(PrjBdData prjBdData) {
        log.info("boardList start");
        List<PrjBdData> prjBdDataList = PBDdao.boardList(prjBdData);
        log.info("boardList END");
        return prjBdDataList;
    }
}
