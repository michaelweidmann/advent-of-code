package _2025

import (
	"advent-of-code/internal/utils"
	"bufio"
	"fmt"
	"strings"
)

type Operation int

const (
	ADDITION Operation = iota
	MULTIPLICATION
)

type Day6 struct {
	// Part 1
	numbers    [][]int64
	operations []Operation
	// Part 2
	lines [][]rune
}

func (day *Day6) ParseLine(scanner *bufio.Scanner) error {
	day.numbers = make([][]int64, 0)
	day.lines = make([][]rune, 0)

	for scanner.Scan() {
		line := scanner.Text()

		if strings.ContainsAny(line, "0123456789") {
			err := day.parseNumbers(line)
			if err != nil {
				return err
			}
		} else if strings.ContainsAny(line, "+*") {
			err := day.parseOperations(line)
			if err != nil {
				return err
			}
		}
	}

	return nil
}

func (day *Day6) parseNumbers(line string) error {
	runes := []rune(line)
	day.lines = append(day.lines, runes)

	parts := strings.Fields(line)
	for i := 0; i < len(parts); i++ {
		if len(day.numbers)-1 < i {
			day.numbers = append(day.numbers, make([]int64, 0))
		}

		numberAsString := parts[i]

		number, err := utils.ParseInt64(numberAsString)
		if err != nil {
			return err
		}

		day.numbers[i] = append(day.numbers[i], number)
	}

	return nil
}

func (day *Day6) parseOperations(line string) error {
	parts := strings.Fields(line)

	for i := 0; i < len(parts); i++ {
		operation := parts[i]

		if operation == "+" {
			day.operations = append(day.operations, ADDITION)
		} else if operation == "*" {
			day.operations = append(day.operations, MULTIPLICATION)
		} else {
			return fmt.Errorf("could not parse %s to a valid value", operation)
		}
	}

	return nil
}
