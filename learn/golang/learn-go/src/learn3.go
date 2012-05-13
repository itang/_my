package main

import (
	"fmt"
)

func main() {
	test_func_define()
	test_scope()
	test_mult_return_values()
	test_defer()
	test_varargs()
	test_panic_recover()
	test_learn()
}

type mytype int //新的类型

//receiver
//funcname
//named return parameters 
func (p mytype) funcname(q int) (r, s int) {
	return 0, 0
}
func test_func_define() {
	//
	a := mytype(10)
	fmt.Println(a.funcname(10))

	rec(5)
}

func rec(i int) {
	if i == 10 {
		return
	}
	rec(i + 1)
	fmt.Printf("%d ", i)
}

var global_a = 100

func test_scope() {
	//作用域有就近原则
	//在Go中，定义在函数外的变量是全局的， 那些定义在函数内部的变量，对于函数来说是局部的
	//如果命名覆盖， 一个局部变量与一个全局变量有相同的名字 在函数执行的时候， 局部变量将覆盖全局变量。
	fmt.Println(global_a)
}

func test_mult_return_values() (int, int) {
	//func (file * File) Write(b []byte) (n int, err error)
	return 1, 2
}

func test_named_return_parameters() {
	var r MyReader
	var b []byte
	ReadFull(r, b)
}

type MyReader struct {
}

func (r *MyReader) Read(buf []byte) (int, error) {
	return 0, nil
}

func ReadFull(r MyReader, buf []byte) (n int, err error) {
	for len(buf) > 0 && err == nil {
		var nr int
		nr, err = r.Read(buf)
		n += nr
		buf = buf[nr:len(buf)]
	}
	return
}

type File struct {
}

func (f *File) Open(s string) error {
	return nil
}

func (f *File) Close() error {
	return nil
}

func test_defer() {
	//在defer后指定的函数会在当前函数退出执行前调用
	file := new(File)
	file.Open("file")

	defer file.Close()
	//将以将多个函数放入"延迟列表中
	//后进先出 LIFO
	for i := 0; i < 5; i++ {
		defer fmt.Printf("%d ---", i)
	}

	//defer可以修改返回值

	f := func() (ret int) {
		defer func() {
			ret++
		}()
		return 0
	}

	fmt.Println("defer", f())
}

func test_varargs() {
	//Anonymous function
	myfunc := func(arg ...int) {
		for i, v := range arg {
			fmt.Println(i, v)
		}
	}
	myfunc(1, 2, 3, 4, 54)

	arr := []int{10, 11, 12, 13, 14, 15}
	myfunc(arr...)
	myfunc(arr[:2]...)

	fmt.Printf("%T\n", myfunc)

	hf := func(f func(int) int) {
		fmt.Println(f(10))
	}

	hf(func(i int) (ret int) {
		ret = i * 120
		return
	})

	fmt.Printf("%T\n", test_varargs)
}

func test_panic_recover() {
	//go 没有例如Java那样的异常机制： 不能抛出一个异常。作为替代
	// 它使用了panic-and-recover）机制。
	// Panic
	/*
	   是一个内建函数， 可以中断原有的控制流程， 进入一个令人恐慌的流程中。当函数
	   F调用panic，函数F的执行被中断，并且F中的延迟函数会被正常的执行， 然后F
	   返回到调用它的地方，在调用的地方， F的行为就像调用了panic.这一过程继续向上，知道程序崩溃时的所有goroutine
	   返回。

	   恐慌可以直接调用panic产生。也可以由运行时错误产生， 例如访问越界的数组。

	*/

	//Recover
	/*
	  是一个内建函数，可以让进入令人恐慌的流程种的goroutine恢复过来。
	  Recover仅在延迟函数中有效。
	  在正常的执行过程中， 调用recover返回nil并且没有其他任何效果。如果当前
	  的goroutine陷入恐慌，调用recover可以捕获到panic的输入值，并且恢复正常的执行。

	*/
	f := func() {
		panic(fmt.Sprintf("%v", 100))
	}

	ret := throwsPanic(f)
	fmt.Println(ret)
}

func throwsPanic(f func()) (b bool) {
	defer func() {
		if x := recover(); x != nil {
			b = true
		}
	}()
	f()
	return
}

func test_learn() {
	f := func(a, b int) (int, int) {
		if a > b {
			return b, a
		}
		return a, b
	}

	fmt.Println(f(1, 2))
	fmt.Println(f(2, 1))

	Map := func(f func(int) int, s ...int) []int {
		ret := make([]int, len(s))
		for i := 0; i < len(s); i++ {
			ret[i] = f(s[i])
		}
		return ret
	}
	fmt.Println(Map(func(x int) int {
		return x * 2
	}, []int{1, 2, 3, 4, 5, 23}...))

	plusTwo := func(x int) int { return x + 2 }
	fmt.Println(plusTwo(2))

	plusX := func(x int) func(int) int {
		return func(d int) int { return d + x }
	}
	fmt.Printf("%v\n", plusX(100)(200))
}
