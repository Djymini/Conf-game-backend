package com.loutredev.conf_game_backend.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.loutredev.conf_game_backend.domain.services.NoteService;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestCreateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteRequestUpdateDTO;
import com.loutredev.conf_game_backend.exposition.dtos.note.NoteResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.NoteQueryDTO;
import com.loutredev.conf_game_backend.persistence.entities.ConferenceEntity;
import com.loutredev.conf_game_backend.persistence.entities.NoteEntity;
import com.loutredev.conf_game_backend.persistence.entities.SessionEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.ConferenceRepository;
import com.loutredev.conf_game_backend.persistence.repositories.NoteRepository;
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
public class NoteServiceIntegrationTest {

    @Autowired private NoteService noteService;
    @Autowired private NoteRepository noteRepository;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private ConferenceRepository conferenceRepository;

    private UUID userId;
    private Long sessionId;

    @BeforeEach
    void setUp() {
        noteRepository.deleteAll();
        sessionRepository.deleteAll();
        conferenceRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = new UserEntity();
        user.setEmail("bingouser@test.com");
        user.setUsername("Bingo");
        user.setPassword("SecuredPass123");
        userId = userRepository.save(user).getId();

        ConferenceEntity conference = new ConferenceEntity();
        conference.setName("Conference for Note");
        conference.setDescription("Desc");
        conference.setOrganisator("Org");
        conference.setStreamDate(LocalDateTime.now().plusDays(1));
        conference.setLinkStream("http://link");
        conference.setImageUrl("http://img");
        conferenceRepository.save(conference);

        SessionEntity session = new SessionEntity();
        session.setConference(conference);
        session.setUsers(user);
        sessionId = sessionRepository.save(session).getId();
    }

    @Test
    void create_shouldPersistNote() {
        NoteRequestCreateDTO dto = new NoteRequestCreateDTO("My First Note", "This content is relevant.", sessionId, userId);

        NoteResponseDTO saved = noteService.create(dto);

        assertThat(saved.id()).isNotNull();
        assertThat(noteRepository.existsById(saved.id())).isTrue();
        assertThat(saved.title()).isEqualTo("My First Note");
    }

    @Test
    void update_shouldModifyTitleAndContent() {
        NoteResponseDTO created = noteService.create(new NoteRequestCreateDTO("Old Title", "Old Content", sessionId, userId));
        NoteRequestUpdateDTO updateDto = new NoteRequestUpdateDTO(created.id(), "New Title", "New Content");

        NoteResponseDTO updated = noteService.update(updateDto);

        assertThat(updated.title()).isEqualTo("New Title");
        assertThat(updated.content()).isEqualTo("New Content");
    }

    @Test
    void getAllBySessionId_shouldReturnPagedNotes() {
        noteService.create(new NoteRequestCreateDTO("Note 1", "Content 1", sessionId, userId));
        noteService.create(new NoteRequestCreateDTO("Note 2", "Content 2", sessionId, userId));

        NoteQueryDTO query = new NoteQueryDTO();
        query.page = 0;

        Page<NoteEntity> pageResult = noteService.getAllBySessionId(sessionId, query);

        assertThat(pageResult.getContent()).hasSize(2);
    }

    @Test
    void delete_shouldRemoveNote() {
        NoteResponseDTO created = noteService.create(new NoteRequestCreateDTO("To Delete", "Content", sessionId, userId));

        noteService.delete(created.id());

        assertThat(noteRepository.existsById(created.id())).isFalse();
    }
}
