package com.oracle.s202350101.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//@Data
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
	private Date 	create_date;
	
	// 페이징 작업
	private int rn;
	private String search;   	private String keyword;
	private String pageNum;		private int total;
	private int start; 		 	private int end;
	
	// 조회용
	private String 			meetuser_id;
	private String			user_name;
	private MeetingMember	meetingMember;
	private String  		attach_delete_flag;		// 편집저장시 기존첨부 삭제여부(D)
	
}
