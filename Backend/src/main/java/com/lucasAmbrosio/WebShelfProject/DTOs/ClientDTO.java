package com.lucasAmbrosio.WebShelfProject.DTOs;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    String id;
    String cpf;
    String name;
    boolean hasActiveLoan;
    String email;
    LocalDate registryDate;
}
