package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotEmpty;

//@Date 
@Data
//@ScriptAssert(lang="javascripte" script="", message="")
public class Task {
		private int		task_id;
		private int		project_id;
		private int		project_step_seq;
		private String	user_id;

		@NotEmpty(message = "필수이빈다")
		private String	task_subject;
		private String	task_content;
		private java.sql.Date task_stat_time;
		private java.sql.Date	task_end_itme;
		private String	task_priority;
		private String	task_status;
		private int		garbage;
		
		//읽기 전용 
		private int status_0_count;
		private int status_1_count;
		private int status_2_count;
		private String project_s_name;
		private String user_name;


		private String search;   	private String keyword;
		private String pageNum;
		private int start; 		 	private int end;
		private int rn;
}
