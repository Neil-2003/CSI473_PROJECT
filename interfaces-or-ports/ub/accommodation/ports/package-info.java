/**
 * The ports of the allocation core (Ports and Adapters, ADR-002).
 *
 * <p><b>Driving port</b> (called from outside): {@link ub.accommodation.ports.AllocationUseCase}.<br>
 * <b>Strategy port</b> (business rules): {@link ub.accommodation.ports.AllocationRule}.<br>
 * <b>Driven ports</b> (called by the core): {@code AllocationRepository}, {@code RoomRepository},
 * {@code ApplicationRepository}, {@code OccupancyRepository}.
 *
 * <p>Rules for this package: the core owns these interfaces; they import only {@code java.*} and
 * {@code ub.accommodation.domain}; no framework annotations or types (FD-001). Run
 * {@code tools/check-ports.sh} to verify.
 */
package ub.accommodation.ports;
