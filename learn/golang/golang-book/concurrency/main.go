package main

import (
"fmt"
"time"
)

func f(n int) {
  for i :=0; i < 10; i++ {
    fmt.Println(n, ":", i)
  }
}

func pinger(c chan<- string) {
  for i :=0; ; i ++ {
    c <- "ping"
  }
}

func printer(c <- chan string) {
  for {
    msg := <- c
    fmt.Println(msg)
    time.Sleep(time.Second * 1)
  }
}

func main() {
  go f(0)

  c := make(chan string)
  go pinger(c)
  go printer(c)



  c1 := make(chan string)
  c2 := make(chan string)

  go func() {
    for {
      c1 <- "from 1"
      time.Sleep(time.Second * 2)
    }
  }()

  go func() {
    for {
      c2 <- "from 2"
      time.Sleep(time.Second * 3)
    }
  }()

  go func(){
    for {
      //select picks the first channel that is ready and receives from it (or sends to it).
      select {
      case msg1 := <- c1:
        fmt.Println(msg1)
      case msg2 := <- c2:
        fmt.Println(msg2)
      case <- time.After(time.Second):
        fmt.Println("timeout")
      //default :
        //fmt.Println("Nothing ready")
      }
    }
  }()

  var input string
  fmt.Scanln(&input)
  fmt.Println(input)
}
