# Usability Test Record

CSI473 · Laboratory 10 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

| Field | Details |
|---|---|
| **Artefact ID** | UX-EV-001 |
| **Prototype tested** | `prototype/wireframes.svg` v1 (printed or shown full-screen, frame by frame) |
| **Tasks** | T1 allocate, T1-C seeded conflict, T2 student status (`docs/user-profile-and-tasks.md`) |
| **Participants** | 3 (P1, P2, P3; codes only, no personal data recorded) |
| **Status** | **Protocol ready. Observations, results and revisions below are blank until the session is run.** Do not fill them with expected results. |

## 1. Method

| Item | Plan |
|---|---|
| Format | Moderated, think-aloud, about 10 minutes per participant |
| Roles | One facilitator, one note-taker (a third teammate is the "colleague" in T1-C) |
| Participants | P1: peer playing the officer. P2: keyboard-only (do not use the mouse). P3: browser at 200% zoom or a narrow window, plus does T2 on a phone-width view. |
| Consent | Verbal; tell participants the design is tested, not them; record by code only |
| Script | Read the scenario aloud; do not explain the screens; ask "what do you expect to happen?" before each click |

## 2. Tasks

| ID | Participant instruction | Success |
|---|---|---|
| T1 | "Neo Moeng is approved. Give Neo a room in Hall B that has space, and confirm it." | Reaches A3 with the right applicant and room; no help |
| T1-C | Same task. While the participant is choosing, the colleague announces they have taken the last space in the chosen room. The participant then confirms. Show frame A2-error. | Participant says what happened, that nothing was saved for Neo, and picks another room |
| T2 | "You are a student. Find out whether you have a room and which one." Show S1 (allocated), then the not-yet-allocated variant. | States the result within 10 s; calls the pending state "not yet", not "broken" |

## 3. Measures

| Measure | How |
|---|---|
| Completion | Y / partial / N |
| Deliberate actions | Count from loaded queue to Confirm (target ≤ 3) |
| Time | Stopwatch (target for T1 ≤ 60 s once familiar) |
| Hesitation or error | Note the screen element and what the participant said |
| Severity | 0 none, 1 cosmetic, 2 minor, 3 major, 4 blocker |

## 4. Observation record (complete during the session)

| Participant | Task | Observation (what happened, quote) | Result | Actions | Time | Severity |
|---|---|---|---|---|---|---|
| P1 | T1 | | | | | |
| P1 | T1-C | | | | | |
| P1 | T2 | | | | | |
| P2 | T1 | | | | | |
| P2 | T1-C | | | | | |
| P2 | T2 | | | | | |
| P3 | T1 | | | | | |
| P3 | T1-C | | | | | |
| P3 | T2 | | | | | |

## 5. Risks to watch (hypotheses, not findings)

These come from the design review, not from users. Confirm or discard each during the session.

| ID | Hypothesis | Watch for | Prepared revision if observed |
|---|---|---|---|
| H1 | The review panel sits below the room list and may be missed or scrolled off at 1366×768. | Participant stops after choosing a room and looks for a button | Move the review panel next to the applicant summary (always visible) |
| H2 | The RULE_002 banner may be read as a system fault, not as a normal conflict. | Participant says "it broke" or retries the same room | Lead the message with "Another officer took the last space"; add a one-line reason |
| H3 | "Check status" after a timeout may be unclear. | Participant retries blindly or abandons | Rename to "Check if Neo was allocated"; show the result inline |
| H4 | The not-yet-allocated state on S1 may look like an error. | Participant asks "what went wrong?" | Add a neutral status badge ("Pending") and remove any warning styling |
| H5 | Keyboard users may not notice that selecting a room revealed the review panel. | P2 searches for Confirm | Move focus to the review heading on selection, or keep the panel always rendered |

## 6. Revision log (complete after the session)

| Rev | Source (observation ID or hypothesis) | Change made | Screens and files updated | Verification after implementation |
|---|---|---|---|---|
| R-1 | | | | |
| R-2 | | | | |

At least one revision must trace to an observed difficulty in section 4, not only to a hypothesis. After revising, bump the wireframe title to v2 and update `docs/ui-states.md` and `docs/accessibility-checklist.md` if the change touches them.

## 7. Exit record

| Question | Answer (complete after the session) |
|---|---|
| Most important usability problem observed | |
| Interface change made | |
| How it will be verified after implementation | |

Suggested verification wording once the problem is known: an end-to-end test that reproduces the situation (for a conflict, two simultaneous `allocate()` calls for the last space as in the ADR-002 acceptance test) and asserts the visible message, focus position and next step, repeated by keyboard only.
