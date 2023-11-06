package com.oracle.s202350101.handler;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {

    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();

    HashMap<String, String> sessionUserMap = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String msg = message.getPayload();
        log.info("handleTextMessage msg -->> " + msg);
        JSONObject jsonObj = jsonToObjectParser(msg);
        String msgType = (String) jsonObj.get("type");

        log.info("handleTextMessage");
        super.handleTextMessage(session, message);

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        sessionMap.put(session.getId(), session);
        //  세션에
        log.info("session is : "+ session);
//		session is : StandardWebSocketSession[id=8787aa7f-ab13-f9d9-dbcf-342e1c381052, uri=ws://localhost:8188/chating]
        log.info("session ID is : "+ session.getId());
//		session ID is : 8787aa7f-ab13-f9d9-dbcf-342e1c381052
        log.info("session toString is : "+ session.toString());
//		session toString is : StandardWebSocketSession[id=8787aa7f-ab13-f9d9-dbcf-342e1c381052, uri=ws://localhost:8188/chating]

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionMap.remove(session.getId());
        super.afterConnectionClosed(session, status);
    }


    // handleTextMessage 메소드 에 JSON파일이 들어오면 파싱해주는 함수를 추가
    private static JSONObject jsonToObjectParser(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObj;
    }
}
