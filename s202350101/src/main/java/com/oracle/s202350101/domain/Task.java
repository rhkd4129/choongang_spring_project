package com.oracle.s202350101.domain;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class Task {
		private int		Task_Id;
		private int		Project_Id;
		private int		Project_Step_Seq;
		private String	User_Id;
		private String	Task_Subject;
		private String	Task_Content;
		private Date	Task_Stat_Time;
		private Date	Task_End_Itme;
		private String	Task_Priority;
		private String	Task_Status;
		private int		Garbage;
}
