# Accessibility Checklist

CSI473 · Laboratory 10 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

| Field | Details |
|---|---|
| **Artefact ID** | UX-DOC-003 |
| **Target** | WCAG 2.2 Level AA (design intent for the vertical slice) |
| **Screens** | A1, A2, A3, A5, S1 (`prototype/wireframes.svg`) |
| **Traceability** | UX-01 – UX-08, `docs/ui-states.md` |
| **Status** | Design column: provided in wireframe v1. Verification column: to be ticked after implementation. |

## 1. Lab 10 minimum standard

| Lab requirement | Items below | Design provision |
|---|---|---|
| Keyboard access | K-01 – K-04 | Native controls, logical order, no trap, skip link |
| Visible focus | F-01, F-02 | 3 px offset ring on every control, never hidden |
| Persistent labels | L-01, L-02 | Visible labels above inputs; no placeholder-only labels |
| Non-colour cues | C-01 – C-03 | Icon, text and border for status, error and occupancy |
| Clear errors | E-01 – E-04 | Plain-language messages, focus and announcement |
| Readable responsive layout | R-01 – R-04 | Reflow at 320 px, zoom to 200%, adequate targets |

## 2. Checklist

| ID | Requirement | WCAG 2.2 | Applies to | Design provision | Verification (after implementation) | Done |
|---|---|---|---|---|---|---|
| K-01 | All functions work by keyboard; no keyboard trap. | 2.1.1, 2.1.2 | All | Native buttons, links, radios and selects; review panel is inline, not a modal | Keyboard-only run of T1 and T2 | ☐ |
| K-02 | Focus order follows visual order. | 2.4.3 | A1, A2, A3 | Applicant summary, then rooms, then review panel, then Confirm | Tab-through | ☐ |
| K-03 | Skip link to main content. | 2.4.1 | All officer pages | First focusable element, visible on focus (wireframe A1) | Tab once from page load | ☐ |
| K-04 | Room choice usable with arrow keys. | 2.1.1 | A2 | Native radio group: arrows move, Tab leaves the group | Keyboard run | ☐ |
| F-01 | Focus indicator always visible. | 2.4.7 | All | 3 px ring, offset, ≥ 3:1 against background | Visual check, contrast tool | ☐ |
| F-02 | Focused element is not hidden by sticky UI. | 2.4.11 | A2 | Review panel and header are not sticky; if made sticky, add scroll padding | Zoom 200% plus Tab | ☐ |
| L-01 | Every input has a visible, persistent label. | 3.3.2, 2.4.6 | A1 search, A2 hall filter | Label above field, never placeholder-only | Automated check plus review | ☐ |
| L-02 | Buttons and links have names that make sense out of context. | 2.4.6, 4.1.2 | A1 rows | "Allocate Neo Moeng", not just "Allocate" | Screen-reader element list | ☐ |
| L-03 | Each page has a unique title and one h1. | 2.4.2, 1.3.1 | All | Titles such as "Allocate a room – UB Accommodation" | Review | ☐ |
| C-01 | Colour is never the only cue (status, error, selection). | 1.4.1 | All | Icon plus text ("✓ Allocated", "Error:") plus border; selected row has thick border plus radio | Greyscale screenshot | ☐ |
| C-02 | Occupancy is available as text. | 1.4.1, 1.1.1 | A2 | Bar always paired with "2 of 4 occupied" and "Full" or "n spaces free" | Screen-reader read-out | ☐ |
| C-03 | Text and UI contrast. | 1.4.3, 1.4.11 | All | Body text ≥ 4.5:1; borders, focus ring and bar segments ≥ 3:1 | Contrast tool | ☐ |
| E-01 | Errors identified in text and associated with the task. | 3.3.1 | A2, A1 | Banner names the problem and the affected room or applicant | Seeded-conflict test | ☐ |
| E-02 | Errors suggest what to do next. | 3.3.3 | A2 | Each message ends with a next step (choose another room, Check status) | Message review against `docs/ui-states.md` | ☐ |
| E-03 | Status and errors are announced without moving focus unexpectedly. | 4.1.3 | A1, A2, A3 | `role="alert"` for errors (focus moved deliberately to the banner); `role="status"` for success and refresh | Screen-reader walkthrough | ☐ |
| E-04 | Data-changing action is confirmed before commit. | 3.3.4 (intent) | A2 | Review panel states applicant and room; only Confirm calls the API | Usability test SC-3 | ☐ |
| R-01 | Content reflows at 320 CSS px without two-dimensional scrolling. | 1.4.10 | All | Two columns at ≥ 1024 px; single column below; room table becomes stacked cards | Resize to 320 px | ☐ |
| R-02 | Text resizes to 200% without loss. | 1.4.4 | All | Relative units; no fixed-height containers for text | Browser zoom | ☐ |
| R-03 | Touch and click targets are large enough. | 2.5.8 | All | ≥ 24×24 CSS px minimum, aim 44 px on S1 | Inspect | ☐ |
| R-04 | Body text is readable. | 1.4.3, 1.4.12 | All | Base size ≥ 16 px; line spacing not fixed | Visual | ☐ |
| T-01 | Tables use real header cells. | 1.3.1 | A1, A2 | `th` with scope; room radio rows labelled | Screen-reader table navigation | ☐ |
| T-02 | Name, role and value are exposed for custom parts. | 4.1.2 | A2 | Native elements first; `aria-disabled` on Full rooms and on Allocating button | Automated check | ☐ |
| T-03 | Input causes no unexpected change of context. | 3.2.2 | A2 | Selecting a room only reveals the review panel (announced); it does not navigate | Keyboard run | ☐ |

## 3. Verification plan

| Method | When | Covers |
|---|---|---|
| Automated checker (for example axe) on each screen state | Each Phase 2 UI commit | L-01, L-02, C-03, T-02 |
| Keyboard-only walkthrough of T1 and T2 | Before UI sprint review | K-01 – K-04, F-01, F-02 |
| Screen-reader walkthrough (NVDA or VoiceOver) of the RULE_002 conflict | Before UI sprint review | E-01 – E-03 |
| Zoom 200% and 320 px resize | Before UI sprint review | R-01 – R-04 |
| Greyscale review | Before UI sprint review | C-01, C-02 |

Do not tick "Done" from the wireframes alone; the boxes record post-implementation checks.
