package main

import "fmt"

func main() {
  i := 1
  for i <= 10 {
    fmt.Print(i)

    if i % 2 == 0 {
      fmt.Println(" Even")
    } else {
      fmt.Println(" Odd")
    }
    fmt.Println()
    i ++
  }

  for i := 1; i <= 2; i++ {
    fmt.Printf("%d:%d\n", i, i)
  }

  j := 10
  switch j {
    case 1: 
    case 10: fmt.Println(10)
    default: fmt.Println("Unknow Number")
  }
}
