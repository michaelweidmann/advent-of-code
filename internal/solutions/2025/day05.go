package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"sort"
)

func init() {
	solutions.RegisterDay(2025, 5, &Day5{})
}

func (day *Day5) SolvePart1() string {
	freshIngredientsCounter := int64(0)

	for _, ingredientIdToCheck := range day.ingredientIdsToCheck {
		for _, freshIngredientsIdRange := range day.freshIngredientsIdRanges {
			if freshIngredientsIdRange.firstId <= ingredientIdToCheck && ingredientIdToCheck <= freshIngredientsIdRange.lastId {
				freshIngredientsCounter++
				break
			}
		}
	}

	return utils.Int64ToString(freshIngredientsCounter)
}

func (day *Day5) SolvePart2() string {
	freshIngredientsIdRanges := utils.CopySlice(day.freshIngredientsIdRanges)
	sort.Slice(freshIngredientsIdRanges, func(i, j int) bool {
		return freshIngredientsIdRanges[i].firstId < freshIngredientsIdRanges[j].firstId
	})

	var resultList []IdRangeDay5
	currentIdRange := freshIngredientsIdRanges[0]

	for i := 1; i < len(freshIngredientsIdRanges); i++ {
		idRangeToCheck := freshIngredientsIdRanges[i]
		isOverlapping := overlaps(idRangeToCheck, currentIdRange)

		if isOverlapping {
			currentIdRange.lastId = utils.MaxInt64(currentIdRange.lastId, idRangeToCheck.lastId)
		} else {
			resultList = append(resultList, currentIdRange)
			currentIdRange = idRangeToCheck
		}
	}

	resultList = append(resultList, currentIdRange)

	freshIngredientIdsCounter := int64(0)
	for _, idRange := range resultList {
		freshIngredientIdsCounter += idRange.lastId - idRange.firstId + 1
	}

	return utils.Int64ToString(freshIngredientIdsCounter)
}

func overlaps(idRange1 IdRangeDay5, idRange2 IdRangeDay5) bool {
	if idRange2.firstId < idRange1.firstId {
		return idRange1.firstId <= idRange2.lastId
	} else {
		return idRange2.firstId <= idRange1.lastId
	}
}
