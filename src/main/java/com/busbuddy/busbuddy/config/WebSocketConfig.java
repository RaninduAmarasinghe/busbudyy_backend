package com.busbuddy.busbuddy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/location"); // Topic prefix for subscribers
        config.setApplicationDestinationPrefixes("/app"); // Prefix for messages from client
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-location") // WebSocket endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Enable fallback for browsers that don't support WebSocket
    }
}