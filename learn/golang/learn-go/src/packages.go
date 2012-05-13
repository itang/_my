package main

import (
	"fmt"
)

func main() {
	fmt.Printf("%v\n", 100)
	fmt.Printf("%#v\n", 100)
	fmt.Printf("%+v\n", 100)
	fmt.Printf("%T\n", 100)

	fmt.Printf("%v\n", "hello")
	fmt.Printf("%T\n", "hello")
}
