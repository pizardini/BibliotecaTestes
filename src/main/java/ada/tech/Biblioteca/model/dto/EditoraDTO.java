package ada.tech.Biblioteca.model.dto;

import ada.tech.Biblioteca.model.entity.CategoriaEntity;
import ada.tech.Biblioteca.model.entity.EditoraEntity;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditoraDTO {
    private Long id;
    @Size(max = 255, message = "tamanho máximo excedido")
    private String nome;
    private String descricao;

}

