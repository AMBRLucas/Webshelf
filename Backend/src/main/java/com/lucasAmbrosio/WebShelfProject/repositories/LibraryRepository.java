package com.lucasAmbrosio.WebShelfProject.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucasAmbrosio.WebShelfProject.models.Library;

@Repository
public interface LibraryRepository extends JpaRepository<Library, String>{
    Optional<Library> findByUsernameAndPassword(String username, String password);
}
