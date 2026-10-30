package ub.accommodation.ports;

import java.util.Optional;
import ub.accommodation.domain.Room;

/**
 * <b>Driven port</b> (DP-01). Persistence contract for rooms.
 *
 * <p><b>Concurrency note (ADR-002):</b> inside the {@code allocate()} transaction the Phase 2 SQL adapter
 * reads the room with {@code SELECT ... FOR UPDATE}, so two simultaneous allocations for the same room
 * serialise and BR-02 cannot be violated by a race. The port stays unchanged; the locking is an adapter
 * detail. The in-memory adapter needs no locking.
 *
 * <p><b>Contract:</b> never returns {@code null}; participates in the caller's transaction.
 *
 * <p><b>Used by:</b> {@code RoomCapacityRule}, {@code AllocationService}.
 */
public interface RoomRepository {

    /** @return the room, or empty if the id is unknown */
    Optional<Room> findById(String roomId);

    /** Insert or update the room and return the stored value. */
    Room save(Room room);
}
