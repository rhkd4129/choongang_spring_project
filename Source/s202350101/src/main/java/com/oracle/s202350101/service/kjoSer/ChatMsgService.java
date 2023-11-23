package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.*;

import java.util.List;
import java.util.Map;

public interface ChatMsgService {
//  조회용 날짜 변환
    List<ChatMsg> todatelist(ChatRoom chatRoom);
//  메시지 읽음 처리 및 조회
    List<ChatMsg> inviteChatRoom(ChatRoom cr);
//  메세지 insert 후 메세지 반환
    ChatMsg findsaveMsg(ChatMsg msg);
//  모든 메시지 조회 및 최신 메시지, 읽지 않은 메시지 수 반환
    KjoResponse cnttomsg(ChatRoom cr, UserInfo user, List<ChatRoom> chatRooms);

//<!--모든 메시지 조회-->
    List<ChatMsg> findAll();

//  메시지 읽음 처리
    int updateRead(ChatRoom cr);


//  사용자 별 읽지 않은 메세지
    List<ChatMsg> cntnoreadMsg(ChatRoom cr);

//  내가 읽지 않은 메시지
    int findnoReadMsg(List<ChatMsg> findmsg, ChatRoom cr);

//  채팅방 별 읽지 않은 메시지
    int findbyChatRoomMsg(List<ChatMsg> cm,ChatRoom cr,UserInfo ui);

//	최신 메시지가 있는 채팅방
    Map<Integer,ChatMsg> nowMsgs(List<ChatMsg> findmsg);


//-----------------not Use-----------------
//-----------------not Use-----------------
//-----------------not Use-----------------
//-----------------not Use-----------------
//<!--특정 채팅방 내 모든 메시지 조회-->
    List<ChatMsg> findByRoomId(ChatRoom cr);
//<!--메시지 저장-->
    int saveMsg(ChatMsg msg);
//<!--채팅방 내 총 메시지 개수-->
    int cntMsg(ChatMsg msg);
//  메시지 insert
    int cntsaveMsg(ChatMsg msg);

}
