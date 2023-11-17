package com.oracle.s202350101.model;


import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Data
@Getter
@Setter
@ToString
public class UserInfo {
	@NotBlank(message = "아이디를 입력해 주세요.")
	@Size(min = 5, max = 20, message = "5~20자의 영문 소문자, 숫자와 특수기호(_),(-)만 사용 가능합니다.")
	@Pattern(regexp = "^(?!.*([a-zA-Z0-9\\\\-_])\\1\\1\\1)[a-zA-Z0-9\\\\-_]{5,20}$", message = "사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.")
	private String 	user_id;
	private int		class_id;
	private int		project_id;
	@NotBlank(message = "비밀번호를 입력해 주세요.")
	@Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$", message = "적어도 하나의 숫자와 영문자 그리고 특수문자가 포함되어야 합니다.")
	private String 	user_pw;
	@NotBlank(message = "이름을 입력해 주세요.")
	@Size(min = 3, max = 10, message = "3자 이상 10자 이하로 입력해주세요.")
	private String 	user_name;
	private String 	user_gender;
	@Pattern(regexp = "(01[016789])[-]?(\\d{3,4})[-]?(\\d{4})", message = "휴대폰 번호 바르게 입력해 주세요.")
	@NotBlank(message = "전화번호를 입력해 주세요.")
	private String 	user_number;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date 	user_birth;
	private String 	user_address;
	@Email(message = "이메일 주소를 바르게 입력해 주세요.")
	@NotBlank(message = "이메일을 입력해 주세요.")
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
