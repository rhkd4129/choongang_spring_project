package com.oracle.s202350101.configuration;

import com.oracle.s202350101.handler.SocketHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker   //메세지 브로커가 지원하는 WebSocket 메시지 처리를 활성화 시킵니다.
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        연결할 소켓 앤드포인트를 등록하는 메서드
        registry.addEndpoint("/chat")
                .withSockJS();      // 웹 소캣을 지원하지 않는다면 sockJs를 사용함.
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//      @MessageMapping 주석이 달린 메서드에 바인딩된 메시지의 "/app" 접두사를 지정합니다.
//      메시지 전송할 상위 주소
        registry.setApplicationDestinationPrefixes("/app");

//      "/topic" 하위로 들어오는 모든 주소로 메세지를 전송하겠다는 설정입니다.
        registry.enableSimpleBroker("/queue");
    }
}
