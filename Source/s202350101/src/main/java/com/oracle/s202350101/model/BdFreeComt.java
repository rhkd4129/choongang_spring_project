package com.oracle.s202350101.model;

import java.sql.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// @Data 
@Getter
@Setter
@ToString
public class BdFreeComt {
	private int 	doc_no;
	private int 	comment_doc_no;
	private String 	user_id;
	private Date 	create_date;
	private Date 	modify_date;
	private String 	comment_context;
	private String 	alarm_flag;
	
	// 조회용
	private String  user_name;
	private String  app_name;
	
	// 페이징 작업 
	private int rn;
	private String search;   	
	private String keyword;
	private String pageNum;		
	private int total;
	private int start; 		 	
	private int end;
}
