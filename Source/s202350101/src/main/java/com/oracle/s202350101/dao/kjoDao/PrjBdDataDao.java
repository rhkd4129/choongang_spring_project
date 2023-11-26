package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.PrjBdData;

import java.util.List;

public interface PrjBdDataDao {
    int             totalCount();
//    pbd 게시글 페이징 조회
    List<PrjBdData> boardList(PrjBdData prjBdData);
//<!--강의실, 프로젝트 별 모든 prj_bd_data-->
    List<PrjBdData> findByClassProjectId(PrjBdData prjBdData);
//  pbd 게시글 댓글 삭제
    int             del_bdpc(PrjBdData pbd);
//  pbd 게시글 추천 삭제
    int             del_bdpg(PrjBdData pbd);
//  pbd 게시글 삭제
    int             delpbd(PrjBdData pbd);
}
