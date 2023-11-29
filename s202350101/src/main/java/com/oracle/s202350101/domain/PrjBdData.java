package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjBdData {
	private int 	doc_no;				private int 	project_id;
	private int 	app_id;				private String 	user_id;
	private Date 	create_date;		private Date 	modify_date;
	private String 	subject;			private String 	notify_flag;
	private String 	bd_category;		private String 	doc_body;
	private String 	attach_name;		private String 	attach_path;
	private int 	bd_count;			private int 	good_count;
	private int 	doc_group;			private int 	doc_step;
	private int 	doc_indent;			private String 	alarm_flag;
	private String 	parent_doc_user_id;	private String 	parent_doc_no;
	
}
