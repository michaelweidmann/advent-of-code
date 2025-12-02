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
	counter := 0
	dialValue := 50
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

			// Needed to prevent the following case:
			// If previous value was not 0 zero, and the dial was moved using the left direction, then an additional increment is required
			// 50 --> L51 --> -1 --> increment of counter is needed and not done with the above equation (1 / 100 = 0)
			// If previous value was 0, and the dial was moved using the left direction, then no additional increment is allowed
			// 0 --> L150 --> -150 --> additional increment not allowed, only the one from the above equation (150 / 100 = 1)
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
