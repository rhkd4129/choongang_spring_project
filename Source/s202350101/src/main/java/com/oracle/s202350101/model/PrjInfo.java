package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjInfo {
	private int		project_id;
	private String	project_name;
	private Date	project_startdate;
	private Date	project_enddate;
	private String	project_manager_id;
	private String	project_intro;
	private int		project_approve;
	private int		project_status;
	private Date	project_create_date;
	private String	attach_name;
	private String	attach_path;
	private String	alarm_flag;
	private int		del_status;
}
