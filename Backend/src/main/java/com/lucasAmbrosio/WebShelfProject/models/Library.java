package com.lucasAmbrosio.WebShelfProject.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Table(name = Library.TABLE_NAME)
@Data
public class Library {

    public static final String TABLE_NAME = "library";

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotBlank(message = "O username não pode ser nulo, ou vazio")
    private String username;

    @Column(name = "libraryName", length = 100, nullable = false)
    @NotBlank(message = "A biblioteca precisa de um nome")
    private String libraryName;

    @Column(name = "email", length = 100, nullable = false, unique= true)
    @NotBlank(message = "Insira um email valido")
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    @NotBlank(message = "A Senha precisa de caracteres validos")
    @Size(min = 6, max = 100)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Client> clients;

    @PrePersist
    public void generateId(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        this.id = LocalDateTime.now().format(formatter);
    }

}
