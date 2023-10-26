package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class ChatMsg {
	private String 	chat_room_id;
	private int 	msg_id;
	private String 	sender_id;
	private String 	msg_con;
	private Date 	send_time;
	private String 	read_chk;

}
