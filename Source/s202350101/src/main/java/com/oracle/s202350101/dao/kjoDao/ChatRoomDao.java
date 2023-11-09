package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;

import java.util.List;

public interface ChatRoomDao {


//<!--모든 채팅방 조회-->
    List<ChatRoom> findAll();

//<!--개인별 채팅방 조회-->
    List<ChatRoom> findByUserId(UserInfo ui);

//<!--강의실 개수 조회-->
    ChatRoom findById(ChatRoom cr);

//<!--상대방과의 채팅방 조회-->
    ChatRoom findByYouAndMe(ChatRoom cr);

//<!--시퀀스 적용해 메시지 저장, 시퀀스 값 반환-->
    int addChatRoom(ChatRoom cr);
}
