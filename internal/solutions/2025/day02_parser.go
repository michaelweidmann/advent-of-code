package _2025

import (
	"advent-of-code/internal/utils"
	"bufio"
	"fmt"
	"strings"
)

type Day2 struct {
	idRanges []IdRangeDay2
}

type IdRangeDay2 struct {
	firstId int64
	lastId  int64
}

func (day *Day2) ParseLine(scanner *bufio.Scanner) error {
	for scanner.Scan() {
		line := scanner.Text()

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

			day.idRanges = append(day.idRanges, IdRangeDay2{firstId, secondId})
		}
	}

	return nil
}
