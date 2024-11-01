package com.lucasAmbrosio.WebShelfProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucasAmbrosio.WebShelfProject.models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String>{
    
    @Query("SELECT c FROM Client c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%')) AND c.library.id = :libraryId")
    List<Client> findClientsByNameAndLibraryId(@Param("name") String name, @Param("libraryId") String libraryId);

    @Query("SELECT c FROM Client c WHERE c.library.id = :libraryId AND c.cpf LIKE CONCAT(:cpf, '%')")
    List<Client> findClientsByLibraryAndCpf(@Param("libraryId") String libraryId, @Param("cpf") String cpf);
}