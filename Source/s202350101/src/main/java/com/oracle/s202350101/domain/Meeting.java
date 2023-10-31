package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//@Date 
@Getter
@Setter
@ToString
public class Meeting {
	private int 	meeting_id;
	private int 	project_id;
	private Date 	meeting_date;
	private String 	meeting_title;
	private String 	meeting_place;
	private String 	user_id;
	private String 	meeting_category;
	private String 	meeting_content;
	private int 	meeting_status;
	private String 	attach_name;
	private String 	attach_path;

}
