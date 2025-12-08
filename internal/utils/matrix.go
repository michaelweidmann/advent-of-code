package utils

func TransposeMatrix[T any](slice [][]T) [][]T {
	rowLength := len(slice)
	columnLength := len(slice[0])

	result := make([][]T, columnLength)

	for i := range result {
		result[i] = make([]T, rowLength)
	}

	for i := 0; i < columnLength; i++ {
		for j := 0; j < rowLength; j++ {
			result[i][j] = slice[j][i]
		}
	}

	return result
}
