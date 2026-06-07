package com.loutredev.conf_game_backend.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.loutredev.conf_game_backend.domain.services.BingoService;
import com.loutredev.conf_game_backend.exceptions.ressource.UserNotFoundException;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestCreatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoRequestUpdatedDTO;
import com.loutredev.conf_game_backend.exposition.dtos.bingo.BingoResponseDTO;
import com.loutredev.conf_game_backend.exposition.dtos.queries.BingoQueryDTO;
import com.loutredev.conf_game_backend.persistence.entities.BingoEntity;
import com.loutredev.conf_game_backend.persistence.entities.UserEntity;
import com.loutredev.conf_game_backend.persistence.repositories.BingoRepository;
import com.loutredev.conf_game_backend.persistence.repositories.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
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
public class BingoServiceIntegrationTest {

    @Autowired private BingoService bingoService;
    @Autowired private BingoRepository bingoRepository;
    @Autowired private UserRepository userRepository;

    private UUID userId;

    @BeforeEach
    void setUp() {
        bingoRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity user = new UserEntity();
        user.setEmail("bingouser@test.com");
        user.setUsername("Bingo");
        user.setPassword("SecuredPass123");
        userId = userRepository.save(user).getId();
    }

    @Test
    void create_shouldPersistBingo_whenUserExists() {
        BingoRequestCreatedDTO dto = new BingoRequestCreatedDTO("Tech Words", List.of("Docker", "K8s", "AI"), userId);

        BingoResponseDTO saved = bingoService.create(dto);

        assertThat(saved.id()).isNotNull();
        assertThat(bingoRepository.existsById(saved.id())).isTrue();
    }

    @Test
    void create_shouldThrow_whenUserNotFound() {
        BingoRequestCreatedDTO dto = new BingoRequestCreatedDTO("Ghost Bingo", List.of("Null"), UUID.randomUUID());

        assertThrows(UserNotFoundException.class, () -> bingoService.create(dto));
    }

    @Test
    void update_shouldModifyBingoData() {
        BingoResponseDTO created = bingoService.create(new BingoRequestCreatedDTO("Initial Title", List.of("A"), userId));
        BingoRequestUpdatedDTO updateDto = new BingoRequestUpdatedDTO(created.id(), "Updated Title", List.of("A", "B"));

        BingoResponseDTO updated = bingoService.update(updateDto);

        assertThat(updated.title()).isEqualTo("Updated Title");
        BingoEntity entity = bingoRepository.findById(created.id()).get();
        assertThat(entity.getProposals()).hasSize(2);
    }

    @Test
    void getAllByUserId_shouldReturnPagedBingos() {
        bingoService.create(new BingoRequestCreatedDTO("Bingo 1", List.of("1"), userId));
        bingoService.create(new BingoRequestCreatedDTO("Bingo 2", List.of("2"), userId));

        BingoQueryDTO query = new BingoQueryDTO();
        query.page = 0;

        Page<BingoEntity> pageResult = bingoService.getAllByUserId(userId, query);

        assertThat(pageResult.getContent()).hasSize(2);
    }
}
