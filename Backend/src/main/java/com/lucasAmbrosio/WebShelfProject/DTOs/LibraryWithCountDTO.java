package com.lucasAmbrosio.WebShelfProject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibraryWithCountDTO {
    private String id;
    private String libraryName;
    private Long bookCount;
}
