package com.lucasAmbrosio.WebShelfProject.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.lucasAmbrosio.WebShelfProject.DTOs.ClientWithLibName;
import com.lucasAmbrosio.WebShelfProject.DTOs.ClientWithLoansDTO;
import com.lucasAmbrosio.WebShelfProject.models.Client;
import com.lucasAmbrosio.WebShelfProject.services.ClientService;

@Controller
@RequestMapping("/client")
public class ClientController {
    
    @Autowired
    private ClientService clientService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Client client){
        this.clientService.create(client);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientWithLibName> findById(@PathVariable String id){
        ClientWithLibName client = this.clientService.findById(id);
        
        return ResponseEntity.ok().body(client);
    }

    @GetMapping("/{id}/with-loans")
    public ResponseEntity<ClientWithLoansDTO> findClientWithLoansById(@PathVariable String id){
        ClientWithLoansDTO client = this.clientService.findClientWithLoans(id);

        return ResponseEntity.ok().body(client);
    }
}
