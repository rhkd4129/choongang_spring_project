package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class BdQna {
	private int 	doc_no;					private int 	app_id;
	private String  user_id;				private String 	subject;
	private String 	doc_body;				private Date 	create_date;
	private Date 	modify_date;			private String 	bd_category;
	private String 	attach_name;			private String 	attach_path;
	private int 	bd_count;				private int 	good_count;
	private int 	doc_group;				private int 	doc_step;
	private int 	odc_indent;				private String 	alarm_flag;
	private String 	parent_doc_user_id; 	private int 	parent_doc_no;

}
