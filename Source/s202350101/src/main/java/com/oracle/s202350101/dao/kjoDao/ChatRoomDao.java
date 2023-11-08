package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;

import java.util.List;

public interface ChatRoomDao {


    List<ChatRoom> findAll();

    List<ChatRoom> findByUserId(UserInfo ui);

    ChatRoom findById(ChatRoom cr);

    ChatRoom findByYouAndMe(ChatRoom cr);

    int addChatRoom(ChatRoom cr);
}
