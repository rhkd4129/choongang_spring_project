package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class UserInfo {
	private String 	user_id;
	private int		class_id;
	private int		project_id;
	private String 	user_pw;
	private String 	user_name;
	private String 	user_gender;
	private String 	user_number;
	private Date 	user_birth;
	private String 	user_address;
	private String 	user_email;
	private String 	user_auth;
	private String 	attach_name;
	private String 	attach_path;
	private int		del_status;
	private String 	chat_room_ses;

//	조회용
	private String project_name;
// 조회용
	private String search;   	private String keyword;
	private String pageNum;		private int total;
	private int start; 		 	private int end;

	private String class_area;
}
