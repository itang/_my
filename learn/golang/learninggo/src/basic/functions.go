package main


type mytype int

func (p mytype) Funcname(q mytype) (r, s mytype) {
  return p + q, p + q + 1
}
	
func main() {
	var a mytype = 100
	
	c, _:= a.Funcname(111)
	
	println(c)

	r : = mux.NewRouter()

  r.Get("/", func(){ 

  })
}
