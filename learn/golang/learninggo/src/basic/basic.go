package main

import (
	"fmt"
)

func main(){
	//variables
	var a int
	var b int
	a = 15
	b = false
	
	a1 := 15
	b1 := false

	var (
		x int
		y bool
	)

	a2, b2 := 20, false	
	_, b3 := 34, true

	//types
	//int int32 int64 int8 int16, uint, byte ,float32, float64, bool

	//Constants
	const (
		v1 = iota
		v2 = iota
	)

	const (
		v3 = 0
		v4 string = "0"
		)

	//Strings
	s := `Starting part
        Ending part`
	
  //Array
	var arr[10]int
	println(arr[0])
	arr[0] = 42
	println(arr[0])
	
	b := [3]int{1,2,3}
	println(b[0])
	
	a1 := [2][2]int{[2]int{1,2}, [2]int{3,4}}
	println(a1[0][1])
	a1 = [2][2]int{ {1, 2}, {3, 4} }
	println(a1[1][1])
	
	//slices
	
	sl := make([]int, 10)
	println(sl[5])
	println( len(sl) )
	println( cap(sl) )
	
	a := [...]int{1,2,3,4,5}
	s1 := a[2:4]
	println(len(s1))
	println(a[:])
	println(a[:4])
	println(a[1:5][3])
	
	d0 := [2]int{1,2}
	
	sa := d0[:]
	println(cap(sa))
	sa1 := append(sa, 3)
	for _, v := range sa1{
		println(v)
	}
	for _, v := range d0{
		println(v)
	}

	//Maps
	mons := map[string]int {
	 "Jan" : 31, "Feb":28,
	}
	
	fmt.Printf("%d\n", mons["Feb"])
	
	year := 0
	for _,days := range mons {
		year += days
	}
	println(year)
	
	mons["Undecim"] = 30
	mons["Feb"] = 29
	println(mons["Feb"])
	
	value, present := mons["Jan1"]
	println(value, present)
	
	str := "A"
	for i := 0; i < 10; i++ {
		fmt.Printf("%s\n", str)
		str = str + "A"
	}
	
	
}