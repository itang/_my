package main

import "fmt"

func main() {

  var x [5]int
  fmt.Println(x, x[0])
  x[4] = 100
  fmt.Println(x)

  for i := range x {
    fmt.Println(i)
  }

  for i, value := range x {
    fmt.Printf("%d:%v\n", i, value)
  }

  for _, value := range x {
    fmt.Println(value)
  }

  y := [5]float64{ 98, 93, 77, 82, 83}
  sum := 0.0
  for _, v := range y {
    sum += v
  }
  fmt.Printf("Sum:%.2f\n", sum)

  var s []float64
  s = y[:]
  //s = y
  fmt.Println(s)
  s = y[1:3]
  fmt.Println(s)

  z := make([]int, 5, 6)
  fmt.Printf("%v, len:%d, cap:%d\n", z, len(z), cap(z))
  z[1] = 300

  var m []int
  m = append(z, 1000)
  fmt.Println(z, m)
  z[1] = 400
  fmt.Println(z, m)

  m = append(m, 2000)
  z[2] = 500
  fmt.Println(z, m)

  slice1 := []int{1,2,3}
  slice2 := make([]int, 2)
  copy(slice2, slice1)
  fmt.Println(slice1, slice2)


  var ma map[string]int = make(map[string]int)
  fmt.Println(ma)
  ma["itang"] = 31
  fmt.Println(ma)
  delete(ma, "itang")
  fmt.Println(ma)

  fmt.Println(ma["tqibm"])

  elements := map[string]string{
    "H": "Hydrogen",
    "Ne": "Neon",
  }
  fmt.Println(elements["Ne"])

  var xx uint8 = 255
  fmt.Println(xx)
}