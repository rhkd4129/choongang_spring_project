package com.oracle.s202350101.service.kjoSer;

import com.oracle.s202350101.dao.kjoDao.ChatMsgDao;
import com.oracle.s202350101.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ChatMsgServiceImpl implements ChatMsgService {
    private final ChatMsgDao CMdao;
    private final UserInfoService UIser;
//  조회용 날짜로 변경하여 반환
    @Override
    public List<ChatMsg> todatelist(ChatRoom chatRoom) {
        List<ChatMsg> CMList =  CMdao.findByRoomId(chatRoom);

        SimpleDateFormat newDtFormat = new SimpleDateFormat("yy.MM.dd a HH:mm");
        for (int i = 0; i < CMList.size(); i++) {
            Date time = CMList.get(i).getSend_time();
            CMList.get(i).setShow_time(newDtFormat.format(time));
        }
        return CMList;
    }
//    상대방 메시지 읽음처리
    @Override
    public int updateRead(ChatRoom cr) {
        int result = 0;
        try{
            result = CMdao.updateRead(cr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
//  메시지 읽음 처리 및 조회
    @Override
    public List<ChatMsg> inviteChatRoom(ChatRoom cr) {
        List<ChatMsg> ChatList = null;
        try {
            //    상대방 메시지 읽음처리
            updateRead(cr);
            //  조회용 날짜로 변경하여 반환
            ChatList = todatelist(cr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ChatList;
    }
//  메세지 insert 후 메세지 반환
    @Override
    public ChatMsg findsaveMsg(ChatMsg msg) {
        ChatMsg cm = null;
        /*------------------비즈니스 로직--------------------*/
        msg.setRead_chk("N");
        //  채팅방 내 메세지 개수
        msg.setMsg_id(CMdao.cntMsg(msg) + 1);
        //  메세지 insert 후 메세지 pk 반환
        CMdao.saveMsg(msg);
        //  해당 pk를 지닌 메세지 select
        cm = CMdao.findbyCMid(msg);
        /*------------------비즈니스 로직--------------------*/
        return cm;
    }
    //  모든 메시지 조회 및 최신 메시지, 읽지 않은 메시지 수 반환
    @Override
    public KjoResponse cnttomsg(ChatRoom cr, UserInfo user, List<ChatRoom> chatRooms) {
        KjoResponse res = new KjoResponse();
//		내가 읽지 않은 메시지 수
        int noreadC =0;
//		나의 모든 메시지           //  사용자와 관련된 모든 메시지 조회하는 쿼리
        List<ChatMsg> findmsg = cntnoreadMsg(cr);
//		최신 메시지가 있는 채팅방
        Map<Integer,ChatMsg> msgchats = nowMsgs(findmsg);
//		나의 채팅방에 최근 메시지 및 시간 추가
//      하나의 속성으로 반환하기 위함.
        for(Integer key : msgchats.keySet()){
            for (ChatRoom crr : chatRooms) {
                int cri = crr.getChat_room_id();
                if (cri == key) {       //  메시지가 있는 채팅방id == 나의 채팅방id
                    crr.setMsg_con(msgchats.get(key).getMsg_con());
                    crr.setShow_time(msgchats.get(key).getShow_time());
                }
            }
        }
//      채팅 상태방의 프로필 사진을 가져오기 위함.
        for (ChatRoom chatR : chatRooms) {
            int noreadCnt = findbyChatRoomMsg(findmsg, chatR,user);
            UserInfo us = new UserInfo();
            String usID = (Objects.equals(user.getUser_id(), chatR.getReceiver_id()))? chatR.getSender_id() : chatR.getReceiver_id();
            us.setUser_id(usID);
            us = UIser.findbyuserId(us);

            chatR.setUser_name(us.getUser_name());
            chatR.setAttach_name(us.getAttach_name());
            chatR.setAttach_path(us.getAttach_path());
            chatR.setRead_cnt(noreadCnt);

            noreadC += noreadCnt;
        }

//		나의 채팅방
        res.setSecList(chatRooms);

//		내가 읽지 않은 메시지 수
        res.setObj(noreadC);

        return res;
    }

//  사용자 별 읽지 않은 메세지
    @Override
    public List<ChatMsg> cntnoreadMsg(ChatRoom cr) {
        List<ChatMsg> result = null;
        try{
            result = CMdao.findbyuseridnoRead(cr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
//		최신 메시지
    @Override
    public Map<Integer, ChatMsg> nowMsgs(List<ChatMsg> findmsg) {
        Map<Integer, ChatMsg> msg = new HashMap<>();
        Collections.reverse(findmsg);

        for (ChatMsg cm : findmsg) {
            if (msg.get(cm.getChat_room_id()) == null) {
                SimpleDateFormat newDtFormat = new SimpleDateFormat("yy.MM.dd a HH:mm");
                Date time = cm.getSend_time();
                cm.setShow_time(newDtFormat.format(time));

                msg.put(cm.getChat_room_id(), cm);
            }

        }
        return msg;
    }
//  내가 읽지 않은 메시지
    @Override
    public int findnoReadMsg(List<ChatMsg> findmsg, ChatRoom cr) {
        int result = 0;
        List<ChatMsg> noreadMsgs = new ArrayList<>();
        for (ChatMsg cm : findmsg) {
            if (!cm.getMsgsender().equals(cr.getSender_id()) && cm.getRead_chk().equals("N")) {
//                log.info("TRUE: "+cm.getMsgsender());
                noreadMsgs.add(cm);
            }
//            log.info("False: " + cm.getMsgsender());
        }
        return noreadMsgs.size();
    }

//  채팅방 별 읽지 않은 메시지
    @Override
    public int findbyChatRoomMsg(List<ChatMsg> cm,ChatRoom cr,UserInfo ui) {
        List<ChatMsg> noreadMsgs = new ArrayList<>();
        for (ChatMsg chatMsg : cm) {
            //  1.  채팅방 id와 메시지의 채팅방id가 같고,
            //  2.  메시지 전송자id와 접속자 id가 같지 않고,
            //  3.  읽지 않은 메시지 일 때.
            if (cr.getChat_room_id() == chatMsg.getChat_room_id() && !chatMsg.getMsgsender().equals(ui.getUser_id()) && chatMsg.getRead_chk().equals("N")) {
                noreadMsgs.add(chatMsg);
            }
        }
        return noreadMsgs.size();
    }
//<!--모든 메시지 조회-->
    @Override
    public List<ChatMsg> findAll() {
        return CMdao.findAll();
    }


//-----------------not Use-----------------
//-----------------not Use-----------------
//-----------------not Use-----------------
//-----------------not Use-----------------


//<!--특정 채팅방 내 모든 메시지 조회-->
    @Override
    public List<ChatMsg> findByRoomId(ChatRoom cr) {
        return CMdao.findByRoomId(cr);
    }
//<!--메시지 저장-->
    @Override
    public int saveMsg(ChatMsg msg) {
        return CMdao.saveMsg(msg);
    }
//<!--채팅방 내 총 메시지 개수-->
    @Override
    public int cntMsg(ChatMsg msg) {
        return CMdao.cntMsg(msg);
    }
//  메시지 insert
    @Override
    public int cntsaveMsg(ChatMsg msg) {
        int result = 0;
        msg.setMsg_id(CMdao.cntMsg(msg) + 1);
        result = CMdao.saveMsg(msg);
        return result;
    }

}
