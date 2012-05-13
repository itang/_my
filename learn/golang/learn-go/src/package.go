package main

import (
	"even"
	f "fmt" //alias package 
	// (:require [fmt :as f])k      n  b,,,
)

func main() {
	f.Println(`
  包是函数和数据的集合。用package保留字定义一个包。文件名不需要与包名一致。
  包名的约定是使用小写字符。 Go包可以由多个文件组成， 但是使用相同的package<name>这一行。
  `)

	f.Println(even.Even(10))
}
