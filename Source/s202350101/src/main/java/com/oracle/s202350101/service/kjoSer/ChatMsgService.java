package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;

import java.util.List;

public interface ChatMsgService {
//<!--모든 메시지 조회-->
    List<ChatMsg> findAll();
//<!--특정 채팅방 내 모든 메시지 조회-->
    List<ChatMsg> findByRoomId(ChatRoom cr);
//<!--메시지 저장-->

    int saveMsg(ChatMsg msg);
//<!--채팅방 내 총 메시지 개수-->

    int cntMsg(ChatMsg msg);
//  메시지 insert
    int cntsaveMsg(ChatMsg msg);

//  메세지 insert 후 메세지 반환
    ChatMsg findsaveMsg(ChatMsg msg);
}
