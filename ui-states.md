# UI States

CSI473 · Laboratory 10 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

| Field | Details |
|---|---|
| **Artefact ID** | UX-DOC-002 |
| **Screens** | A1 Allocation queue, A2 Allocate to room, A3 Allocation confirmed, A5 Auth pages, S1 My accommodation (see `models/navigation.svg`, `prototype/wireframes.svg`) |
| **Traceability** | UX-06, UX-08, BR-01, BR-02, QS-01, QS-03, ADR-002 |
| **Status** | Draft v1 |

## 1. Cross-cutting rules

| Rule | Detail |
|---|---|
| Server decides | Occupancy on screen is informational and timestamped. BR-01 and BR-02 are decided in the server transaction (ADR-002), so the UI never blocks or permits an allocation on cached numbers. |
| Loading | Show text such as "Loading rooms…" (not a spinner alone) after about 300 ms; set `aria-busy="true"` on the region; keep old content during refresh. Copy the QS-01 numeric target here when known. |
| Errors | Banner above the main content, `role="alert"`, focus moved to it, icon plus "Error:" text plus thick border. |
| Success and status | `role="status"` (polite), announced once. |
| Double submit | After Confirm, the button reads "Allocating…" and uses `aria-disabled="true"`; it stays focusable so focus is not lost. |
| Timeout | No automatic retry. Offer "Check status", which reloads the applicant and the queue. Safe because allocation is atomic (QS-03). |
| Offline | Not supported. Show the unavailable state; never queue an allocation (DD-UX-06). |
| Prevention first | Rooms are chosen by radio buttons and Confirm exists only once a room is selected, so "no room chosen" is prevented by design, not reported. |

## 2. Screen A1: Allocation queue

| State | Trigger | What the user sees | Assistive technology | Recovery |
|---|---|---|---|---|
| Normal | Queue loaded | Table of waiting applicants, count, "Updated hh:mm" | Table headers; button names include the applicant | n/a |
| Loading | First load or Refresh | "Loading applications…" in the table area; old rows kept on refresh | `aria-busy`; "Applications updated" announced on completion | n/a |
| Empty | No approved applications waiting | "No applications are waiting for a room." with a link to Rooms | Heading plus text; link focusable | Open Rooms |
| Empty search | Filter matches nothing | "No applicants match “{query}”." and a Clear search button | Announced via status | Clear search |
| Validation | n/a | The search box accepts any text, so there is nothing to reject | n/a | n/a |
| Error | Queue request fails | "Error: We couldn't load the queue." and Try again | Alert, focus on banner | Try again |
| Stale | Data older than the proposed 5-minute threshold | "Updated 10:42, may be out of date" with Refresh emphasised | Text, not colour | Refresh |
| Unavailable | Service down or no network | "Error: The accommodation service is unavailable. Try again in a few minutes." | Alert | Try again later |
| Unauthorised | AUTH_001 or AUTH_002 | Auth pages (section 5) | See A5 | See A5 |

## 3. Screen A2: Allocate to room

