package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class Todo {
	private String	user_id;
	private int		todo_no;
	private String	todo_list;
	private String	todo_check;
	private Date 	todo_date;
	private int		todo_priority;
}
