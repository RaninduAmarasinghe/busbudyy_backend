package com.busbuddy.busbuddy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/location", "/topic"); // ✅ Added /topic for alerts
        config.setApplicationDestinationPrefixes("/app"); // Messages from client to backend
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-location") // WebSocket endpoint
                .setAllowedOriginPatterns("*")
                .withSockJS(); // Enables fallback (e.g. for mobile browsers)
    }
}