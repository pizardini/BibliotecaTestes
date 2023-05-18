package ada.tech.Biblioteca.service;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import ada.tech.Biblioteca.model.mapper.LivroMapper;
import ada.tech.Biblioteca.repository.LivroRepository;
import ada.tech.Biblioteca.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LivroServiceTest {

    @Mock
    private LivroRepository repository;

    @Mock
    private LivroMapper mapper;

    @InjectMocks
    private LivroService livroService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void pegarPorIdTest() {
        Long livroId = 1L;
        LivroEntity livroEntity = new LivroEntity();
        livroEntity.setId(livroId);
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livroId);

        when(repository.findById(livroId)).thenReturn(Optional.of(livroEntity));
        when(mapper.update(livroEntity)).thenReturn(livroDTO);

        LivroDTO result = livroService.pegarPorId(livroId);

        assertEquals(livroDTO, result);
        verify(repository, times(1)).findById(livroId);
        verify(mapper, times(1)).update(livroEntity);
    }

    @Test
    public void pegarPorIdFalhaTest() {
        Long livroId = 1L;

        when(repository.findById(livroId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> livroService.pegarPorId(livroId));
        verify(repository, times(1)).findById(livroId);
        verify(mapper, never()).update(any(LivroEntity.class));
    }

}

