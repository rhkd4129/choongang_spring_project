package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.PrjBdDataDao;
import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.PrjBdData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PrjBdDataServiceImpl implements PrjBdDataService {
    private final PrjBdDataDao PBDdao;
    private final PlatformTransactionManager transactionManager;
    @Override
    public int totalCount() {
        log.info("totalCount start");
        int totalCnt = PBDdao.totalCount();
        log.info("totalCount : {}", totalCnt);
        return totalCnt;
    }

//<!--pbd 게시글 페이징 조회-->
    @Override
    public List<PrjBdData> boardList(PrjBdData prjBdData) {
        log.info("boardList start");
        List<PrjBdData> prjBdDataList = PBDdao.boardList(prjBdData);
        log.info("boardList END");
        return prjBdDataList;
    }

//<!--강의실, 프로젝트 별 모든 prj_bd_data-->
    @Override
    public List<PrjBdData> findByClassProjectId(PrjBdData prjBdData) {
        log.info("findByClassProjectId start");
        List<PrjBdData> prjBdDataList = PBDdao.findByClassProjectId(prjBdData);
        log.info("findByClassProjectId END");
        return prjBdDataList;
    }
    
//pbd 삭제 _ comt, good과 함께
    @Override
    public int delpbd(KjoRequestDto kjoRequestDto) {
//	firList: prj_delbox , secList:doc_delbox

        List<String> pbd_nos = (List<String>) kjoRequestDto.getFirList();
        List<String> doc_nos = (List<String>) kjoRequestDto.getSecList();
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            for (int i = 0; i < doc_nos.size(); i++) {
                PrjBdData pbd = new PrjBdData();
                String pid = pbd_nos.get(i);
                String did = doc_nos.get(i);
                pbd.setProject_id(Integer.parseInt(pid));
                pbd.setDoc_no(Integer.parseInt(did));
                result += PBDdao.del_bdpc(pbd);
                result += PBDdao.del_bdpg(pbd);
                result += PBDdao.delpbd(pbd);
            }
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            log.info("delpbd Error :{}",e.getMessage());
            result = 0;
            transactionManager.rollback(txStatus);
        }
        return result;

    }
}
