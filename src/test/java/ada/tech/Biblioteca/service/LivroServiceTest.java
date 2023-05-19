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

    @Test
    public void CriarLivroTest() {
        LivroDTO livroDTO = new LivroDTO();
        LivroEntity livroEntity = new LivroEntity();
        when(mapper.update(livroDTO)).thenReturn(livroEntity);
        when(repository.save(livroEntity)).thenReturn(livroEntity);
        when(mapper.update(livroEntity)).thenReturn(livroDTO);

        LivroDTO result = livroService.criar(livroDTO);

        assertEquals(livroDTO, result);
        verify(mapper, times(1)).update(livroDTO);
        verify(repository, times(1)).save(livroEntity);
        verify(mapper, times(1)).update(livroEntity);
    }

    @Test
    public void editarLivroTest() {
        Long livroId = 1L;
        LivroDTO livroDTO = new LivroDTO();
        LivroEntity livroEntity = new LivroEntity();
        livroEntity.setId(livroId);

        when(repository.existsById(livroId)).thenReturn(true);
        when(mapper.update(livroDTO)).thenReturn(livroEntity);
        when(repository.save(livroEntity)).thenReturn(livroEntity);
        when(mapper.update(livroEntity)).thenReturn(livroDTO);

        LivroDTO result = livroService.editar(livroDTO, livroId);

        assertEquals(livroDTO, result);
        verify(repository, times(1)).existsById(livroId);
        verify(mapper, times(1)).update(livroDTO);
        verify(repository, times(1)).save(livroEntity);
        verify(mapper, times(1)).update(livroEntity);
    }

    @Test
    public void editarLivroFalhaTest() {
        Long livroId = 1L;
        LivroDTO livroDTO = new LivroDTO();

        when(repository.existsById(livroId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> livroService.editar(livroDTO, livroId));
        verify(repository, times(1)).existsById(livroId);
        verify(mapper, never()).update(any(LivroDTO.class));
        verify(repository, never()).save(any(LivroEntity.class));
        verify(mapper, never()).update(any(LivroEntity.class));
    }

    @Test
    public void deletarLivroTest() {
        Long livroId = 1L;
        LivroEntity livroEntity = new LivroEntity();

        when(repository.findById(livroId)).thenReturn(Optional.of(livroEntity));

        livroService.deletar(livroId);

        verify(repository, times(1)).findById(livroId);
        verify(repository, times(1)).delete(livroEntity);
    }

    @Test
    public void deletarLivroTestFalha() {
        Long livroId = 1L;

        when(repository.findById(livroId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> livroService.deletar(livroId));
        verify(repository, times(1)).findById(livroId);
        verify(repository, never()).delete(any(LivroEntity.class));
    }

    @Test
    public void listarLivrosTest() {
        List<LivroEntity> livroEntities = new ArrayList<>();
        livroEntities.add(new LivroEntity());
        livroEntities.add(new LivroEntity());

        List<LivroDTO> livroDTOs = new ArrayList<>();
        livroDTOs.add(new LivroDTO());
        livroDTOs.add(new LivroDTO());

        when(repository.findAll()).thenReturn(livroEntities);
        when(mapper.updateListDTO(livroEntities)).thenReturn(livroDTOs);

        List<LivroDTO> result = livroService.listar();

        assertEquals(livroDTOs, result);
        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).updateListDTO(livroEntities);
    }

    @Test
    public void BuscaNomeIsbnTest() {
        String nome = "Livro 1";
        String isbn = "123456";

        List<LivroEntity> livroEntities = new ArrayList<>();
        livroEntities.add(new LivroEntity());
        livroEntities.add(new LivroEntity());

        List<LivroDTO> livroDTOs = new ArrayList<>();
        livroDTOs.add(new LivroDTO());
        livroDTOs.add(new LivroDTO());

        when(repository.findByTituloOrIsbn(nome, isbn)).thenReturn(livroEntities);
        when(mapper.updateListDTO(livroEntities)).thenReturn(livroDTOs);

        List<LivroDTO> result = livroService.buscarPorNomeOuIsbn(nome, isbn);

        assertEquals(livroDTOs, result);
        verify(repository, times(1)).findByTituloOrIsbn(nome, isbn);
        verify(mapper, times(1)).updateListDTO(livroEntities);
    }
}

