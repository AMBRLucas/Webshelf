package com.lucasAmbrosio.WebShelfProject.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Table(name = Book.TABLE_NAME)
@Entity
public class Book {
    
    public static final String TABLE_NAME = "book";

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "title")
    @NotBlank(message = "O livro precisa de um titulo valido!")
    private String title;

    @Column(name = "author")
    @NotBlank(message = "O Autor precisa ser valido")
    private String Author;

    @Column(name = "genre")
    @NotBlank(message = "O livro precisa de um genero valido")
    private String genre;

    @Column(name = "loaned")
    @NotNull
    private boolean isLoaned = false;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    @PrePersist
    public void generateId(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        this.id = LocalDateTime.now().format(formatter);
    }
}
