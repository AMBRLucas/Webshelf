package com.lucasAmbrosio.WebShelfProject.DTOs;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientWithLoansDTO {
    String id;
    String name;
    String cpf;
    boolean hasActiveLoan;
    LocalDate registryDate;
    List<LoanDTO> loans;
}
