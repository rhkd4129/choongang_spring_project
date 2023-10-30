package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 


//@Date
@Getter
@Setter
@ToString
public class Task {
	private int		task_id;
	private int		project_id;
	private int		project_step_seq;
	private String	user_id;
	private String	task_subject;
	private String	task_content;
	private Date	task_stat_time;
	private Date	task_end_itme;
	private String	task_priority;
	private String	task_status;
	private int		garbage;
}
