package com.lucasAmbrosio.WebShelfProject.models;

import java.time.LocalDate;
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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = Client.TABLE_NAME)
public class Client {

    public static final String TABLE_NAME = "client";

    @Id
    @Column(name = "id", unique = true)
    private String id;

    @Column(name = "cpf", unique = true)
    @NotBlank(message = "Insira um cpf valido")
    private String cpf;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "insira um nome valido")
    private String name;

    @Column(name = "email", unique = true)
    @NotBlank(message = "insira um email valido")
    private String email;

    @Column(name = "registryDate", nullable = false)
    private LocalDate registryDate;

    @Column(name = "hasActiveLoan", nullable = false)
    private boolean hasActiveLoans;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loan> loans;

    @ManyToOne
    @JoinColumn(name = "library_id", nullable = false)
    private Library library;

    @PrePersist
    public void generateId(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmmss");
        this.id = LocalDateTime.now().format(formatter);
    }
}
