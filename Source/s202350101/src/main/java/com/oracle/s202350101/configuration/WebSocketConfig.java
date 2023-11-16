package com.oracle.s202350101.configuration;

//import com.oracle.s202350101.handler.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker   //메세지 브로커가 지원하는 WebSocket 메시지 처리를 활성화 시킵니다. 
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    발행자, 구독자, 메시지 브로커 사이 통신 과정을 알아야한다.
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        연결할 소켓 앤드포인트를 등록하는 메서드
        registry.addEndpoint("/chat")
                .withSockJS();      // 웹 소캣을 지원하지 않는다면 sockJs를 사용함.

        registry.addEndpoint("/websocket")
                .withSockJS();
    }

//  바로 메시지를 전달하는 것이 아니라 메시지 브로커에게 전달하고
    //  브로커가 전달한다.
/*
                           request
                            ->
    HTTP 통신방식 : Client           Server
                            <-
                           response
----------------------------------------------------------

    폴링: ajax 비동기 통신을 주기적으로 요청하고 응답받는 것.
    SSE(Server-Sent Events): 서버에서 클라이언트로 변경이 필요한 데이터를 전송(푸쉬)하는 방식.
            단, 단방향 통신이다. ( 서버 -> 클라 )
    웹소캣:    TCP연결(양방향 통신 채널)
            1단계: 3-Way-HandShake를 통해 초기 통신을 시작.
            2단계: 웹소캣 프로토콜로 변환 -> 데이터를 전송.
            
            1단계 응답 : 101 Status 코드
                     : Connection: "Upgrade"
    ws 와 wss 의 차이 : wss는 시큐어 코딩이 가능하다.

    STOMP ( Simple Text Oriented Messaging Protocol )
        구독 : /sub/channel
        발생 : /pub/hello
            /pub/hello(channelId:"no01") 라면,
            /sub/channel/ch01 <- 채널에선 메시지를 받을 수 있다.
            /sub/channel/ch02 <- 여기선 메시지를 받을 수 없다.
        EX_ 아파트나 빌라에 있는 우편함을 떠올려보자.

 */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//      @MessageMapping 주석이 달린 메서드에 바인딩된 메시지의 "/app" 접두사를 지정합니다.
//      메시지 전송할 상위 주소
//      /queue으로 시작하는 메시지만 해당 Broker에서 받아서 처리
//      통합시 "/queue/chat", "/queue/notiry"의 형식으로 하는 것 제시하기.
        registry.setApplicationDestinationPrefixes("/queue");       //  app
        /*      pub => publish || sub => subscribe    */
//      "/topic" 하위로 들어오는 모든 주소로 메세지를 전송하겠다는 설정입니다.
//      해당 주소를 구독하는 클라이언트에게 메시지를 보낸다
//      통합시 "/sub/chat", "/sub/notiry"의 형식으로 하는 것 제시하기.
//        registry.enableSimpleBroker("/noti");
        registry.enableSimpleBroker("/app","/noti");        //  queue
    }
}
