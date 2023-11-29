package com.oracle.s202350101.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//@Date 
@Getter
@Setter
@ToString
public class ChatRoom {
	private int 	chat_room_id;
	private String 	sender_id;
	private String 	receiver_id;
	
	// 	조회용
	private String  user_name;
	private String	msg_con;
	private String 	show_time;	//	날짜포맷용
	private int		read_cnt;	//	읽지 않은 메시지 수

	private String 	attach_name;		//	사용자 사진
	private String 	attach_path;		//	사용자 사진

	//	업데이트용
//	private String	reader_id;
	
}
