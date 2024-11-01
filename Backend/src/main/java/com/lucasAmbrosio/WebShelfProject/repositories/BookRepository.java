package com.lucasAmbrosio.WebShelfProject.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lucasAmbrosio.WebShelfProject.models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String>{
    long countByLibraryId(String libraryId);
    List<Book> findByLibraryId(String id);

    @Query("SELECT b FROM Book b WHERE b.library.id = :libraryId AND b.id NOT IN (SELECT l.book.id FROM Loan l WHERE l.isActive = true)")
    List<Book> findAllAvailableBooksByLibraryId(@Param("libraryId") String libraryId);

    @Query("SELECT b FROM Book b WHERE b.library.id = :libraryId AND b.id IN (SELECT l.book.id FROM Loan l WHERE l.isActive = true)")
    List<Book> findAllUnavailableBooksByLibraryId(@Param("libraryId") String libraryId);
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :name, '%')) AND b.library.id = :libraryId")
    List<Book> findBooksByTitleAndLibrary(@Param("name") String name, @Param("libraryId") String libraryId);

    @Query("SELECT b FROM Book b WHERE LOWER(b.Author) LIKE LOWER(CONCAT('%', :name, '%')) AND b.library.id = :libraryId")
    List<Book> findBooksByAuthorAndLibrary(@Param("name") String name, @Param("libraryId") String libraryId);

}