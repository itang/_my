package main

import (
	"fmt"
)

func main() {
	test_array()
	test_slice()
	test_map()

	test_learn()
}

func test_array() {
	var arr [10]int //数组类型， 有固定大小， 大小是类型的一部分
	arr[0] = 42
	for i, v := range arr {
		fmt.Printf("%d:%d\n", i, v)
	}

	a := [3]int{1, 2, 3}
	b := [...]int{1, 2, 3}
	fmt.Println(a, b, a == b)

	c := [2][2]int{[2]int{1, 2}, [2]int{3, 4}}
	d := [2][2]int{[...]int{1, 2}, [...]int{3, 4}}
	fmt.Println(c, d)

	e := [2][2]int{{1, 2}, {3, 4}}
	fmt.Println(e)
}

func test_slice() {
	//slice与array接近，但是在新的元素加入的时候可以增加长度。slice是一个指向array的指针。
	//slice是引用类型， 当赋值给另外的变量， 两个引用会指向同一个array。
	sl := make([]int, 2)
	//slice 总与一个固定长度的array成对出现。
	sl[0] = 1
	for _, v := range sl {
		fmt.Println(v)
	}

	var array [10]int
	array[9] = 100
	slice := array[0:5]
	fmt.Println(slice)
	fmt.Println(len(slice))
	fmt.Println(cap(slice))

	a := [...]int{1, 2, 3, 4, 5}
	s1 := a[1:4]
	s2 := s1[:]
	fmt.Println(len(s2), ", ", cap(s2), ", ", s2)

	x0 := []int{0, 0}
	x1 := append(x0, 1)
	fmt.Println(x0)
	fmt.Println(x1)
	fmt.Println(x0)

	test_slice_copy()
}

func test_slice_copy() {
	fmt.Println("test slice copy")
	var a = [...]int{0, 1, 2, 3, 4, 5, 6, 7}
	var s = make([]int, 6)
	n1 := copy(s, a[0:])
	fmt.Println(s)
	n2 := copy(s, s[2:])
	fmt.Println(n1, n2, s)
}

func test_map() {
	fmt.Println("map[<from type>]<to type>")
	monthdays := map[string]int{
		"Jan": 31, "Feb": 28, "Mar": 31,
	}
	fmt.Println(monthdays, monthdays["Jan"])
	year := 0
	for _, days := range monthdays {
		year += days
	}
	fmt.Println(year)

	monthdays["Undecim"] = 30
	monthdays["Feb"] = 29

	m := make(map[int]int)
	m[1] = 1000
	fmt.Println(m[1], m[2])

	var value int
	var present bool
	value, present = monthdays["Jan"]
	fmt.Println(value, present)
	v, ok := monthdays["Jan"]
	fmt.Println(v, ok)

	delete(monthdays, "Mar")
}

func test_learn() {
	for index := 0; index < 10; index++ {
		fmt.Println(index)
	}

	var i int
IT:
	if i < 10 {
		fmt.Print(i)
		i++
		goto IT
	}

	for d := 1; d <= 100; d++ {
		switch {
		case d%3 == 0 && d%5 == 0:
			fmt.Print("FizzBuzz")
		case d%3 == 0:
			fmt.Print("Fizz")
		case d%5 == 0:
			fmt.Print("Buzz")
		default:
			fmt.Print(d)
		}
	}

}
