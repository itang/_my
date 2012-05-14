package main

import (
	"bytes"
	"fmt"
	"sync"
)

func main() {
	test_pointer()
	test_memory_alloc_new_make()
	test_custom_types()
}

func test_pointer() {
	var p *int
	fmt.Printf("%v\n", p)

	var i int
	p = &i //p 指向i
	fmt.Printf("%v\n", p)

	*p = 8                 // 修改i的值
	fmt.Printf("%v\n", *p) // * 对指针求值
	fmt.Printf("%v\n", i)
}

func test_memory_alloc_new_make() {
	// new(T) 分配T类型的空间，并填充了零值， 且返回其地址，即*T
	p := new(SyncedBuffer)
	var v SyncedBuffer
	fmt.Printf("%v\n", p)
	fmt.Printf("%v\n", *p)
	fmt.Printf("%v\n", v)

	// make(T, args) 只能用来创建slice,map和channel
	//并且返回一个有初始值（非零）的T类型，而不是*T， make初始化了内部数据
	//结构，填充适当的值

}

type SyncedBuffer struct {
	lock   sync.Mutex
	buffer bytes.Buffer
}

func test_custom_types() {
	a := new(NameAge)
	a.name = "Pete"
	a.age = 42

	fmt.Printf("%v\n", a)
}

type NameAge struct {
	name string
	age  int
}
