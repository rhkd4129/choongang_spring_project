package com.oracle.s202350101.model;

//	insert 위해 util -> sql 변경
//	util:	년/월/일/시/분/초
//	sql:	년/월/일
/*
	java.sql.Date는 시간 정보가 없이 날짜 정보만 저장하며, JDBC에서 일반적으로 사용된다.
	자바 8 이후에서는 java.util.Date의 사용을 권고하지 않는다.
*/
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class ClassRoom {
	private int 	class_id;
	private int 	class_room_num;
	private Date 	class_start_date;
	private Date 	class_end_date;
	private String 	class_master;
	private String 	class_name;
	private String 	class_area;

}
