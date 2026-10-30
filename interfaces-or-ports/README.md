# src/interfaces-or-ports/

The ports of the allocation core (Lab 09, ADR-002). Plain Java 21, no dependencies, no framework imports.

## Map

| Interface | Kind | Called / implemented by | Solves |
|---|---|---|---|
| `AllocationUseCase` | Driving port | called by `AllocationController` and tests; implemented by `AllocationService` | DP-03, DP-04 |
| `AllocationRule` | Strategy port | implemented by `NoDuplicateActiveAllocation` (BR-01), `RoomCapacityRule` (BR-02) | DP-02 |
| `AllocationRepository` | Driven port | implemented by `Sql*` / `InMemory*` | DP-01 |
| `RoomRepository` | Driven port | implemented by `Sql*` / `InMemory*` | DP-01 |
| `ApplicationRepository` | Driven port | implemented by `Sql*` / `InMemory*` | DP-01 |
| `OccupancyRepository` | Driven port | implemented by `Sql*` / `InMemory*` | DP-01, DP-05 |

## Dependency rule

`Controller -> AllocationUseCase <- AllocationService -> AllocationRule / Repository ports <- Sql* / InMemory*`

The core owns the ports. Ports and domain import only `java.*` and `ub.accommodation.{domain,ports}`.

## Conventions every implementation must follow

- Never return `null`: use `Optional` or empty lists.
- Join the caller's transaction; repositories never commit.
- Rules report failures through `RuleResult.violation(ErrorCodes.RULE_00x, message)`.
- Rules are evaluated in registration order: BR-01, then BR-02; first failure wins.
- `countActiveByRoom(r)` must equal `findActiveByRoom(r).size()`.

## Build and check

```
cd src
javac --release 21 -d out @sources.txt
bash tools/check-ports.sh        # Windows: powershell -File tools/check-ports.ps1
```

## Domain stubs

`domain/` holds the small value types the ports use (`AllocationRequest`, `Allocation`, `Room`, ...), `ErrorCodes`, and the two exceptions. Check the `ErrorCodes` wording against your Lab 8 API contract.

## Build and run

Build settings, VS Code tasks and run instructions are in `src/README.md` (open the `src` folder, not this one).
