**Functional Requirements,**

# 1\. Functional Requirements

Each requirement states what the system must do and is independently verifiable.

| **ID** | **Functional Requirement**                                                                                                                           |
| ------ | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| FR-01  | The system shall allow a registered UB student to log in using their student account credentials.                                                    |
| FR-02  | The system shall allow a logged-in UB student to submit an accommodation application, capturing the student's details and accommodation preferences. |
| FR-03  | The system shall validate a submitted accommodation application and record it with a Pending status.                                                 |
| FR-04  | The system shall allow a student to view the current status of their application.                                                                    |
| FR-05  | The system shall allow an authorised accommodation administrator to view the list of submitted accommodation applications and their statuses.        |
| FR-06  | The system shall allow an authorised accommodation administrator to review a Pending application against room availability and eligibility rules.    |
| FR-07  | The system shall allow an authorised accommodation administrator to allocate an eligible student to an available room.                               |
| FR-08  | The system shall reject an allocation attempt and notify the administrator when the selected room has no remaining capacity.                         |
| FR-09  | The system shall prevent a student from holding more than one active accommodation allocation at the same time.                                      |
| FR-10  | The system shall update a room's occupancy record whenever a student is allocated to, or removed from, that room.                                    |
| FR-11  | The system shall allow an authorised accommodation administrator to record an application as Rejected/Unsuccessful and also state the reason.        |
| FR-12  | The system shall allow an authorised accommodation administrator to add, update, or deactivate residence and room records including room capacity.   |
| FR-13  | The system shall allow an authorised system administrator to manage user accounts and assign role-based permissions .                                |