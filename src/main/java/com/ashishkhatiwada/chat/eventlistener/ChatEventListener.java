package com.ashishkhatiwada.chat.eventlistener;

import com.ashishkhatiwada.chat.util.UserCacheUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Log4j2
public class ChatEventListener {
    @EventListener
    public void onDisconnect(SessionDisconnectEvent disconnectEvent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
        String userId = (String) accessor.getSessionAttributes().get("userId");
        if (StringUtils.hasText(userId)) {
            log.info("User {} disconnected from chat", userId);
            UserCacheUtil.removeUserId(userId);
        }
    }
}
