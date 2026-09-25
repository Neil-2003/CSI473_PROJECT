# Architecture Alternatives and Selection

CSI473 · Laboratory 7 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

## 1. Architecture Driver and Design Obligation Table

The following quality scenarios and constraints from Phase 1 (`docs/quality-requirements` / Phase 1 report Section 5) were selected as the most architecturally significant. Each is translated into a concrete design obligation that the architecture must satisfy.

| **Driver ID** | **Source (Quality Scenario / Constraint)** | **Architectural Design Obligation** |
| :--- | :--- | :--- |
| **AD-01** | **QS-03 (Data integrity / reliability):** An administrator attempts to allocate a student to a room already at capacity. The allocation must be rejected in 100% of attempts. | The allocation workflow must enforce **BR-01** (no duplicate active allocation) and **BR-02** (no over-capacity) **atomically**. There must be a single point of enforcement that cannot be bypassed. Concurrent allocation attempts must be serialised or use optimistic locking with a conflict check. |
| **AD-02** | **QS-04 (Security / authorisation):** An unauthorised user attempts to access an administrator-only function. 100% of attempts must be denied and logged. | Administrator functions (review, allocate, reject, manage rooms) must be behind a **centralised authorisation boundary**. Role checks must be enforced at the service layer, not only in the UI. All denied attempts must produce an audit log entry. |
| **AD-03** | **QS-01 (Performance):** A student requests their application status list. 95% of requests must complete within 2 seconds at up to 100 concurrent users. | Status retrieval must be a **read-optimised path** that does not require expensive joins or real-time occupancy recalculation. Data access must be bounded (indexed lookups by student ID). |
| **AD-04** | **QS-02 (Availability):** The system must maintain at least 99% uptime during the weekday application window (08:00–17:00). | The architecture must avoid a **single point of failure** in the critical request path. Fault isolation should be possible (e.g. a failure in the notification component must not block application submission). |
| **AD-05** | **Constraint (D-001):** Payment processing and external system integration are excluded from the system boundary. | The architecture must be a **standalone deployable unit** with no dependency on external university APIs. All data (student, room, application) must be maintained internally. |

## 2. Comparison of Architecture Alternatives

Two realistic architecture alternatives are compared using the same project-specific criteria derived from the drivers above. Editable diagram sources and readable exports for both alternatives are committed at `models/alt-a-layered-monolith.*` and `models/alt-b-modular-monolith.*`.

### Alternative A: Single Layered Monolith

A single deployable application containing all layers: presentation, application/service, domain, and persistence. All business logic — including allocation — sits in the service layer alongside every other service.

*(See `models/alt-a-layered-monolith.pdf` for the layer diagram.)* The Allocation logic (BR-01, BR-02) lives inside `AllocationService`, a peer of `AuthService`, `ApplicationService` and `RoomService` within one undifferentiated service layer.

### Alternative B: Modular Monolith with Separated Allocation Module (selected)

A single deployable unit, but internally structured as **vertical modules** with explicit interfaces. The Allocation module is isolated because it owns the most critical business rules (BR-01, BR-02) and the highest-integrity data.

*(See `models/alt-b-modular-monolith.pdf` for the module overview, and `models/component-architecture.*` for the full detailed component diagram.)*

### Comparison Table

| **Criterion** | **Alternative A: Layered Monolith** | **Alternative B: Modular Monolith** |
| :--- | :--- | :--- |
| **AD-01: Atomic allocation** | Enforced in `AllocationService`; risk that other services bypass it and write directly to repositories. | Enforced in an **isolated Allocation module** with an explicit interface; other modules cannot bypass it. Stronger ownership. |
| **AD-02: Centralised authorisation** | Authorisation in service layer; all services share the same layer so consistency depends on discipline. | Authorisation can be a **shared cross-cutting concern** or a dedicated Auth module; module boundaries make it explicit where checks must occur. |
| **AD-03: Read performance** | Simple: one process, direct repository queries. Easy to index. | Same performance characteristics (single process), but the Student module can have its own read-optimised query path without touching allocation logic. |
| **AD-04: Availability / fault isolation** | A failure in any layer affects the whole application. No isolation between notification and submission. | Better: notification can be a separate module with its own error handling; a failure there does not prevent allocation. Still one deployable unit, so process-level faults still affect all. |
| **AD-05: Standalone, no external APIs** | Both alternatives satisfy this equally. | Both alternatives satisfy this equally. |
| **Complexity / effort** | Lower: fewer explicit module boundaries, quicker to implement. | Moderate: requires defining module interfaces and enforcing dependency direction. More disciplined but more work. |
| **Feasibility for 12-week semester** | High: team can implement the full vertical slice quickly. | Medium-High: manageable if modules are kept small; risk of over-engineering. |
| **Testability of BR-01/BR-02** | Requires integration test through the service layer. | Allocation module can be unit-tested in isolation, which directly supports QS-03 verification. |
| **Risk of design drift** | Higher: developers may put allocation logic in UI or repository layer. | Lower: module boundary makes violations visible. |

## 3. Selected Architecture: Modular Monolith (Alternative B)

**Decision:** The team selects **Alternative B (Modular Monolith with Separated Allocation Module)** because it provides stronger enforcement of the critical business rules (AD-01) and clearer ownership of data, while remaining feasible within the semester timeframe.

Full rationale, positive/negative consequences and reconsideration triggers are recorded in `decisions/ADR-001-architecture.md`. The detailed component/module structure, responsibilities and interfaces are in `models/component-architecture.*`. The mapping from each quality scenario to the architectural elements that satisfy it is in `docs/quality-to-architecture.md`.
