package ub.accommodation.ports;

import java.util.List;
import java.util.Optional;
import ub.accommodation.domain.Allocation;

/**
 * <b>Driven port</b> (DP-01, Repository pattern). Persistence contract for allocations.
 *
 * <p>"Active" means {@code end_date IS NULL}; {@link #findActiveByStudent} mirrors the BR-01 partial index
 * from the Lab 8 data model, so the database and the rule agree.
 *
 * <p><b>Contract for implementers</b>
 * <ul>
 *   <li>Never returns {@code null}: absence is {@code Optional.empty()} or an empty list.</li>
 *   <li>Participates in the caller's transaction; never commits or rolls back by itself.</li>
 *   <li>{@link #countActiveByRoom} must equal {@code findActiveByRoom(roomId).size()} (D-001, one source
 *       of truth for occupancy).</li>
 *   <li>{@link #save} assigns an id when {@code allocation.id()} is {@code null} and returns the stored value.</li>
 * </ul>
 *
 * <p><b>Implemented by:</b> {@code Sql*Repository} (Phase 2), {@code InMemory*Repository} (tests).<br>
 * <b>Used by:</b> {@code NoDuplicateActiveAllocation}, {@code OccupancyTracker}, {@code AllocationService}.
 */
public interface AllocationRepository {

    /** @return the student's active allocation, or empty. At most one can exist (BR-01). */
    Optional<Allocation> findActiveByStudent(String studentId);

    /** @return all active allocations in the room; empty list if none */
    List<Allocation> findActiveByRoom(String roomId);

    /** @return how many active allocations the room has; 0 if none or the room is unknown */
    int countActiveByRoom(String roomId);

    /** Insert a new allocation, or update an existing one (for example when it is closed). */
    Allocation save(Allocation allocation);
}
