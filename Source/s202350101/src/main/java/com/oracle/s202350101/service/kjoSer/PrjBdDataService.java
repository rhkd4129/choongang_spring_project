package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.KjoRequestDto;
import com.oracle.s202350101.model.PrjBdData;

import java.util.List;

public interface PrjBdDataService {
    int 			        totalCount();
//<!--pbd 게시글 페이징 조회-->
    List<PrjBdData>         boardList(PrjBdData prjBdData);
//<!--강의실, 프로젝트 별 모든 prj_bd_data-->
    List<PrjBdData>         findByClassProjectId(PrjBdData prjBdData);
//  pbd 게시글 + 추천 + 댓글 삭제
    int                     delpbd(KjoRequestDto kjoRequestDto);
}
