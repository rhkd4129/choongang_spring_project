package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.PrjBdData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PrjBdDataDaoImpl implements PrjBdDataDao{
    private final SqlSession session;
    @Override
    public int totalCount() {
        log.info("totalCount start");
        int totalCnt = 0;
        try {
            totalCnt = session.selectOne("prjBdDataTotal");
            log.info("totalCount : {}", totalCnt);
        } catch (Exception e) {
            log.info("totalCount Exception : {}", e.getMessage());
        }
        log.info("totalCount END");
        return totalCnt;
    }
//    pbd 게시글 페이징 조회
    @Override
    public List<PrjBdData> boardList(PrjBdData prjBdData) {
        log.info("boardList start");
        List<PrjBdData> prjBdDataList = null;
        try {
            prjBdDataList = session.selectList("findByPage", prjBdData);
            if (prjBdDataList != null) {
                log.info("Complete");
            } else {
                log.info("boardList SQL 오류");
            } 
        } catch (Exception e) {
            log.info("boardList Exception : {}",e.getMessage());
        }
        log.info("boardList END");
        return prjBdDataList;
    }
//    <!--강의실, 프로젝트 별 모든 prj_bd_data-->
    @Override
    public List<PrjBdData> findByClassProjectId(PrjBdData prjBdData) {
        log.info("findByClassProjectId start");
        List<PrjBdData> prjBdDataList = null;
        try {
            prjBdDataList = session.selectList("findByClassProjectId", prjBdData);
            if (prjBdDataList != null) {
                log.info("findByClassProjectId Complete");
            } else {
                log.info("findByClassProjectId SQL 오류");
            }
        } catch (Exception e) {
            log.info("findByClassProjectId Exception : {}",e.getMessage());
        }
        log.info("findByClassProjectId END");
        return prjBdDataList;
    }

//  pbd 게시글 삭제
    @Override
    public int delpbd(PrjBdData pbd) {
        log.info("delpbd start");
        int result = 0;
        try {
            result = session.delete("delPrjBdData",pbd);
        } catch (Exception e) {
            log.info("delpbd Exception : {}",e.getMessage());
        }
        log.info("delpbd END");
        return result;
    }
//  pbd 게시글 추천 삭제
    @Override
    public int del_bdpg(PrjBdData pbd) {
        log.info("del_bdpg start");
        int result = 0;
        try {
            result = session.delete("del_bdpg",pbd);
        } catch (Exception e) {
            log.info("del_bdpg Exception : {}",e.getMessage());
        }
        log.info("del_bdpg END");
        return result;
    }
//  pbd 게시글 댓글 삭제
    @Override
    public int del_bdpc(PrjBdData pbd) {
        log.info("del_bdpc start");
        int result = 0;
        try {
            result = session.delete("del_bdpc",pbd);
        } catch (Exception e) {
            log.info("del_bdpc Exception : {}",e.getMessage());
        }
        log.info("del_bdpc END");
        return result;
    }
}
