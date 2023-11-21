package com.oracle.s202350101.dao.cyjDao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.BdFreeGood;
import com.oracle.s202350101.model.BdQna;
import com.oracle.s202350101.model.BdQnaGood;
import com.oracle.s202350101.model.Code;
import com.oracle.s202350101.model.PrjBdData;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CyjDaoImpl implements CyjDao {
	
	private final SqlSession session;

	// 총 갯수
	@Override
	public int totalBdFree() {
		System.out.println("CyjDaoImpl totalBdFree Start");
	
		int totalBdFree = 0;
		try {
			totalBdFree = session.selectOne("cyBdFreeTotal");
			System.out.println("CyjDaoImpl totalBdFree-> " + totalBdFree);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl totalBdFree Exception-> " + e.getMessage());
		}	
		return totalBdFree;
	}
	
	// 추천수 가장 높은 row 3개
	@Override
	public List<BdFree> goodList(BdFree bdFree) {
		System.out.println("CyjDaoImpl goodList Start");
		
		List<BdFree> goodList = new ArrayList<BdFree>();
		try {
			goodList = session.selectList("cyGoodList", bdFree);
			System.out.println("CyjDaoImpl goodList-> " + goodList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl goodList Exception-> " + e.getMessage());
		}
		return goodList;
	}

	// 전체 리스트
	@Override
	public List<BdFree> listBdFree(BdFree bdFree) {
		System.out.println("CyjDaoImpl listBdFree Start");
		
		List<BdFree> bdFreeList = new ArrayList<BdFree>();
		try {
			bdFreeList = session.selectList("cyBdFreeList", bdFree);
			System.out.println("CyjDaoImpl listBdFree bdFreeList.size()-> " + bdFreeList.size());
		} catch (Exception e) {
			System.out.println("CyjDaoImpl listBdFree Exception-> " + e.getMessage());
		}
		return bdFreeList;
	}
	
// -------------------------------------------------------------------
	
	// 새 글 작성
	@Override
	public int insertBdFree(BdFree bdFree) {
		System.out.println("CyjDaoImpl insertBdFree Start");
		
		int insertBdFree = 0;
		try {
			if (bdFree.getAttach_name() == null) bdFree.setAttach_name("");
			if (bdFree.getAttach_path() == null) bdFree.setAttach_path("");			
			insertBdFree = session.insert("cyBdFreeInsert", bdFree);
			System.out.println("CyjDaoImpl insertBdFree-> " + insertBdFree);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl insertBdFree Exception-> " + e.getMessage());
		}		
		return insertBdFree;
	}

// ----------------------------------------------------------------
	
	// 상세페이지
	@Override
	public BdFree bdFreeContent(int doc_no) {
		System.out.println("CyjDaoImpl bdFreeContent Start");
		
		BdFree bdFreeContent = null;
		try {
			bdFreeContent = session.selectOne("cybdFreeContent", doc_no);
			System.out.println("CyjDaoImpl bdFreeContent-> " + bdFreeContent);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl bdFreeContent Exception-> " + e.getMessage());
		}
		return bdFreeContent;
	}
	
	// 조회수 
	@Override
	public int bdCount(int doc_no) {
		System.out.println("CyjDaoImpl bdCount Start");
		
		int bdCount = 0;
		try {
			bdCount = session.update("cyBdCount", doc_no);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl bdCount Exception-> " + e.getMessage());
		}
		return bdCount;
	}

// ----------------------------------------------------------------

	// 수정 
	@Override
	public int bdFreeUpdate2(BdFree bdFree) {
		System.out.println("CyjDaoImpl bdFreeUpdate2 Start");

		// 수정 안됐을 때 : mapper의 sql이 잘못된 건지 DB에서 확인하기 전에 값을 갖고 오는지 확인하기 위함 -> DB와 분리해서 비교하는 작업  
		System.out.println("CyjDaoImpl bdFreeUpdate2  bdFree.getDoc_no()-> "   + bdFree.getDoc_no());
		System.out.println("CyjDaoImpl bdFreeUpdate2  bdFree.getSubject()-> "  + bdFree.getSubject());
		System.out.println("CyjDaoImpl bdFreeUpdate2  bdFree.getDoc_body()-> " + bdFree.getDoc_body());

		int bdFreeUpdate2 = 0;
		try {
			bdFreeUpdate2 = session.update("cybdFreeUpdate2", bdFree);
			System.out.println("CyjDaoImpl bdFreeUpdate2-> " + bdFreeUpdate2);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl bdFreeUpdate2 Exception-> " + e.getMessage());
		}
		return bdFreeUpdate2;
	}

// ----------------------------------------------------------------

	// 삭제
	@Override
	public int boardDelete(int doc_no) {
		System.out.println("CyjDaoImpl boardDelete Start");
		
		int boardDelete = 0;
		try {
			boardDelete = session.delete("cyboardDelete", doc_no);
			System.out.println("CyjDaoImpl boardDelete-> " + boardDelete);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl boardDelete Exception-> " + e.getMessage());
		}
		return boardDelete;
	}
	
// ----------------------------------------------------------------

	// 1. 추천자 목록에 있는지 중복 체크
	@Override
	public int goodConfirm(BdFreeGood bdFreeGood) {
		System.out.println("CyjDaoImpl goodConfirmb Start");
		
		int goodConfirm = 0;
		try {
			goodConfirm = session.selectOne("cyboardGoodConfirm", bdFreeGood);
			System.out.println("CyjDaoImpl goodConfirm-> " + goodConfirm);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl goodConfirm Exception-> " + e.getMessage());
		}
		return goodConfirm;
	}
	
	// 2. 수행 후 추천기록이 없으면 BdFreeGood 테이블에 추천 추가
	@Override
	public int notifyGoodInsert(BdFreeGood bdFreeGood) {
		System.out.println("CyjDaoImpl notifyGoodInsert Start");
		
		int notifyGoodInsert = 0;
		try {
			notifyGoodInsert = session.insert("notifyGoodInsert", bdFreeGood);
			System.out.println("CyjDaoImpl notifyGoodInsert-> " + notifyGoodInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl notifyGoodInsert Exception-> " + e.getMessage());
		}
		return notifyGoodInsert;
	}
	
	// 3. bd_free 테이블에 good_count를 업데이트
	@Override
	public int notifyGoodUpdate(BdFreeGood bdFreeGood) {
		System.out.println("CyjDaoImpl notifyGoodUpdate Start");
		
		int notifyGoodUpdate = 0;
		try {
			notifyGoodUpdate = session.update("cynotifyGoodUpdate", bdFreeGood);
			System.out.println("CyjDaoImpl notifyGoodUpdate-> " + notifyGoodUpdate);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl notifyGoodUpdate Exception-> " + e.getMessage());
		}
		return notifyGoodUpdate;
	}
	
	// 4. 추천 select
	@Override
	public int notifyGoodSelect(BdFree bdFree) {
		System.out.println("CyjDaoImpl notifyGoodSelect Start");
		
		int notifyGoodSelect = 0;
		try {
			notifyGoodSelect = session.selectOne("cynotifyGoodSelect", bdFree);
			System.out.println("CyjDaoImpl notifyGoodSelect-> " + notifyGoodSelect);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl notifyGoodSelect Exception-> " + e.getMessage());
		}
		return notifyGoodSelect;
	}

// ------------------------------------------------------------------------	
// ------------------------- 이벤트 게시판 ------------------------------------

	// 이벤트_총 갯수 
	@Override
	public int eventTotal() {
		System.out.println("CyjDaoImpl eventTotal Start");
		
		int total = 0;
		try {
			total = session.selectOne("cyEventTotal");
			System.out.println("CyjDaoImpl total-> " + total);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventTotal Exception-> " + e.getMessage());
		}
		return total;
	}

	// 이벤트_리스트 
	@Override
	public List<BdFree> listEvent(BdFree bdFree) {
		System.out.println("CyjDaoImpl listEvent Start");
		
		List<BdFree> listEvent = new ArrayList<BdFree>();
		try {
			listEvent = session.selectList("cyEventList", bdFree);
			System.out.println("CyjDaoImpl listEvent-> " + listEvent);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl listEvent Exception-> " + e.getMessage());
		}
		return listEvent;
	}

	// 이벤트_추천수 가장 높은 row 3개 
	@Override
	public List<BdFree> eventGood(BdFree bdFree) {
		System.out.println("CyjDaoImpl eventGood Start");
		
		List<BdFree> eventList = new ArrayList<BdFree>();
		try {
			eventList = session.selectList("cyEventGoodList", bdFree);
			System.out.println("CyjDaoImpl eventList-> " + eventList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventList Exception-> " + e.getMessage());
		}
		return eventList;
	}
	
// ------------------------------------------------------------------------		

	// 이벤트_상세 
	@Override
	public BdFree eventContent(int doc_no) {
		System.out.println("CyjDaoImpl eventContent Start");
		
		BdFree content = null;
		try {
			content = session.selectOne("cyEventContent", doc_no);
			System.out.println("CyjDaoImpl content-> " + content);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventContent Exception-> " + e.getMessage());
		}
		return content;
	}

	// 이벤트_추천수
	@Override
	public int eventCount(int doc_no) {
		System.out.println("CyjDaoImpl eventCount Start");
		
		int count = 0;
		try {
			count = session.update("cyEventCount", doc_no);
			System.out.println("CyjDaoImpl count-> " + count);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventCount Exception-> " + e.getMessage());
		}
		return count;
	}
	
	// 이벤트_댓글입력 
	@Override
	public int comtInsert(BdFreeComt bdFreeComt) {
		System.out.println("CyjDaoImpl ajaxComt Start");
		
		int ajaxInsert = 0;
		try {
			ajaxInsert = session.insert("cyComtInsert", bdFreeComt);
			System.out.println("CyjDaoImpl ajaxInsert-> " + ajaxInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl ajaxComt Exception-> " + e.getMessage());
		}
		return ajaxInsert;
	}
	
	// 이벤트_댓글리스트
	@Override
	public List<BdFreeComt> eventComt(BdFreeComt bdFreeComt) {
		System.out.println("CyjDaoImpl eventComt Start");
		
		List<BdFreeComt> comt = new ArrayList<BdFreeComt>();
		System.out.println("eventComt comt.size()-> " + comt.size());
		try {
			comt = session.selectList("cyEventComt", bdFreeComt);
			System.out.println("CyjDaoImpl comt-> " + comt);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventCount Exception-> " + e.getMessage());
		}
		return comt;
	}
	
	// 이벤트_해당 게시글에 대한 댓글 총 갯수
	@Override
	public int eventComtCount(int doc_no) {
		System.out.println("CyjDaoImpl eventComtCount Start");
		
		int eventComtCount = 0;
		try {
			eventComtCount = session.selectOne("cyEventComtCount", doc_no);
			System.out.println("CyjDaoImpl eventComtCount-> " + eventComtCount);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventComtCount Exception-> " + e.getMessage());
		}
		return eventComtCount;
	}
	
	// 현재 로그인 사용자가 글작성자인 경우 댓글들 alarm_flag='Y'로 일괄 변경처리
	@Override
	public int cyUpdateCommentAlarmFlag(BdFree eventContent) {
		System.out.println("CyjDaoImpl cyUpdateCommentAlarmFlag Start");
		
		int resultCount = 0;
		try {
			resultCount = session.update("cyPrjBdDataUpdateCommentAlarmFlag", eventContent);
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				// 성공 
			} else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("CyjDaoImpl cyUpdateCommentAlarmFlag Exception-> " + e.getMessage());
		}
		System.out.println("CyjDaoImpl cyUpdateCommentAlarmFlag End");
		return resultCount;
	}
	
// ------------------------------------------------------------------------		

	// 이벤트_새 글 입력 
	@Override
	public int eventInsert(BdFree bdFree) {
		System.out.println("CyjDaoImpl eventInsert Start");
		
		int eventInsert = 0;
		try {
			if (bdFree.getAttach_name() == null) bdFree.setAttach_name("");
			if (bdFree.getAttach_path() == null) bdFree.setAttach_path("");
			eventInsert = session.insert("cyEventInsert", bdFree);
			System.out.println("CyjDaoImpl eventInsert-> " + eventInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventInsert Exception-> " + e.getMessage());
		}
		return eventInsert;
	}
	
// ------------------------------------------------------------------------		
	
	// 이벤트_수정
	@Override
	public int eventUpdate(BdFree bdFree) {
		System.out.println("CyjDaoImpl eventUpdate Start");
		
		int eventUpdate = 0;
		try {
			eventUpdate = session.update("cyEventUpdate", bdFree);
			System.out.println("CyjDaoImpl eventUpdate-> " + eventUpdate);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventUpdate Exception-> " + e.getMessage());
		}		
		return eventUpdate;
	}

// ------------------------------------------------------------------------		

	// 이벤트_삭제
	@Override
	public int eventDelete(int doc_no) {
		System.out.println("CyjDaoImpl eventDelete Start");
		
		int eventDelete = 0;
		try {
			eventDelete = session.delete("cyEventDelete", doc_no);
			System.out.println("CyjDaoImpl eventDelete-> " + eventDelete);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl eventDelete Exception-> " + e.getMessage());
		}
		return eventDelete;
	}

// ------------------------------------------------------------------------	
// ------------------------- 자유 게시판 ------------------------------------

	// 자유_총 갯수
	@Override
	public int freeTotal() {
		System.out.println("CyjDaoImpl freeTotal Start");
		
		int freeTotal = 0;
		try {
			freeTotal = session.selectOne("cyFreeTotal");
			System.out.println("CyjDaoImpl freeTotal-> " + freeTotal);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl freeTotal Exception-> " + e.getMessage());
		}
		return freeTotal;
	}
	
	// 자유_추천수 가장 높은 row 3개
	@Override
	public List<BdFree> freeList() {
		System.out.println("CyjDaoImpl freeList Start");
		
		List<BdFree> freeList = new ArrayList<BdFree>();
		try {
			freeList = session.selectList("cyFreeList");
			System.out.println("CyjDaoImpl freeList-> " + freeList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl freeList Exception-> " + e.getMessage());
		}
		return freeList;
	}

	// 자유_전체 리스트
	@Override
	public List<BdFree> freeTotalList(BdFree bdFree) {
		System.out.println("CyjDaoImpl freeTotalList Start");
		
		List<BdFree> totalList = new ArrayList<BdFree>();
		try {
			totalList = session.selectList("cyTotalList", bdFree);
			System.out.println("CyjDaoImpl totalList.size()-> " + totalList.size());
		} catch (Exception e) {
			System.out.println("CyjDaoImpl totalList Exception-> " + e.getMessage());
		}
		return totalList;
	}

// ------------------------------------------------------------------------	

	// 자유_상세
	@Override
	public BdFree freeContent(int doc_no) {
		System.out.println("CyjDaoImpl freeContent Start");
		
		BdFree content = null;
		try {
			content = session.selectOne("cyFreeContent", doc_no);
			System.out.println("CyjDaoImpl content-> " + content);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl content Exception-> " + e.getMessage());
		}
		return content;
	}

	// 자유_조회수 증가
	@Override
	public int freeCount(int doc_no) {
		System.out.println("CyjDaoImpl freeCount Start");
		
		int freeCount = 0;
		try {
			freeCount = session.update("cyFreeCount", doc_no);
			System.out.println("CyjDaoImpl freeCount-> " + freeCount);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl freeCount Exception-> " + e.getMessage());
		}
		return freeCount;
	}

	// 자유_댓글리스트
	@Override
	public List<BdFreeComt> freeComtList(int doc_no) {
		System.out.println("CyjDaoImpl freeComtList Start");
		
		List<BdFreeComt> freeComt = new ArrayList<BdFreeComt>();
		try {
			freeComt = session.selectList("cyFreeComtList", doc_no);
			System.out.println("CyjDaoImpl freeComt-> " + freeComt);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl freeComtList Exception-> " + e.getMessage());
		}
		return freeComt;
	}

// ------------------------------------------------------------------------	

	// 자유_새 글 입력    
	@Override
	public int freeInsert(BdFree bdFree) {
		System.out.println("CyjDaoImpl freeInsert Start");
		
		int freeInsert = 0;
		try {
			if (bdFree.getAttach_name() == null) bdFree.setAttach_name("");;
			if (bdFree.getAttach_path() == null) bdFree.setAttach_path("");
			freeInsert = session.insert("cyFreeInsert", bdFree);
			System.out.println("CyjDaoImpl freeInsert-> " + freeInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl freeInsert Exception-> " + e.getMessage());
		}
		return freeInsert;
	}

// ------------------------------------------------------------------------	

	// 자유_수정
	@Override
	public int bdFreeUpdate(BdFree bdFree) {
		System.out.println("CyjDaoImpl bdFreeUpdate Start");
		
		int freeUpdate = 0;
		try {
			freeUpdate = session.update("cyFreeUpdate", bdFree);
			System.out.println("CyjDaoImpl freeUpdate-> " + freeUpdate);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl bdFreeUpdate Exception-> " + e.getMessage());
		}
		return freeUpdate;
	}

// ------------------------------------------------------------------------	

	// 자유_게시글 삭제
	@Override
	public int freeDelete(int doc_no) {
		System.out.println("CyjDaoImpl freeDelete Start");
		
		int freeDelete = 0;
		try {
			freeDelete = session.delete("cyFreeDelete", doc_no);
			System.out.println("CyjDaoImpl freeDelete-> " + freeDelete);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl bdFreeUpdate Exception-> " + e.getMessage());
		}
		return freeDelete;
	}
	
// ------------------------------------------------------------------------	
	
	// 자유_댓글 삭제
	@Override
	public int freeComtDelete(BdFreeComt bdFreeComt) {
		System.out.println("CyjDaoImpl freeComtDelete Start");
		
		int comtDelete = 0;
		try {
			comtDelete = session.delete("cyFreeComtDelete", bdFreeComt);
			System.out.println("CyjDaoImpl comtDelete-> " + comtDelete);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl comtDelete Exception-> " + e.getMessage());
		}
		return comtDelete;
	}
	
// ------------------------------------------------------------------------	
// ------------------------- qna 게시판 ------------------------------------

	// qna_전체 count
	@Override
	public int qnaSelectCount(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaTotalCount Start");
		
		int qnaTotalCount = 0;
		try {
			qnaTotalCount = session.selectOne("cyQnaTotalCount");
			System.out.println("CyjDaoImpl qnaTotalCount-> " + qnaTotalCount);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaTotalCount Exception-> " + e.getMessage());
		}
		return qnaTotalCount;
	}

	// qna_검색 건수 가져오기 
	@Override
	public int searchCount(BdQna bdQna) {
		System.out.println("CyjDaoImpl searchCount Start");
		
		int searchCount = 0;
		try {
			searchCount = session.selectOne("cyQnaSearchCount", bdQna);
			System.out.println("CyjDaoImpl searchCount-> " + searchCount);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl searchCount Exception-> " + e.getMessage());
		}
		return searchCount;
	}
	
	
	// qna_추천수 가장 높은 row 3개
	@Override
	public List<BdQna> qnaList() {
		System.out.println("CyjDaoImpl qnaList Start");
		
		List<BdQna> qnaList = null;
		try {
			qnaList = session.selectList("cyQnaList");
			System.out.println("CyjDaoImpl qnaList-> " + qnaList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaList Exception-> " + e.getMessage());
		}
		return qnaList;
	}

	// qna_전제 리스트
	@Override
	public List<BdQna> qnaTotalList(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaTotalList Start");
		
		List<BdQna> qnaTotalList = null;
		try {
			qnaTotalList = session.selectList("cyQnaTotalList", bdQna);
			System.out.println("CyjDaoImpl qnaTotalList-> " + qnaTotalList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaTotalList Exception-> " + e.getMessage());
		}
		return qnaTotalList;
	}
	

	@Override
	public List<BdQna> searchList(BdQna bdQna) {
		System.out.println("CyjDaoImpl searchList Start");
		
		List<BdQna> qnaSearchList = null;
		try {
			qnaSearchList = session.selectList("cyQnaSearchList", bdQna);
			System.out.println("CyjDaoImpl searchList qnaSearchList.size()-> " + qnaSearchList.size());
		} catch (Exception e) {
			System.out.println("CyjDaoImpl searchList Exception-> " + e.getMessage());
		}
		return qnaSearchList;
	}
	

	// qna_검색 분류 코드 가져오기
	@Override
	public List<Code> codeList(Code code) {
		System.out.println("CyjDaoImpl codeList Start");
		
		List<Code> codeList = null;
		try {
			codeList = session.selectList("cyCodeListSelect", code);
			System.out.println("CyjDaoImpl codeList-> " + codeList);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl codeList Exception-> " + e.getMessage());
		}
		return codeList;
	}
	
// ------------------------------------------------------------------------	

	// qna_상세
	@Override
	public BdQna qnaContent(int doc_no) {
		System.out.println("CyjDaoImpl qnaContent Start");
		
		BdQna qnaContent = null;
		try {
			qnaContent = session.selectOne("cyQnaContent", doc_no);
			System.out.println("CyjDaoImpl qnaContent-> " + qnaContent);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaContent Exception-> " + e.getMessage());
		}
		return qnaContent;
	}

	// qna_조회수	
	@Override
	public int qnaCount(int doc_no) {
		System.out.println("CyjDaoImpl qnaCount Start");
		
		int qnaCount = 0;
		try {
			qnaCount = session.update("cyQnaCount", doc_no);
			System.out.println("CyjDaoImpl qnaCount-> " + qnaCount);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaCount Exception-> " + e.getMessage());
		}
		return qnaCount;
	}
	
	// 현재 로그인 사용자가 답글의 부모글 작성자인 경우 답글 조회시 alarm_flag='Y'로 변경처리
	@Override
	public int cyUpdateReplyAlarmFlag(BdQna qnaContent) {
		System.out.println("CyjDaoImpl cyUpdateReplyAlarmFlag Start");
		
		int resultCount = 0;
		try {
			resultCount = session.update("cyPrjBdDataUpdateReplyAlarmFlag", qnaContent);
			System.out.println("resultCount->"+resultCount);
			if(resultCount > 0) {
				//성공
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("CyjDaoImpl cyUpdateReplyAlarmFlag Exception-> " + e.getMessage());
		}
		System.out.println("CyjDaoImpl cyUpdateReplyAlarmFlag END...");
		return resultCount;
	}

// ------------------------------------------------------------------------	

	// qna_수정
	@Override
	public int qnaUpdate(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaUpdate Start");
		
		int qnaUpdate = 0;
		try {
			qnaUpdate = session.update("cyQnaUpdate", bdQna);
			System.out.println("CyjDaoImpl qnaUpdate-> " + qnaUpdate);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaUpdate Exception-> " + e.getMessage());
		}
		return qnaUpdate;
	}

// ------------------------------------------------------------------------	

	// qna_추천 1.중복체크
	@Override
	public int qnaConfrim(BdQnaGood bdQnaGood) {
		System.out.println("CyjDaoImpl qnaConfrim Start");
		
		int qnaConfirm = 0;
		try {
			qnaConfirm = session.selectOne("cyQnaConfrim", bdQnaGood);
			System.out.println("CyjDaoImpl qnaConfirm-> " + qnaConfirm);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaConfirm Exception-> " + e.getMessage());
		}
		return qnaConfirm;
	}

	// qna_추천 2. insert 
	@Override
	public int qnaGoodInsert(BdQnaGood bdQnaGood) {
		System.out.println("CyjDaoImpl qnaGoodInsert Start");
		
		int qnaGoodInsert = 0;
		try {
			qnaGoodInsert = session.insert("cyQnaGoodInsert", bdQnaGood);
			System.out.println("CyjDaoImpl qnaGoodInsert-> " + qnaGoodInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaGoodInsert Exception-> " + e.getMessage());
		}
		return qnaGoodInsert;
	}

	// qna_추천 3. update
	@Override
	public int qnaGoodUpdate(BdQnaGood bdQnaGood) {
		System.out.println("CyjDaoImpl qnaGoodUpdate Start");
		
		int qnaGoodUpdate = 0;
		try {
			qnaGoodUpdate = session.update("cyQnaGoodUpdate", bdQnaGood);
			System.out.println("CyjDaoImpl qnaGoodUpdate-> " + qnaGoodUpdate);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaGoodUpdate Exception-> " + e.getMessage());
		}
		return qnaGoodUpdate;
	}

	// qna_추천 4. select
	@Override
	public int qnaGoodSelect(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaGoodSelect Start");
		
		int qnaGoodSelect = 0;
		try {
			qnaGoodSelect = session.selectOne("cyQnaGoodSelect", bdQna);
			System.out.println("CyjDaoImpl qnaGoodSelect-> " + qnaGoodSelect);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaGoodSelect Exception-> " + e.getMessage());
		}
		return qnaGoodSelect;
	}
	
// ------------------------------------------------------------------------	

	// qna_새 글 입력하기 위한 상세 
	@Override
	public BdQna selectBdQna(BdQna bdQna) {
		System.out.println("CyjDaoImpl selectBaQna Start");
		
		BdQna selectBdQna = null;
		try {
			selectBdQna = session.selectOne("cySelectBdQna", bdQna);
			System.out.println("CyjDaoImpl selectBdQna-> " + selectBdQna);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl selectBdQna Exception-> " + e.getMessage());
		}
		return selectBdQna;
	}
		
	// qna_새 글 입력 
	@Override
	public int qnaInsert(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaInsert Start");
		
		if (bdQna.getAttach_name() == null) bdQna.setAttach_name("");
		if (bdQna.getAttach_path() == null) bdQna.setAttach_path("");		
		
		int qnaInsert = 0;
		try {
			System.out.println("**2**app_id:"+bdQna.getApp_id());
			System.out.println("**2**user_id:"+bdQna.getUser_id());
			System.out.println("**2**subject:"+bdQna.getSubject());
			System.out.println("**2**doc_body:"+bdQna.getDoc_body());
			System.out.println("**2**bd_category:"+bdQna.getBd_category());
			System.out.println("**2**doc_group:"+bdQna.getDoc_group());
			System.out.println("**2**doc_step:"+bdQna.getDoc_step());
			System.out.println("**2**doc_indent:"+bdQna.getDoc_indent());
			System.out.println("**2**parent_doc_user_id:"+bdQna.getParent_doc_user_id());
			System.out.println("**2**parent_doc_no:"+bdQna.getParent_doc_no());

			qnaInsert = session.insert("cyQnaInsert", bdQna);
			System.out.println("CyjDaoImpl qnaInsert-> " + qnaInsert);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaInsert Exception-> " + e.getMessage());
		}
		return qnaInsert;
	}

// ------------------------------------------------------------------------	

	// qna_답변 순서 조절
	@Override
	public int qnaReply(BdQna bdQna) {
		System.out.println("CyjDaoImpl qnaReply Start");
		
		int qnaReply = 0;
		try {
			qnaReply = session.update("cyQnaReply", bdQna);
			System.out.println("CyjDaoImpl qnaReply-> " + qnaReply);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaReply Exception-> " + e.getMessage());
		}
		return qnaReply;
	}
	
// ------------------------------------------------------------------------	

	// qna_삭제	
	@Override
	public int qnaDelete(int doc_no) {
		System.out.println("CyjDaoImpl qnaDelete Start");
		
		int qnaDelete = 0;
		try {
			qnaDelete = session.delete("cyQnaDelete", doc_no);
			System.out.println("CyjDaoImpl qnaDelete-> " + qnaDelete);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl qnaDelete Exception-> " + e.getMessage());
		}
		return qnaDelete;
	}

// ------------------------------------------------------------------------	

	// 알림 목록
	@Override
	public List<BdQna> alarmList(BdQna bdQna) {
		System.out.println("CyjDaoImpl alarmList Start");
		
		List<BdQna> qnaAlarmList = null;
		try {
			qnaAlarmList = session.selectList("cyQnaAlarmList", bdQna);
			if(qnaAlarmList != null) {
				System.out.println("JmhDaoImpl alarmList qnaAlarmList.get(0).getSubject()-> " +  (qnaAlarmList.get(0)).getSubject());
			}else {
				System.out.println("SQL오류");
			}
		} catch (Exception e) {
			System.out.println("CyjDaoImpl alarmList Exception-> " + e.getMessage());
		}
		return qnaAlarmList;
	}
	
// ------------------------------------------------------------------------	

	// 답글 목록 열때 count
	@Override
	public int alarmCount(BdQna bdQna) {
		System.out.println("CyjDaoImpl alarmCount Start");
		
		int alarmCnt = 0;				
		try {
			//--------------------------------------------------------------------
			alarmCnt = session.selectOne("cyPrjBdDataListAlarmCount", bdQna);
			//--------------------------------------------------------------------
			System.out.println("CyjDaoImpl alarmCount alarmCnt-> " + alarmCnt);
		} catch (Exception e) {
			System.out.println("CyjDaoImpl alarmCount Exception->"+e.getMessage());
		}
		System.out.println("CyjDaoImpl alarmCount END...");
		return alarmCnt;
	}




	














	

	

	

	


	



	

	
	
	
	
	
	
	
	
	



		

		
	
	
	
	
	
	
	
	
	
	
	
	
	

}
