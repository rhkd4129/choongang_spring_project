package com.oracle.s202350101.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

//@Date 
@Getter
@Setter
@ToString
public class ChatMsg {
	private int 	chat_room_id;
	private int 	msg_id;
	private String 	sender_id;
	private String 	msg_con;
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date 	send_time;
	private String 	read_chk;

	private String show_time;
//	조회용
	private String msgsender;
	private String receiver_id;
}
