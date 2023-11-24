package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;

import java.util.List;
import java.util.Map;

public interface ChatRoomService {
//<!--상대방과의 채팅방 조회 후 없으면 채팅방 생성-->
    ChatRoom findByYouAndMeNotEmpty(ChatRoom cr);
//<!--개인별 채팅방 조회-->
    List<ChatRoom> findByUserId(UserInfo ui);
//<!--모든 채팅방 조회-->
    List<ChatRoom> findAll();
//<!--상대방과의 채팅방 조회-->
    ChatRoom findByYouAndMe(ChatRoom cr);

//나의 채팅방 조회
    Map<?, ?> findByUserIdV2(List<ChatMsg> findmsg, ChatRoom cr);


}
