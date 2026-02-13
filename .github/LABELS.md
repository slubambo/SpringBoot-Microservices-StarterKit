# Label Guide

This guide defines how maintainers should label issues and PRs for consistent triage.

## Core triage labels

| Label | Purpose | When to use |
|---|---|---|
| `needs-triage` | New and unreviewed issue | Apply by default on issue creation |
| `in-progress` | Actively being worked on | Apply when work starts |
| `blocked` | Cannot proceed | Waiting on dependency/decision |
| `duplicate` | Already tracked elsewhere | Link the source issue and close |
| `invalid` | Not actionable / out of scope | Missing reproducible details or not relevant |
| `wontfix` | Accepted but intentionally not implemented | Add rationale before closing |

## Type labels

| Label | Purpose |
|---|---|
| `bug` | Defect or regression |
| `enhancement` | New or improved capability |
| `documentation` | README/docs/templates updates |
| `ci` | CI/CD workflows or pipeline behavior |
| `security` | Security-related changes |
| `refactor` | Internal code restructuring without behavior change |

## Priority labels

| Label | Meaning |
|---|---|
| `priority: P0` | Production blocker / critical breakage |
| `priority: P1` | High impact, should be done soon |
| `priority: P2` | Normal backlog priority |
| `priority: P3` | Nice-to-have |

## Effort labels

| Label | Typical effort |
|---|---|
| `size: XS` | < 1 hour |
| `size: S` | ~1-4 hours |
| `size: M` | ~1-2 days |
| `size: L` | Multi-day / multi-PR |

## Community labels

| Label | Purpose |
|---|---|
| `good first issue` | Beginner-friendly, well-scoped, low-risk task |
| `help wanted` | Seeking external contribution |

## `good first issue` criteria

Apply `good first issue` only when all are true:
- clear problem statement and acceptance criteria exist
- minimal architecture/security risk
- can be completed without deep project context
- estimated size is `XS` or `S`

## Suggested triage flow

1. Confirm issue type (`bug`, `enhancement`, `documentation`, etc.).
2. Keep `needs-triage` until scope and priority are set.
3. Add one priority label and one size label.
4. Add `good first issue` and/or `help wanted` when appropriate.
5. Remove `needs-triage` once triage is complete.

## Default labels used by issue templates

- Bug report template: `bug`, `needs-triage`
- Feature request template: `enhancement`, `needs-triage`
