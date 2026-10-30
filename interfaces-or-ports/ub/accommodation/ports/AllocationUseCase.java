package ub.accommodation.ports;

import ub.accommodation.domain.AllocationRequest;
import ub.accommodation.domain.AllocationResult;
import ub.accommodation.domain.RuleViolationException;
import ub.accommodation.domain.ValidationException;

/**
 * <b>Driving port.</b> The core operation behind {@code POST /api/allocations} (UC-01 step 6): allocate a
 * student with an approved application to a room.
 *
 * <p><b>Why it exists:</b> the HTTP controller and automated tests call the same operation without either
 * depending on the other (DP-03, DP-04).
 *
 * <p><b>Contract</b>
 * <ul>
 *   <li><i>Atomic:</i> on success the allocation, the application approval and the occupancy record are all
 *       saved; on any failure nothing is saved (no partial state, QS-03).</li>
 *   <li><i>Rules:</i> every registered {@link AllocationRule} runs in registration order (BR-01, then
 *       BR-02). The first failure wins and decides the error code.</li>
 *   <li><i>Authorisation:</i> not checked here; the caller is assumed authorised (QS-04).</li>
 * </ul>
 *
 * <p><b>Implemented by:</b> {@code AllocationService} (transaction boundary).<br>
 * <b>Called by:</b> {@code AllocationController}, tests.
 */
public interface AllocationUseCase {

    /**
     * @param request the allocation request; never {@code null}
     * @return the created allocation
     * @throws ValidationException    if the application or room does not exist, or the input is invalid
     * @throws RuleViolationException if a business rule fails (RULE_001 for BR-01, RULE_002 for BR-02, ...)
     */
    AllocationResult allocate(AllocationRequest request);
}
