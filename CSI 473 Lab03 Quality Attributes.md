# 5\. Quality-Attribute Scenarios

Each scenario states a stimulus, context, expected response and a measurable response level.

## QS-01 — Performance (submission load)

| **Field**        | **Detail**                                                                                                     |
| ---------------- | -------------------------------------------------------------------------------------------------------------- |
| Stimulus         | 200 students submit accommodation applications concurrently during the opening hour of the application period. |
| Context          | Normal operation and peak submission period.                                                                   |
| Response         | The system accepts and confirms each valid submission.                                                         |
| Response measure | At least 95% of submissions are validated and acknowledged within 3 seconds.                                   |

## QS-02 — Security (unauthorised access)

| **Field**        | **Detail**                                                                                 |
| ---------------- | ------------------------------------------------------------------------------------------ |
| Stimulus         | An unauthenticated user attempts to access the room allocation function.                   |
| Context          | Runtime, production environment.                                                           |
| Response         | The system denies access and logs the attempt.                                             |
| Response measure | 100% of unauthorised attempts are denied with an audit log entry recorded within 1 second. |

## QS-03 — Reliability / data integrity (concurrent allocation)

| **Field**        | **Detail**                                                                                                                    |
| ---------------- | ----------------------------------------------------------------------------------------------------------------------------- |
| Stimulus         | Two administrators simultaneously attempt to allocate the last available space in the same room to two different students.    |
| Context          | Concurrent access at runtime.                                                                                                 |
| Response         | The system allows only one allocation to succeed and rejects the other with a "room full" message.                            |
| Response measure | Over-allocation is prevented in 100% of concurrent allocation attempts and the rejected request is returned within 2 seconds. |

## QS-04 — Usability (first-time student use)

| **Field**        | **Detail**                                                                                                |
| ---------------- | --------------------------------------------------------------------------------------------------------- |
| Stimulus         | A first-time student user attempts to complete and submit an accommodation application.                   |
| Context          | Normal use, no prior training or user guide.                                                              |
| Response         | The student completes and submits the application unaided.                                                |
| Response measure | At least 90% of first-time users successfully submit the application without assistance within 5 minutes. |

## QS-05 — Availability (service disruption)

| **Field**        | **Detail**                                                                                                                           |
| ---------------- | ------------------------------------------------------------------------------------------------------------------------------------ |
| Stimulus         | The accommodation system experiences a server fault during the application period.                                                   |
| Context          | Peak submission period, production environment.                                                                                      |
| Response         | The system detects the fault and resumes service.                                                                                    |
| Response measure | The system maintains at least 99% availability during the application period, with recovery from any single fault within 10 minutes. |