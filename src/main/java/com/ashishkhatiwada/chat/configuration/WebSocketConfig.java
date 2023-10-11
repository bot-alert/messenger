package com.ashishkhatiwada.chat.configuration;


import com.ashishkhatiwada.chat.util.UserCacheUtil;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.Assert;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Log4j2
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .setHandshakeHandler(new WebSocketHeaderInterceptor())
                .withSockJS();
        registry.addEndpoint("/ws")
                .setHandshakeHandler(new WebSocketHeaderInterceptor())
                .setAllowedOriginPatterns("*");

    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }

    public static class WebSocketHeaderInterceptor extends DefaultHandshakeHandler {
        @Override
        protected Principal determineUser(ServerHttpRequest request, @NonNull WebSocketHandler wsHandler, @NonNull Map<String, Object> attributes) {

            UriComponents uriComponents = UriComponentsBuilder
                    .fromUri(request.getURI()).build();

            String userId = uriComponents.getQueryParams().getFirst("userId");

            Assert.hasText(userId, "Cannot find userId in request param");

            log.info("User {} connected from chat", userId);

            attributes.put("userId", userId);

            UserCacheUtil.add(userId);

            return new MyPrinciple(userId);
        }
    }

    record MyPrinciple(String userId) implements Principal {
        @Override
        public String getName() {
            return userId;
        }
    }
}