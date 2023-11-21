package com.oracle.s202350101.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class BdQna {
	private int 	doc_no;					private int 	app_id;
	
											@NotNull(message = "필수 입력란입니다")
	private String  user_id;				private String 	subject;
	
	@NotNull(message = "필수 입력란입니다")
	private String 	doc_body;				private Date 	create_date;

	private Date 	modify_date;			private String 	bd_category;
	private String 	attach_name;			private String 	attach_path;
	private int 	bd_count;				private int 	good_count;
	private int 	doc_group;				private int 	doc_step;
	private int 	doc_indent;				private String 	alarm_flag;
	private String 	parent_doc_user_id; 	private int 	parent_doc_no;

	// 페이징 작업
	private int rn;
	private String search;   	private String keyword;
	private String pageNum;		private int total;
	private int start; 		 	private int end;
	
	// 조회용
	private String app_name;
	private String user_name;
	private String bd_category_name;
	
	// 알림용 목록 표시 (Y/y)
	private String  doc_group_list;		
	

	
}
