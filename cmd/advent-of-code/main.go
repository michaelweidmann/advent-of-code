package main

import (
	"advent-of-code/internal/flags"
	"advent-of-code/internal/solutions"
	_ "advent-of-code/internal/solutions/2025"
	"advent-of-code/internal/utils"
	"fmt"
)

func main() {
	year, day, example := flags.ParseCommandLineArguments()
	flags.ValidateCommandLineArguments(year, day)

	fileName := utils.GetInputFileName(year, day, example)

	constructor := solutions.Registry[year][day]
	sol := constructor()

	result := solutions.Run(sol, fileName)

	fmt.Println("=== Result ===")
	fmt.Println("Part 1:", result.Part1)
	fmt.Println("Part 2:", result.Part2)

	fmt.Println()
	fmt.Println("=== Timings ===")
	fmt.Println("Parse: ", result.ParseTime)
	fmt.Println("Part 1:", result.Part1Time)
	fmt.Println("Part 2:", result.Part2Time)
}
