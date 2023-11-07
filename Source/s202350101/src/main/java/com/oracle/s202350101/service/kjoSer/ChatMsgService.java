package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;

import java.util.List;

public interface ChatMsgService {
    List<ChatMsg> findAll();

    List<ChatMsg> findByRoomId(ChatRoom cr);

    int saveMsg(ChatMsg msg);

    int cntMsg(ChatMsg msg);

    int cntsaveMsg(ChatMsg msg);
}
