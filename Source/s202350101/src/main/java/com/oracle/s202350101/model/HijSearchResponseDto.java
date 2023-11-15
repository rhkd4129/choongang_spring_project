package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Data;

@Data
public class HijSearchResponseDto {
	
	// 검색한 결과 (받는거)
	private int 	doc_no;				private int 	project_id;
	private int 	app_id;				private String 	user_id;
	private Date 	create_date;		private Date 	modify_date;
	private String 	subject;			
	private String 	bd_category;		private String 	doc_body;
	private String 	attach_name;		private String 	attach_path;
	

	//조회용
	private int		start;				//페이지 시작번호
	private int		end;				//페이지 끝번호
	private String	user_name;			//작성자명
	private String	bd_category_name; 	//분류명
	private String  app_name;			//어플리케이션 이름

}
