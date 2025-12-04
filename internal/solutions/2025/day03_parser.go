package _2025

type Day3 struct {
	batteryBanks []string
}

func (day *Day3) ParseLine(line string, _ int) error {
	day.batteryBanks = append(day.batteryBanks, line)
	return nil
}
