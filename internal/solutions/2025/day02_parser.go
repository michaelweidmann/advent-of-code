package _2025

import (
	"advent-of-code/internal/utils"
	"fmt"
	"strings"
)

type Day2 struct {
	idRanges []IdRange
}

type IdRange struct {
	firstId int64
	lastId  int64
}

func (day *Day2) ParseLine(line string, _ int) error {
	idRanges := strings.Split(line, ",")

	for _, idRange := range idRanges {
		idRangeSplit := strings.Split(idRange, "-")

		if len(idRangeSplit) != 2 {
			return fmt.Errorf("invalid format of an ID range: %s", idRange)
		}

		firstId, err := utils.ParseInt64(idRangeSplit[0])
		if err != nil {
			return err
		}

		secondId, err := utils.ParseInt64(idRangeSplit[1])
		if err != nil {
			return err
		}

		day.idRanges = append(day.idRanges, IdRange{firstId, secondId})
	}

	return nil
}
