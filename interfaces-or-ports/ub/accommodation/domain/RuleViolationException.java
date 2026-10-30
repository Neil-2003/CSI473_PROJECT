package ub.accommodation.domain;

/** A business rule failed. The HTTP adapter maps {@link #code()} (RULE_001-003) to the response. */
public class RuleViolationException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private final String code;

    public RuleViolationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String code() { return code; }
}