| State | Trigger | What the user sees | Assistive technology | Recovery |
|---|---|---|---|---|
| Normal | Applicant and rooms loaded | Applicant summary; room radios with "n of m occupied" bar plus text; review panel after a room is selected | Radio group with arrow-key movement; row label read as "B-204, Hall B, 2 of 4 occupied, 2 spaces free" | Confirm or Cancel |
| Loading rooms | Open A2 or Refresh rooms | "Loading rooms…"; applicant summary already shown | `aria-busy` on room region | n/a |
| Empty | No room has free space | "No rooms currently have free space." and links to Refresh and Rooms; Confirm not shown | Status text | Refresh; or free a room |
| Prevented validation | No room selected | No Confirm control is present | n/a | Select a room |
| Server validation (V-1) | Request invalid, room or application no longer exists | "Error: That room or application is no longer available. Rooms have been refreshed." | Alert, focus on banner | Choose again or return to queue |
| RULE_001 | Student already has an active allocation (BR-01, checked first) | Error banner; room list and Confirm hidden; link "View current allocation" | Alert | Return to queue |
| RULE_002 | Room full when saved (BR-02), including the concurrent case | Error banner; rooms re-read; selection cleared; "Full" shown on that room | Alert; focus on banner | Pick another room |
| Submitting | Confirm activated | Button reads "Allocating…" | `aria-disabled`, status "Allocating" | Wait |
| Timeout or no response | No answer in time | "Error: We could not confirm whether the allocation was saved. Do not retry yet." and Check status | Alert | Check status, then decide |
| Stale | Room data older than 5 minutes | "Updated 10:42, may be out of date" and Refresh rooms | Text | Refresh rooms |
| Unavailable | Service down | "Error: The accommodation service is unavailable." | Alert | Try again later |
| Unauthorised | AUTH_001 or AUTH_002 | Auth pages (section 5) | See A5 | See A5 |

## 4. Screen A3: Allocation confirmed

| State | Trigger | What the user sees | Assistive technology | Recovery |
|---|---|---|---|---|
| Normal | Success | "Allocation confirmed", applicant, room and updated occupancy; actions Allocate next applicant and View room | Heading focused; `role="status"` | n/a |
| Occupancy read fails | Follow-up read fails | Success message without the occupancy line: "Occupancy will refresh on the Rooms page." | Status | Open Rooms |

## 5. Auth pages (A5) and session states

| State | Trigger | What the user sees | Recovery |
|---|---|---|---|
| AUTH_001 | Not signed in or session expired | "Your session has expired. Sign in again to continue." After sign-in the user returns to the same screen. | Sign in again |
| AUTH_002 | Signed in, role not permitted | "You do not have permission to allocate rooms." No allocation controls rendered. | Go to my accommodation |

## 6. Screen S1: My accommodation (student)

| State | Trigger | What the user sees | Assistive technology | Recovery |
|---|---|---|---|---|
| Allocated | Allocation exists | "✓ Allocated", room, hall, date, application reference | Status text, not colour | n/a |
| Not yet allocated | Approved, no room yet | "Your application is approved. No room has been assigned yet. Check back here." | Plain text; not styled as an error | Check later |
| Loading | Page load | "Loading your accommodation…" | `aria-busy` | n/a |
| Error or unavailable | Request fails | "We can't load your details right now." and Try again | Alert | Try again |
| Unauthorised | AUTH_001 | Sign-in page; return afterwards | See A5 | Sign in |

## 7. Message catalogue

Placeholders in braces are filled from the response. Codes other than those below use the generic message (assumption A6).

| Code | Source | Message | Shown on |
|---|---|---|---|
| `RULE_001` | BR-01, `NoDuplicateActiveAllocation` | "Error: {applicant} already has an active allocation ({room}). A student can have only one." Link: View current allocation. | A2 |
| `RULE_002` | BR-02, `RoomCapacityRule` | "Error: Room {room} is now full. Another allocation took its last space while you were choosing. Nothing was allocated for {applicant}. Choose another room." | A2 |
| `AUTH_001` | Role check | "Your session has expired. Sign in again to continue." | A5 |
| `AUTH_002` | Role check | "You do not have permission to allocate rooms." | A5 |
| Validation (code per Lab 8 contract) | `AllocationService.validate()` | "Error: That room or application is no longer available. Rooms have been refreshed." | A2 |
| Timeout | Client | "Error: We could not confirm whether the allocation was saved. Do not retry yet. Check status first." | A2 |
| Generic | Any other failure | "Error: Something went wrong and nothing was changed. Try again, or contact the accommodation office. Reference: {id}." | Any |

The Generic message says "nothing was changed" only where the transaction guarantee applies (QS-03). For timeouts the Timeout message is used instead, because the outcome is unknown.
