package _2025

import "fmt"

type Day4 struct {
	// True means there is a roll of paper located on that position.
	// False means that position is empty.
	grid [][]bool
}

func (day *Day4) ParseLine(line string, lineNumber int) error {
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

	return nil
}
