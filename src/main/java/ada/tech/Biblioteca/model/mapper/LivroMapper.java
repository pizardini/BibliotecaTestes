package ada.tech.Biblioteca.model.mapper;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LivroMapper {

    public LivroDTO update(LivroEntity livroEntity) {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(livroEntity.getId());
        livroDTO.setTitulo(livroEntity.getTitulo());
        livroDTO.setIsbn(livroEntity.getIsbn());
        livroDTO.setResumo(livroEntity.getResumo());
        livroDTO.setSumario(livroEntity.getSumario());
        livroDTO.setPreco(livroEntity.getPreco());
        livroDTO.setPaginas(livroEntity.getPaginas());
//        livroDTO.setDataSis(livroEntity.getDataSis());
//        livroDTO.setEditora(editoraMapper.update(livroEntity.getEditora()));
//        livroDTO.setCategoria(categoriaMapper.update(livroEntity.getCategoria()));
        return livroDTO;
    }

    public LivroEntity update(LivroDTO livroDTO) {
        LivroEntity livroEntity = new LivroEntity();
        livroEntity.setTitulo(livroDTO.getTitulo());
        livroEntity.setIsbn(livroDTO.getIsbn());
        livroEntity.setResumo(livroDTO.getResumo());
        livroEntity.setSumario(livroDTO.getSumario());
        livroEntity.setPreco(livroDTO.getPreco());
        livroEntity.setPaginas(livroDTO.getPaginas());
//        livroEntity.setDataSis(livroDTO.getDataSis());
//        livroEntity.setEditora(editoraMapper.update(livroDTO.getEditora()));
//        livroEntity.setCategoria(categoriaMapper.update(livroDTO.getCategoria()));
        return livroEntity;
    }

    public List<LivroEntity> updateListEntity(List<LivroDTO> listaDTO) {
        return listaDTO.stream().map(livroDTO -> this.update(livroDTO)).toList();
    }

    public List<LivroDTO> updateListDTO(List<LivroEntity> listaEntity) {
        return listaEntity.stream().map(livroEntity -> this.update(livroEntity)).toList();
    }
}
