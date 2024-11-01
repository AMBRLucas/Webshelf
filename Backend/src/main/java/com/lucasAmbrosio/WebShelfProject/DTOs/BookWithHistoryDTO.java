package com.lucasAmbrosio.WebShelfProject.DTOs;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookWithHistoryDTO {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean isLoaned;
    private List<LoanDTO> history;
}
