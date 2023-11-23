package com.oracle.s202350101.dao.kjoDao;

import com.oracle.s202350101.model.BdFree;
import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.UserInfo;
import com.oracle.s202350101.service.kjoSer.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ChatRoomDaoImpl implements ChatRoomDao {
    private final SqlSession session;
    @Override
    public ChatRoom findByYouAndMe(ChatRoom cr) {
//<!--상대방과의 채팅방 조회-->
        log.info("findByYouAndMe start");
        try {
            cr = session.selectOne("findByYouAndMe",cr);

        }catch (Exception e) {
            System.out.println("findByYouAndMe Error -->>" + e.getMessage());
        }

        return cr;
    }
    @Override
    public List<ChatRoom> findByUserId(UserInfo ui) {
//<!--개인별 채팅방 조회-->
        log.info("findByUserId start");
        List<ChatRoom> CRList = null;
        try {

            CRList = session.selectList("findByUserId",ui);
            //	결과	출력
        }catch (Exception e) {
            System.out.println("findByUserId Error -->>" + e.getMessage());
        }
        return CRList;
}
    @Override
    public List<ChatRoom> findAll() {
//<!--모든 채팅방 조회-->

        log.info("findAll start");
        List<ChatRoom> CRList = null;
        try {
            log.info("findAll");
            CRList = session.selectList("findAllchatroom");
            //	결과	출력

        } catch (Exception e) {
            System.out.println("findAll Error -->>" + e.getMessage());
        }

        return CRList;
    }


    @Override
    public ChatRoom findById(ChatRoom cr) {
//<!--강의실 개수 조회-->
        log.info("findById start");
        ChatRoom CRList = null;
        try {
            log.info("findById" + cr.getChat_room_id());
            CRList = session.selectOne("findById",cr);
            //	결과	출력

        }catch (Exception e) {
            System.out.println("findById Error -->>" + e.getMessage());
        }

        return CRList;
    }


    @Override
    public int addChatRoom(ChatRoom cr) {
//<!--시퀀스 적용해 메시지 저장, 시퀀스 값 반환-->
        log.info("addChatRoom start");
        int result = 0;
        try {
            result = session.insert("addChatRoom", cr);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
