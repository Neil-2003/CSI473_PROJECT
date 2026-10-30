package ub.accommodation.domain;

import java.util.Objects;

/**
 * Outcome of one {@code AllocationRule}. A passing result carries no code; a failing one carries the
 * {@link ErrorCodes} value that the API will return and a human-readable message.
 */
public record RuleResult(boolean passed, String code, String message) {
    public RuleResult {
        if (!passed) {
            Objects.requireNonNull(code, "a failing RuleResult needs an error code");
            Objects.requireNonNull(message, "a failing RuleResult needs a message");
        }
    }

    public static RuleResult ok() { return new RuleResult(true, null, null); }

    public static RuleResult violation(String code, String message) { return new RuleResult(false, code, message); }

    public boolean failed() { return !passed; }

    /** Convert a failing result into the exception the API contract maps to RULE_00x. No-op when passed. */
    public void orThrow() {
        if (failed()) throw new RuleViolationException(code, message);
    }
}
