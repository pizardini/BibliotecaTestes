package ada.tech.Biblioteca.controller;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.dto.MensagemDTO;
import ada.tech.Biblioteca.model.entity.LivroEntity;
import ada.tech.Biblioteca.repository.LivroRepository;
import ada.tech.Biblioteca.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LivroControllerTest {

    @Autowired
    private LivroController livroController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

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
        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void listarLivrosExceptionTest() throws Exception {
        when(livroService.listar()).thenThrow(new RuntimeException("Erro ao listar livros."));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Erro ao listar livros."));
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
        livroDTO.setDataSis(LocalDate.now().plusDays(1));

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

//    @Rule
//    public ExpectedException exceptionRule = ExpectedException.none();

//    @Test
//    public void criarLivroExceptionTest() throws Exception {
//        doThrow(new RuntimeException()).when(livroService).criar(any(LivroDTO.class));
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros"))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
//    Não deu certo criar uma instãncia qualquer, achei o método @Rule que talvez possa ajudar, mas não consegui aplicar


    @Test
    public void criarLivroExceptionUnitarioTest() {
        LivroDTO livroDTO = new LivroDTO();
        when(livroService.criar(livroDTO)).thenThrow(new RuntimeException("Mensagem da exceção"));

        ResponseEntity<Object> response = livroController.criar(livroDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Mensagem da exceção", ((MensagemDTO) response.getBody()).getMensagem());

        verify(livroService, times(1)).criar(livroDTO);
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
    public void criarLivroDataErradaTest() throws Exception {
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo("Criar Livro Teste");
        livroDTO.setIsbn("22");
        livroDTO.setResumo("Lorem ipsum");
        livroDTO.setSumario("sumario de teste");
        livroDTO.setPreco(59.90);
        livroDTO.setPaginas(200);
        livroDTO.setDataSis(LocalDate.now().minusDays(1));

        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule()); correção do localdate que acabou não precisando depois
        mapper.findAndRegisterModules();
        String json = mapper.writeValueAsString(livroDTO);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    public void pegarUmTest() throws Exception {
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
    public void pegarUmNotFound() throws Exception {
        Long livroId = 2L;
        when(livroService.pegarPorId(livroId)).thenThrow(new EntityNotFoundException());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/{id}", livroId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void pegarUmExceptionTest() throws Exception {
        Long livroId = 3L;
        when(livroService.pegarPorId(livroId)).thenThrow(new RuntimeException("Erro ao pegar livro."));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/{id}", livroId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensagem").value("Erro ao pegar livro."));
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

//    @Test
//    public void editarNotFoundTest() throws Exception {
//        Long livroId = 3L;
//        LivroDTO livroDTO = new LivroDTO();
//        when(livroService.editar(livroDTO, livroId)).thenThrow(new EntityNotFoundException("Não encontrado"));
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(livroDTO);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put("/livros/{id}", livroId)
//                )
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
////                .andExpect(MockMvcResultMatchers.status().isNotFound()); não estou conseguindo retornar 404.
//    }
//
//    @Test
//    public void editarLivroExceptionTest() throws Exception {
//        Long livroId = 3L;
//        LivroDTO livroDTO = new LivroDTO();
//        when(livroService.editar(livroDTO, livroId)).thenThrow(new RuntimeException());
//
//        this.mockMvc.perform(MockMvcRequestBuilders.put("/livros/{id}", livroId))
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isBadRequest());
//    }
    // Código que está dando certo, mas não está passando pelo catch exception do Controller

    @Test
    public void editarEntityNotFoundExceptionTest() {
        Long livroId = 3L;
        LivroDTO livroDTO = new LivroDTO();

        when(livroService.editar(livroDTO, livroId)).thenThrow(new EntityNotFoundException());
        ResponseEntity<Object> response = livroController.editar(livroDTO, livroId);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(livroService, times(1)).editar(livroDTO, livroId);
    }

    @Test
    public void editarRuntimeExceptionTest() {
        Long livroId = 3L;
        LivroDTO livroDTO = new LivroDTO();

        when(livroService.editar(livroDTO, livroId)).thenThrow(new RuntimeException("Erro ao editar livro"));
        ResponseEntity<Object> response = livroController.editar(livroDTO, livroId);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro ao editar livro", ((MensagemDTO) response.getBody()).getMensagem());
        verify(livroService, times(1)).editar(livroDTO, livroId);
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

    @Test
    public void deletarNotFoundTest() throws Exception {
        Long livroId = 9L;
        doThrow(new EntityNotFoundException("sdfjkshk")).when(livroService).deletar(livroId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/livros/{id}", livroId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void deletarExceptionTest() throws Exception {
        Long livroId = 8L;
        doThrow(new RuntimeException()).when(livroService).deletar(livroId);

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/livros/{id}", livroId))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void buscarPorNomeOuIsbnTest() throws Exception {
        LivroEntity livro1 = new LivroEntity();
        livro1.setTitulo("Livro de Aventura");
        livro1.setIsbn("1234567890123");
        livro1.setResumo("Resumo do livro de aventura");
        livro1.setPreco(59.99);
        livro1.setPaginas(200);

        LivroEntity livro2 = new LivroEntity();
        livro2.setTitulo("Livro de Ficção Científica");
        livro2.setIsbn("9876543210987");
        livro2.setResumo("Resumo do livro de ficção científica");
        livro2.setPreco(24.99);
        livro2.setPaginas(300);

        repository.save(livro1);
        repository.save(livro2);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/BuscaNomeIsbn")
                        .param("nome", "Aventura")
                        .param("isbn", ""))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/BuscaNomeIsbn")
                        .param("nome", "")
                        .param("isbn", "9876543210987"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/livros/BuscaNomeIsbn")
                        .param("nome", "Romance")
                        .param("isbn", "1234567890123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
