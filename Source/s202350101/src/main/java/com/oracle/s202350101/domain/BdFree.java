package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
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
	private String 	subject;
	private String 	doc_body;
	private int 	bd_count;
	private int 	good_count;
	private String 	attach_name;
	private String 	attach_path;
}
