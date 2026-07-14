# 🎄 Advent of Code

My solutions to [Advent of Code](https://adventofcode.com/) puzzles, written in **Go**.

## Progress

| Year | Days Solved |
|------|-------------|
| 2025 | 1–6         |

## Project Structure

```
.
├── cmd/advent-of-code/       # CLI entrypoint
├── internal/
│   ├── flags/                # Command-line argument parsing
│   ├── solutions/            # Solution runner, registry & per-year solutions
│   │   └── 2025/             # 2025 solutions (dayXX.go + dayXX_parser.go)
│   └── utils/                # Shared helpers (math, slices, matrix, etc.)
├── resources/<year>/<day>/   # Puzzle inputs & task descriptions
└── create_day.sh             # Scaffolding script for new days
```

Each day is split into two files:

- **`dayXX_parser.go`** – defines the day's data struct and input parsing logic.
- **`dayXX.go`** – registers the day in the solution registry and implements `SolvePart1()` / `SolvePart2()`.

Solutions self-register via Go `init()` functions and a central registry, so adding a new day requires no changes to
existing code.

## Getting Started

### Prerequisites

- [Go](https://go.dev/) 1.25+

### Build

```bash
go build -o bin/advent-of-code ./cmd/advent-of-code
```

### Run a Solution

```bash
./bin/advent-of-code --year <YEAR> --day <DAY>
```

Use the `--example` flag to run against the example input instead of the real puzzle input:

```bash
./bin/advent-of-code -year <YEAR> -day <DAY> --example
```

#### Example Output

```
=== Result ===
Part 1: 3
Part 2: 6

=== Timings ===
Parse:  35.377µs
Part 1: 251ns
Part 2: 381ns
```

## Scaffolding a New Day

A helper script generates boilerplate files for a new day:

```bash
./create_day.sh <YEAR> <DAY>
```

This creates:

- `internal/solutions/<YEAR>/day<DD>.go` – solution stub
- `internal/solutions/<YEAR>/day<DD>_parser.go` – parser stub
- `resources/<YEAR>/<DD>/example-input.txt`, `input.txt`, `task.md` – empty resource files

## License

This project is licensed under the [MIT License](LICENSE).

> **Note:** Puzzle texts and inputs are the property of [Advent of Code](https://adventofcode.com/) and are not included
> in this license.
