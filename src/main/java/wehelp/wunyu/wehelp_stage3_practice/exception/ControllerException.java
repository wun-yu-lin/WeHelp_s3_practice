package wehelp.wunyu.wehelp_stage3_practice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class ControllerException {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> prepareResponseForRunTimeException(RuntimeException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("RunTimeException: " + exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> prepareResponseForIOException(IOException exception){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("RunTimeException: " + exception.getMessage());
    }

    @ExceptionHandler(FileEmptyException.class)
    public ResponseEntity<String> prepareResponseForFileEmptyException(FileEmptyException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("FileEmptyException: " + exception.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> prepareResponseForFileUploadException(FileUploadException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("FileUploadException: " + exception.getMessage());
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<String> prepareResponseForFileDownloadException(FileDownloadException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("FileDownloadException: " + exception.getMessage());
    }

    @ExceptionHandler(FileDeleteException.class)
    public ResponseEntity<String> prepareResponseForFileDeleteException(FileDeleteException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("FileDeleteException: " + exception.getMessage());
    }
}
