package _2025

import (
	"bufio"
	"fmt"
)

type Day4 struct {
	// True means there is a roll of paper located on that position.
	// False means that position is empty.
	grid [][]bool
}

func (day *Day4) ParseLine(scanner *bufio.Scanner) error {
	lineNumber := 1

	for scanner.Scan() {
		line := scanner.Text()

		if day.grid == nil {
			day.grid = make([][]bool, 0)
		}

		day.grid = append(day.grid, make([]bool, len(line)))
		row := day.grid[lineNumber-1]

		for column, fieldContent := range line {
			if fieldContent == '@' {
				row[column] = true
			} else if fieldContent == '.' {
				row[column] = false
			} else {
				return fmt.Errorf("invalid element on field %c", fieldContent)
			}
		}

		lineNumber++
	}

	return nil
}
