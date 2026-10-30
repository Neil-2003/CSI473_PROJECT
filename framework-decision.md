# Framework Decision Note

CSI473 · Laboratory 9 · Semester 1, 2026/27 · Project: UB Student Accommodation System · Team 14

| Field | Details |
|---|---|
| **Decision ID** | FD-001 |
| **Date** | 9 October 2026 |
| **Issue** | Which application framework and dependency-management approach should the team use to implement the vertical slice, given the modular monolith (ADR-001), the ports-and-adapters design, and the 12-week constraint? |
| **Selected** | Spring Boot (Java) with Maven for dependency management. |
| **Traceability** | ADR-001, ADR-002, DP-01, DP-04, QS-03 |
| **Alternatives considered** | Plain Java with manual dependency injection; Python with Flask; PHP with Laravel. |

## Rationale

1. **Dependency injection is built in.** Spring wires `AllocationService` with its ports and adapters, and lets `SqlAllocationRepository` be swapped for `InMemoryAllocationRepository` in tests (DP-01, DP-04).
2. **Declarative transactions.** `@Transactional` on `AllocationService.allocate()` gives the atomic boundary required by Lab 8's failure-recovery design (QS-03, BR-01, BR-02). The isolation approach is stated in ADR-002.
3. **Mature HTTP adapter support.** Spring MVC lets `AllocationController` stay a thin adapter over `AllocationUseCase`.
4. **Testing support.** Spring Boot Test and JUnit support in-memory unit tests and database integration tests.
5. **Team capability.** The team has prior Java/Spring coursework experience, which reduces the learning curve compared with Flask or Laravel.
6. **Maven.** A standard, reproducible build with explicit dependency versions avoids hidden-dependency problems.

## Consequences Accepted

- Spring annotations couple the wiring and adapter classes to the framework; the ports stay framework-free to limit this.
- Team members new to Spring need ramp-up time; the fallback to manual wiring is recorded in ADR-002.
- A larger runtime footprint than plain Java, acceptable for a single-deployable modular monolith (ADR-001).

## Dependency-Management Approach

- All third-party libraries are declared in `pom.xml` with pinned versions; no manually copied JARs.
- Framework annotations (`@Service`, `@Transactional`, `@Repository`) are confined to wiring and adapter classes where possible. The interfaces in `src/interfaces-or-ports/` carry no framework imports, so the ports stay stable if the framework changes.
- The skeletons in `src/` currently compile and run on plain Java 21 with no dependencies, which demonstrates that the core does not need Spring to be tested.

## Rejected Alternative: Plain Java with Manual DI

| Aspect | Assessment |
|---|---|
| **Pros** | No framework overhead; full control; smaller runtime footprint. |
| **Cons** | Manual wiring becomes unwieldy as ports and adapters grow. Hand-written transaction management is error-prone for the atomicity requirement (QS-03). HTTP handling needs a separate library with more boilerplate. |
| **Verdict** | Rejected: it would cost more time and add more transaction-handling risk than the framework overhead it avoids. |
| **Revisit if** | The team cannot get Spring DI working in the first Phase 2 sprint; fall back to manual wiring while keeping the port interfaces (see ADR-002 triggers). |
