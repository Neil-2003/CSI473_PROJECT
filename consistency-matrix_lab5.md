# Consistency Matrix

CSI473 · Laboratory 5 · Semester 1, 2026/27
Project: UB Student Accommodation System

Links the UC-01 (Apply for Accommodation) steps to the sequence-diagram messages, the responsible class (from `docs/crc-cards.md`), the resulting application state (from `models/lifecycle-or-activity.*`), and the requirement/business rule each step satisfies.

| **UC-01 step** | **Sequence message** | **Responsible class (CRC)** | **Resulting state** | **Requirement(s) / rule(s)** |
| :--- | :--- | :--- | :--- | :--- |
| 1–2. Student fills in and submits the application. | `Student -> App: submitApplication(details, preferences)` | `AccommodationApplication` (CRC-01: create, store) | `DRAFT` → `SUBMITTED` | FR-02 |
| 3. System validates the submission and records it as Pending. | `App -> App: validate()` → `App --> Student: status = PENDING` | `AccommodationApplication` (CRC-01: validate, track status) | `SUBMITTED` → `PENDING` (or back to `DRAFT` if invalid) | FR-03, BR-03, BR-05 |
| 4–5. Administrator opens and reviews the Pending application. | `Admin -> App: reviewApplication()`; `App -> Room: checkAvailability()` | `Administrator` (CRC-04: review); `Room` (CRC-02: calculate occupancy, determine availability) | `PENDING` (unchanged during review) | FR-05 (Revised), FR-06 |
| 6. Administrator allocates an eligible student to an available room (basic flow). | `Admin -> Alloc: allocate(student, room)`; `Alloc -> Student: hasActiveAllocation()?`; `Alloc -> Room: hasCapacity()?`; `Alloc -> Occ: openRecord(...)`; `Alloc -> App: setStatus(APPROVED)` | `Allocation` (CRC-03: create, ensure BR-01/BR-02, record date); `Room` (CRC-02: refuse if full); `OccupancyRecord` (CRC-05: open record) | `PENDING` → `APPROVED` | FR-07, FR-10, BR-01, BR-02, BR-06 |
| Postcondition. Student is notified of the outcome. | `App --> Student: notify(APPROVED)` | `AccommodationApplication` (CRC-01: notify) | `APPROVED` (terminal) | Objective 3 |
| A2. Room has no remaining capacity. | `Alloc -> Admin: reject("room at capacity")` | `Room` (CRC-02: refuse allocation); `Allocation` (CRC-03: ensure BR-02) | `PENDING` (unchanged) | FR-08, BR-02 |
| A3. Student already holds an active allocation. | `Alloc -> Admin: reject("student already allocated")` | `Allocation` (CRC-03: ensure BR-01) | `PENDING` (unchanged) | FR-09, BR-01 |
| A4. Administrator rejects the application with a reason. | `Admin -> App: rejectApplication(reason)`; `App -> App: setStatus(REJECTED)`; `App --> Student: notify(REJECTED, reason)` | `Administrator` (CRC-04: reject); `AccommodationApplication` (CRC-01: record rejection reason, notify) | `PENDING` → `REJECTED` | FR-11, BR-03 |
| Invariant (all steps). | Every transition is checked against the lifecycle model before being applied. | `AccommodationApplication` (CRC-01: track and manage status) | Blocks `PENDING → DRAFT` and any transition out of `APPROVED`/`REJECTED` | BR-03 |

## Notes on consistency

- Every actor named in the sequence diagram (`Student`, `Administrator`) matches an actor defined in Section 4.2 of the Phase 1 report and in `docs/traceability-matrix.md`.
- Every object lifeline (`:AccommodationApplication`, `:Room`, `:Allocation`, `:OccupancyRecord`) matches a class in `models/domain-model.pdf` and a CRC card in `docs/crc-cards.md`; no new classes were introduced only for the sequence diagram.
- Every state named in `models/lifecycle-or-activity.*` (`DRAFT`, `SUBMITTED`, `PENDING`, `APPROVED`, `REJECTED`) matches the `ApplicationStatus` enumeration in the domain model and BR-03 in `docs/business-rules.md`.
- Every requirement ID referenced above (FR-02 … FR-11, BR-01 … BR-06) matches the IDs used in the Phase 1 report's functional requirements table and in `docs/traceability-matrix.md`.
