package ada.tech.Biblioteca.model.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LivroDTO {
    private Long id;

    @NotNull
    private String titulo;

    @NotNull
    private String isbn;

    @NotNull
    @Size(max = 500, message="resumo acima do limite")
    private String resumo;
    private String sumario;

    @NotNull
    @Min(value = 20, message = "preço mínimo deve ser 20")
    private Double preco;

    @Min(value = 100, message = "o mínimo de páginas é 100")
    private Integer paginas;

    @Future(message="A data precisa ser futura")
    private LocalDate dataSis;

}

