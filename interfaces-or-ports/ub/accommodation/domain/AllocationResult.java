package ub.accommodation.domain;

/** Output of a successful {@code allocate()}: what the API contract returns for POST /api/allocations. */
public record AllocationResult(String allocationId, String studentId, String roomId) {
    public static AllocationResult of(Allocation a) {
        return new AllocationResult(a.id(), a.studentId(), a.roomId());
    }
}
