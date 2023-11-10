package com.oracle.s202350101.model;


import java.util.Date;
import javax.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
//@Getter
//@Setter
//@ToString
@Data
public class UserInfo {
	@NotEmpty(message = "아이디를 입력해 주세요.")
	private String 	user_id;
	private int		class_id;
	private int		project_id;
	@NotEmpty(message = "비밀번호를 입력해 주세요.")
	private String 	user_pw;
	private String 	user_name;
	private String 	user_gender;
	private String 	user_number;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date 	user_birth;
	private String 	user_address;
	private String 	user_email;
	private String 	user_auth;
	private String 	attach_name;
	private String 	attach_path;
	private int		del_status;
	private String 	chat_room_ses;
	
	// 조회용
	private String  class_area;
	private String  project_name;
	private String  search;      
	private String  keyword;
	private String  pageNum;    
	private int     total;
	private int     start;        
	private int     end;
	
	
}
