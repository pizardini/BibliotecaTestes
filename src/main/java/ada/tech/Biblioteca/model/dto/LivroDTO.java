package ada.tech.Biblioteca.model.dto;

import jakarta.validation.constraints.Min;
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

    @Size(max = 500, message="resumo acima do limite")
    private String resumo;
    private String sumario;

    @Min(value = 20, message = "preço mínimo deve ser 20")
    private Double preco;

    @Min(value = 100, message = "o mínimo de páginas é 100")
    private Integer nPgs;
    private LocalDate dataSis;

//    public LivroDTO update(LivroEntity livro) {
//        this.id = livro.getId();
//        this.nome = livro.getNome();
//        this.isbn = livro.getIsbn();
//        return this;
//    }

}

