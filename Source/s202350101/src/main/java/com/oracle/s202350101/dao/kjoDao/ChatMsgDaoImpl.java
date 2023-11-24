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
    //<!--특정 채팅방 내 모든 메시지 조회-->
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
//  메시지 읽음처리
    @Override
    public int updateRead(ChatRoom cr) {
        log.info("updateRead start");
        int result = 0;
        try {
            result = session.update("updateRead", cr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    //<!--채팅방 내 총 메시지 개수-->
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
    //<!--메시지 저장-->
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
    //<!--채팅방, 메시지 id기준 메시지 조회-->
    @Override
    public ChatMsg findbyCMid(ChatMsg msg) {
        log.info("findbyCMid start");
        ChatMsg cm = new ChatMsg();
        try {
            cm = session.selectOne("findbyCMid",msg);

        }catch (Exception e) {
            System.out.println("findbyCMid Error -->>" + e.getMessage());
        }
        return cm;
    }

//<!--모든 메시지 조회-->
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

    //  사용자와 연관된 모든 메시지
    public List<ChatMsg> findbyuseridnoRead(ChatRoom cr) {
        log.info("updateRead start");
        List<ChatMsg> result = null;
        try {
            result = session.selectList("findMyAllMessage", cr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }


}
