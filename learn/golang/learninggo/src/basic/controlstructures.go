package main

import "fmt"

func main(){
	a, b := 1, 2
	max := if a > b{
		a
	}else{
		b
	}

	if err := file.Chmod(0664); err != nil {
		log.Stderr(err) // Scope of err is limited to it's body
		return err
	}
	
	if true && true{
		println("true")
	}

	if ! false {
		println("true")
	}

	f, err := os.Open(name, os.O_RDONLY, 0)
	if err != nil {
		return err
	}
	d, err := f.Stat()
	if err != nil {
		return err
	}

	doSomething(f, d)

	//for break continue
	 sum := 0
	 for i := 0; i < 10; i++ {
	 sum += i
	 }

	 //Range
   /*
		The keyword range can be used for loops. It can loop over slices, array, strings, maps and channels. range is an iterator that, when called , returns a key-value pair form the thing it loops over. Depending on what that is, range return different things 
   */
	list := []string{"a", "b", "c", "d", "e", "f"}
	for k, v := range list {
		//
	}
	 
  //switch
	switch {
	  case '0' < c : return 0
	  case 'a' < c : return 1
	}

	switch i {
		case 0:  /*fallthrough*/
		case 1:
			f()
		default:
			g()
	}

	switch c {
		case ' ', '?':
			return true
	}
	return false
	 
	 //Arrays
	 // the size is part of the type.

}