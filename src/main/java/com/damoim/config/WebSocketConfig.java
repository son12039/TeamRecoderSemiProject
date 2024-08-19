package com.damoim.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@EnableWebSocketMessageBroker
@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	// WebSocketMessageBrokerConfigurer이 인터페이스는 웹소켓과 스텀프메세지 관련 메서드제공
	// chatting.js와 연결되는 메서드들임
	
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	// 클라이언트가 웹소켓연결을 시도할 URL 엔드포인트 등록
        registry.addEndpoint("/websocket").withSockJS();     
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    	// 누군가 topic으로 시작하는 경로로 보낸 메세지를 서버에게 받아서 수신할 수 있게 도와줌
        registry.enableSimpleBroker("/topic");

    }
}