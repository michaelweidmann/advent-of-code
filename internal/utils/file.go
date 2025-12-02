package utils

import (
	"fmt"
	"os"
)

func GetInputFileName(year int, day int, example bool) string {
	if example {
		return fmt.Sprintf("resources/%d/%02d/example-input.txt", year, day)
	}

	return fmt.Sprintf("resources/%d/%02d/input.txt", year, day)
}

func CloseFile(file *os.File) {
	err := file.Close()
	if err != nil {
		HandleErrorFatally(err)
	}
}
