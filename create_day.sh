#!/usr/bin/env bash

YEAR="$1"
DAY="$2"

if [[ -z "$YEAR" || -z "$DAY" ]]; then
    echo "Usage: $0 <YEAR> <DAY>"
    exit 1
fi

if (( YEAR < 2015 || YEAR > 2025 )); then
    echo "ERROR: YEAR is set to an invalid value: $YEAR"
    exit 1
fi

if (( YEAR == 2025 )); then
    if (( DAY < 1 || DAY > 12 )); then
        echo "ERROR: DAY is set to an invalid value: $DAY"
        exit 1
    fi
else
    if (( DAY < 1 || DAY > 24 )); then
        echo "ERROR: DAY is set to an invalid value: $DAY"
        exit 1
    fi
fi

DAY_PADDED=$(printf "%02d" "$DAY")

TARGET_RESOURCES_DIR="resources/$YEAR/$DAY_PADDED"
EXAMPLE_INPUT_FILE="$TARGET_RESOURCES_DIR/example-input.txt"
INPUT_FILE="$TARGET_RESOURCES_DIR/input.txt"
TASK_FILE="$TARGET_RESOURCES_DIR/task.md"

mkdir -p "$TARGET_RESOURCES_DIR"
touch $EXAMPLE_INPUT_FILE $INPUT_FILE $TASK_FILE

TARGET_DIR="internal/solutions/$YEAR"
SOLUTION_FILE="$TARGET_DIR/day${DAY_PADDED}.go"
PARSER_FILE="$TARGET_DIR/day${DAY_PADDED}_parser.go"

mkdir -p "$TARGET_DIR"

cat > "$SOLUTION_FILE" <<EOF
package _$YEAR

import (
	"advent-of-code/internal/solutions"
)

func init() {
	solutions.RegisterDay($YEAR, $DAY, &Day$DAY{})
}

func (day *Day$DAY) SolvePart1() string {
	return ""
}

func (day *Day$DAY) SolvePart2() string {
	return ""
}
EOF

cat > "$PARSER_FILE" <<EOF
package _$YEAR

type Day$DAY struct {
}

func (day *Day$DAY) ParseLine(line string, lineNumber int) error {
	return nil
}
EOF
