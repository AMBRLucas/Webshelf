package com.lucasAmbrosio.WebShelfProject.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.models.Loan;
import com.lucasAmbrosio.WebShelfProject.services.LoanService;

@Controller
@RequestMapping("/loan")
public class LoanController {
    
    @Autowired
    private LoanService loanService;

    //! Cria emprestimo de livro
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Loan loan){
        this.loanService.Create(loan);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(loan.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    //! Busca um emprestimo pelo Id
    @GetMapping("/{id}")
    public ResponseEntity<LoanDTO> findById(@PathVariable String id){
        LoanDTO loan = this.loanService.FindById(id);

        return ResponseEntity.ok().body(loan);
    }

    //! Devulução do livro
    @PutMapping("/return/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable String id){
        this.loanService.returnBook(id);

        return ResponseEntity.noContent().build();
    }

}
