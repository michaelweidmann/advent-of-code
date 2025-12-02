package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"strconv"
)

func init() {
	solutions.RegisterDay(2025, 1, &Day01{})
}

func (day *Day01) SolvePart1() string {
	dialValue := 50
	counter := 0

	for _, instruction := range day.InstructionSet {
		direction := instruction.Direction
		amount := instruction.Amount

		if direction == LEFT {
			dialValue -= amount
		} else if direction == RIGHT {
			dialValue += amount
		}

		dialValue = utils.Modulo(dialValue, 100)

		if dialValue == 0 {
			counter++
		}
	}

	return strconv.Itoa(counter)
}

func (day *Day01) SolvePart2() string {
	dialValue := 50
	counter := 0
	isPreviousDialValueZero := false

	for _, instruction := range day.InstructionSet {
		direction := instruction.Direction
		amount := instruction.Amount

		if direction == LEFT {
			dialValue -= amount
		} else if direction == RIGHT {
			dialValue += amount
		}

		if dialValue < 0 {
			counter += utils.Abs(dialValue) / 100

			if !isPreviousDialValueZero {
				counter++
			}
		} else if dialValue > 99 {
			counter += dialValue / 100
		} else if dialValue == 0 {
			counter++
		}

		dialValue = utils.Modulo(dialValue, 100)

		if dialValue == 0 {
			isPreviousDialValueZero = true
		} else {
			isPreviousDialValueZero = false
		}
	}

	return strconv.Itoa(counter)
}
