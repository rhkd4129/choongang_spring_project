package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatMsgDao;
import com.oracle.s202350101.dao.kjoDao.ChatRoomDao;
import com.oracle.s202350101.model.ChatMsg;
import com.oracle.s202350101.model.ChatRoom;
import com.oracle.s202350101.model.KjoResponse;
import com.oracle.s202350101.model.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomDao CHdao;
    private final ChatMsgDao CMdao;
//<!--상대방과의 채팅방 조회 후 없으면 채팅방 생성-->
    @Override
    public ChatRoom findByYouAndMeNotEmpty(ChatRoom cr) {
        int result = 0;
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
        } catch (Exception e) {
            log.info("findByYouAndMeNotEmpty Error" + e.getMessage());
        }
        return findChatRoom;
    }
//<!--상대방과의 채팅방 조회-->
    @Override
    public ChatRoom findByYouAndMe(ChatRoom cr) {
        return CHdao.findByYouAndMe(cr);
    }
//<!--개인별 채팅방 조회-->
    @Override
    public List<ChatRoom> findByUserId(UserInfo ui) {
        List<ChatRoom> crList = CHdao.findByUserId(ui);
        return crList;
    }



//<!--모든 채팅방 조회-->
    @Override
    public List<ChatRoom> findAll() {
        return CHdao.findAll();
    }
//<!--강의실 개수 조회-->
    @Override
    public ChatRoom findById(ChatRoom cr) {
        return CHdao.findById(cr);
    }
//  사용자 별 메세지가 있는 채팅방
    @Override
    public Map<?, ?> findByUserIdV2(List<ChatMsg> findmsg, ChatRoom cr) {
        KjoResponse res = new KjoResponse();
        Map<Integer, ChatRoom> map = new HashMap<>();

        for (ChatMsg cm : findmsg) {
            int cri = cm.getChat_room_id();
            if (map.get(cri) == null) {
                String senderID = cm.getSender_id();
                String recID = cm.getReceiver_id();
                ChatRoom chr = new ChatRoom();
                chr.setChat_room_id(cri);
                chr.setSender_id(senderID);
                chr.setReceiver_id(recID);

                map.put(cri, chr);
            }
        }
        return map;
    }

}
