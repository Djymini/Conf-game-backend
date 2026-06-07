package com.loutredev.conf_game_backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.loutredev.conf_game_backend.domain.services.SessionService;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.session.SessionResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.SessionQueryDTO;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import com.loutredev.conf_game_backend.persistence.repositories.SessionRepository;
import com.loutredev.conf_game_backend.persistence.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=integration")
@ActiveProfiles("integration")
@Transactional
public class SessionServiceIntegrationTest {

    @Autowired private SessionService sessionService;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ConferenceRepository conferenceRepository;

    private UUID userId;
    private Long conferenceId;

    @BeforeEach
    void setUp() {
        sessionRepository.deleteAll();
        conferenceRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = new UserEntity();
        user.setEmail("bingouser@test.com");
        user.setUsername("Bingo");
        user.setPassword("SecuredPass123");
        userId = userRepository.save(user).getId();

        ConferenceEntity conference = new ConferenceEntity();
        conference.setName("Main Track");
        conference.setDescription("Desc");
        conference.setOrganisator("Org");
        conference.setStreamDate(LocalDateTime.now().plusDays(1));
        conference.setLinkStream("http://stream");
        conference.setImageUrl("http://img");
        conferenceId = conferenceRepository.save(conference).getId();
    }

    @Test
    void create_shouldPersistSession_andApplyRules() {
        SessionRequestCreateDTO dto = new SessionRequestCreateDTO("INCOMMING", 100, conferenceId, userId);

        SessionResponseDTO saved = sessionService.create(dto);

        assertThat(saved.id()).isNotNull();
        assertThat(sessionRepository.existsById(saved.id())).isTrue();
        assertThat(saved.status()).isEqualTo("INCOMMING");
    }

    @Test
    void update_shouldModifyStatusAndParticipants() {
        SessionResponseDTO created = sessionService.create(new SessionRequestCreateDTO("INCOMMING", 100, conferenceId, userId));
        SessionRequestUpdateDTO updateDto = new SessionRequestUpdateDTO(created.id(), "REDIFFUSION", 150, 10, conferenceId);

        SessionResponseDTO updated = sessionService.update(updateDto);

        assertThat(updated.status()).isEqualTo("REDIFFUSION");
        assertThat(updated.limitParticipant()).isEqualTo(150);
        assertThat(updated.participant()).isEqualTo(10);
    }

    @Test
    void getAllByUserId_shouldReturnMatchingUserSessions() {
        sessionService.create(new SessionRequestCreateDTO("INCOMMING", 50, conferenceId, userId));

        SessionQueryDTO query = new SessionQueryDTO();
        query.page = 0;

        Page<SessionEntity> result = sessionService.getAllByUserId(userId, query);
        assertThat(result.getContent()).hasSize(1);
    }
}
