package com.oracle.s202350101.model;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@ToString
public class BdFree {
	private int 	doc_no;
	private String  user_id;
	private int 	app_id;
	private Date 	create_date;
	private Date 	modify_date;
	private String 	bd_category;
	
	@NotBlank(message = "필수 입력란입니다")
	private String 	subject;
	@NotEmpty(message = "필수 입력란입니다")
	private String 	doc_body;	
	
	private int 	bd_count;
	private int 	good_count;
	private String 	attach_name;
	private String 	attach_path;
	
	// 페이징 작업
	private int rn;
	private String search;   	private String keyword;
	private String pageNum;		private int total;
	private int start; 		 	private int end;
	
	// 조회용 
	private String user_name;
	private String app_name;
	private String comment_context;

		
}
