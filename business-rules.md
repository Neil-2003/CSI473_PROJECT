# Business Rules and Invariants

CSI473 · Laboratory 4 · Semester 1, 2026/27
Project: UB Student Accommodation System

## Domain Rules

### BR-01: Unique Active Allocation
> **Statement:** A student must not hold more than one active accommodation allocation at the same time.
> **Rationale:** This prevents a single student from occupying multiple rooms, ensuring fair distribution of accommodation.
> **Verification:** A system check must be performed before creating a new allocation. If an active allocation for the student exists, the new allocation attempt must be rejected.
> **Source:** FR-09, AC-04, Project Proposal (Important Business Rules), UC-01 (A3).

### BR-02: Room Capacity Enforcement
> **Statement:** The number of students allocated to a room must never exceed the room's maximum capacity.
> **Rationale:** This ensures safety, legal compliance, and accurate resource management.
> **Verification:** Before an allocation is confirmed, the system must count the number of active allocations for the target room and compare it to the room's capacity. If the room is full, the allocation must be rejected.
> **Source:** FR-08, AC-03, QS-03, Project Proposal (Important Business Rules), UC-01 (A2).

### BR-03: Application Status State Machine
> **Statement:** An application must follow a defined life cycle: `DRAFT` → `SUBMITTED` → `PENDING` → `APPROVED` or `REJECTED`.
> **Rationale:** Provides a clear and auditable trail for every application.
> **Verification:** The system must only allow valid status transitions. For example, an application cannot go directly from `PENDING` to `DRAFT`, and an `APPROVED` or `REJECTED` application cannot be changed to a new status.
> **Source:** FR-03, FR-11, Project Proposal (Important State-Sensitive Entities), UC-01.

### BR-04: Authorised Administrator Actions
> **Statement:** Only a user with the `ACCOMMODATION_ADMIN` or `SYSTEM_ADMIN` role is permitted to review applications, make allocations, reject applications, and manage room/residence records.
> **Rationale:** Ensures data integrity and security by limiting critical actions to authorised personnel.
> **Verification:** The system must check the user's role before granting access to administrative functions. All administrator actions must be logged.
> **Source:** FR-05, FR-06, FR-07, FR-12, Project Proposal (Important Business Rules).

### BR-05: Student Eligibility for Application
> **Statement:** Only registered UB students may submit an accommodation application.
> **Rationale:** Restricts the service to the intended user base.
> **Verification:** A user must be logged in with the `STUDENT` role to access and submit the application form.
> **Source:** FR-01, FR-02, Project Proposal (Important Business Rules), UC-01 (Preconditions).

### BR-06: Occupancy Accuracy
> **Statement:** A room's occupancy information must always be consistent with its active allocations.
> **Rationale:** Provides a reliable, real-time view of room availability to prevent over-allocation and aid planning.
> **Verification:** The system must automatically update the room's occupancy record whenever an allocation is created or removed. The number of active allocations for a room must always equal its current occupancy count.
> **Source:** FR-10, Project Proposal (Important Business Rules), AC-02, UC-01 (Postconditions).

## Exit Record

**Concept/responsibility that moved after critique:** The "one active allocation per student" check (BR-01) was initially considered as an attribute-level constraint on `Student` (a nullable `currentRoomId` field). After critique it was reallocated as a responsibility of the `Allocation` class itself (see D-002), which checks for existing active allocations before creating a new one.

**Why it moved:** The original placement put enforcement logic on `Student`, a class that does not own allocation history and would need to query `Allocation` records anyway to stay accurate.

**How the change improves cohesion/reduces coupling:** `Allocation` now owns both the creation logic and the invariant it must satisfy (BR-01), which is a stronger information-expert fit. `Student` no longer needs to know about allocation internals, reducing coupling between the two classes and keeping the "one active allocation" rule enforced in a single place.
