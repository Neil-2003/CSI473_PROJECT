package ub.accommodation.domain;

import java.util.Objects;

/** A student's accommodation application. {@code status} values follow the Phase 1 / Lab 8 workflow. */
public record Application(String id, String studentId, String status) {
    public Application {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(studentId, "studentId");
        Objects.requireNonNull(status, "status");
    }

    public Application withStatus(String newStatus) { return new Application(id, studentId, newStatus); }
}
