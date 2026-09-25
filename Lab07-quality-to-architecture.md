# Quality-to-Architecture Traceability

CSI473 · Laboratory 7 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

Maps each Phase 1 quality scenario to the architectural element(s) responsible for satisfying it, and reviews the coupling, data ownership, security and failure boundaries that result from the selected architecture (ADR-001 — Modular Monolith).

## 1. Quality Scenario → Architectural Element

| **Quality Scenario** | **Architectural Element(s)** | **How the Architecture Responds** |
| :--- | :--- | :--- |
| **QS-01 (Performance)** | Student Module, Persistence Layer, Shared Domain | Status retrieval is a read-only path through Student Module → ApplicationRepository. No occupancy recalculation required for status display. Indexed by student ID. |
| **QS-02 (Availability)** | Whole system (single deployable), Allocation Module | Module-level error handling isolates faults. Notification (if implemented) is separate from allocation. Single deployable unit is a known risk — see ADR-001 reconsideration trigger. |
| **QS-03 (Data integrity)** | **Allocation Module** (AllocationService, OccupancyTracker) | BR-01 and BR-02 enforced atomically in `AllocationService.allocate()`. `OccupancyTracker.hasCapacity()` queries active allocations directly (single source of truth). No other module may create allocations. |
| **QS-04 (Security)** | Admin Module, Allocation Module, Presentation Layer | Role checks enforced at module entry points (AdminController, AllocationService). Denied attempts logged. UI hides admin functions but security does not rely on UI alone. |
| **QS-05 (Usability)** | Presentation Layer | Admin views support pagination, filtering, and sorting (FR-05 Revised). Empty-state messaging implemented in the UI. |

## 2. Coupling Review

| **Dependency** | **Direction** | **Coupling Type** | **Assessment** |
| :--- | :--- | :--- | :--- |
| Presentation → Student/Admin Modules | Downward | Method calls | Acceptable: UI depends on application services. |
| Admin Module → Allocation Module | Downward | Method calls | Acceptable and intentional: admin actions delegate allocation to the owner. |
| Student Module → Allocation Module | None | — | Good: students cannot invoke allocation directly. |
| Modules → Shared Domain | Downward | Shared classes | Acceptable: domain is a stable shared vocabulary. |
| Modules → Persistence | Downward | Repository interfaces | Acceptable: dependency inversion via interfaces. |

## 3. Data Ownership

| **Data** | **Owner** | **Access** |
| :--- | :--- | :--- |
| Student records | Student Module | Read/write by Student Module; read by Admin Module |
| Accommodation applications | Student Module (create), Admin Module (review/reject) | Shared via ApplicationRepository |
| Allocations | **Allocation Module** (exclusive write) | Read by Admin Module, Student Module |
| Room records | Admin Module | Read by Allocation Module for capacity checks |
| Occupancy (derived) | **Allocation Module** (computed) | Read by Admin Module |

## 4. Security Boundaries

- **Authentication boundary:** Login (UC-08) establishes identity and role.
- **Authorisation boundary:** Admin Module and Allocation Module check role before executing. Student Module only allows actions on the authenticated student's own data.
- **Audit boundary:** All denied authorisation attempts and all allocation/rejection actions are logged.

## 5. Failure Boundaries

| **Failure** | **Impact** | **Isolation Strategy** |
| :--- | :--- | :--- |
| Persistence failure | All modules affected | Transaction rollback; error returned to caller. |
| Notification failure (future) | Student not notified | Notification is a separate module; allocation remains valid; retry mechanism. |
| Allocation Module failure | Allocations cannot be created or released | Application submission and status viewing remain available; administrators see error. |
