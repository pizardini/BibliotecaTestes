package ada.tech.Biblioteca.controller;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import ada.tech.Biblioteca.repository.LivroRepository;
import ada.tech.Biblioteca.model.mapper.LivroMapper;
import ada.tech.Biblioteca.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    @Autowired
    private LivroController livroController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LivroRepository repository;

    @Test
    public void carregouContexto() {
        Assertions.assertNotNull(livroController);
    }

    @Test
    public void testeOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void listarLivrosTest() throws Exception {
        List<LivroDTO> listaLivros = new ArrayList<>(); // Lista simulada de livros


        LivroDTO livro1 = new LivroDTO();
        livro1.setTitulo("Teste de listar livros");
        livro1.setIsbn("1");
        livro1.setResumo("Lorem ipsum");
        livro1.setSumario("sumario de teste");
        livro1.setPreco(59.90);
        livro1.setPaginas(200);

        listaLivros.add(livro1);

        LivroService livroService = Mockito.mock(LivroService.class);
        Mockito.when(livroService.listar()).thenReturn(listaLivros);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void criarLivroTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Criar Livro Teste");
        livroDTO.setIsbn("22");
        livroDTO.setResumo("Lorem ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void criarLivroSemTituloTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setIsbn("333");
        livroDTO.setResumo("Lorem ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void criarLivroSemIsbnTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Sem isbn");
        livroDTO.setResumo("Lorem ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void criarLivroResumo501Test() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro 501char");
        livroDTO.setIsbn("4444");
        livroDTO.setResumo("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
                "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
                "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
                "|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||" +
                "||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void criarLivroResumo500Test() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro 500char");
        livroDTO.setIsbn("55555");
        livroDTO.setResumo("||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    @Test
    public void criarLivroPrecoBaixoTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Preço menos de 20");
        livroDTO.setIsbn("666666");
        livroDTO.setResumo("Lorem Ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(19.90);
        livroDTO.setPaginas(200);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void criarLivroPgsBaixaTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro páginas menos de 100");
        livroDTO.setIsbn("7777777");
        livroDTO.setResumo("Lorem Ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(19.90);
        livroDTO.setPaginas(99);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void pegarUmLivroTest() throws Exception {

        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Livro Pegar Um");
        livro.setIsbn("88888888");
        livro.setResumo("Resumo do livro teste");
        livro.setSumario("sumario de teste");
        livro.setPreco(59.99);
        livro.setPaginas(200);

        repository.save(livro);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/{id}", livro.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void editarLivroTest() throws Exception {
        LivroEntity livro = new LivroEntity();
        livro.setTitulo("Livro Antigo");
        livro.setIsbn("999999999");
        livro.setResumo("Resumo do livro antigo");
        livro.setSumario("Lorem Ipsum1");
        livro.setPreco(59.99);
        livro.setPaginas(200);

        repository.save(livro);

        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Livro Atualizado");
        livroDTO.setIsbn("101010101010");
        livroDTO.setResumo("Resumo do livro atualizado");
        livroDTO.setSumario("Lorem Ipsum2");
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
        livro.setIsbn("11");
        livro.setResumo("Resumo do livro a ser deletado");
        livro.setSumario("Lorem Ipsum");
        livro.setPreco(59.99);
        livro.setPaginas(200);

        repository.save(livro);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/livros/{id}", livro.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
