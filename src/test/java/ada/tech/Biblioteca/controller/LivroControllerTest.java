package ada.tech.Biblioteca.controller;
import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.service.LivroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LivroController.class)
public class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Test
    public void testGetLivro() throws Exception {
        LivroDTO livro = new LivroDTO();
        livro.setId(1L);
        livro.setTitulo("Livro de Teste");

        when(livroService.pegarPorId(1L)).thenReturn(livro);

        mockMvc.perform(get("/livros/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.titulo", is("Livro de Teste")));
    }

//    @Test
//    public void testCriarLivro() throws Exception {
//        LivroDTO livro = new LivroDTO();
//        livro.setId(1L);
//        livro.setTitulo("Livro de Teste");
//
//        when(livroService.criar(any(LivroDTO.class))).thenReturn(livro);
//
//        mockMvc.perform(post("/livros")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"titulo\": \"Livro de Teste\"}"))
//                .andExpect(status().isCreated())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.titulo", is("Livro de Teste")));
//    }

//    @Test
//    public void testAtualizarLivro() throws Exception {
//        LivroDTO livro = new LivroDTO();
//        livro.setId(1L);
//        livro.setTitulo("Livro Atualizado");
//
//        when(livroService.atualizarLivro(eq(1L), any(LivroDTO.class))).thenReturn(livro);
//
//        mockMvc.perform(put("/livros/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content("{\"titulo\": \"Livro Atualizado\"}"))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.titulo", is("Livro Atualizado")));
//    }

    @Test
    public void testDeletarLivro() throws Exception {
        doNothing().when(livroService).deletar(1L);

        mockMvc.perform(delete("/livros/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(livroService, times(1)).deletar(1L);
    }
}

