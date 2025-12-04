package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"strconv"
)

func init() {
	solutions.RegisterDay(2025, 4, &Day4{})
}

func (day *Day4) SolvePart1() string {
	grid := day.grid
	maxRowNumber := len(grid) - 1
	maxColumnNumber := len(grid[0]) - 1
	accessiblePaperRolls := 0

	for rowNumber, row := range grid {
		for columnNumber, isPaperRoll := range row {
			if !isPaperRoll {
				continue
			}

			if isFieldAccessable(grid, rowNumber, columnNumber, maxRowNumber, maxColumnNumber) {
				accessiblePaperRolls++
			}
		}
	}
	return strconv.Itoa(accessiblePaperRolls)
}

func (day *Day4) SolvePart2() string {
	grid := copyGrid(day.grid)
	maxRowNumber := len(grid) - 1
	maxColumnNumber := len(grid[0]) - 1
	removedPaperRolls := 0
	paperRollsRemoved := true

	for paperRollsRemoved {
		copiedGrid := copyGrid(grid)
		paperRollsRemoved = false

		for rowNumber, row := range grid {
			for columnNumber, isPaperRoll := range row {
				if !isPaperRoll {
					continue
				}

				if isFieldAccessable(grid, rowNumber, columnNumber, maxRowNumber, maxColumnNumber) {
					copiedGrid[rowNumber][columnNumber] = false
					removedPaperRolls++
					paperRollsRemoved = true
				}
			}
		}

		grid = copiedGrid
	}

	return strconv.Itoa(removedPaperRolls)
}

func isFieldAccessable(grid [][]bool, rowNumber, columnNumber, maxRowNumber, maxColumnNumber int) bool {
	startRow := utils.Max(rowNumber-1, 0)
	finishRow := utils.Min(rowNumber+1, maxRowNumber)
	startColumn := utils.Max(columnNumber-1, 0)
	finishColumn := utils.Min(columnNumber+1, maxColumnNumber)

	adjacentPaperRolls := 0

	for currentRow := startRow; currentRow <= finishRow; currentRow++ {
		for currentColumn := startColumn; currentColumn <= finishColumn; currentColumn++ {
			if currentRow == rowNumber && currentColumn == columnNumber {
				continue
			}

			if grid[currentRow][currentColumn] {
				adjacentPaperRolls++
			}
		}
	}

	if adjacentPaperRolls < 4 {
		return true
	}

	return false
}

func copyGrid(grid [][]bool) [][]bool {
	gridCopy := make([][]bool, len(grid))
	copy(gridCopy, grid)
	return gridCopy
}
