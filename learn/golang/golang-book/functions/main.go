package main

import (
  "fmt"
)

func average(xs []float64) float64 {
  if xs == nil || len(xs) == 0 {
    return 0.0
  }

  total := 0.0
  for _, x := range xs {
    total += x
  }
  return total / float64(len(xs))
}

func f() (int, int) {
  return 1, 2
}

func add(args ...int) int {
  sum := 0
  for _, v := range args {
    sum += v
  }

  return sum
}

// 闭包
func inc() func() int {
  x := 100
  return func() int {
    x ++
    return x
  }
}

func factorial(x uint64) uint64 {
  if x == 0 {
    return 1
  }

  return x * factorial(x - 1)
}

func main() {
  var x []float64 = nil
  fmt.Printf("len(nil):%d\n\n", len(x))
  fmt.Printf("1/0:%v\n", 1/float64(len(x)))

  ret := average([]float64{1,2,100,300,98})
  fmt.Printf("average:%f\n", ret)
  fmt.Println(average(nil))
  fmt.Println(average([]float64{}))

  a, b := f()
  fmt.Printf("a:%v, b:%v\n", a, b)

  // 可变参数
  fmt.Printf("add(1,2,3):%v\n", add(1, 2, 3))
  c := []int{100, 200, 400}
  fmt.Printf("add(c...):%d\n", add(c...))

  // 匿名函数
  add := func(x, y int) int {
    return x + y
  }

  fmt.Printf("%T, %v, %d\n", add, add, add(2, 3))

  theInc := inc()
  for x :=0; x < 1000; x++ {
    theInc()
  }

  fmt.Printf("%T: %d, %d\n", theInc, theInc(), theInc())

  fmt.Printf("factorial(42):%d\n", factorial(42))


  defer func(){
    fmt.Println("I'm the winner############################")
  }()

  defer fmt.Println("defer@!")

  fmt.Println("Here")

  defer func() {
    if err := recover(); err != nil {
      fmt.Println("panic:", err)
    }
  }()
  panic("PANIC")
}

