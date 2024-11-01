package com.lucasAmbrosio.WebShelfProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatableBookDTO {
    private String title;
    private String author;
    private String genre;
}
