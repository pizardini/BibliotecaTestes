package ada.tech.Biblioteca.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name="livro")
public class LivroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="titulo", nullable = false, unique = true)
    private String titulo;

    @Column(name="isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name="resumo", nullable = false, length = 500)
    private String resumo;

    @Column
    private String sumario;

    @Column(name="preço", nullable = false)
    private Double preco;

    @Column(name="paginas", nullable = false)
    private Integer paginas;

    @Column
    private LocalDate dataSis;

}
