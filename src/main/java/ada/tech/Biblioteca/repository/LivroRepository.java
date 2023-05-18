package ada.tech.Biblioteca.repository;

import ada.tech.Biblioteca.model.entity.LivroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<LivroEntity, Long> {


    @Query("SELECT l  FROM LivroEntity l " + "WHERE UPPER(l.titulo) LIKE CONCAT('%', UPPER (:titulo),'%') " + "OR (l.isbn = :isbn)")
    List<LivroEntity> findByTituloOrIsbn(String titulo, String isbn);
}
