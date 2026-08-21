# 4\. Acceptance Criteria — UC-01

**AC-01 — Successful submission**

Given a registered UB student with no active accommodation allocation . When the student submits a complete accommodation application, then the system records the application with a Pending status and the student can see it in their application list.

**AC-02 — Successful allocation**

Given a Pending application and a room with at least one available space. When an authorised accommodation administrator allocates the student to that room then the application status changes to Approved/Allocated and the room's occupied space count increases by one.

**AC-03 — Room at capacity**

Given a room with zero remaining capacity. When an accommodation administrator attempts to allocate a student to that room, the system rejects the allocation and the room's occupancy is unchanged and the administrator sees a message stating the room is full.

**AC-04 — Duplicate active allocation prevented**

Given a student who already holds an active accommodation allocation, When an accommodation administrator attempts to allocate that student to another room then the system rejects the second allocation and the student's original allocation remains unchanged.

**AC-05 — Rejected application**

Given a Pending application that an administrator determines cannot be approved, When the administrator records the application as Rejected with a reason then the student can view the Rejected status and the stated reason and no room occupancy is changed.