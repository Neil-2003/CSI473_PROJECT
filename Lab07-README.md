# Lab 07 — Evidence Summary

CSI473 · Laboratory 7 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14
Date: 25 September 2026

## Repository Evidence

| **File** | **Description** |
| :--- | :--- |
| `docs/architecture-options.md` | Architecture-driver/design-obligation table, comparison of Alternative A (layered monolith) vs. Alternative B (modular monolith), and the selection decision. |
| `models/component-architecture.dot` / `.svg` / `.pdf` | Editable source and readable exports of the detailed component/module diagram for the selected architecture, plus `component-architecture.md` (module responsibilities and interfaces table). |
| `models/alt-a-layered-monolith.*` / `models/alt-b-modular-monolith.*` | Supporting diagrams for the two alternatives compared in `docs/architecture-options.md`. |
| `decisions/ADR-001-architecture.md` | Standalone Architecture Decision Record: context, alternatives, decision, positive/negative consequences, risks and reconsideration triggers. |
| `docs/quality-to-architecture.md` | Traceability from QS-01–QS-05 to architectural elements, plus coupling review, data ownership, security boundaries and failure boundaries. |
| `evidence/lab-07/README.md` | This file — evidence summary and exit record. |

## Commit Record

- Branch: `lab-07` (created/updated from Phase 1 baseline per the Lab 7 brief).
- Initial commit: architecture-driver table, alternatives comparison and first pass of the component diagram.
- Revision commit (after critique — see Exit Record below): tightened the Student Module boundary so it has **no** dependency on the Allocation Module, and added the explicit "delegates allocation requests" edge from Admin Module → Allocation Module so the diagram makes the intended dependency direction unambiguous rather than implicit.

## Exit Record

**Quality requirement that most influenced the architecture:** **QS-03 (Data integrity / reliability)** — the requirement that 100% of over-capacity allocation attempts are rejected. This drove the decision to isolate the Allocation Module as the single enforcement point for BR-01 and BR-02, which in turn shaped the entire module structure (Section 3, `docs/architecture-options.md`; ADR-001).

**Evidence that would cause the team to revise the decision:**
- If automated tests show that BR-01 or BR-02 can be bypassed (i.e. an allocation is created outside the Allocation Module), the architecture must be revised to enforce boundaries more strictly (e.g. architecture tests, package-private visibility, or a separate service).
- If QS-01 (2-second status retrieval) fails because derived occupancy queries are too slow, the team would introduce a read-model cache while preserving the Allocation Module as the single source of truth.
- If QS-02 (99% uptime) cannot be met with a single deployable unit, the team would reconsider splitting the Allocation Module into a separately deployable service.

These triggers are also recorded in the "Risks and Reconsideration Triggers" table of `decisions/ADR-001-architecture.md` so they remain visible alongside the decision itself.
