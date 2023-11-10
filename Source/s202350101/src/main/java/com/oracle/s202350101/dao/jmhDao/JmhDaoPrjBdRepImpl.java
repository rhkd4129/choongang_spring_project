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

	
	@Override
	public int totalCount() {
		
		System.out.println("JmhDaoImpl totalCount START...");
		int totalCnt = 0;				
		try {
			//---------------------------------------------------------
			totalCnt = session.selectOne("jmhPrjBdRepListTotalCount");
			//---------------------------------------------------------
			System.out.println("JmhDaoImpl totalCount totalCnt->"+totalCnt);
		} catch (Exception e) {
			System.out.println("JmhDaoImpl totalCount Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl totalCount END...");
		return totalCnt;
	}
	
	@Override
	public List<PrjBdRep> boardList(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoImpl boardList START...");
		List<PrjBdRep> prjBdRepList = null;		
		try {
			//----------------------------------------------------------------
			prjBdRepList = session.selectList("jmhPrjBdRepList", prjBdRep);
			//----------------------------------------------------------------
			System.out.println("JmhDaoImpl boardList prjBdRepList.get(0).getSubject()->"+((PrjBdRep) prjBdRepList.get(0)).getSubject());
		} catch (Exception e) {
			System.out.println("JmhDaoImpl boardList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl boardList END...");
		return prjBdRepList;
	}

	@Override
	public List<Code> codeList(Code code) {
		
		System.out.println("JmhDaoImpl codeList START...");
		List<Code> reCodeList = null;		
		try {
			//------------------------------------------------------------
			reCodeList = session.selectList("jmhPrjBdRepCodeList", code);
			//------------------------------------------------------------
			System.out.println("reCodeList.size()->"+reCodeList.size());
			if(reCodeList.size() > 0) {
				//성공
				System.out.println("JmhDaoImpl codeList code->"+reCodeList.get(0).getCate_code());
				System.out.println("JmhDaoImpl codeList name->"+reCodeList.get(0).getCate_name());
			}else {
				System.out.println("SQL오류");
				return null;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoImpl codeList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl codeList END...");
		return reCodeList;
	}

	@Override
	public int insertBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoImpl insertBoard START...");
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
			System.out.println("JmhDaoImpl insertBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl insertBoard END...");
		return resultCount;
	}

	@Override
	public PrjBdRep selectBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoImpl selectBoard START...");
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
			System.out.println("JmhDaoImpl selectBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl selectBoard END...");
		return selectPrjBdRep;
	}

	@Override
	public int updateBoard(PrjBdRep prjBdRep) {
		
		System.out.println("JmhDaoImpl updateBoard START...");
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
			System.out.println("JmhDaoImpl updateBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl updateBoard END...");
		return resultCount;
	}

	@Override
	public int deleteBoard(PrjBdRep prjBdRep) {

		System.out.println("JmhDaoImpl deleteBoard START...");
		int resultCount = 0;		
		try {
			//-----------------------------------------------------------------
			resultCount = session.delete("jmhPrjBdRepDeleteBoard", prjBdRep);
			//-----------------------------------------------------------------
			System.out.println("JmhDaoImpl resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
				return 0;
			}
		} catch (Exception e) {
			System.out.println("JmhDaoImpl deleteBoard Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl deleteBoard END...");
		return resultCount;
	}

	@Override
	public int insertComment(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoImpl insertComment START...");
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
			System.out.println("JmhDaoImpl insertComment Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl insertComment END...");
		return resultCount;
	}

	@Override
	public List<BdRepComt> selectCommentList(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoImpl selectCommentList START...");
		List<BdRepComt> resultBdRepCommentList = null;		
		try {
			//----------------------------------------------------------------------------------------
			resultBdRepCommentList = session.selectList("jmhPrjBdRepSelectCommentList", bdRepComt);
			//----------------------------------------------------------------------------------------
			System.out.println("resultBdRepGoodList.size()->"+resultBdRepCommentList.size());
		} catch (Exception e) {
			System.out.println("JmhDaoImpl selectCommentList Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl selectCommentList END...");
		return resultBdRepCommentList;
	}

	@Override
	public int deleteComment(BdRepComt bdRepComt) {
		
		System.out.println("JmhDaoImpl deleteComment START...");
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
			System.out.println("JmhDaoImpl deleteComment Exception->"+e.getMessage());
		}
		System.out.println("JmhDaoImpl deleteComment END...");
		return resultCount;
	}
}
