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
	
	
}