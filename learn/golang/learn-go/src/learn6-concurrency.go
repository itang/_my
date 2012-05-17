package main 

import (
  "fmt"
  "time"
  "runtime"
)

/*
 goroutine 是一个普通的函数, 只是需要使用保留字go作为开头.
 
  ready("Tea", 2) <-普通函数调用
  go ready("Tea", 2) <- ready() 作为goroutine运行
*/

var c chan int
func main() {
  runtime.GOMAXPROCS(4)

  c = make(chan int)
  go ready("Tea", 2)
  go ready("Coffee", 1)
  fmt.Println("I'm waiting")
  
  //如果不等待goroutine 的执行(例如,移除第17 行),程序立刻终止,而任何正在执行的goroutine 都会停止。
  //time.Sleep(5 * time.Second) 
  fmt.Println(<-c)
  //<-c
  
  //使用make 创建channel, 还需定义channel的值的类型
  // ci := make(chan int)
  // 使用 <- 发送或接收数据， 具体由<-的位置决定
  // ci <- 1 //发送1 到 channel ci
  // <-ci    // 从channel ci接收整数
  /*
  使用ch := make(chan bool) 创建的是无缓冲channel
  读取会被阻塞， 直到有数据接受
  发送将会阻塞，知道数据被读出
  
  指定缓冲大写， 即channel可以存储多少个元素：
  cn := make(chan bool, 4), 创建了可以存储4个元素的bool型channel。在这个channel种，
  前4个元素可以无阻塞的写入。当写入第5元素时， 代码将会阻塞， 知道其他goroutine从channel种读取一些
  元素，腾出空间。
  */
  
  //非阻塞的读取 
  x , ok := <-c //???
  fmt.Println(x, ok)
}

func ready(w string, sec int) {
  time.Sleep(time.Duration(sec) * time.Second)
  fmt.Println(w, "is ready")
  c <- sec
}
