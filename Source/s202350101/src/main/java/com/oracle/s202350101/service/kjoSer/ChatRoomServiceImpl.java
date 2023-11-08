package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatRoomDao;
import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomDao CHdao;
    private final PlatformTransactionManager transactionManager;

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

    @Override
    public ChatRoom findByYouAndMeNotEmpty(ChatRoom cr) {
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        ChatRoom findChatRoom = null;
        try {
            findChatRoom = CHdao.findByYouAndMe(cr);
            if (findChatRoom == null) {
                log.info("findByYouAndMeNotEmpty empty cr: "+cr.toString());
                CHdao.addChatRoom(cr);
                findChatRoom = cr;
                findChatRoom.setChat_room_id(cr.getChat_room_id());
                findChatRoom.setSender_id(cr.getSender_id());
                findChatRoom.setReceiver_id(cr.getReceiver_id());
                findChatRoom.setUser_name(cr.getUser_name());
            }
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            log.info("findByYouAndMeNotEmpty Error" + e.getMessage());
            transactionManager.rollback(txStatus);
        }
        return findChatRoom;
    }



}
