# ADR-001: Architecture Selection

CSI473 · Laboratory 7 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

| **Field** | **Details** |
| :--- | :--- |
| **Decision ID** | ADR-001 |
| **Date** | 25 September 2026 |
| **Issue** | Which architecture best satisfies the project's quality requirements (QS-01 to QS-05) and constraints (D-001), given a 12-week semester and a five-person team? |
| **Alternative A (rejected)** | **Layered Monolith** — a single deployable application with presentation, service, domain, and persistence layers. |
| **Alternative B (selected)** | **Modular Monolith with Separated Allocation Module** — a single deployable unit internally structured as vertical modules, with the Allocation Module isolated because it owns BR-01 and BR-02. |
| **Decision** | Alternative B is selected. |

## Rationale

1. **AD-01 (Atomic allocation, from QS-03):** The Allocation Module provides a single, explicit enforcement point for BR-01 and BR-02. In Alternative A, allocation logic could be placed in multiple services or bypassed by direct repository access. The module boundary makes bypass visible and testable.
2. **AD-02 (Centralised authorisation, from QS-04):** Module boundaries make it clear where authorisation checks must occur. The Admin Module and Allocation Module both require role checks; separating them makes the security boundary explicit.
3. **AD-04 (Fault isolation, from QS-02):** Within a single deployable unit, module-level error handling means a failure in a non-critical module (e.g. notification) does not prevent the core allocation workflow from completing.
4. **Feasibility:** Alternative B is only marginally more complex than Alternative A. The module boundaries are logical (packages/namespaces), not physical (separate deployments), so operational cost remains low.

## Positive Consequences

- Critical business rules (BR-01, BR-02) are enforced in one place and can be unit-tested in isolation.
- Clear dependency direction reduces the risk of design drift.
- The architecture directly supports the traceability from QS-03 and QS-04 to concrete components (see `docs/quality-to-architecture.md`).
- Feasible within the semester timeframe.

## Negative Consequences

- Requires discipline to maintain module boundaries; without enforcement (e.g. package-private visibility or architecture tests), developers could still bypass the Allocation Module.
- Slightly more upfront design effort than a flat layered monolith.
- Occupancy is derived (per D-001), so the Allocation Module must query active allocations — a potential performance concern at very large scale (accepted trade-off).

## Risks and Reconsideration Triggers

| **Risk** | **Trigger for Reconsideration** |
| :--- | :--- |
| Module boundaries not enforced | If code review finds allocation logic outside the Allocation Module, introduce an architecture test or refactor. |
| Performance degradation from derived occupancy | If QS-01 (2-second status retrieval) is not met due to occupancy queries, introduce a read-model cache (while preserving the single source of truth). |
| Team inability to maintain modular discipline | If the team finds the module boundaries slow down development, simplify to Alternative A with strict code review. |
| Availability requirement not met (QS-02) | If the single deployable unit cannot meet 99% uptime, consider splitting the Allocation Module into a separate service (moves toward a distributed architecture). |
