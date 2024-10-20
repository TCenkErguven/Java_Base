package com.java.base.config.session;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionEventListener {
    private static final Logger LOGGER = LogManager.getLogger("Session-Event-Listener");

    @EventListener
    public void processSessionCreatedEvent(SessionCreatedEvent event) {
        // do the necessary work
        LOGGER.info("Session create successfully, with SESSION ID: {}", event.getSessionId());
    }

    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        // do the necessary work
        LOGGER.info("Session deleted successfully, with SESSION ID: {}", event.getSessionId());
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        // do the necessary work
        LOGGER.error("Session destroyed event, with SESSION ID: {}", event.getSessionId());
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        // do the necessary work
        LOGGER.warn("Session expired event, with SESSION ID: {}", event.getSessionId());
    }
}
