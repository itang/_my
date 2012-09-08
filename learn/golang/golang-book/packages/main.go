package main

import "fmt"

func main() {
  arr := []byte("test")  
  arr1 := []byte{'t', 'e', 's', 't'}

  fmt.Printf("%v %v %s %s\n", arr, arr1, arr, arr1)
}
