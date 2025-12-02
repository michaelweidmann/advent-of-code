package flags

import (
	"flag"
	"fmt"
	"log"
)

func ParseCommandLineArguments() (int, int, bool) {
	year := flag.Int("year", 0, "year to run")
	day := flag.Int("day", 0, "day to run")
	example := flag.Bool("example", false, "use example input")

	flag.Parse()
	checkTailFlags()

	return *year, *day, *example
}

func ValidateCommandLineArguments(year int, day int) {
	if 2015 <= year && year <= 2024 {
		if day <= 0 || 25 <= day {
			log.Fatalf("day is set to an invalid value: %d", day)
		}
	} else if 2025 <= year && year <= 2025 {
		if day <= 0 || 13 <= day {
			log.Fatalf("day is set to an invalid value: %d", day)
		}
	} else {
		log.Fatalf("year is set to an invalid value: %d", year)
	}
}

func checkTailFlags() {
	tailFlags := flag.Args()
	if tailFlags != nil && len(tailFlags) != 0 {
		fmt.Println("ignoring the following tail flags", tailFlags)
	}
}
