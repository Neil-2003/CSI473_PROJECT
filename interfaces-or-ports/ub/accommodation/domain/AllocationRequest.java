package ub.accommodation.domain;

/**
 * Input to {@code AllocationUseCase.allocate()}: allocate the student of an approved application to a room.
 * Structural checks only (non-blank ids); business rules belong to the AllocationRule strategies.
 */
public record AllocationRequest(String applicationId, String studentId, String roomId) {
    public AllocationRequest {
        applicationId = requireText(applicationId, "applicationId");
        studentId = requireText(studentId, "studentId");
        roomId = requireText(roomId, "roomId");
    }

    private static String requireText(String v, String name) {
        if (v == null || v.isBlank()) throw new IllegalArgumentException(name + " must not be blank");
        return v;
    }
}
