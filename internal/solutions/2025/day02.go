package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"strings"
)

func init() {
	solutions.RegisterDay(2025, 2, &Day2{})
}

func (day *Day2) SolvePart1() string {
	var counter int64 = 0

	for _, idRange := range day.idRanges {
		for id := idRange.firstId; id <= idRange.lastId; id++ {
			idAsString := utils.Int64ToString(id)
			idLength := len(idAsString)

			if idLength%2 != 0 {
				continue
			}

			delimiterIndex := idLength / 2
			firstPart := idAsString[:delimiterIndex]
			secondPart := idAsString[delimiterIndex:]

			if firstPart == secondPart {
				counter += id
			}
		}
	}
	return utils.Int64ToString(counter)
}

func (day *Day2) SolvePart2() string {
	var counter int64 = 0

	for _, idRange := range day.idRanges {
		for id := idRange.firstId; id <= idRange.lastId; id++ {
			idAsString := utils.Int64ToString(id)
			idLength := len(idAsString)

			if idLength <= 1 {
				continue
			}

			for delimiterIndex := range idLength / 2 {
				delimiter := delimiterIndex + 1
				if idLength%delimiter != 0 {
					continue
				}

				sequenceToCheck := idAsString[:delimiter]
				repeat := strings.Repeat(sequenceToCheck, idLength/delimiter)

				if repeat == idAsString {
					counter += id
					break
				}
			}
		}
	}
	return utils.Int64ToString(counter)
}
