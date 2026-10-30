package ub.accommodation.ports;

import ub.accommodation.domain.OccupancyRecord;

/**
 * <b>Driven port</b> (DP-01, DP-05). Saves the occupancy record opened when an allocation is created.
 *
 * <p>Added after review (docs/critique-record.md, finding C-1): the first draft saved this record through
 * an omitted repository, a hidden dependency of {@code AllocationService}. Declaring the port makes every
 * dependency of the service visible. Note that occupancy <i>counts</i> are derived from allocations
 * (D-001); this port only stores the record.
 *
 * <p><b>Contract:</b> participates in the caller's transaction, so a failed allocation leaves no record.
 */
public interface OccupancyRepository {

    /** Insert the record and return the stored value. */
    OccupancyRecord save(OccupancyRecord record);
}
