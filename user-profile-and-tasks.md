# User Profile and Task Scenarios

CSI473 · Laboratory 10 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

| Field | Details |
|---|---|
| **Artefact ID** | UX-DOC-001 |
| **Date** | 23 October 2026 (Laboratory 10) |
| **Applies to** | Vertical slice "Allocate Student to Room": `POST /api/allocations` behind `AllocationController` → `AllocationUseCase` (ADR-002) |
| **Status** | Draft v1, awaiting the three-person usability check (`evidence/usability-test.md`) |
| **Traceability** | FR-07 – FR-10, BR-01, BR-02, BR-06, D-001, QS-01, QS-03, DP-03, DP-04, ADR-002, proposed UX-01 – UX-08 (section 7) |

## 1. Scope and assumptions to confirm

The Lab 9 files define the allocation workflow but not the screens. These assumptions are made so the UI agrees with them. Confirm each against the Lab 8 API contract and amend this file if one is wrong.

| # | Assumption | Basis |
|---|---|---|
| A1 | Only an **Accommodation Officer** may call `POST /api/allocations`; students are refused. | Class design: `AllocationController` does a role check (AUTH_001/002). |
| A2 | AUTH_001 = not signed in or session expired; AUTH_002 = signed in but role not permitted. | Naming only; not defined in the Lab 9 files. |
| A3 | An allocation request identifies one approved application and one room. | `AllocationRequest`, `ApplicationRepository`, `RoomRepository`. |
| A4 | Read endpoints for the officer queue and the student status view exist or will be added in Phase 2. | Not named in Lab 9; screens are designed ahead of them. |
| A5 | UI delivery (server-rendered pages or JavaScript front end) is undecided. Wireframes are technology-neutral. | FD-001 covers the back end only. |
| A6 | Any error code not listed in `docs/ui-states.md` is shown with the generic error message. | Safe default. |
| A7 | Field names and example data in wireframes are illustrative. | Align with the Lab 8 data model. |

## 2. Primary user: Accommodation Officer

| Attribute | Profile (assumed; validate in the usability check) |
|---|---|
| **Role** | University staff member who places approved applicants into rooms (administrator role, FR-07 – FR-09 as cited in DP-04). |
| **Context** | Desk-based, desktop or laptop browser (down to 1366×768), interrupted by phone calls and walk-ins. |
| **Volume** | Dozens of allocations in one sitting at the start of semester; speed and keyboard efficiency matter. |
| **Collaboration** | Several officers work at once. Room occupancy changes under them (the QS-03 / BR-02 race that ADR-002 handles with `SELECT … FOR UPDATE`). |
| **Experience** | Comfortable with web forms and tables; not a developer; does not know rule codes. |
| **Accessibility needs considered** | Keyboard-heavy use, screen magnification or 200% zoom, colour-vision deficiency, screen readers. |
| **Goals** | Place each applicant in a suitable room quickly and correctly; never double-allocate a student or overfill a room. |
| **Frustrations to design against** | Not seeing who is being allocated; acting on out-of-date room availability; cryptic errors; not knowing whether a failed click saved anything. |

## 3. Secondary user: Student

| Attribute | Profile |
|---|---|
| **Role** | Applicant who has applied for accommodation. |
| **Context** | Phone-first, intermittent connection, checks status repeatedly. |
| **Goal** | Find out whether a room has been allocated and which one. |
| **Frustration to design against** | Mistaking "not yet allocated" for a failure. |

**Why the officer is primary.** The Lab 9 slice (ADR-002) is the officer's allocation workflow, so it gets the deepest design. **Alternative considered:** make the student primary. Rejected because the student only reads the outcome and cannot call `POST /api/allocations`. **Consequence accepted:** the student screen (S1) is a light, read-only design.

## 4. Task T1 (primary): Allocate an approved applicant to a room

**Scenario.** It is the first week of semester. The officer opens the queue of approved applications. Neo Moeng (S-1001, APP-0412) is next. The officer must place Neo in a Hall B room that has a free space, confirm the allocation and move on to the next applicant. While the officer is choosing, a colleague may take the last space in the same room.

**Preconditions.** Officer signed in; application approved and not yet allocated; at least one room with space.

**Main path (A1 → A2 → A3).**
1. On A1, activate "Allocate" for Neo Moeng.
2. On A2, check the applicant summary, then select a room.
3. Read the review panel and activate "Confirm allocation".
4. On A3, read the confirmation and activate "Allocate next applicant".

**Success criteria.**

| ID | Criterion | Check |
|---|---|---|
| SC-1 | Participant reaches A3 with the correct applicant and room, without help. | Usability test, 3 of 3 |
| SC-2 | No more than 3 deliberate actions from the loaded queue to confirmation (Allocate, select room, Confirm). | Navigation model, test count |
| SC-3 | Applicant and room are both visible on screen before Confirm. | Wireframe A2 |
| SC-4 | Practised officer completes in 60 seconds or less (target; revise after test). | Stopwatch |
| SC-5 | After a RULE_002 conflict, participant explains what happened and allocates another room without help. | Seeded-conflict task |
| SC-6 | After a timeout, no participant retries blindly and creates a duplicate; BR-01 remains the backstop. | Timeout task |

