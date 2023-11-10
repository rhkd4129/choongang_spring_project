package com.oracle.s202350101.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjInfo {
	private int		project_id;
	private String	project_name;
	private Date	project_startdate;
	private Date	project_enddate;
	private String	project_manager_id;
	private String	project_intro;
	private int		project_approve;
	private int		project_status;
	private Date	project_create_date;
	private String	attach_name;
	private String	attach_path;
	private String	alarm_flag;
	private int		del_status;
	
	// Join
	private String 	project_manager_name; //팀장이름
	private String 	member_user_id; //선택된 멤버id
	private int  project_manager_class;		//팀장 class
	private String project_approve_name;	// 프로젝트 승인 상태
	private String project_status_name;  // 프로젝트 진행 상태
	private String del_status_name;		 // 프로젝트 삭제상태
	
	// 조회용
	private String search;   	private String keyword;
	private String pageNum;		private int total;
	private int start; 		 	private int end;
}
