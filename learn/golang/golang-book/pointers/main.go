package main

import (
  "fmt"
)

func zero(xPtr *int) {
  *xPtr = 0
  fmt.Printf("%T, %v, %d\n", xPtr, xPtr, *xPtr)
}

func one(p *int) {
  *p = 1
}

func main() {
  x := 5
  zero(&x)
  fmt.Println(x)

  p := new(int)
  one(p)
  fmt.Println(*p)
}
