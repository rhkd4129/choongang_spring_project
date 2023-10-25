package com.oracle.home.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {
	private String id;
	private String name;
	private String password;
	private Date reg_date;
}

