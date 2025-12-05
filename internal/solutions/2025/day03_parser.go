package _2025

import "bufio"

type Day3 struct {
	batteryBanks []string
}

func (day *Day3) ParseLine(scanner *bufio.Scanner) error {
	for scanner.Scan() {
		line := scanner.Text()
		day.batteryBanks = append(day.batteryBanks, line)
	}

	return nil
}
