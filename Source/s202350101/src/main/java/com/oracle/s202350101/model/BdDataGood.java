package com.oracle.s202350101.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class BdDataGood {
	private int 	doc_no;
	private int 	project_id;
	private String  user_id;
	
	//조회용
	private String  user_name;
	
	//내가 추천한 게시글 조회용
	private String  app_id;
	private String  app_name;
	private String  bd_category;
	private String  subject;
	private String  create_date;
	private String  bd_count;
	private String  good_count;
}
