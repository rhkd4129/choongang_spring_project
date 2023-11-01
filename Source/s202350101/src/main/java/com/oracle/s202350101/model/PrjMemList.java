package com.oracle.s202350101.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class PrjMemList {
	private int		project_id;
	private String	user_id;


	//조회용
	private String        user_name;
}
