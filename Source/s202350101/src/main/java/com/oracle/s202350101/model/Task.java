package com.oracle.s202350101.model;

import java.util.Date;
import java.util.List;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//@Date 
@Data
//@ScriptAssert(lang="javascripte" script="", message="")
public class Task {
		private int		app_id;
		private int		task_id;
		private int		project_id;
		private int		project_step_seq;
		private String	user_id;
		@NotEmpty(message = "이 항목은 필수입니다")
		@Length(min = 1, max = 150, message = "길이는 1에서 150 사이여야 합니다.")
		private String	task_subject;
		@NotEmpty(message = "이 항목은 필수입니다")
		private String	task_content;
		private java.sql.Date task_start_time;
		private java.sql.Date task_end_time;
		private java.sql.Date create_date;
		@NotNull(message = "이 항목은 필수입니다")
		private String	task_priority;
		@NotNull(message = "이 항목은 필수입니다")
		private String	task_status;
		private int		garbage;
		//읽기 전용
		private int status_progress; //진척률
		private int status_all_count; //개인별 총 작업수
		private int status_0_count; //개인별 예정
		private int status_1_count; //개인별 진행중
		private int status_2_count; //개인별 완료
		private String project_s_name;
		private String user_name;
		private String keyword;
		private String keyword_division;
		private String pageNum;
		private int start;
		private int end;
		private int rn;
		

	// 다중 테이블 insert
	private List<String> workerIdList;
	private int		attach_no;
	private String	attach_name;
	private String	attach_path;	
}
