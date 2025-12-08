package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"strings"
)

func init() {
	solutions.RegisterDay(2025, 6, &Day6{})
}

func (day *Day6) SolvePart1() string {
	sumOfAllCalculations := int64(0)

	for idx, numbers := range day.numbers {
		operation := day.operations[idx]
		currentValue := getStartValue(operation)

		for _, number := range numbers {
			if operation == ADDITION {
				currentValue += number
			} else if operation == MULTIPLICATION {
				currentValue *= number
			}
		}

		sumOfAllCalculations += currentValue
	}

	return utils.Int64ToString(sumOfAllCalculations)
}

func (day *Day6) SolvePart2() string {
	sumOfAllCalculations := int64(0)

	transposedInput := utils.TransposeMatrix(day.lines)

	operationIndex := len(day.operations) - 1
	operation := day.operations[operationIndex]
	currentValue := getStartValue(operation)

	for i := len(transposedInput) - 1; i >= 0; i-- {
		numberAsRunes := transposedInput[i]
		numberAsString := strings.ReplaceAll(string(numberAsRunes), " ", "")

		if strings.TrimSpace(numberAsString) == "" {
			sumOfAllCalculations += currentValue

			operationIndex--
			operation = day.operations[operationIndex]
			currentValue = getStartValue(operation)
			continue
		}

		number, _ := utils.ParseInt64(numberAsString)

		if operation == ADDITION {
			currentValue += number
		} else if operation == MULTIPLICATION {
			currentValue *= number
		}
	}

	sumOfAllCalculations += currentValue

	return utils.Int64ToString(sumOfAllCalculations)
}

func getStartValue(operation Operation) int64 {
	if operation == ADDITION {
		return 0
	} else if operation == MULTIPLICATION {
		return 1
	}

	return -1
}
