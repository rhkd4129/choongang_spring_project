package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class ChatMsgDaoImpl implements ChatMsgDao{

    private final SqlSession session;


    @Override
    public List<ChatMsg> findAll() {
        log.info("findAll start");
        List<ChatMsg> CMList = null;
        try {
            CMList = session.selectList("findAll");

        }catch (Exception e) {
            System.out.println("findAll Error -->>" + e.getMessage());
        }
        return CMList;
    }

    @Override
    public List<ChatMsg> findByRoomId(ChatRoom cr) {
        log.info("findByRoomId start");
        List<ChatMsg> CMList = null;
        try {
            CMList = session.selectList("findByRoomId",cr);

        }catch (Exception e) {
            System.out.println("findByRoomId Error -->>" + e.getMessage());
        }

        return CMList;
    }

    @Override
    public int saveMsg(ChatMsg msg) {
        log.info("saveMsg start");
        log.info("MSG: "+msg.toString());
//        MSG: ChatMsg(chat_room_id=1, msg_id=48, sender_id=tester1, msg_con=ㄹㅇㅁㄴ, send_time=2023-11-08 09:00:07.851, read_chk=null)
        int result = 0;
        try {
            result = session.insert("saveMsg",msg);

        }catch (Exception e) {
            System.out.println("findByRoomId Error -->>" + e.getMessage());
        }

        return result;
    }

    @Override
    public int cntMsg(ChatMsg msg) {
        log.info("cntMsg start");
        int result = 0;
        try {
            result = session.selectOne("cntMsg",msg);

        }catch (Exception e) {
            System.out.println("cntMsg Error -->>" + e.getMessage());
        }

        return result;
    }

    @Override
    public ChatMsg findbyid(ChatMsg msg) {
        log.info("findbyid start");
        ChatMsg cm = new ChatMsg();
        try {
            cm = session.selectOne("findbyid",msg);

        }catch (Exception e) {
            System.out.println("findbyid Error -->>" + e.getMessage());
        }
        return cm;
    }
}
