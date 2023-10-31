package com.oracle.s202350101.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjStep {
	private int		project_id;
	private int		project_step_seq;
	private int		project_order;
	private String	project_s_name;
	private String	project_s_context;
}
