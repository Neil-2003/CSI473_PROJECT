# Traceability Matrix

CSI473 · Laboratory 4 · Semester 1, 2026/27
Project: UB Student Accommodation System

Maps requirement → use case → analysis element → verification strategy.

| **Requirement ID** | **Requirement Description** | **Related Use Case(s)** | **Key Analysis Elements** | **Verification Strategy** |
| :--- | :--- | :--- | :--- | :--- |
| **FR-01** | Allow registered UB student to log in. | UC-08 (Log In), UC-01 | `User`, `Student` | **Test:** Login with valid/invalid credentials. **Evidence:** Successful login; error message shown for invalid credentials. |
| **FR-02** | Allow logged-in student to submit an accommodation application. | UC-01 (Submit Application) | `AccommodationApplication`, `Student`, BR-05 | **Test:** Fill and submit application form. **Evidence:** Application is created with `PENDING` status. |
| **FR-03** | Validate application and record it with Pending status. | UC-01 | `AccommodationApplication`, BR-03 | **Test:** Submit a complete vs. an incomplete application. **Evidence:** Incomplete application is rejected; complete one is saved as `PENDING`. |
| **FR-04** | Allow a student to view application status. | UC-02 (View Application Status), UC-01 | `AccommodationApplication`, `Student` | **Test:** Student views their applications. **Evidence:** Status (PENDING, APPROVED, REJECTED, etc.) is displayed correctly. |
| **FR-05** (Revised) | Allow admin to view a paginated, filterable, sortable list of applications. | UC-03 (Review Application) | `AccommodationApplication`, `Administrator` | **Test:** Admin navigates to list, filters, sorts, paginates. **Evidence:** List displays correctly; empty list shows a message. |
| **FR-06** | Allow admin to review a Pending application against the rules. | UC-03, UC-01 | `AccommodationApplication`, `Administrator`, BR-04, BR-05 | **Test:** Admin reviews an application. **Evidence:** Admin can view details and record a decision. |
| **FR-07** | Allow admin to allocate an eligible student to an available room. | UC-04 (Allocate Room), UC-01 | `Administrator`, `Allocation`, `Room`, `Student`, BR-01, BR-02 | **Test:** Admin allocates a room to an eligible student. **Evidence:** Allocation is created; application status becomes `APPROVED`; room occupancy updates. |
| **FR-08** | Reject an allocation attempt and notify the admin when a room is full. | UC-01 (A2) | `Administrator`, `Room`, `Allocation`, BR-02 | **Test:** Admin tries to allocate a student to a full room. **Evidence:** Allocation is rejected; admin sees a "room full" message. |
| **FR-09** | Prevent a student from having more than one active allocation. | UC-01 (A3) | `Student`, `Allocation`, `Administrator`, BR-01 | **Test:** Admin tries to allocate a student who already has an active allocation. **Evidence:** Second allocation is rejected; admin is notified. |
| **FR-10** | Update a room's occupancy record whenever a student is allocated or removed. | UC-01 | `Allocation`, `Room`, `OccupancyRecord`, BR-06 | **Test:** Allocate/end an allocation and then view room occupancy. **Evidence:** Occupancy count matches the number of active allocations. |
| **FR-11** | Allow admin to record an application as Rejected with a reason. | UC-05 (Reject Application), UC-01 (A4) | `Administrator`, `AccommodationApplication`, BR-03 | **Test:** Admin rejects an application. **Evidence:** Status changes to `REJECTED` with a provided reason stored. |
| **FR-12** | Allow admin to add, update, or deactivate residence and room records. | UC-06 (Manage Rooms) | `Administrator`, `Room`, `Residence`, BR-04 | **Test:** Admin creates, edits, and deactivates a room. **Evidence:** Changes are saved and reflected in the system. |
| **BR-03** | Application status must follow the defined state machine. | UC-01, UC-05 | `AccommodationApplication` | **Test:** Attempt an invalid status transition (e.g. DRAFT → APPROVED). **Evidence:** Transition is blocked. |
