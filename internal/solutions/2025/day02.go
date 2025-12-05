package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"strings"
)

type IsIdInvalidFunc func(idToCheck string) bool

func init() {
	solutions.RegisterDay(2025, 2, &Day2{})
}

func (day *Day2) SolvePart1() string {
	isIdInvalid := func(idToCheck string) bool {
		idLength := len(idToCheck)

		if idLength%2 != 0 {
			return false
		}

		sequenceLength := idLength / 2
		firstPart := idToCheck[:sequenceLength]
		secondPart := idToCheck[sequenceLength:]

		if firstPart == secondPart {
			return true
		}

		return false
	}

	return calculateSumOfAllInvalidIds(day.idRanges, isIdInvalid)
}

func (day *Day2) SolvePart2() string {
	isIdInvalid := func(idToCheck string) bool {
		idLength := len(idToCheck)

		if idLength <= 1 {
			return false
		}

		for sequenceLengthZeroIndexed := range idLength / 2 {
			sequenceLength := sequenceLengthZeroIndexed + 1
			if idLength%sequenceLength != 0 {
				continue
			}

			sequenceToCheck := idToCheck[:sequenceLength]
			fullSequence := strings.Repeat(sequenceToCheck, idLength/sequenceLength)

			if fullSequence == idToCheck {
				return true
			}
		}

		return false
	}

	return calculateSumOfAllInvalidIds(day.idRanges, isIdInvalid)
}

func calculateSumOfAllInvalidIds(idRanges []IdRangeDay2, isIdInvalid IsIdInvalidFunc) string {
	var sumOfAllInvalidIds int64 = 0

	for _, idRange := range idRanges {
		sumOfInvalidIdsInIdRange := getSumOfInvalidIdsInIdRange(idRange, isIdInvalid)
		sumOfAllInvalidIds += sumOfInvalidIdsInIdRange
	}

	return utils.Int64ToString(sumOfAllInvalidIds)
}

func getSumOfInvalidIdsInIdRange(idRange IdRangeDay2, isIdInvalid IsIdInvalidFunc) int64 {
	var sum int64 = 0

	for id := idRange.firstId; id <= idRange.lastId; id++ {
		idAsString := utils.Int64ToString(id)

		if isIdInvalid(idAsString) {
			sum += id
		}
	}

	return sum
}
