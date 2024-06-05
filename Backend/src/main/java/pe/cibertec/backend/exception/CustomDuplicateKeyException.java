package pe.cibertec.backend.exception;

public class CustomDuplicateKeyException extends RuntimeException {
    public CustomDuplicateKeyException(String message) {
        super(message);
    }
}

