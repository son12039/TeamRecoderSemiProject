package com.damoim.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketInterceptor implements ChannelInterceptor {

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		System.out.println("preSend!");
		//StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

		//if (accessor.getCommand() == StompCommand.CONNECT) {
			System.out.println("socket!!!!!!!!!");
			// SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//}
		return message;
	}

}
