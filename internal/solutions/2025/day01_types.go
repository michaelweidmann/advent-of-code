package _2025

import (
	"advent-of-code/internal/utils"
	"bufio"
	"fmt"
	"os"
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

func (day *Day01) Parse(fileName string) error {
	file, err := os.Open(fileName)
	if err != nil {
		return err
	}
	defer utils.CloseFile(file)

	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		line := scanner.Text()

		direction, err := parseDirection(line[:1])
		if err != nil {
			return err
		}

		amount, err := strconv.Atoi(line[1:])
		if err != nil {
			return err
		}

		appendInstructionSet(day, direction, amount)
	}

	if err := scanner.Err(); err != nil {
		return err
	}

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
