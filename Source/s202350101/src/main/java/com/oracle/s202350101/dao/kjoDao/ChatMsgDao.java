package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;

import java.util.List;

public interface ChatMsgDao {
    List<ChatMsg> findAll();
    List<ChatMsg> findByRoomId(ChatRoom cr);
}
