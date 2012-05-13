package even

/*
-module(even)
-export([Even/1])
*/
func Even(i int) bool { //可导出函数
	return i%2 == 0
}

func odd(i int) bool { //私有函数
	return i%2 == 1
}
