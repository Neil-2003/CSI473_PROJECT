# API / Service Contract — Core Operation

CSI473 · Laboratory 8 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

## Operation: Allocate Student to Room

This is the core operation of the vertical slice (UC-01, step 6). It enforces BR-01 and BR-02 atomically, and is owned exclusively by the Allocation Module (`AllocationService`, see `docs/quality-to-architecture.md` from Lab 7).

**Endpoint:** `POST /api/allocations`

**Authentication:** Required (Administrator role — `ACCOMMODATION_ADMIN` or `SYSTEM_ADMIN`, per BR-04)

### Request

```json
{
  "studentId": "string (UUID, required)",
  "roomId": "string (UUID, required)",
  "applicationId": "string (UUID, required)"
}
```

### Request Validation

| **Field** | **Validation** | **Error Code** |
| :--- | :--- | :--- |
| studentId | Required, must be a valid UUID, must exist | VALIDATION_001 |
| roomId | Required, must be a valid UUID, must exist | VALIDATION_002 |
| applicationId | Required, must be a valid UUID, must exist, must belong to studentId | VALIDATION_003 |
| Authenticated user | Must have ACCOMMODATION_ADMIN or SYSTEM_ADMIN role | AUTH_001 |

### Business Rule Checks (performed within a single transaction)

| **Check** | **Rule** | **Failure Response** |
| :--- | :--- | :--- |
| Application status is PENDING | BR-03 | 409 CONFLICT — "Application is not in PENDING status" |
| Student has no active allocation | BR-01 | 409 CONFLICT — "Student already has an active allocation" |
| Room has available capacity | BR-02 | 409 CONFLICT — "Room is at full capacity" |

### Success Response (201 Created)

```json
{
  "allocationId": "string (UUID)",
  "studentId": "string (UUID)",
  "roomId": "string (UUID)",
  "applicationId": "string (UUID)",
  "allocationDate": "ISO 8601 timestamp",
  "applicationStatus": "APPROVED",
  "roomOccupancy": {
    "current": 3,
    "capacity": 4,
    "available": 1
  }
}
```

### Error Responses

| **HTTP Status** | **Error Code** | **Meaning** | **Recovery Action** |
| :--- | :--- | :--- | :--- |
| 400 Bad Request | VALIDATION_001 | Invalid or missing studentId | Correct request and retry |
| 400 Bad Request | VALIDATION_002 | Invalid or missing roomId | Correct request and retry |
| 400 Bad Request | VALIDATION_003 | Application does not belong to student | Correct request and retry |
| 401 Unauthorized | AUTH_001 | Missing or invalid authentication | Re-authenticate |
| 403 Forbidden | AUTH_002 | Insufficient role permissions | Use administrator account |
| 404 Not Found | NOT_FOUND_001 | Student, room, or application not found | Verify IDs |
| 409 Conflict | RULE_001 | Application not in PENDING status | Refresh application state |
| 409 Conflict | RULE_002 | Student already has active allocation (BR-01) | Release existing allocation first |
| 409 Conflict | RULE_003 | Room at full capacity (BR-02) | Select a different room |
| 500 Internal Server Error | SYSTEM_001 | Unexpected server error | Retry; if persists, contact support |
| 503 Service Unavailable | SYSTEM_002 | Database temporarily unavailable | Retry after a short delay |

### Idempotency

The operation supports an optional `Idempotency-Key` header. If the same key is received within 24 hours, the original response is returned without re-executing the operation. This prevents duplicate allocations from network retries.

### Transaction Boundary

All business rule checks and the allocation record creation occur within a single database transaction. If any check fails, the transaction is rolled back and no partial state is created. See `models/failure-recovery.*` for the worked concurrency scenario and `docs/data-integrity.md` for the full traceability from this contract back to BR-01/BR-02 and FR-07/FR-08/FR-09.
