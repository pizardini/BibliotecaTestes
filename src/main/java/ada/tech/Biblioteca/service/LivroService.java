package ada.tech.Biblioteca.service;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.entity.CategoriaEntity;
import ada.tech.Biblioteca.model.entity.EditoraEntity;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import ada.tech.Biblioteca.model.mapper.LivroMapper;
import ada.tech.Biblioteca.repository.LivroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private LivroMapper mapper;

    public LivroDTO pegarPorId(Long id) {
        Optional<LivroEntity> livroEntityOp = repository.findById(id);

        if(livroEntityOp.isPresent()) {
            LivroEntity livroEntity = livroEntityOp.get();
            return mapper.update(livroEntity);
        }

        throw new EntityNotFoundException("Livro não encontrado");
    }

    public LivroDTO criar(LivroDTO livroDTO) {
        LivroEntity livro = mapper.update(livroDTO);
        livro = repository.save(livro);

        return mapper.update(livro);
    }

    public LivroDTO editar(LivroDTO livroDTO, Long id) {
        if(repository.existsById(id)) {
            LivroEntity livroEntity = mapper.update(livroDTO);
            livroEntity.setId(id);
            livroEntity = repository.save(livroEntity);

            return mapper.update(livroEntity);
        }

        throw new EntityNotFoundException("Livro não encontrado");
    }

    public void deletar(Long id) {
        Optional<LivroEntity> livroEntityOp = repository.findById(id);

        if(livroEntityOp.isPresent()) {
            LivroEntity livroEntity = livroEntityOp.get();
            repository.delete(livroEntity);
            return;
        }

        throw new EntityNotFoundException("Livro não encontrado");
    }

    public List<LivroDTO> listar() {
        List<LivroEntity> listaEntities = repository.findAll();
        return mapper.updateListDTO(listaEntities);
    }

    public Object buscarPorCategoria(Long categoriaId) {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(categoriaId);
        List<LivroEntity> listaEntities = repository.findByCategoriaId(categoriaId);
        return mapper.updateListDTO(listaEntities);
    }

    public Object buscarPorEditora(Long editoraId) {
        EditoraEntity editora = new EditoraEntity();
        editora.setId(editoraId);
        List<LivroEntity> listaEntities = repository.findByEditoraId(editoraId);
        return mapper.updateListDTO(listaEntities);
    }

    public List<LivroDTO> buscarPorNomeOuIsbn(String nome, String isbn) {
        List<LivroEntity> listaEntities = repository.findByTituloOrIsbn(nome, isbn);
        return mapper.updateListDTO(listaEntities);
    }
}