**Failure signals.** Wrong applicant or room confirmed; participant cannot tell whether the allocation saved; participant misses the Confirm step; keyboard user cannot complete.

## 5. Task T2 (secondary role): Student checks accommodation status

**Scenario.** The student signs in on a phone and wants to know whether a room has been assigned.

**Main path (Sign in → S1).** S1 is the landing page, so no navigation action is needed.

| ID | Criterion |
|---|---|
| SC-7 | Participant states allocated or not, and the room and hall, within 10 seconds of S1 loading. |
| SC-8 | "Not yet allocated" is understood as pending, not as an error. |
| SC-9 | No allocation controls are available to students. |

## 6. Design decisions (rationale, alternative, consequence)

| ID | Decision | Realistic alternative | Consequence accepted |
|---|---|---|---|
| DD-UX-01 | One **Allocate** screen (A2) showing applicant, rooms and confirmation together. | Three-step wizard (applicant, room, confirm). | A denser screen that must reflow to one column on small viewports; far fewer steps and no back-and-forth, which supports SC-2. |
| DD-UX-02 | Room choice by **radio selection plus a review panel**; only Confirm calls the API. | One "Allocate" button on every room row. | One extra action. Prevents mis-clicks on an action that changes data (error prevention, WCAG 3.3.4 intent). |
| DD-UX-03 | **Server is the authority** on capacity. The UI shows occupancy as information with a timestamp and never blocks or permits on cached numbers. | Disable full rooms and trust the list. | A RULE_002 error is still possible and gets a designed recovery path (A2 error state). Matches ADR-002 (check re-run on committed data). |
| DD-UX-04 | **No auto-retry** after a timeout; offer "Check status". | Silent automatic retry. | One manual step; avoids unconfirmed duplicate attempts (QS-03, BR-01). |
| DD-UX-05 | Full rooms stay focusable (`aria-disabled`) and say "Full". | HTML `disabled` radios. | Slightly more script; keyboard and screen-reader users still discover why the room is unavailable. |
| DD-UX-06 | **No offline queueing** of allocations; show an unavailable state. | Queue the request until reconnection. | Officers must be online; avoids committing against stale capacity. |

## 7. Consistency with Lab 9 and traceability

### 7.1 UI action to design element

| UI element | Calls | Core element (Lab 9) | Rule or decision | UI outcome |
|---|---|---|---|---|
| Confirm allocation | `POST /api/allocations` | `AllocationController` → `AllocationUseCase.allocate()` → `AllocationService` | Rules run in order BR-01 (`RULE_001`) then BR-02 (`RULE_002`); first failure decides the error (ADR-002). | If both fail, the officer sees the RULE_001 message. |
| Occupancy figures in room list | room read | `OccupancyTracker.deriveOccupancy()` | D-001, BR-06 | Displayed as "n of m occupied" with a timestamp; not computed in the browser. |
| Role gate | any protected route | `AllocationController` role check | AUTH_001 / AUTH_002 | Sign-in redirect or "not permitted" page (A5). |
| Atomic save | n/a | `@Transactional`, `SELECT … FOR UPDATE` | QS-03 | A failed or timed-out allocation leaves no partial state, so "Check status" is safe. |

### 7.2 Proposed usability and accessibility requirements

These are new. Add them to the requirements register (suggested IDs UX-01 – UX-08).

| ID | Requirement | Linked to | Artefacts | Verification after implementation |
|---|---|---|---|---|
| UX-01 | Every task (T1, T2) is completable by keyboard alone, with no trap. | FR-07 – FR-10 | Wireframes A1–A3, S1; checklist K-items | Keyboard-only script on T1 and T2 |
| UX-02 | Focus is always visible (≥ 3:1 contrast, not hidden by sticky UI). | FR-07 – FR-10 | Wireframe focus rings | Manual tab-through, automated check |
| UX-03 | Every input has a persistent visible label. | FR-10 | A1 search, A2 hall filter | Review plus automated check |
| UX-04 | Officer completes T1 in ≤ 3 deliberate actions; applicant and room are visible before Confirm. | DP-04, QS-01 | `models/navigation.svg`, A2 | Usability test SC-2, SC-3 |
| UX-05 | Status, error and occupancy are never conveyed by colour alone. | BR-02, BR-06 | A2 occupancy bars and text, banners | Greyscale view, automated contrast check |
| UX-06 | Every error says what happened, the effect and the next step, and is announced to assistive technology. | QS-03, BR-01, BR-02 | `docs/ui-states.md` message catalogue | Screen-reader walkthrough, seeded-conflict test |
| UX-07 | Layout reflows at 320 px and at 200% zoom; targets ≥ 24×24 CSS px (aim 44). | FR-07 – FR-10 | A1–A3, S1 responsive notes | Resize and zoom test |
| UX-08 | Loading, empty, validation, error, unauthorised and unavailable/stale states exist for every screen. | QS-01, QS-03 | `docs/ui-states.md` | State-by-state test with stubbed API |

QS-01: copy the numeric target from the Lab 8 register into UX-08 so the loading-state threshold is testable.
