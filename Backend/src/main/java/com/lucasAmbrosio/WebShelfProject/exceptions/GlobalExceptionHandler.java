package com.lucasAmbrosio.WebShelfProject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import com.lucasAmbrosio.WebShelfProject.services.exceptions.ActiveLoanException;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.NotFoundInDbException;
import com.lucasAmbrosio.WebShelfProject.services.exceptions.RelatedDataException;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);    

    @ExceptionHandler(ActiveLoanException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleActiveLoanException(ActiveLoanException ex){
        logger.error("Tentativa invalida de emprestimo", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Tentativa invalida de emprestimo, verifique se o cliente tem algum emprestimo ativo ou se o livro já não está emprestado");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(NotFoundInDbException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundInDbException(NotFoundInDbException ex){
        logger.error("Dado não encontrado no banco de dados", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Um dado não existente foi procurado no banco de dados, verifique os dados enviados");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RelatedDataException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleRelatedDataException(RelatedDataException ex){
        logger.error("Dado não pode ser deletado pois ha entidades relacionadas", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse("Livro não pode ser excluido pois esta emprestado");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    public static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
