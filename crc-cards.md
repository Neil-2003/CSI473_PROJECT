# CRC Cards / Responsibility Records

CSI473 · Laboratory 4 · Semester 1, 2026/27
Project: UB Student Accommodation System

## CRC-01: AccommodationApplication

| **Responsibility** | **Collaboration** |
| :--- | :--- |
| **Create** a new application instance from submitted data. | Student |
| **Store** applicant preferences and personal details. | |
| **Track** and **manage** its own status (DRAFT, SUBMITTED, PENDING, APPROVED, REJECTED). | |
| **Validate** that all required fields are complete before submission. | |
| **Record** a rejection reason when status is REJECTED. | Administrator |
| **Provide** its status and details for viewing by the Student. | Student |
| **Request** an allocation to a room. | Allocation, Room |
| **Notify** its status when it changes. | Notification (Future) |

## CRC-02: Room

| **Responsibility** | **Collaboration** |
| :--- | :--- |
| **Store** its own details (room number, capacity, residence). | Residence |
| **Calculate** its current occupancy by querying its active allocations. | Allocation |
| **Determine** if it has available capacity for a new student. | |
| **Refuse** an allocation request if it is at full capacity. | Allocation |
| **Update** its capacity when changed by an administrator. | Administrator |

## CRC-03: Allocation

| **Responsibility** | **Collaboration** |
| :--- | :--- |
| **Create** a new allocation for a student and a room. | Student, Room |
| **Ensure** a student does not already have an active allocation (BR-01). | Student |
| **Ensure** the target room has available capacity (BR-02). | Room |
| **Record** the date of the allocation. | |
| **End** an allocation, which updates occupancy records. | OccupancyRecord |
| **Notify** when a new allocation is created. | Notification (Future) |

## CRC-04: Administrator

| **Responsibility** | **Collaboration** |
| :--- | :--- |
| **Review** a list of pending applications. | AccommodationApplication |
| **Make** an allocation decision. | Allocation, AccommodationApplication, Room |
| **Reject** an application and provide a reason. | AccommodationApplication |
| **Manage** residence and room records (add, update, deactivate). | Room, Residence |
| **View** status of all applications. | AccommodationApplication |
| **Ensure** only authorised actions are performed (via role checks). | User |

## CRC-05: OccupancyRecord

| **Responsibility** | **Collaboration** |
| :--- | :--- |
| **Store** the history of who occupied a given room and for how long. | Room, Student |
| **Open** a new occupancy record when an allocation starts. | Allocation |
| **Close** an occupancy record (set endDate) when an allocation ends. | Allocation |
| **Supply** occupancy history for reporting/auditing. | Administrator |
