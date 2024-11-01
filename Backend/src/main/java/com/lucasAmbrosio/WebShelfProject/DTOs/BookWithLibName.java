package com.lucasAmbrosio.WebShelfProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookWithLibName {
    private String id;
    private String title;
    private String genre;
    private String author;
    private boolean loaned;
    private String libraryName;
}
