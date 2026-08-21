# 3.UC-01: Use-case_UC

**Primary actor: UB Student**

**Secondary actor: Accommodation Administrator (performs review/allocation steps within the same use case)**

**System boundary: UB Student Accommodation System**

**Level: User goal**

## Preconditions

- The student is a registered UB student with a valid account.
- The student does not already hold an active accommodation allocation .

## Postconditions (success guarantee)

- The application exists in the system with a final status of Approved/Allocated or Rejected.
- If allocated, the assigned room's occupancy record reflects the new occupant .
- The student can view the final status of the application .

## Main Flow

1\. The student logs into the system using their registered student account.

2\. The student opens the accommodation application form and enters the required information and preferences.

3\. The student submits the application.

4\. The system validates the application and records it with a Pending status .

5\. An authorised accommodation administrator reviews the Pending application against eligibility rules and available room capacity .

6\. The administrator allocates the student to a specific available room.

7\. The system updates the application status to Approved/Allocated and updates the room's occupancy record).

8\. The student views the updated application status .

## Alternative Flows

**A1 — Incomplete application (at step 3):**

- 3a. The system detects missing required information.
- 3b. The system rejects the submission and prompts the student to complete the missing fields.
- 3c. The student corrects the information and resumes at step 3.

**A2 — Room at full capacity (at step 6):**

- 6a. The administrator attempts to allocate the student to a room with no remaining capacity.
- 6b. The system rejects the allocation and informs the administrator that the room is full.
- 6c. The administrator selects a different available room and resumes at step 6, or defers the application.

**A3 — Duplicate active allocation (at step 6):**

- 6a. The system detects that the student already holds an active accommodation allocation.
- 6b. The system blocks the allocation and informs the administrator .
- 6c. Use case ends without a new allocation.

**A4 — Application not approved (at step 5 or 6):**

- 5a. The administrator determines the student is not eligible, or no suitable room is available.
- 5b. The administrator records the application as Rejected/Unsuccessful with a reason).
- 5c. The student views the Rejected status and reason. Use case ends.

## Exceptions

- E1: If the system becomes unavailable after step 3 but before step 4, the submission is not confirmed and the student is informed to retry; no Pending record is created.