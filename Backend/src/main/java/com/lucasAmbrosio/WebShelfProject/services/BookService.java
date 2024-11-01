package com.lucasAmbrosio.WebShelfProject.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucasAmbrosio.WebShelfProject.DTOs.BookDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.BookWithHistoryDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.BookWithLibName;
import com.lucasAmbrosio.WebShelfProject.DTOs.LoanDTO;
import com.lucasAmbrosio.WebShelfProject.DTOs.UpdatableBookDTO;
import com.lucasAmbrosio.WebShelfProject.models.Book;
import com.lucasAmbrosio.WebShelfProject.models.Loan;
import com.lucasAmbrosio.WebShelfProject.repositories.BookRepository;
import com.lucasAmbrosio.WebShelfProject.repositories.LoanRepository;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.NotFoundInDbException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private LoanRepository loanRepository;

    //! Cria um livro
    public Book create(Book book){
        book.setId(null);
        bookRepository.save(book);

        return book;
    }

    //! Retorna os livros com o nome da biblioteca
    public BookWithLibName findById(String id){
        Book book = this.bookRepository.findById(id).orElseThrow();

        BookWithLibName bookWithLibName = new BookWithLibName(
            book.getId(), 
            book.getTitle(),
            book.getGenre(),
            book.getAuthor(),
            book.isLoaned(),
            book.getLibrary().getLibraryName()
        );

        return bookWithLibName;
    }

    //! Acha todos os livros de uma biblioteca
    public List<BookDTO> findAllbyLibId(String id){
        List<Book> rawBookList = this.bookRepository.findByLibraryId(id);
        List<BookDTO> bookList = rawBookList.stream().map(book -> {
            BookDTO bookDTO = new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.isLoaned()
            );
            return bookDTO;
        }).collect(Collectors.toList());

        return bookList;
    }

    //! Achar todos os livros DISPONIVEIS de uma biblioteca
    public List<BookDTO> findAllDisponibleByLibId(String id){
        List<Book> rawBookList = this.bookRepository.findAllAvailableBooksByLibraryId(id);

        List<BookDTO> bookList = rawBookList.stream().map(book -> {
            BookDTO bookDTO = new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.isLoaned()
            );
            return bookDTO;
        }).collect(Collectors.toList());

        return bookList;
    }

    //! Achar todos os livros INDISPONIVEIS de uma biblioteca
    public List<BookDTO> findAllUnavailableByLibId(String id){
        List<Book> rawBookList = this.bookRepository.findAllUnavailableBooksByLibraryId(id);

        List<BookDTO> bookList = rawBookList.stream().map(book -> {
            BookDTO bookDTO = new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenre(),
                book.isLoaned()
            );
            return bookDTO;
        }).collect(Collectors.toList());

        return bookList;
    }

    //! Atualiza dados de um livro
    public Book UpdateBookData(String id,UpdatableBookDTO book){
        Book newBook = this.bookRepository.findById(id).orElseThrow(() -> new NotFoundInDbException("Livro não encontrado"));
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        newBook.setGenre(book.getGenre());
        
        this.bookRepository.save(newBook);

        return newBook;
    }

    //! Deleta um livro
    public void DeleteBook(String id){
        Optional<Loan> activeLoan = this.loanRepository.findActiveLoanByBookId(id);
        if(activeLoan.isPresent()){
            throw new RuntimeException("Não é possivel deletar um livro emprestado");
        }

        this.bookRepository.deleteById(id);
    }

    //! Retorna um livro com seu historico de emprestimos
    public BookWithHistoryDTO findBookWithHistoryById(String id){
        Book book = this.bookRepository.findById(id).orElseThrow(()-> new NotFoundInDbException("Não encontrado no banco"));

        List<LoanDTO> history = book.getLoans().stream().map(loan -> {
            LoanDTO loanDTO = new LoanDTO(
                loan.getId(), 
                loan.getClient().getName(), 
                loan.getBook().getTitle(), 
                loan.getLoanDate(), 
                loan.getReturnDate(), 
                loan.isActive());
            
                return loanDTO;
        }).collect(Collectors.toList());

        BookWithHistoryDTO bookWithHistoryDTO = new BookWithHistoryDTO(
            book.getId(), 
            book.getTitle(), 
            book.getAuthor(), 
            book.getGenre(), 
            book.isLoaned(), 
            history
        );

        return bookWithHistoryDTO;
    }

    //! Buscar livros pelo nome em uma bibliteca
    public List<BookDTO> findAllBooksByNameAndLibrary(String bookName, String libId){
        List<Book> rawBookList = this.bookRepository.findBooksByTitleAndLibrary(bookName, libId);

        List<BookDTO> bookList = rawBookList.stream().map(book -> {
            BookDTO bookDTO = new BookDTO(
                book.getId(), 
                book.getTitle(), 
                book.getAuthor(), 
                book.getGenre(), 
                book.isLoaned()
            );
            return bookDTO;
        }).collect(Collectors.toList());

        return bookList;
    }

    //! Buscar livros pelo autor
    public List<BookDTO> findAllBooksByAuthor(String Author, String libId){
        List<Book> rawBookList = this.bookRepository.findBooksByAuthorAndLibrary(Author, libId);

        List<BookDTO> bookList = rawBookList.stream().map(book -> {
            BookDTO bookDTO = new BookDTO(
                book.getId(), 
                book.getTitle(), 
                book.getAuthor(), 
                book.getGenre(), 
                book.isLoaned()
            );
            return bookDTO; 
        }).collect(Collectors.toList());

        return bookList;
    }
}
