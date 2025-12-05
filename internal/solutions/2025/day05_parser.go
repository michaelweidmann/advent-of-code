package _2025

import (
	"advent-of-code/internal/utils"
	"bufio"
	"fmt"
	"strings"
)

type Day5 struct {
	freshIngredientsIdRanges []IdRangeDay5
	ingredientIdsToCheck     []int64
}

type IdRangeDay5 struct {
	firstId int64
	lastId  int64
}

func (day *Day5) ParseLine(scanner *bufio.Scanner) error {
	for scanner.Scan() {
		idRangeS := scanner.Text()

		if idRangeS == "" {
			break
		}

		idRangeSplit := strings.Split(idRangeS, "-")

		if len(idRangeSplit) != 2 {
			return fmt.Errorf("invalid format of an ID range: %s", idRangeS)
		}

		firstId, err := utils.ParseInt64(idRangeSplit[0])
		if err != nil {
			return err
		}

		secondId, err := utils.ParseInt64(idRangeSplit[1])
		if err != nil {
			return err
		}

		day.freshIngredientsIdRanges = append(day.freshIngredientsIdRanges, IdRangeDay5{firstId, secondId})
	}

	for scanner.Scan() {
		ingredientIdString := scanner.Text()

		ingredientId, err := utils.ParseInt64(ingredientIdString)
		if err != nil {
			return nil
		}

		day.ingredientIdsToCheck = append(day.ingredientIdsToCheck, ingredientId)
	}

	return nil
}
