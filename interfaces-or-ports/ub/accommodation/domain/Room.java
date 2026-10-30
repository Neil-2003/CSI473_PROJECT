package ub.accommodation.domain;

/**
 * A room and its capacity. Current occupancy is intentionally not a field: it is derived from active
 * allocations by OccupancyTracker so there is one source of truth (D-001, BR-06).
 */
public record Room(String id, int capacity) {
    public Room {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id must not be blank");
        if (capacity < 1) throw new IllegalArgumentException("capacity must be at least 1");
    }
}
