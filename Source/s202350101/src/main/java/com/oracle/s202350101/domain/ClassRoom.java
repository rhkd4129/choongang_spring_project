package com.oracle.s202350101.domain;

import java.util.Date;

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
