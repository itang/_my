package main

import (
	"fmt"
	"reflect"
)

type S struct {
	i int
}

func (p *S) Get() int  { return p.i }
func (p *S) Put(v int) { p.i = v }

type I interface {
	Get() int
	Put(int)
}

type R struct{ i int }

func (p *R) Get() int  { return p.i }
func (p *R) Put(v int) { p.i = v }

func f(p I) {
	fmt.Println(p.Get())
	p.Put(1)
	fmt.Println(p.Get())

	//
	switch t := p.(type) {
	case *S:
		fmt.Printf("*S: %T\n", t)
	case *R:
		fmt.Printf("*R: %T\n", t)
	default:
		fmt.Printf("Unknown real type")
	}

	//fmt.Println(p.(type)) //use of .(type) outside type switch
}

//每个类型都能匹配到空接口
func g(something interface{}) int {
	return something.(I).Get()
}

type Foo int

//不能扩展内建类型
//不能扩展非本地类型（及其他包的类型）
func (self Foo) Emit() {
	fmt.Printf("%v\n", self)
}

func main() {
	defer func() {
		if err := recover(); err != nil {
			fmt.Println("defer1:Error")
		}
	}()
	//每个类型都有接口， 以为着对那个类型定义了方法集合
	//对于接口I， S是合法的实现， 因为它定义了I所需的两个方法。
	var s S
	f(&s) // *S 是在s的指针上定义了方法，实现了I接口， 所以需要传s的指针

	f(&R{100})

	sp := &s

	fmt.Println(g(sp))
	fmt.Println(g(&R{100}))

	defer func() {
		fmt.Println("defer2")
	}()

	i := Foo(11111)
	i.Emit()

	//接口类型的方法
	//接收者类型必须是T 或*T,这里的T 是类型名。T 叫做接收者基础类型或简
	//称基础类型。基础类型一定不能使指针或接口类型,并且定义在与方法相
	//同的包中。

	test_reflect()

	//panic
	fmt.Println(g(1))
	//exec ignore
	defer func() {
		fmt.Println("defer3")
	}()

}

func test_reflect() {
	p1 := new(Person)
	ShowTag(p1)
}

type Person struct {
	name string "namestr"
	age  int
}

func ShowTag(i interface{}) {
	switch t := reflect.TypeOf(i); t.Kind() {
	case reflect.Ptr:
		tag := t.Elem().Field(0).Tag
		fmt.Println("tag:", tag)
	}
}
