package com.lucasAmbrosio.WebShelfProject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasAmbrosio.WebShelfProject.DTOs.BookDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.ClientDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.LibraryAllIncluded;
import com.lucasAmbrosio.WebShelfProject.DTOs.LibraryWithCountDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.models.Library;
import com.lucasAmbrosio.WebShelfProject.repositories.BookRepository;
import com.lucasAmbrosio.WebShelfProject.repositories.LibraryRepository;


@Service
public class LibraryService {
    
    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    //! Funcção para criar uma nova biblioteca
    public Library Create(Library lib){
        lib.setId(null);
        libraryRepository.save(lib);

        return lib;
    }

    //! Função para achar uma biblioteca com todos os dados possiveis
    public LibraryAllIncluded FindById(String id){
        Library lib = this.libraryRepository.findById(id).orElseThrow();

        List<BookDTO> booksDTOList = lib.getBooks().stream().map(book -> {
            
            BookDTO bookDTO = new BookDTO(
                book.getId(), 
                book.getTitle(), 
                book.getAuthor(), 
                book.getGenre(),
                book.isLoaned()
            );

            return bookDTO;
        }).collect(Collectors.toList()); 

        List<ClientDTO> clientDTOList = lib.getClients().stream().map(client -> {
            
            ClientDTO clientDTO = new ClientDTO(
                client.getId(),
                client.getCpf(),
                client.getName(),
                client.isHasActiveLoans(),
                client.getEmail(),
                client.getRegistryDate()
            );

            return clientDTO;
        }).collect(Collectors.toList());

        List<LoanDTO> loanDTOlist = lib.getLoans().stream().map(loan -> {
            
            LoanDTO loanDTO = new LoanDTO(
                loan.getId(), 
                loan.getClient().getName(), 
                loan.getBook().getTitle(), 
                loan.getLoanDate(), 
                loan.getReturnDate(), 
                loan.isActive()
            );

            return loanDTO;
        }).collect(Collectors.toList());

        LibraryAllIncluded libNew = new LibraryAllIncluded(
            lib.getId(),
            lib.getLibraryName(), 
            booksDTOList,
            clientDTOList,
            loanDTOlist
        );

        return libNew;
    }

    //! Função para achar uma biblioteca retornando apenas seu nome e a contagem de livros
    public LibraryWithCountDTO FindLibraryOnlyById(String id){
        Library lib = this.libraryRepository.findById(id).orElseThrow(() -> new RuntimeException("Library not found"));

        Long bookCount = this.bookRepository.countByLibraryId(id);

        LibraryWithCountDTO libraryOnlyDTO = new LibraryWithCountDTO(
            lib.getId(), 
            lib.getLibraryName(), 
            bookCount
        );

        return libraryOnlyDTO;
    }

    public Optional<Library> LoginWithUsernameAndPassword(String username, String password){
        Optional<Library> lib = this.libraryRepository.findByUsernameAndPassword(username, password);

        return lib;
    }
}
