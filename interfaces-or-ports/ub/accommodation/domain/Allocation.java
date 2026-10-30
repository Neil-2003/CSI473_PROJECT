package ub.accommodation.domain;

import java.time.LocalDate;
import java.util.Objects;

/**
 * A student's place in a room. "Active" means {@code endDate == null}, mirroring the BR-01 partial index
 * and the Lab 8 ALLOCATION table. Immutable: closing an allocation returns a new value.
 */
public record Allocation(String id, String studentId, String roomId, LocalDate startDate, LocalDate endDate) {
    public Allocation {
        Objects.requireNonNull(studentId, "studentId");
        Objects.requireNonNull(roomId, "roomId");
        Objects.requireNonNull(startDate, "startDate");
        if (endDate != null && endDate.isBefore(startDate))
            throw new IllegalArgumentException("endDate must not be before startDate");
    }

    /** A new active allocation. {@code id} is null until the repository assigns one on save(). */
    public static Allocation open(String studentId, String roomId, LocalDate start) {
        return new Allocation(null, studentId, roomId, start, null);
    }

    public boolean isActive() { return endDate == null; }

    public Allocation close(LocalDate end) { return new Allocation(id, studentId, roomId, startDate, end); }
}
