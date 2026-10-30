# Pattern Decisions

CSI473 · Laboratory 9 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

## 1. Design Problems Identified

Identified by reviewing the Phase 1 baseline, the Lab 7 architecture (ADR-001) and the Lab 8 data/API design. Each problem is specific to this project and linked to a named requirement, quality scenario or architectural obligation.

| ID | Design problem | Source / link | Why it needs a solution |
|---|---|---|---|
| DP-01 | The core domain logic (especially allocation) must not depend on the concrete persistence mechanism (SQLite/PostgreSQL). If it does, BR-01 and BR-02 enforcement is coupled to database access, unit testing without a database becomes impossible, and the module boundary from ADR-001 is violated. | AD-01 (Lab 7), BR-01, BR-02, QS-03 | Core logic must be testable in isolation; persistence must be swappable without touching domain code. |
| DP-02 | `AllocationService` must enforce two independent business rules (BR-01, BR-02) atomically. If the checks are hard-coded as a linear sequence, adding or reordering rules later requires modifying the service and re-testing the whole workflow. | BR-01, BR-02, QS-03, AD-01 | Rule enforcement should be extensible and individually testable without rewriting the service. |
| DP-03 | The API layer receives HTTP requests and must translate them into domain operations, but the domain model should not know about HTTP, JSON or status codes. | Lab 8 API contract, `POST /api/allocations` | Request/response handling must be separated from domain logic. |
| DP-04 | Different clients (student browser, administrator browser, automated tests) need to call the same core operations. If the service is only reachable via HTTP controllers, unit tests must start a web server. | FR-07, FR-08, FR-09, QS-03 | The core operation must be callable directly from tests without HTTP. |
| DP-05 | `OccupancyTracker` derives occupancy by counting active allocations. The count is needed by both the allocation workflow (BR-02) and the admin room view (FR-10, QS-01). If each calls the database independently, performance suffers and the single source of truth (D-001) can diverge. | D-001, BR-06, QS-01 | Occupancy derivation must be centralised and reusable. |

## 2. Pattern Decision Table

Three techniques are selected. Each starts from a named problem; participants are visible in `models/detailed-class-design.*` and implemented in `src/`.

| Pattern | Problems | Why it fits | Alternatives considered | Consequence accepted | Participants (in `src/`) |
|---|---|---|---|---|---|
| **Repository** | DP-01, DP-04 | `AllocationService` depends on repository interfaces, not SQL; in-memory fakes enable database-free unit tests. | Direct SQL in the service; Active Record. | One extra interface layer and a domain-object ↔ row mapping step. Accepted: directly supports AD-01 and QS-03. | `AllocationRepository`, `RoomRepository`, `ApplicationRepository`, `OccupancyRepository`, `InMemory*Repository` |
| **Strategy** | DP-02 | Each business rule is a separate `AllocationRule`; the service iterates over an ordered list. A new rule (e.g. Phase 2 eligibility/priority rules) is a new class, not an edit to the service. | Hard-coded if/else sequence; Chain of Responsibility. | Slightly more classes; rules must be registered **and ordered** (BR-01 before BR-02). Accepted: BR-01/BR-02 are the most critical rules and must be independently testable (QS-03). | `AllocationRule`, `NoDuplicateActiveAllocation`, `RoomCapacityRule` |
| **Ports and Adapters** (applied selectively) | DP-03, DP-04, DP-05 | Explicit ports for the core operation; thin adapters (HTTP controller, tests) call them. `OccupancyTracker` is one shared domain service behind the ports. | Layered architecture with direct controller → service calls. | Port interfaces must be defined and adapters kept thin. Accepted: the core is callable without HTTP and the domain stays free of delivery concerns. | `AllocationUseCase`, `AllocationService`, `OccupancyTracker`, `AllocationController` (Phase 2) |

## 3. Explicit Rejection Decisions

### 3.1 Rejected technology: microservices / separate Allocation Service

| | |
|---|---|
| **Considered for** | AD-01 (atomic allocation), AD-04 (fault isolation) |
| **Reason for rejection** | Accidental complexity and operational cost: the operational cost (service discovery, network calls, distributed transactions, separate deployment) is disproportionate for a 12-week semester project with five people. The same atomicity is achieved inside one deployable unit with a database transaction (Lab 8, `models/failure-recovery.*`). Distributed transactions would add complexity the project cannot justify or test in the time available, and network calls would hide dependencies that are explicit in one process. Consistent with ADR-001 (modular monolith). |
| **Revisit if** | The allocation module needs independent scaling or deployment, which the Phase 2 load estimates (QS-01) do not currently suggest. |

### 3.2 Rejected pattern: Factory (for allocation strategy creation)

| | |
|---|---|
| **Considered for** | DP-02: creating the list of `AllocationRule` instances. |
| **Reason for rejection** | Only two rules exist and neither has configuration branching or polymorphic construction; both take their ports through the constructor. A factory would add accidental complexity (a class and an indirection with no decision for it to make) and would create a hidden dependency by obscuring which rules run. Rules are registered explicitly in one wiring class, which keeps the order visible. |
| **Consequence accepted** | Adding a rule means adding one line to the registration list. |
| **Revisit if** | Rule selection becomes runtime-configurable (e.g. per-hall or per-semester rule sets from the admin UI). That is the point at which construction has real branching. |

## 4. Patterns Deliberately Not Used

To avoid pattern-listing for its own sake, the team did not adopt Observer or Decorator in Phase 2's first slice: no design problem above needs them. Notification (a possible Observer use) is future work and will be re-assessed only when it is actually implemented.
