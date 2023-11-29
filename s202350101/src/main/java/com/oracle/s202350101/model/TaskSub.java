package com.oracle.s202350101.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

//@Date 
@Data
public class TaskSub {

	@NotEmpty
	private int		task_id;
	private int		project_id;
	private String	worker_id;


	//읽기 전용
	private String user_name;
}
