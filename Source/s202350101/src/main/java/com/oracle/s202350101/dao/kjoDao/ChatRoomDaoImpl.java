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
    public List<ChatRoom> findAll() {

        log.info("findAll start");
        List<ChatRoom> CRList = null;
        try {
            log.info("findAll");
            CRList = session.selectList("findAll");
            //	결과	출력

        } catch (Exception e) {
            System.out.println("findAll Error -->>" + e.getMessage());
        }

        return CRList;
    }

    @Override
    public List<ChatRoom> findByUserId(UserInfo ui) {
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
    public ChatRoom findById(ChatRoom cr) {
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
    public ChatRoom findByYouAndMe(ChatRoom cr) {
        log.info("findByYouAndMe start");
        ChatRoom CRList = null;
        try {
            CRList = session.selectOne("findByYouAndMe",cr);

        }catch (Exception e) {
            System.out.println("findByYouAndMe Error -->>" + e.getMessage());
        }

        return CRList;
    }
}
