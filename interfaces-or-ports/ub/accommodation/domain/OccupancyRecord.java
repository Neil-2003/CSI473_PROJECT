package ub.accommodation.domain;

import java.util.Objects;

/** The occupancy record opened when an allocation is created (saved through OccupancyRepository). */
public record OccupancyRecord(String roomId, String studentId) {
    public OccupancyRecord {
        Objects.requireNonNull(roomId, "roomId");
        Objects.requireNonNull(studentId, "studentId");
    }
}
