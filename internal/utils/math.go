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

func Max(number1, number2 int) int {
	if number1 > number2 {
		return number1
	}

	return number2
}

func Min(number1, number2 int) int {
	if number1 < number2 {
		return number1
	}

	return number2
}

func MaxInt64(number1, number2 int64) int64 {
	if number1 > number2 {
		return number1
	}

	return number2
}
