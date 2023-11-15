//package com.oracle.s202350101.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class NotifyConfig implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//		// TODO Auto-generated method stub
//		// 연결할 소켓 엔드포인트 지정
//		registry.addEndpoint("/websocket").withSockJS();
//	}
//
//	@Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//		// TODO Auto-generated method stub
//		// queue = 일대일 , topic = 일대다
//		registry.enableSimpleBroker("/noti");					// 받을 곳 (sendTo / subscribe)
//        registry.setApplicationDestinationPrefixes("/queue");	// 전송할 곳 (send)
//	}
//}
