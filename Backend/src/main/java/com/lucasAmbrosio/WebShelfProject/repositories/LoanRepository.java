package com.lucasAmbrosio.WebShelfProject.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucasAmbrosio.WebShelfProject.models.Loan;

@Repository
public interface LoanRepository extends JpaRepository<Loan, String>{
    
    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.client.id = :clientId AND l.isActive = true")
    Optional<Loan> findActiveLoanByBookAndClient(@Param("bookId") String bookId, @Param("clientId") String clientId);

    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.isActive = true")
    Optional<Loan> findActiveLoanByBookId(@Param("bookId") String bookId);

    @Query("SELECT l FROM Loan l WHERE l.client.id = :clientId AND l.isActive = true")
    Optional<Loan> findActiveLoanByClientId(@Param("clientId") String clientId);
    
    @Query("SELECT l FROM Loan l WHERE l.isActive = true AND l.returnDate < CURRENT_DATE AND l.book.library.id = :libraryId")
    List<Loan> findAllOverdueActiveLoansByLibraryId(@Param("libraryId") String libraryId);
}