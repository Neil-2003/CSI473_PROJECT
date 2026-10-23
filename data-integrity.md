# Data Integrity, Traceability and Test Plan

CSI473 · Laboratory 8 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

## 1. Data Integrity Constraints Summary

| **ID** | **Constraint** | **Enforcement** | **Related Rule** |
| :--- | :--- | :--- | :--- |
| DI-01 | A student may have at most one active allocation. | Partial unique index + service check | BR-01 |
| DI-02 | Active allocations per room ≤ room capacity. | Service-layer check within transaction | BR-02 |
| DI-03 | Application status must follow valid transitions. | Service-layer state machine check | BR-03 |
| DI-04 | Only registered students may submit applications. | FK constraint + role check | BR-05 |
| DI-05 | Rejection reason required when status = REJECTED. | CHECK constraint | FR-11 |
| DI-06 | Room capacity must be positive. | CHECK constraint | FR-12 |
| DI-07 | Room number unique within a residence. | UNIQUE constraint | FR-12 |
| DI-08 | Occupancy count matches active allocations. | Derived (not stored) — single source of truth | BR-06 |

## 2. Integrity Constraints to Requirement Traceability

| **Constraint** | **Business Rule** | **Requirement** | **Test** |
| :--- | :--- | :--- | :--- |
| DI-01 | BR-01 | FR-09, AC-04 | Allocate same student twice → second allocation rejected |
| DI-02 | BR-02 | FR-08, AC-03 | Allocate to full room → rejected with "room full" |
| DI-03 | BR-03 | FR-03, FR-11 | Attempt invalid status transition → blocked |
| DI-04 | BR-05 | FR-01, FR-02 | Non-student attempts to submit → denied |
| DI-05 | — | FR-11 | Reject without reason → blocked |
| DI-06 | — | FR-12 | Set capacity to 0 or negative → blocked |
| DI-07 | — | FR-12 | Duplicate room number in same residence → blocked |
| DI-08 | BR-06 | FR-10, AC-02 | Allocate → occupancy count matches active allocations |

## 3. Quality Scenario to Design Element Traceability

| **Quality Scenario** | **Design Element(s)** | **How the Design Responds** |
| :--- | :--- | :--- |
| QS-01 (Performance) | Logical data model (indexed student_id, application_id), API contract | Read-optimised queries; status retrieval by student ID index |
| QS-02 (Availability) | Deployment model (single deployable), failure handling (503 responses) | Module-level fault isolation; graceful error responses |
| QS-03 (Data integrity) | Allocation table constraints, transaction design, API rule checks | BR-01 and BR-02 enforced atomically within a transaction |
| QS-04 (Security) | Deployment trust boundaries, API authentication/authorisation | Role checks at API layer; denied attempts logged |
| QS-05 (Usability) | API error codes and messages, deployment model | Clear error meanings; empty-state messaging in UI |

## 4. Failure Handling Mechanisms

Full worked example: `models/failure-recovery.*` (concurrent allocation attempts on the same room).

| **Failure** | **Mechanism** | **Outcome** | **Rule Preserved** |
| :--- | :--- | :--- | :--- |
| **Concurrent allocation (race condition)** | Database transaction with serialisable isolation level; capacity check and insert within the same transaction. | One allocation succeeds; the other receives 409 Conflict and is rolled back. | BR-02 (no over-capacity) |
| **Duplicate request (network retry)** | Idempotency-Key header; service caches responses for 24 hours. | Duplicate request returns the original response without creating a second allocation. | BR-01 (no duplicate allocation) |
| **Database unavailable** | Service catches connection error and returns 503 Service Unavailable with a Retry-After header. | No partial state; client can retry. | Data integrity |
| **Application not in PENDING status** | Service checks application status before proceeding. | Returns 409 Conflict with an explanatory message. | BR-03 (valid transitions) |
| **Student already has active allocation** | Service queries `getActiveAllocation(studentId)` before creating. | Returns 409 Conflict; existing allocation unchanged. | BR-01 |
| **Partial failure during transaction** | Database transaction rollback. | No allocation record, no application status change, no occupancy record. | All-or-nothing consistency |

## 5. Recovery Procedures

| **Failure Type** | **Recovery Action** | **Verification** |
| :--- | :--- | :--- |
| Transaction rollback | Retry the operation with corrected data or a different room. | Check that no orphaned records exist. |
| Database connection failure | Wait for Retry-After period, then retry. | Confirm database is reachable. |
| Application status conflict | Refresh application state; verify current status. | Confirm status is PENDING before retrying. |
| Duplicate allocation detected | Release the existing allocation before creating a new one. | Confirm student has no active allocation. |

## 6. Test Plan for the Critical Concurrency Scenario

The test that directly verifies QS-03 against the mechanism in `models/failure-recovery.*`:

1. Set up a room with capacity 1 and no active allocations.
2. Fire two simultaneous allocation requests for the same room to two different students.
3. Assert that exactly one request returns 201 Created and the other returns 409 Conflict.
4. Assert that the room's active allocation count is exactly 1 (not 2).
5. Assert that no orphaned occupancy records exist.

This test, plus the DI-01 through DI-08 test cases in Section 2 above, form the data-integrity portion of the Phase 2 test plan.
