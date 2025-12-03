package utils

import "strconv"

func ParseInt64(str string) (int64, error) {
	return strconv.ParseInt(str, 10, 64)
}

func ParseInt64WithoutError(str string) int64 {
	number, err := ParseInt64(str)
	if err != nil {
		HandleErrorFatally(err)
	}
	return number
}

func Int64ToString(number int64) string {
	return strconv.FormatInt(number, 10)
}
