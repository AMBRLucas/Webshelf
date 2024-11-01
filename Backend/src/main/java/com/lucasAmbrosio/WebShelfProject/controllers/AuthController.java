package com.lucasAmbrosio.WebShelfProject.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lucasAmbrosio.WebShelfProject.DTOs.LoginDTO;
import com.lucasAmbrosio.WebShelfProject.models.Library;
import com.lucasAmbrosio.WebShelfProject.services.LibraryService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private LibraryService libraryService;
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO login){
        Optional<Library> lib = this.libraryService.LoginWithUsernameAndPassword(login.getUsername(), login.getPassword());

        if(lib.isPresent()){
            return ResponseEntity.ok(lib.get().getId());
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais invalidas!");
        }
    }

}
