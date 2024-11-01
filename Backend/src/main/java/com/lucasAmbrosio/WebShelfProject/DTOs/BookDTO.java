package com.lucasAmbrosio.WebShelfProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {
    private String id;
    private String title;
    private String author;
    private String genre;
    private boolean isLoaned;
}
