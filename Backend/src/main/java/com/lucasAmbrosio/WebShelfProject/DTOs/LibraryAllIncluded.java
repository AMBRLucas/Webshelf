package com.lucasAmbrosio.WebShelfProject.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryAllIncluded {
    String id;
    String libraryName;
    List<BookDTO> booksDTO;
    List<ClientDTO> clientDTO;
    List<LoanDTO> loanDTO;
}
