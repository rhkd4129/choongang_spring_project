package com.oracle.s202350101.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.oracle.s202350101.model.Paging;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.BdFreeComt;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.cyjSer.CyjService;

import lombok.RequiredArgsConstructor;


//@Slf4j
@Controller
@RequiredArgsConstructor
public class CyjControllerFreeQna {

    private final CyjService cs;

// ------------------------- 자유 게시판 ------------------------------------

    // 자유_리스트
    @RequestMapping(value = "board_free")
    public String boardFree(BdFree bdFree, String currentPage, Model model) {
        System.out.println("CyjControllerQna board_free Start----------------------");

        // 총 갯수
        int freeTotal = cs.freeTotal();
        System.out.println("자유 게시판 freeTotal-> " + freeTotal);
        model.addAttribute("freeTotal", freeTotal);

        // 추천수 가장 높은 row 3개
        List<BdFree> freeRow = cs.freeRow();
        System.out.println("자유 추천수 가장 높은 row.size()-> " + freeRow.size());
        model.addAttribute("freeRow", freeRow);

        // paging 작업
        Paging page = new Paging(freeTotal, currentPage);  // 전달인자
        bdFree.setStart(page.getStart());
        bdFree.setEnd(page.getEnd());
        model.addAttribute("page", page);

        // 전체 리스트
        List<BdFree> freeList = cs.freeList(bdFree);
        System.out.println("자유 게시판 freeList.size()-> " + freeList.size());
        model.addAttribute("freeList", freeList);

        return "board/board_free/board_free_list";
    }

// ------------------------------------------------------------------

    // 자유_상세 페이지
    @GetMapping(value = "free_content")
    public String freeContent(HttpServletRequest request, int doc_no, Model model) {
        System.out.println("CyjControllerQna free_content Start----------------------");

        System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

        // loginId : 상세 페이지에서 접속자 (수정 버튼 보이게) / 작성자 아님 (수정 버튼 안 보이게)
        String loginId = userInfoDTO.getUser_id();
        System.out.println("상세페이지 접속자 loginId-> " + loginId);

        // 자유_상세
        BdFree freeContent = cs.freeContent(doc_no);
        System.out.println("CyjControllerQna freeContent-> " + freeContent);
        model.addAttribute("freeContent", freeContent);

        // 접속자와 작성자 비교 --> 같으면 1, 다르면 0
        int result = 0;
        if (loginId.equals(freeContent.getUser_id())) {
            result = 1;
        } else {
            result = 0;
        }
        model.addAttribute("result", result);

        // 자유_조회수
        int freeCount = cs.freeCount(doc_no);
        System.out.println("CyjControllerQna freeCount-> " + freeCount);
        model.addAttribute("freeContent", freeContent);

        // 자유_댓글리스트
        List<BdFree> freeComtList = cs.freeComtList(doc_no);
        System.out.println("CyjControllerQna freeComtList-> " + freeComtList);
        model.addAttribute("freeComtList", freeComtList);

        return "board/board_free/board_free_content";
    }

    // 자유_댓글
    @ResponseBody
    @RequestMapping(value = "ajaxFreeComt")
    public List<BdFreeComt> ajaxfreeComt(BdFreeComt bdFreeComt){
        System.out.println("CyjControllerQna ajaxFreeComt Start----------------------");

        // 댓글 입력
        int ajaxComtInsert = cs.ajaxFreeComt(bdFreeComt);
        System.out.println("CyjControllerQna ajaxComtInsert-> " + ajaxComtInsert);

        // 입력한 댓글 갖고 옴
        List<BdFreeComt> freeComtList = cs.freeComtList(bdFreeComt);
        System.out.println("CyjControllerQna freeComtList-> " + freeComtList);

        return freeComtList;
    }

// ------------------------------------------------------------------

    // 자유_추천
    @ResponseBody
    @RequestMapping(value = "ajaxFreeGoodCount")
    public int freeGoodCount(int doc_no, Model model) {
        System.out.println("CyjControllerQna ajaxFreeGoodCount Start----------------------");

        // 추천수 +1 올리기
        int freeGoodCount = cs.freeGoodCount(doc_no);
        System.out.println("추천 doc_no-> "     + doc_no);
        System.out.println("freeGoodCount-> " + freeGoodCount);

        // 올린 추천수 갖고 오기
        int freeGoodGet = cs.freeGoodGet(doc_no);
        System.out.println("freeGoodGet-> " + freeGoodGet);
        model.addAttribute("freeGoodGet", freeGoodGet);

        return freeGoodGet;
    }

// ------------------------------------------------------------------

    // 자유_새 글 입력하기 위한 페이지 이동
    @RequestMapping(value = "free_insert_from")
    public String freeInsert() {
        System.out.println("CyjControllerQna free_insert_from Start----------------------");

        return "board/board_free/board_free_insert";
    }

    // 새 글 입력
    @PostMapping(value = "free_insert")
    public String freeInsert(HttpServletRequest request, BdFree bdFree, Model model) {
        System.out.println("CyjControllerQna free_insert Start----------------------");

        System.out.println("session.userInfo-> " + request.getSession().getAttribute("userInfo"));
        UserInfo userInfoDTO = (UserInfo) request.getSession().getAttribute("userInfo");

        // loginId : 새 글 작성에는 접속자이자 작성자
        String loginId = userInfoDTO.getUser_id();
        System.out.println(request.getSession().getAttribute("loginId"));
        bdFree.setUser_id(loginId);

        int freeInsert = cs.freeInsert(bdFree);
        System.out.println("free free_insert 새 글 입력-> " + freeInsert);

        model.addAttribute("freeInsert", freeInsert);

        return "redirect:/board_free";
    }

// ------------------------------------------------------------------

    // 자유_수정: 상세 정보 get
    @GetMapping(value = "free_update")
    public String freeUpdate(int doc_no, Model model) {
        System.out.println("CyjControllerQna free_update Start----------------------");

        BdFree content = cs.freeContent(doc_no);
        System.out.println("CyjControllerQna content-> " + content);

        model.addAttribute("content", content);

        return "board/board_free/board_free_update";
    }

    // 수정
    @PostMapping(value = "free_update2")
    public String freeUpdate2(BdFree bdFree, Model model, RedirectAttributes redirectAttributes) {
        System.out.println("CyjControllerQna free_update2 Start----------------------");

        int freeUpdate = cs.freeUpdate(bdFree);
        System.out.println("CyjControllerQna freeUpdate-> " + freeUpdate);

        model.addAttribute("freeUpdate", freeUpdate);

        redirectAttributes.addAttribute("doc_no", bdFree.getDoc_no());

        return "redirect:/free_content?doc_no={doc_no}";
    }

// ------------------------------------------------------------------

    // 자유_삭제
    @ResponseBody
    @RequestMapping(value = "freeDelete")
    public int ajaxFreeDelete(int doc_no) {
        System.out.println("CyjControllerQna ajaxFreeDelete Start----------------------");

        int ajaxFreeDelete = cs.freeDelete(doc_no);
        System.out.println("CyjControllerQna ajaxFreeDelete-> " + ajaxFreeDelete);

        return ajaxFreeDelete;
    }






























}
