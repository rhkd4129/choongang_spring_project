package com.oracle.s202350101.domain;

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
}
