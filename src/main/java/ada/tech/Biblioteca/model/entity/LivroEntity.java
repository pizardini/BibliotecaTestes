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

//    private Long editora_id;
    @ManyToOne
    @JoinColumn(name="editora")
    private EditoraEntity editora;

//    private Long categoria_id;
    @ManyToOne
    @JoinColumn(name="categoria")
    private CategoriaEntity categoria;

    @Column
    private String resumo;

    @Column
    private String sumario;

    @Column
    private Double preco;

    @Column
    private Integer nPgs;

    @Column
    private LocalDate dataSis;

//    public LivroEntity update(LivroDTO livro) {
//        this.id = livro.getId();
//        this.nome = livro.getNome();
//        this.isbn = livro.getIsbn();
//        return this;
//    }
}
