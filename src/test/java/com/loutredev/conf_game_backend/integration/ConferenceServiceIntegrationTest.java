package com.loutredev.conf_game_backend.integration;


import static org.assertj.core.api.Assertions.assertThat;

import com.loutredev.conf_game_backend.domain.services.ConferenceService;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.conference.ConferenceResponseDTO;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = "spring.profiles.active=integration")
@ActiveProfiles("integration")
@Transactional
public class ConferenceServiceIntegrationTest {

    @Autowired private ConferenceService conferenceService;
    @Autowired private ConferenceRepository conferenceRepository;

    @BeforeEach
    void setUp() {
        conferenceRepository.deleteAll();
    }

    @Test
    void create_shouldPersistConference() {
        ConferenceRequestCreateDTO dto = new ConferenceRequestCreateDTO(
                "Java Conf 2026", "Description Tech", "Loutre Dev",
                LocalDateTime.now().plusDays(5), "http://stream.link", "http://image.url", "INCOMMING"
        );

        ConferenceResponseDTO saved = conferenceService.create(dto);

        assertThat(saved.id()).isNotNull();
        assertThat(conferenceRepository.existsById(saved.id())).isTrue();
    }

    @Test
    void getById_shouldReturnConference_whenExists() {
        ConferenceRequestCreateDTO dto = new ConferenceRequestCreateDTO(
                "Quick Conf", "Desc", "Speaker", LocalDateTime.now().plusDays(2), "http://link", "http://img", "INCOMMING"
        );
        ConferenceResponseDTO created = conferenceService.create(dto);

        ConferenceResponseDTO found = conferenceService.getById(created.id());

        assertThat(found.id()).isEqualTo(created.id());
        assertThat(found.name()).isEqualTo("Quick Conf");
    }

    @Test
    void update_shouldModifyPersistedConference() {
        ConferenceRequestCreateDTO dto = new ConferenceRequestCreateDTO(
                "Old Name", "Desc", "Speaker", LocalDateTime.now().plusDays(2), "http://link", "http://img", "INCOMMING"
        );
        ConferenceResponseDTO created = conferenceService.create(dto);

        ConferenceRequestUpdateDTO updateDto = new ConferenceRequestUpdateDTO(
                created.id(), "New Name", "Desc", "Speaker", LocalDateTime.now().plusDays(2), "http://link", "http://img", "INCOMMING", 50
        );

        ConferenceResponseDTO updated = conferenceService.update(updateDto);

        assertThat(updated.name()).isEqualTo("New Name");
        ConferenceEntity entity = conferenceRepository.findById(created.id()).get();
        assertThat(entity.getWatchNumber()).isEqualTo(50);
    }

    @Test
    void delete_shouldRemoveConference() {
        ConferenceRequestCreateDTO dto = new ConferenceRequestCreateDTO(
                "To Delete", "Desc", "Speaker", LocalDateTime.now().plusDays(2), "http://link", "http://img", "INCOMMING"
        );
        ConferenceResponseDTO created = conferenceService.create(dto);

        conferenceService.delete(created.id());

        assertThat(conferenceRepository.existsById(created.id())).isFalse();
    }
}