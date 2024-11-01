package com.lucasAmbrosio.WebShelfProject.controllers;

//import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasAmbrosio.WebShelfProject.DTOs.BookDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.ClientDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.LibraryAllIncluded;
import com.lucasAmbrosio.WebShelfProject.DTOs.LibraryWithCountDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.models.Library;
import com.lucasAmbrosio.WebShelfProject.services.BookService;
import com.lucasAmbrosio.WebShelfProject.services.ClientService;
import com.lucasAmbrosio.WebShelfProject.services.LibraryService;
import com.lucasAmbrosio.WebShelfProject.services.LoanService;

@Controller
@RequestMapping("/library")
public class LibraryController {
    
    @Autowired
    private LibraryService libraryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;
    
    //! Cria uma biblioteca
    @PostMapping
    public ResponseEntity<LibraryWithCountDTO> create(@RequestBody Library lib) {
        
        this.libraryService.Create(lib);

        //URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(lib.getId()).toUri();
        
        LibraryWithCountDTO newLib = this.libraryService.FindLibraryOnlyById(lib.getId());

        return ResponseEntity.ok().body(newLib);
    }
    
    //! Busca uma Biblioteca pelo ID (Com todos os dados possiveis)
    @GetMapping("/{id}")
    public ResponseEntity<LibraryAllIncluded> findLibrary(@PathVariable String id) {
       LibraryAllIncluded lib = this.libraryService.FindById(id);

       return ResponseEntity.ok().body(lib);
    }

    //! Busca uma biblioteca pelo ID (retorna apenas o ID, nome e contagem de livros)
    @GetMapping("/{id}/w-count")
    public ResponseEntity<LibraryWithCountDTO> findLibraryOnly(@PathVariable String id) {
       LibraryWithCountDTO lib = this.libraryService.FindLibraryOnlyById(id);

       return ResponseEntity.ok().body(lib);
    }

    //! Busca Todos os livros de uma biblioteca
    @GetMapping("/all/{libId}")
    public ResponseEntity<List<BookDTO>> findAllByLibId(@PathVariable String libId){
        List<BookDTO> bookList = this.bookService.findAllbyLibId(libId);

        return ResponseEntity.ok().body(bookList);
    }

    //! Acha todos os livros INDISPONIVEIS numa biblioteca
    @GetMapping("/indisponible/{libId}")
    public ResponseEntity<List<BookDTO>> findAllIndisponibleByLibId(@PathVariable String libId){
        List<BookDTO> bookList = this.bookService.findAllUnavailableByLibId(libId);

        return ResponseEntity.ok().body(bookList);
    }

    //! Acha todos os livros DISPONIVEIS numa biblioteca
    @GetMapping("/disponible/{libId}")
    public ResponseEntity<List<BookDTO>> findAllDisponibleByLibId(@PathVariable String libId){
        List<BookDTO> bookList = this.bookService.findAllDisponibleByLibId(libId);
    
        return ResponseEntity.ok().body(bookList);
    }

    //! Lista os emprestimos em atraso de um biblioteca
    @GetMapping("/overdue/{libId}")
    public ResponseEntity<List<LoanDTO>> overdueLoans(@PathVariable String libId){
        List<LoanDTO> loanList = this.loanService.OverdueLoans(libId);

        return ResponseEntity.ok().body(loanList);
    }

    //! Lista todos os livros com o mesmo nome de uma biblioteca
    //? ATENÇAO: bookName É UM REQUEST PARAM ENTÂO A REQUISICAO É FEITA DA SEGUIINTE MANEIRA
    //? /{id da biblioteca}/search?bookName={nome do livro}
    @GetMapping("/{id}/search")
    public ResponseEntity<List<BookDTO>> allbooksByNameAndLibrary(@PathVariable String id, @RequestParam String bookName){
        List<BookDTO> bookList = this.bookService.findAllBooksByNameAndLibrary(bookName, id);

        return ResponseEntity.ok().body(bookList);
    }

    //! Lista todos os livros com base na pesquisa de autor
    //? ATENÇAO: authorName É UM REQUEST PARAM ENTÂO A REQUISICAO É FEITA DA SEGUIINTE MANEIRA
    //? /{id da biblioteca}/author-search?authorName={nome do autor}
    @GetMapping("/{id}/author-search")
    public ResponseEntity<List<BookDTO>> allBookByAuthorAndLibrary(@PathVariable String id, @RequestParam String authorName){
        List<BookDTO> bookList = this.bookService.findAllBooksByAuthor(authorName, id);

        return ResponseEntity.ok().body(bookList);
    }

    //! Clientes da Biblioteca por nome
    @GetMapping("/{id}/client-search")
    public ResponseEntity<List<ClientDTO>> allClientsByNameAndId(@PathVariable String id, @RequestParam String clientName){
        List<ClientDTO> clientList = this.clientService.findClientByNameAndLibrary(clientName, id);

        return ResponseEntity.ok().body(clientList);
    }
    
    //! Cliente da biblioteca por CPF
    @GetMapping("/{id}/cpf-search")
    public ResponseEntity<List<ClientDTO>> allClientsByCpfAndLibrary(@PathVariable String id, @RequestParam String ClientCpf){
        List<ClientDTO> clientList = this.clientService.findClientByCpfAndLibrary(ClientCpf, id);

        return ResponseEntity.ok().body(clientList);
    }
}
