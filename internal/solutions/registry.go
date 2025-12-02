package solutions

var Registry = map[int]map[int]func() Solution{}

func RegisterDay(year int, day int, solution Solution) {
	if Registry[year] == nil {
		Registry[year] = map[int]func() Solution{}
	}
	Registry[year][day] = func() Solution {
		return solution
	}
}
