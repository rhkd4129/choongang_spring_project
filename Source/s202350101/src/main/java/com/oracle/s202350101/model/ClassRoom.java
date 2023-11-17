package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@Date 
@Getter
@Setter
@ToString
public class ClassRoom {
	private int 	class_id;

	@NotNull(message = "강의실 번호는 비어있을 수 없습니다.")
	private Integer 	class_room_num;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "과정 시작 날짜는 비어있을 수 없습니다.")
	private Date 	class_start_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "과정 종료 날짜는 비어있을 수 없습니다.")
	private Date 	class_end_date;
	@NotEmpty(message = "과정 담당자의 이름은 비어있을 수 없습니다.")
	private String 	class_master;
	@NotEmpty(message = "강의 이름은 비어있을 수 없습니다.")
	private String 	class_name;
	private String 	class_area;

	private String startDate;
	private String endDate;

}
