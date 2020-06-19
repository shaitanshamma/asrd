package com.kropotov.asrd.exceptions;


public class PdfWriteException extends RuntimeException {

    public PdfWriteException(String message) {
        super(message);
    }

    public PdfWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
