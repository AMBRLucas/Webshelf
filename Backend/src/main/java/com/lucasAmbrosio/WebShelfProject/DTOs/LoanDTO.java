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
public class LoanDTO {
    private String id;
    private String client_name;
    private String book_name;
    private LocalDate registryDate;
    private LocalDate expected_return;
    private boolean isActive;
}
