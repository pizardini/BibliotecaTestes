package ada.tech.Biblioteca.controller;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import ada.tech.Biblioteca.repository.LivroRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    @Autowired
    private LivroController livroController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository livroRepository;

    @Test
    public void carregouContexto() {
        Assertions.assertNotNull(livroController);
    }

    @Test
    public void listarLivrosTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void criarLivroTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Teste");
        livroDTO.setIsbn("1234567890123");
        livroDTO.setResumo("Resumo do livro teste");
        livroDTO.setPreco(19.99);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void pegarUmLivroTest() throws Exception {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Livro Teste");
        livro.setIsbn("1234567890123");
        livro.setResumo("Resumo do livro teste");
        livro.setPreco(19.99);
        livro.setPaginas(200);

        livroRepository.save(livro);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/{id}", livro.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void editarLivroTest() throws Exception {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Livro Antigo");
        livro.setIsbn("1234567890123");
        livro.setResumo("Resumo do livro antigo");
        livro.setPreco(19.99);
        livro.setPaginas(200);

        livroRepository.save(livro);

        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Atualizado");
        livroDTO.setIsbn("1234567890123");
        livroDTO.setResumo("Resumo do livro atualizado");
        livroDTO.setPreco(29.99);
        livroDTO.setPaginas(300);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/livros/{id}", livro.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deletarLivroTest() throws Exception {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Livro a ser deletado");
        livro.setIsbn("1234567890123");
        livro.setResumo("Resumo do livro a ser deletado");
        livro.setPreco(19.99);
        livro.setPaginas(200);

        livroRepository.save(livro);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/livros/{id}", livro.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
