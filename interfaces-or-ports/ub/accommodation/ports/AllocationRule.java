package ub.accommodation.ports;

import ub.accommodation.domain.AllocationRequest;
import ub.accommodation.domain.RuleResult;

/**
 * <b>Strategy port</b> (DP-02). One business-rule check, e.g. {@code NoDuplicateActiveAllocation} (BR-01)
 * and {@code RoomCapacityRule} (BR-02). A new rule (such as Phase 2 first-year priority) is a new class
 * implementing this interface; {@code AllocationService} does not change.
 *
 * <p><b>Contract for implementers</b>
 * <ul>
 *   <li><i>Pure check:</i> must not save, update or delete anything.</li>
 *   <li><i>Reports, does not throw, on a rule failure:</i> return {@link RuleResult#violation} with the
 *       matching {@code ErrorCodes} value. Throw only for programming errors.</li>
 *   <li><i>Independent:</i> must not rely on another rule having run; ordering is the service's job.</li>
 *   <li><i>Runs inside the caller's transaction</i> and reads only through driven ports.</li>
 * </ul>
 *
 * <p><b>Ordering:</b> rules are evaluated in registration order and evaluation stops at the first failure.
 */
public interface AllocationRule {

    /** @return {@link RuleResult#ok()} if the request satisfies the rule, otherwise a violation */
    RuleResult check(AllocationRequest request);

    /** Name used in logs and test failure messages. Defaults to the class name. */
    default String name() { return getClass().getSimpleName(); }
}
