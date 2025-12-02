package utils

import (
	"math"
)

func Abs(number int) int {
	return int(math.Abs(float64(number)))
}

func Modulo(dividend, divisor int) int {
	if dividend < 0 && divisor < 0 {
		return dividend % divisor
	}
	dividend %= divisor
	if dividend < 0 {
		dividend += divisor
	}
	return dividend
}
