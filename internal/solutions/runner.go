package solutions

import (
	"advent-of-code/internal/utils"
	"fmt"
	"time"
)

type Solution interface {
	Parse(fileName string) error
	SolvePart1() string
	SolvePart2() string
}

type RunResult struct {
	Part1     string
	Part2     string
	ParseTime time.Duration
	Part1Time time.Duration
	Part2Time time.Duration
}

func Run(solution Solution, fileName string) *RunResult {
	result := &RunResult{}

	// Parsing
	parseStart := time.Now()
	err := solution.Parse(fileName)
	if err != nil {
		utils.HandleErrorFatally(fmt.Errorf("parse error: %w", err))
	}
	result.ParseTime = time.Since(parseStart)

	// Part 1
	part1Start := time.Now()
	part1Solution := solution.SolvePart1()
	result.Part1 = part1Solution
	result.Part1Time = time.Since(part1Start)

	// Part 2
	part2Start := time.Now()
	part2Solution := solution.SolvePart2()
	result.Part2 = part2Solution
	result.Part2Time = time.Since(part2Start)

	return result
}
