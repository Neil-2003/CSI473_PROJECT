package ub.accommodation.domain;

/** The request is structurally invalid or refers to something that does not exist (VALIDATION_001-003). */
public class ValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String code;

    public ValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String code() { return code; }
}
