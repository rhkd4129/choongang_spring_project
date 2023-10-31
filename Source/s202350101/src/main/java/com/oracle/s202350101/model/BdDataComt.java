package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class BdDataComt {
	private int 	doc_no;
	private int 	project_id;
	private int 	comment_doc_no;
	private String 	user_id;
	private Date 	create_date;
	private Date 	modify_date;
	private String 	comment_context;
	private String 	alarm_flag;

}
