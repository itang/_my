package main

import (
  "fmt"
)

var msg = "Hello world"

var (
  a = 5
  b = 10
  c = 12
  d, e = 20, 30
)

func main() {
  fmt.Println("1 + 1 =", 1 + 1) 
  fmt.Printf("%c\n", "Hello World"[1]) 
  fmt.Println(!true)

  var x string = "Hello World"
  fmt.Println(x)

  var y string
  y = "itang"
  fmt.Println(y)

  z := "Hello-vbbbnnn nnnnnnnbnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnmnmmnmn     nm  nm   m  vcbvcvcvb World"
  fmt.Printf("%T %v\n", z, z)

  fmt.Println(msg)

  const msg1 = "welcome"
  fmt.Println(msg1)

  fmt.Println(a, d, e)

  fmt.Print("Enter a number: ")
  var input float64
  fmt.Scanf("%f", &input)
  output := input * 2
  
  fmt.Println(output)
}
