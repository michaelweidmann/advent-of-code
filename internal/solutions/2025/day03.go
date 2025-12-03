package _2025

import (
	"advent-of-code/internal/solutions"
	"advent-of-code/internal/utils"
	"math"
)

func init() {
	solutions.RegisterDay(2025, 3, &Day3{})
}

func (day *Day3) SolvePart1() string {
	return calculateTotalOutputJoltage(day.batteryBanks, 2)
}

func (day *Day3) SolvePart2() string {
	return calculateTotalOutputJoltage(day.batteryBanks, 12)
}

func calculateTotalOutputJoltage(batteryBanks []string, batteriesToTurnOn int) string {
	joltageSum := int64(0)

	for _, batteryBank := range batteryBanks {
		resultJoltage := calculateLargestJoltageValue(batteryBank, batteriesToTurnOn)
		joltageSum += resultJoltage
	}

	return utils.Int64ToString(joltageSum)
}

func calculateLargestJoltageValue(batteryBank string, batteriesToTurnOn int) int64 {
	var resultJoltageValues []rune
	batteryJoltages := []rune(batteryBank)
	batteryJoltagesLength := len(batteryJoltages)
	batterySearchFrameFrom := 0

	for batteriesLeftToTurnOn := batteriesToTurnOn; batteriesLeftToTurnOn > 0; batteriesLeftToTurnOn-- {
		batterySearchFrameTo := batteryJoltagesLength - (batteriesLeftToTurnOn - 1)

		batteryJoltage, batteryJoltageIndex := getBiggestBatteryJoltageInSearchFrame(batteryJoltages, batterySearchFrameFrom, batterySearchFrameTo)

		resultJoltageValues = append(resultJoltageValues, batteryJoltage)
		batterySearchFrameFrom = batteryJoltageIndex + 1
	}

	return utils.ParseInt64WithoutError(string(resultJoltageValues))
}

func getBiggestBatteryJoltageInSearchFrame(batteryJoltages []rune, from int, to int) (rune, int) {
	var maxBatteryJoltage rune = math.MinInt32
	maxBatteryJoltageIndex := -1

	for batteryJoltageIndex, batteryJoltage := range batteryJoltages[from:to] {
		if batteryJoltage > maxBatteryJoltage {
			maxBatteryJoltage = batteryJoltage
			maxBatteryJoltageIndex = batteryJoltageIndex
		}
	}

	return maxBatteryJoltage, maxBatteryJoltageIndex + from
}
