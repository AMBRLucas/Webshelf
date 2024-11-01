package com.lucasAmbrosio.WebShelfProject.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientWithLibName {
    private String id;
    private String name;
    private String cpf;
    private String email;
    private boolean hasActiveLoans;
    private LocalDate registryDate;
    private String LibraryName;
}
