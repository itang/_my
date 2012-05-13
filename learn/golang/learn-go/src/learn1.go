package main

import (
	"fmt"
)

func main() {
	fmt.Println("变量 类型 关键字")

	test_vars()
	test_const()
	test_string()
	test_error()
	test_keyword()
	test_if()
	test_goto()
	test_for()
	test_range()
	test_switch()
	test_builtin()
}

func test_vars() {
	var a int
	var b bool
	a = 15
	b = false
	fmt.Println(a)
	fmt.Println(b)

	c := 100
	fmt.Println(c)

	var (
		x int
		y bool
	)
	x = 1
	y = false
	fmt.Println(x)
	fmt.Println(y)

	var d, e int
	fmt.Println(d + e)
	f, g := 100, false
	fmt.Println(f)
	fmt.Println(g)

	_, h := 34, 25
	fmt.Println(h)

}

func test_const() {
	const (
		a = iota // 0
		b = iota // 1
	)

	const (
		c = iota
		d //Implicitly b = iota
	)

	const (
		e        = 0
		f string = "0"
		g        = false
	)
	fmt.Println(e, f, g)
}

func test_string() {
	s := "hello"
	c := []byte(s)
	fmt.Println(s, c)

	c[0] = 'c'
	s2 := string(c)
	fmt.Printf("%s\n", s2)

	x := "Starting part " +
		"Ending part"
	fmt.Println(x)

	y := `Starting part
Ending part`

	fmt.Println(y)
}

func test_error() {
	//error
}

func test_keyword() {
	ks := []string{
		"break",
		"default",
		"func",
		"interface",
		"select",
		"case",
		"defer",
		"go",
		"map",
		"struct",
		"chan",
		"else",
		"goto",
		"package",
		"switch",
		"const",
		"fallthrough",
		"if",
		"range",
		"type",
		"continue",
		"for",
		"import",
		"return",
		"var",
	}
	for i, v := range ks {
		fmt.Println(i, v)
	}
}

func test_if() {
	var x int
	if 1 > 0 {
		x = 12
	} else {
		x = 20
	}
	fmt.Println(x)

	if true && true {
		println("true")
	}

	if !false {
		println("true")
	}
}

func test_goto() {
	i := 0
Here:
	fmt.Println(i)
	i++
	if i > 10 {
		return
	}
	goto Here
}

func test_for() {
	//for init; condition; post {} // for
	// for condition {} // while
	// for {}  //for(;;)

	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)
}

func test_range() {
	//保留字range可用于循环. 它可以在slice、array、string、map和channel。range是个迭代器
	//当他被调用的时候， 从它循环的内容种返回一个键值对。基于不同的内容， range返回不同的东西。
	list := []string{"a", "b"}
	for k, v := range list {
		fmt.Println(k, v)
	}

	for pos, char := range "你好" {
		fmt.Printf("character '%c' starts at byte position %d\n", char, pos)
	}
}

func test_switch() {
	fmt.Println(_unhex('A'))

	i := 0
	switch i {
	case 0:
		fallthrough
	case 1:
		fmt.Println("FALL THROUGH")
	default:
		fmt.Println("DEFAULT")
	}
	//分支可以使用逗号分割的列表。
	fmt.Println(_shouldEscape('?'))
}

func _shouldEscape(c byte) bool {
	switch c {
	case ' ', '?', '&', '=', '#', '+':
		return true
	}
	return false
}

func _unhex(c byte) byte {
	switch {
	case '0' <= c && c <= '9':
		return c - '0'
	case 'a' <= c && c <= 'f':
		return c - 'a' + 10
	case 'A' <= c && c <= 'F':
		return c - 'A' + 10
	}
	return 0
}

func test_builtin() {
	builtins := []string{"close", "new", "panic", "complex", "delete", "make", "recover",
		"real", "len", "append", "print", "imag", "cap", "copy", "println",
	}
	for _, v := range builtins {
		fmt.Println(v)
	}
}
