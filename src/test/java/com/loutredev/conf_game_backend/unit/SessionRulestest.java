package com.loutredev.conf_game_backend.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.loutredev.conf_game_backend.domain.rules.SessionRules;
import com.loutredev.conf_game_backend.enums.SessionStatus;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SessionRulesTest {

    @Mock
    private ConferenceEntity conferenceMock;

    private SessionEntity session;

    @BeforeEach
    void setUp() {
        session = new SessionEntity();
        session.setConference(conferenceMock);
    }

    @Test
    void should_setStatusToRediffusion_when_streamDateIsInThePast() {
        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        when(conferenceMock.getStreamDate()).thenReturn(pastDate);
        SessionRules.customStatusForCreation(session);
        assertEquals(SessionStatus.REDIFFUSION, session.getStatus(),
                "Le statut devrait être REDIFFUSION");
    }

    @Test
    void should_setStatusToIncomming_when_streamDateIsInTheFuture() {
        LocalDateTime futureDate = LocalDateTime.now().plusDays(1);
        when(conferenceMock.getStreamDate()).thenReturn(futureDate);
        SessionRules.customStatusForCreation(session);
        assertEquals(SessionStatus.INCOMMING, session.getStatus(),
                "Le statut devrait être INCOMMING");
    }
}
