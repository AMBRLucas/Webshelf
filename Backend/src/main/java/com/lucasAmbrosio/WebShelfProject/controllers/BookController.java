package com.lucasAmbrosio.WebShelfProject.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasAmbrosio.WebShelfProject.DTOs.BookWithLibName;
import com.lucasAmbrosio.WebShelfProject.DTOs.UpdatableBookDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.BookWithHistoryDTO;
import com.lucasAmbrosio.WebShelfProject.models.Book;
import com.lucasAmbrosio.WebShelfProject.services.BookService;

@Controller
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookService bookService;

    //! Cadastra um novo livro
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Book book){
        this.bookService.create(book);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //! Busca um livro pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<BookWithLibName> findById(@PathVariable String id){
        BookWithLibName book = this.bookService.findById(id);

        return ResponseEntity.ok().body(book);
    }

    //! Busca um livro com seu historico de emprestimo pelo Id
    @GetMapping("/{id}/history")
    public ResponseEntity<BookWithHistoryDTO> findBookHistoryById(@PathVariable String id){
        BookWithHistoryDTO book = this.bookService.findBookWithHistoryById(id);

        return ResponseEntity.ok().body(book);
    }

    //! Atualiza dados do livro
    //? passe o id pela query, e os dados atualizados pelo body
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBookData(@PathVariable String id,@RequestBody UpdatableBookDTO book){
        this.bookService.UpdateBookData(id, book);

        return ResponseEntity.noContent().build();
    }

    //! Deleta um livro
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable String id){
        this.bookService.DeleteBook(id);

        return ResponseEntity.noContent().build();
    }
}
