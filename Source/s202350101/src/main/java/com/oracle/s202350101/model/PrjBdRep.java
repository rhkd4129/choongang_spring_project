package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjBdRep {
	private int		doc_no;
	private int		project_id;
	private int		app_id;
	private String	user_id;
	private Date	create_date;
	private Date	modify_date;
	private String	subject;
	private String	bd_category;
	private String	doc_body;
	private String	attach_name;
	private String	attach_path;
	
	//조회용
	private int		rn;					//목록 rownum번호
	private int		start;				//페이지 시작번호
	private int		end;				//페이지 끝번호
	private String	user_name;			//작성자명
	private String	bd_category_name; 	//분류명
	private String  attach_delete_flag; //편집저장시 기존첨부 삭제여부(D)	
	private String  app_name;			//어플리케이션 이름
	private int		comment_count;		//댓글 수
	private String	search;				//검색대상 필드
	private String	keyword;			//검색어 키워드
}
