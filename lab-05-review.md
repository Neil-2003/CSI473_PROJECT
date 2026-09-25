# Lab 05 — Peer Review Findings and Exit Record

CSI473 · Laboratory 5 · Semester 1, 2026/27
Project: UB Student Accommodation System · Team 14

## Part A: Peer Review of Another Team's Phase 1 Draft

> This section is a template for the in-lab peer-review activity (Studio plan, 92–110 min). Fill in the reviewed team's number and the reviewer's name during the session, then record findings against each rubric criterion from the Lab 4/5 marking focus table.

**Team reviewed:** [Team ___ — to be completed in session]
**Reviewer(s):** [Name(s) — to be completed in session]
**Date:** 4 September 2026

| **Criterion** | **What earns full credit** | **Finding** | **Suggested improvement** |
| :--- | :--- | :--- | :--- |
| Purpose and traceability | Artefact is linked to a named requirement, use case, scenario or quality concern. | [ ] | |
| Technical correctness | Notation and content are appropriate, complete enough and internally valid. | [ ] | |
| Design rationale | The team explains the choice, a realistic alternative and the consequence accepted. | [ ] | |
| Consistency | Names, responsibilities and decisions agree with related artefacts in the same project. | [ ] | |
| Evidence and revision | Editable source, readable export, meaningful commit and a visible revision after critique are present. | [ ] | |

**Overall comment:** [One or two sentences of constructive, specific feedback for the reviewed team.]

## Part B: Exit Record — Our Own Draft

**Most important contradiction found:** While integrating Sections 4 and 6 of the Phase 1 draft, we noticed that Section 4.2 (Actors and Goals) lists **Residence Staff** as a primary actor separate from **Accommodation Administrator**, each with an overlapping goal ("manage applications/rooms/allocations efficiently"). However, the domain model (Lab 4) and CRC cards define only one `Administrator` class, exercising the `ACCOMMODATION_ADMIN` role, with no distinct "Residence Staff" class or use case. The use-case table (4.3) and use-case diagram also had no use case attributed to "Residence Staff" — every accommodation-management use case (UC-03 to UC-06) is attributed to "Accommodation Administrator" only.

**Why it is a contradiction:** A stakeholder that is described as a primary actor with system goals, but has no corresponding use case, domain class, or CRC responsibility, indicates the analysis models and the stakeholder/actor tables have drifted apart — exactly the kind of "individually attractive but describing different versions of the project" trap the Lab 5 brief warns against.

**Artefact(s) changed:** No structural model change was required. We resolved the contradiction by treating "Residence Staff" as a **stakeholder group** (Section 2.5) rather than a distinct **system actor** (Section 4.2): residence staff members who use the system do so through the `ACCOMMODATION_ADMIN` role and are therefore represented by the existing `Administrator`/`Accommodation Administrator` actor and domain class. A clarifying note was added to Section 4.2 in the next revision of the Phase 1 report to make this mapping explicit, so a reader does not expect a separate "Residence Staff" use case or class elsewhere in the document.

**Evidence still needed before Phase 1 submission (11 September 2026):**
- Architecture drivers and at least one ADR for Section 8, to be produced in Lab 6/7.
- Confirmation from the actual in-session peer review (Part A above) with a named partner team, plus a visible revision commit responding to their feedback.
- A final consistency pass across the whole report once Section 8 is added, to check that any new architecture-level terms do not introduce further drift from the domain model and CRC cards.
- Team sign-off that every member can explain each submitted artefact, per the project brief's oral-question condition.
