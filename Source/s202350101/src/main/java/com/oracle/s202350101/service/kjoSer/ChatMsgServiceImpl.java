package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatMsgDao;
import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;
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
public class ChatMsgServiceImpl implements ChatMsgService {
    private final ChatMsgDao CMdao;

    private final PlatformTransactionManager transactionManager;
    @Override
    public List<ChatMsg> findAll() {
        return CMdao.findAll();
    }

    @Override
    public List<ChatMsg> findByRoomId(ChatRoom cr) {
        return CMdao.findByRoomId(cr);
    }

    @Override
    public int saveMsg(ChatMsg msg) {
        return CMdao.saveMsg(msg);
    }

    @Override
    public int cntMsg(ChatMsg msg) {
        return CMdao.cntMsg(msg);
    }

    @Override
    public int cntsaveMsg(ChatMsg msg) {
        int result = 0;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            msg.setMsg_id(CMdao.cntMsg(msg) + 1);
            result = CMdao.saveMsg(msg);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public ChatMsg findsaveMsg(ChatMsg msg) {
        ChatMsg cm = null;
        TransactionStatus txStatus =
                transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            //  채팅방 내 메세지 개수
            msg.setMsg_id(CMdao.cntMsg(msg) + 1);
            //  메세지 insert 후 메세지 pk 반환
            CMdao.saveMsg(msg);
            //  해당 pk를 지닌 메세지 select
            cm = CMdao.findbyid(msg);
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            throw new RuntimeException(e);
        }
        return cm;
    }


}
