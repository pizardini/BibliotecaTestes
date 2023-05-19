package ada.tech.Biblioteca.controller;

import ada.tech.Biblioteca.model.dto.LivroDTO;
import ada.tech.Biblioteca.model.dto.MensagemDTO;
import ada.tech.Biblioteca.service.LivroService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/livros")
@Slf4j
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping
    public ResponseEntity<Object> listar() {
        try {
            return ResponseEntity.ok(livroService.listar());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> pegarUm(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(livroService.pegarPorId(id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new MensagemDTO(e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody @Valid LivroDTO livroDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(livroService.criar(livroDTO));
        } catch (RuntimeException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> editar(@RequestBody @Valid LivroDTO livroDTO, @PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(livroService.editar(livroDTO, id));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO(e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") Long id) {
        try {
            livroService.deletar(id);
            return ResponseEntity.ok(new MensagemDTO("Livro com id "+id+" removido com sucesso"));
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MensagemDTO(e.getMessage()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }




    @GetMapping("/BuscaNomeIsbn")
    public ResponseEntity<Object> buscarPorNomeOuIsbn(@RequestParam(name="nome", defaultValue = "") String nome,
                                                      @RequestParam(name="isbn", defaultValue = "") String isbn) {
        try {
            return ResponseEntity.ok(livroService.buscarPorNomeOuIsbn(nome, isbn));
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MensagemDTO(e.getMessage()));
        }
    }
}
