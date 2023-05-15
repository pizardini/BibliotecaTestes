package ada.tech.Biblioteca.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LivroDTO {
    private Long id;
    private String titulo;
    private EditoraDTO editora;
    private CategoriaDTO categoria;
    @Size(max = 13, message="isbn acima do limite")
    private String isbn;
    private String resumo;
    private String sumario;
    private Double preco;
    private Integer nPgs;
    private LocalDate dataSis;

//    public LivroDTO update(LivroEntity livro) {
//        this.id = livro.getId();
//        this.nome = livro.getNome();
//        this.isbn = livro.getIsbn();
//        return this;
//    }

}

