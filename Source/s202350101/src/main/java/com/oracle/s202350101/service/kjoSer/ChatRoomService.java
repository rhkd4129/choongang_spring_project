package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoom> findAll();

    List<ChatRoom> findByUserId(UserInfo ui);

    ChatRoom findById(ChatRoom cr);

    ChatRoom findByYouAndMe(ChatRoom cr);

    ChatRoom findByYouAndMeNotEmpty(ChatRoom cr);
}
