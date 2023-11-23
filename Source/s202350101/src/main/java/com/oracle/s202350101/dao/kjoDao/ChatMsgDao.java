package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.*;

import java.util.List;

public interface ChatMsgDao {
    //<!--특정 채팅방 내 모든 메시지 조회-->
    List<ChatMsg> findByRoomId(ChatRoom cr);
    //  메시지 읽음 처리
    int updateRead(ChatRoom nowChatRoom);
    //<!--채팅방 내 총 메시지 개수-->
    int cntMsg(ChatMsg msg);
    //<!--메시지 저장-->
    int saveMsg(ChatMsg msg);
    //<!--채팅방, 메시지 id기준 메시지 조회-->
    ChatMsg findbyCMid(ChatMsg msg);
    //<!--모든 메시지 조회-->
    List<ChatMsg> findAll();
    //  사용자 별 읽지 않은 메세지
    List<ChatMsg> findbyuseridnoRead(ChatRoom cr);
}
