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
}
