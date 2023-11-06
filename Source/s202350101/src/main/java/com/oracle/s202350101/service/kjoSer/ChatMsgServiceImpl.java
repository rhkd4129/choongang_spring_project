package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatMsgDao;
import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMsgServiceImpl implements ChatMsgService {
    private final ChatMsgDao CMdao;
    @Override
    public List<ChatMsg> findAll() {
        return CMdao.findAll();
    }

    @Override
    public List<ChatMsg> findByRoomId(ChatRoom cr) {
        return CMdao.findByRoomId(cr);
    }
}
