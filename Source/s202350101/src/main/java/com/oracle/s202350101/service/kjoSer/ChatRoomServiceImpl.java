package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatRoomDao;
import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomDao CHdao;

    @Override
    public List<ChatRoom> findAll() {
        return CHdao.findAll();
    }

    @Override
    public List<ChatRoom> findByUserId(UserInfo ui) {
        return CHdao.findByUserId(ui);
    }

    @Override
    public ChatRoom findById(ChatRoom cr) {
        return CHdao.findById(cr);
    }

    @Override
    public ChatRoom findByYouAndMe(ChatRoom cr) {
        return CHdao.findByYouAndMe(cr);
    }
}
