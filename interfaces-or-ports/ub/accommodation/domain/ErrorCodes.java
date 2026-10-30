package ub.accommodation.domain;

/**
 * Error codes shared with the Lab 8 API contract. The core raises these; the HTTP adapter maps them to
 * status codes and response bodies. AUTH_001/AUTH_002 are deliberately absent: role checks live in the
 * HTTP adapter and the core assumes an authorised caller (QS-04).
 *
 * <p>Check the wording and meaning of each code against the Lab 8 API contract before relying on it.
 */
public final class ErrorCodes {
    private ErrorCodes() {}

    /** BR-01: the student already has an active allocation. */
    public static final String RULE_001 = "RULE_001";
    /** BR-02: the room has no free capacity. */
    public static final String RULE_002 = "RULE_002";
    /** BR-03: see the Lab 8 API contract. */
    public static final String RULE_003 = "RULE_003";

    /** Invalid input; the Lab 8 contract defines what each of these three means. */
    public static final String VALIDATION_001 = "VALIDATION_001";
    public static final String VALIDATION_002 = "VALIDATION_002";
    public static final String VALIDATION_003 = "VALIDATION_003";
}
