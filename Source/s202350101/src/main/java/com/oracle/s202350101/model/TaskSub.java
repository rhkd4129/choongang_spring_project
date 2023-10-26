package com.oracle.s202350101.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class TaskSub {
	private int		task_id;
	private int		project_id;
	private String	worker_id;
}
