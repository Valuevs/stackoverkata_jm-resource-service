package stackover.resource.service.rest.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import stackover.resource.service.exception.AccountExistException;
import stackover.resource.service.exception.AccountNotAvailableException;
import stackover.resource.service.exception.AnswerException;
import stackover.resource.service.exception.ApiRequestException;
import stackover.resource.service.exception.CommentAnswerException;
import stackover.resource.service.exception.ConstrainException;
import stackover.resource.service.exception.FeignRequestException;
import stackover.resource.service.exception.QuestionException;
import stackover.resource.service.exception.TagAlreadyExistsException;
import stackover.resource.service.exception.TagNotFoundException;
import stackover.resource.service.exception.VoteException;

@ControllerAdvice
public class AdviceController {


    // AccountExistException
    @ExceptionHandler(AccountExistException.class)
    public ResponseEntity<String> handleAccountExistException(AccountExistException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    // AccountNotAvailableException
    @ExceptionHandler(AccountNotAvailableException.class)
    public ResponseEntity<String> handleAccountNotAvailableException(AccountNotAvailableException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // AnswerException
    @ExceptionHandler(AnswerException.class)
    public ResponseEntity<String> handleAnswerException(AnswerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // ApiRequestException
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<String> handleApiRequestException(ApiRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // CommentAnswerException
    @ExceptionHandler(CommentAnswerException.class)
    public ResponseEntity<String> handleCommentAnswerException(CommentAnswerException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // ConstrainException
    @ExceptionHandler(ConstrainException.class)
    public ResponseEntity<String> handleConstrainException(ConstrainException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // FeignRequestException
    @ExceptionHandler(FeignRequestException.class)
    public ResponseEntity<String> handleFeignRequestException(FeignRequestException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(ex.getMessage());
    }

    // QuestionException
    @ExceptionHandler(QuestionException.class)
    public ResponseEntity<String> handleQuestionException(QuestionException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // TagAlreadyExistsException
    @ExceptionHandler(TagAlreadyExistsException.class)
    public ResponseEntity<String> handleTagAlreadyExistsException(TagAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }

    // TagNotFoundException
    @ExceptionHandler(TagNotFoundException.class)
    public ResponseEntity<String> handleTagNotFoundException(TagNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    // VoteException
    @ExceptionHandler(VoteException.class)
    public ResponseEntity<String> handleVoteException(VoteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    // Общий обработчик
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Произошла ошибка: " + ex.getMessage());
    }
}

