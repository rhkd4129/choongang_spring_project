package com.oracle.s202350101.dao.jmhDao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdRepComt;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdRep;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JmhDaoPrjBdRepImpl implements JmhDaoPrjBdRep {

	//Mybatis DB 연동
	private final SqlSession session;

	//총건수
	@Override
	public int totalCount(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl totalCount START...");
		int totalCnt = 0;				
		try {
			//------------------------------------------------------------------
			totalCnt = session.selectOne("jmhPrjBdRepListTotalCount", prjBdRep);
			//------------------------------------------------------------------
			System.out.println("JmhDaoPrjBdRepImpl totalCount totalCnt->"+totalCnt);
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl totalCount Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl totalCount END...");
		return totalCnt;
	}

	//검색 문서 건수
	@Override
	public int searchCount(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl searchCount START...");
		int searchCnt = 0;				
		try {
			//----------------------------------------------------------------------
			searchCnt = session.selectOne("jmhPrjBdRepListSearchCount", prjBdRep);
			//----------------------------------------------------------------------
			System.out.println("JmhDaoPrjBdRepImpl searchCount searchCnt->"+searchCnt);
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl searchCount Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl searchCount END...");
		return searchCnt;
	}

	//목록
	@Override
	public List<PrjBdRep> boardList(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl boardList START...");
		List<PrjBdRep> prjBdRepList = null;		
		try {
			//----------------------------------------------------------------
			prjBdRepList = session.selectList("jmhPrjBdRepList", prjBdRep);
			//----------------------------------------------------------------
			System.out.println("JmhDaoPrjBdRepImpl boardList prjBdRepList.get(0).getSubject()->"+((PrjBdRep) prjBdRepList.get(0)).getSubject());
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl boardList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl boardList END...");
		return prjBdRepList;
	}

	//검색
	@Override
	public List<PrjBdRep> searchList(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl searchList START...");
		List<PrjBdRep> prjBdRepList = null;		
		try {
			//----------------------------------------------------------------------
			prjBdRepList = session.selectList("jmhPrjBdRepSearchList", prjBdRep);
			//----------------------------------------------------------------------
			if(prjBdRepList != null) {
				System.out.println("JmhDaoPrjBdRepImpl searchList prjBdDataList.get(0).getSubject()->"+((PrjBdRep) prjBdRepList.get(0)).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl searchList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl searchList END...");
		return prjBdRepList;
	}

	//분류
	@Override
	public List<Code> codeList(Code code) {
		
		System.out.println("JmhDaoPrjBdRepImpl codeList START...");
		List<Code> reCodeList = null;		
		try {
			//------------------------------------------------------------
			reCodeList = session.selectList("jmhPrjBdRepCodeList", code);
			//------------------------------------------------------------
			System.out.println("reCodeList.size()->"+reCodeList.size());
			if(reCodeList.size() > 0) {
				//성공
				System.out.println("JmhDaoPrjBdRepImpl codeList code->"+reCodeList.get(0).getCate_code());
				System.out.println("JmhDaoPrjBdRepImpl codeList name->"+reCodeList.get(0).getCate_name());
			}else {
				System.out.println("SQL오류");
				return null;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl codeList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl codeList END...");
		return reCodeList;
	}

	//등록
	@Override
	public int insertBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl insertBoard START...");
		int resultCount = 0;		
		try {
			//-----------------------------------------------------------------
			resultCount = session.insert("jmhPrjBdRepInsertBoard", prjBdRep);
			//-----------------------------------------------------------------
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
				return 0;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl insertBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl insertBoard END...");
		return resultCount;
	}

	//조회
	@Override
	public PrjBdRep selectBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl selectBoard START...");
		PrjBdRep selectPrjBdRep = null;		
		try {
			//------------------------------------------------------------------------
			selectPrjBdRep = session.selectOne("jmhPrjBdRepSelectBoard", prjBdRep);
			//------------------------------------------------------------------------
			if(selectPrjBdRep.getDoc_no() > 0) {
				//성공
				System.out.println("selectPrjBdRep.getSubject()->"+selectPrjBdRep.getSubject());
			}else {
				System.out.println("SQL오류");
				return null;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl selectBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl selectBoard END...");
		return selectPrjBdRep;
	}

	//수정
	@Override
	public int updateBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl updateBoard START...");
		int resultCount = 0;		
		try {
			//-----------------------------------------------------------------
			resultCount = session.update("jmhPrjBdRepUpdateBoard", prjBdRep);
			//-----------------------------------------------------------------
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
				return 0;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl updateBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl updateBoard END...");
		return resultCount;
	}

	//삭제
	@Override
	public int deleteBoard(PrjBdRep prjBdRep) {

		System.out.println("JmhDaoPrjBdRepImpl deleteBoard START...");
		int resultCount = 0;		
		try {
			//-----------------------------------------------------------------
			resultCount = session.delete("jmhPrjBdRepDeleteBoard", prjBdRep);
			//-----------------------------------------------------------------
			System.out.println("JmhDaoPrjBdRepImpl resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
				return 0;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl deleteBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl deleteBoard END...");
		return resultCount;
	}

	//댓글 등록
	@Override
	public int insertComment(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoPrjBdRepImpl insertComment START...");
		int resultCount = 0;	
		try {
			//--------------------------------------------------------------------
			resultCount = session.insert("jmhPrjBdRepInsertComment", bdRepComt);
			//--------------------------------------------------------------------
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl insertComment Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl insertComment END...");
		return resultCount;
	}

	//댓글 조회
	@Override
	public List<BdRepComt> selectCommentList(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoPrjBdRepImpl selectCommentList START...");
		List<BdRepComt> resultBdRepCommentList = null;		
		try {
			//----------------------------------------------------------------------------------------
			resultBdRepCommentList = session.selectList("jmhPrjBdRepSelectCommentList", bdRepComt);
			//----------------------------------------------------------------------------------------
			System.out.println("resultBdRepGoodList.size()->"+resultBdRepCommentList.size());
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl selectCommentList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl selectCommentList END...");
		return resultBdRepCommentList;
	}

	//댓글 삭제
	@Override
	public int deleteComment(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoPrjBdRepImpl deleteComment START...");
		int resultCount = 0;	
		try {
			//--------------------------------------------------------------------
			resultCount = session.insert("jmhPrjBdRepDeleteComment", bdRepComt);
			//--------------------------------------------------------------------
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl deleteComment Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl deleteComment END...");
		return resultCount;
	}
	

	//댓글들 알림 플래그 일괄 업데이트(N개)
	@Override
	public int updateCommentAlarmFlag(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoPrjBdRepImpl updateCommentAlarmFlag START...");
		int resultCount = 0;	
		try {
			//----------------------------------------------------------------------------
			resultCount = session.update("jmhPrjBdRepUpdateCommentAlarmFlag", prjBdRep);
			//----------------------------------------------------------------------------
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl updateCommentAlarmFlag Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl updateCommentAlarmFlag END...");
		return resultCount;
	}

	//문서의 댓글들 모두 삭제
	@Override
	public int deleteCommentBoard(PrjBdRep prjBdRep) {

		System.out.println("JmhDaoPrjBdRepImpl deleteCommentBoard START...");
		int resultCount = 0;		
		try {
			//----------------------------------------------------------------------
			resultCount = session.delete("jmhPrjBdRepDeleteCommentBoard", prjBdRep);
			//----------------------------------------------------------------------
			System.out.println("JmhDaoPrjBdRepImpl resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("JmhDaoPrjBdRepImpl deleteCommentBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoPrjBdRepImpl deleteCommentBoard END...");
		return resultCount;
	}
}
