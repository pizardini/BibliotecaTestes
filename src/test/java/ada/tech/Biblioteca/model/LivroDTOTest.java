package ada.tech.Biblioteca.model;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LivroDTOTest {

    @Test
    public void testCriacaoLivroDTO() {
        // Criação do objeto LivroDTO
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setId(1L);
        livroDTO.setTitulo("Livro de Teste");
        livroDTO.setIsbn("1234567890123");
        livroDTO.setResumo("Resumo do livro de teste");
        livroDTO.setSumario("Sumário do livro de teste");
        livroDTO.setPreco(25.0);
        livroDTO.setPaginas(150);
//        livroDTO.setDataSis(LocalDate.now().plusDays(1));

        // Verificação das propriedades do objeto LivroDTO
        Assertions.assertEquals(1L, livroDTO.getId());
        Assertions.assertEquals("Livro de Teste", livroDTO.getTitulo());
        Assertions.assertEquals("1234567890123", livroDTO.getIsbn());
        Assertions.assertEquals("Resumo do livro de teste", livroDTO.getResumo());
        Assertions.assertEquals("Sumário do livro de teste", livroDTO.getSumario());
        Assertions.assertEquals(25.0, livroDTO.getPreco());
        Assertions.assertEquals(150, livroDTO.getPaginas());
//        Assertions.assertEquals(LocalDate.now().plusDays(1), livroDTO.getDataSis());
    }
}

