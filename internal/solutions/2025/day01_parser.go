package _2025

import (
	"fmt"
	"strconv"
)

type Direction int

const (
	LEFT Direction = iota
	RIGHT
)

type InstructionSet struct {
	Direction Direction
	Amount    int
}

type Day01 struct {
	InstructionSet []InstructionSet
}

func (day *Day01) ParseLine(line string) error {
	direction, err := parseDirection(line[:1])
	if err != nil {
		return err
	}

	amount, err := strconv.Atoi(line[1:])
	if err != nil {
		return err
	}

	appendInstructionSet(day, direction, amount)
	return nil
}

func parseDirection(direction string) (Direction, error) {
	if direction == "L" {
		return LEFT, nil
	} else if direction == "R" {
		return RIGHT, nil
	}

	return -1, fmt.Errorf("could not parse %s to a valid value", direction)
}

func appendInstructionSet(day *Day01, direction Direction, amount int) {
	day.InstructionSet = append(day.InstructionSet, InstructionSet{
		Direction: direction,
		Amount:    amount,
	})
}
