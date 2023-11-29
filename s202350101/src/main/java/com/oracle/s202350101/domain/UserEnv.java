package com.oracle.s202350101.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class UserEnv {
	private String	user_id;
	private String	env_alarm_comm;
	private String	env_alarm_reply;
	private String	env_alarm_mine;
	private String	env_alarm_meeting;
	private String	env_chat;
}
