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

    @Column(name="isbn", nullable = false, unique = true, length = 13)
    private String isbn;

    @ManyToOne
    @JoinColumn(name="editora")
    private EditoraEntity editora;

    @ManyToOne
    @JoinColumn(name="categoria")
    private CategoriaEntity categoria;

    @Column(name="resumo", nullable = false)
    private String resumo;

    @Column
    private String sumario;

    @Column(name="preço", nullable = false)
    private Double preco;

    @Column(name="n° de páginas", nullable = false)
    private Integer nPgs;

    @Column
    private LocalDate dataSis;

}
