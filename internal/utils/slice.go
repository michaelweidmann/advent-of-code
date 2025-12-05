package utils

func CopySlice[T any](slice []T) []T {
	sliceCopy := make([]T, len(slice))
	copy(sliceCopy, slice)
	return sliceCopy
}
