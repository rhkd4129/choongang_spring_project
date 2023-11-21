package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;

//@Date 
@Getter
@Setter
@ToString
public class ClassRoom {
	private int 	class_id;

	@NotNull(message = "강의실 번호는 비어있을 수 없습니다.")
	@Max(value = 9999)
	@Min(value = 100)
	private Integer class_room_num;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "과정 시작 날짜는 비어있을 수 없습니다.")
	private Date 	class_start_date;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "과정 종료 날짜는 비어있을 수 없습니다.")
	private Date 	class_end_date;
	@NotEmpty(message = "과정 담당자의 이름은 비어있을 수 없습니다.")
	private String 	class_master;
	@NotEmpty(message = "강의 이름은 비어있을 수 없습니다.")
	@Size(min = 3, max = 100, message = "과정 이름의 길이는 100글자 입니다.")
	private String 	class_name;
	private String 	class_area;

	private String startDate;
	private String endDate;

}
