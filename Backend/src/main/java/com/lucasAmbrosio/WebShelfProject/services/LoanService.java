package com.lucasAmbrosio.WebShelfProject.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.models.Book;
import com.lucasAmbrosio.WebShelfProject.models.Loan;
import com.lucasAmbrosio.WebShelfProject.models.Client;
import com.lucasAmbrosio.WebShelfProject.repositories.BookRepository;
import com.lucasAmbrosio.WebShelfProject.repositories.ClientRepository;
import com.lucasAmbrosio.WebShelfProject.repositories.LoanRepository;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.ActiveLoanException;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.NotFoundInDbException;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ClientRepository clientRepository;
    
    //! Cria um emprestimo
    public Loan Create(Loan loan){
        Book book = this.bookRepository.findById(loan.getBook().getId()).orElseThrow(() -> new EntityNotFoundException());
        
        Optional<Loan> bookActiveLoan = this.loanRepository.findActiveLoanByBookId(book.getId());
        if(bookActiveLoan.isPresent()){
            throw new ActiveLoanException("Este livro ja esta emprestado!");
        }
        Optional<Loan> clientActiveLoan = this.loanRepository.findActiveLoanByClientId(loan.getClient().getId());
        if(clientActiveLoan.isPresent()){
            throw new ActiveLoanException("Você ainda tem um emprestimo ativo!");
        }

        Client client = this.clientRepository.findById(loan.getClient().getId()).orElseThrow();

        loan.setBook(book);
        loan.setActive(true);
        loan.setLoanDate(LocalDate.now());

        this.loanRepository.save(loan);
        book.setLoaned(true);
        this.bookRepository.save(book);
        client.setHasActiveLoans(true);
        this.clientRepository.save(client);

        return loan;
    }

    //! acha um emprestimo pelo id
    public LoanDTO FindById(String id){
        Loan loan = loanRepository.findById(id).orElseThrow();

        LoanDTO loanDTO = new LoanDTO(
            loan.getId(),  
            loan.getClient().getName(), 
            loan.getBook().getTitle(), 
            loan.getLoanDate(), 
            loan.getReturnDate(),
            loan.isActive()
            );
        return loanDTO;            
    }

    //! retorna uma lista dos emprestimos em atraso no sistema
    public List<LoanDTO> OverdueLoans(String id){
        List<Loan> rawLoanList = this.loanRepository.findAllOverdueActiveLoansByLibraryId(id);

        List<LoanDTO> loanList = rawLoanList.stream().map(loan -> {
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

        return loanList;
    } 

    //! Devolução de um livro
    public Loan returnBook(String id){
        Loan loan = this.loanRepository.findById(id).orElseThrow(()-> new NotFoundInDbException("Dado não encontrado no banco de dados"));
        Book book = this.bookRepository.findById(loan.getBook().getId()).orElseThrow(()-> new NotFoundInDbException("Dado não encontrado no banco de dados"));
        Loan activeLoan = this.loanRepository.findActiveLoanByBookId(book.getId()).orElseThrow(() -> new NotFoundInDbException("Dado não encontrado"));
        Client client = this.clientRepository.findById(loan.getClient().getId()).orElseThrow();

        activeLoan.setActive(false);
        book.setLoaned(false);
        client.setHasActiveLoans(false);

        this.loanRepository.save(loan);
        this.bookRepository.save(book);
        this.clientRepository.save(client);

        return loan;
    }
}
