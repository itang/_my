package main

import (
  "fmt"
)

/*
Reflection in computing is the ability of a program to examine its own structure,particularly through types; it's a form of metaprogramming. It's also a great source of confution.

in this article we to clarity things by explaining how reflection works in Go
Each languages's reflection model is different(and many languages don't support it at all), but his article is about Go, so for the rest of this article the word "reflection" should be taken to mean "reflection in Go".

Type and interfaces
Because reflection builds on the type system, let's start with a refresher about types in Go.
Go is statically typed.Every variable has a static type, that is, exactly one type know and fixed at compile time: int , float32,*M7Type, []byte, and son, If we decalre type MyInt int
type MyInt int

var i int
var j MyInt

then i has type int and j has type MyInt. The variables i and j have distinct static types and, although they have the same underlying type, they cannot be assigned to one another without a conversion.
One important category of type is interface types, which represent fiexed sets of methods. And interface vaiable can store any concrete (non-finterface) value as long as that value implements the interface's methods. A well-nown pair of examples is io.Reader and io.Writer, the type Reader wnd Weriter form the io package:
//Reader is the interface htat wraps the basic Read method.
type Reader interface {
  Read(p []byte)(n int, err error)
}

type Writer interface {

  Write(p []byte)(n int, err error)
}

Any type that implements a Read(or Writer) method with this signature is Said to implement io.Reader (or io.Writer). For the purposes of this discutssion, that means that a variable of typoe io.Reader can hold any value whois type has a Read method:
var r io.Reader
r = os.Stdin
r = bufio.NewReader(r)
r = new(bytes.Buffer)
// and so on

it's important to be clear that whatever concrete value r may hold, r's type is always io.Reader:Go is statical6y type and the static type of r is io.Reader.
An extremely important example of an interface type is thy empty interface:
interface{}

It represents the empty set of methods and is statified by any value at all, since any value has zeor or more methods.

Some pepole say that Go;s interface are ynamically typed , but that is misleading. They are statically tyuped; a variable of interface type always has the same static type, and even though at run time the value stored in the interface variable may change type.that value ill always satisfy the interface.

we need to be precise about all this because reflection and interfaces are closely related.

The representation of an interface
Russ Cos has written a detailed blog post about the represent ation of interface values in Go.It's not necessary to repeat the full story here, but a simplified summary is in order.

A variable of interface type stores a pair: the the concrete value assigned to the variable, and that value's type decriptor.To be more precise, the value is the underlying concrete data itme that implemetns the interface and tye type desribles the full type of that item. For instance , aflter
var r io.Reader
tty, err := os.OpenFile("/dev/tty", os.O_RDWR, 0)
if err != nil {
  return nil, err
}
r = tty

r contains , schematically, the (value, tyoe )pair,(tty, *os.File). Notice that the tyoe *os.File implements methods otherthan Read; even though the interface value provides access only to the Read methoe, the value inside carries all the type information about that value. That's why we can do things like this:
var w io.Writer
w = r.(io.Writer)

The expression in this assignment is a type assertion; what it asserts is that the item inside also implements io.Writer, and so we can assign it to w. After the assignment, w will contin the pair(tty, *os.File)that's the same pair wa whas held in r. The static type of the interfacde determines what methods may be invoked with an interface variable, even thought the concrete value inside may have a larger set of methods.

Continuing, we can do this:

var empty interface{}
empty = w

and our empty interface value e will again contain that same pair ,(tty, *os.File).This's handy: an empty interface can hold any valoue and contains all the information we could ever need about that value.

(we don't need a type a ssertion here because it's known staticaly that w satisies the empty interface. In the exapmple where we moeved a value from a Reader to a Writer, we needed to be explicit and use a type assertion because Writer's methods are not a subset of Reader's)


One important detail is that the pair inside an interface always has the form (value, concrete type) and cannot have the form(value, interface type).Interfaces do not hold interface values.
Now we're ready to reflect.

The first law of reflection
1. Reflection goes from interface value to reflection object.

At the basic level, reflection is just a mechanism to exammine tye type and value pair stored inside an interface variable. To get started ,there a re two typ0es we need to know about in package reflect: Type and Value.Those two types give access to the contents of an interface variable, and two simple functions, called refect.TypeOf and reflect.ValueOf, retriveve reflect.Type and reflect.Value pieces out of an interface value.(Also, form the reflect Value it's easy get to the reflect.Type, but let's keep the Value and Type concepts separate for now.)

Let's start with TypeOf:

you might be wondering where the interface is here , since the program loks like it's passing the float64 variable x, not an interface value, to reflect.TypeOf. But it's there; as godoc reports, the signature of reflect.TypeOf includes an empty interface:
//TypeOf returns the reflection Type of the Value  in the interface{}

func TypeOf(i interface{}) Type
when we call reflect.TypeOf(x). x is first stored in an empty interface, , whcih is then passed as the argument; reflect.TypeOf unpacks that emty interface to recover the type information.

The reflect.ValueOf function, of course, recovers the value (from here on we'll eliede the bolierplatate and focus just on the executeable code):
var x float64 = 3.4
fmt.Println("value:", reflect.ValueOf(x))

bothe reflect.Type, and reflect.Value have lots of methods to lets us exampmine and manipulate them. One important example is that Value has a Type method that returns the Type of a reflect.Value. Another is that both Type and Value have a Kind method that returns a constant indicating what sort of item is tored: Uint, Float64, Slice, and so on. Also methods on Value with names like Int and Float let us grab value(as int64 and float64) sotred inside:

There are alos methods like SetInt and SetFloat but to use them we need to understatnd settablity, the subject of the third law of reflection, iscussed below.

The reflection library has a couple of properties woth singlng out. First , to keep the API simple, the "getter" and "setter" methods of Value operate on the largest type that can hold the value: int64 for all the signed intergers,f or instance. t

the second property is that the kind of a reflection object describes the underlying type, not the static type. If a reflection object contains a value of a user-defined interger type, as in 

the second law of reflection

2 reflection goes from reflection object to interface value.

Given a reflect.Value we can recover an interface value using the Interface method; in effect the method packs the type and value intefmation back into an interface representation and returns the result:

//Interface return v's value as an interface{}.

func (v Value) Interface() interface{}

again, there's so need to type-assert the result of v.Interface() to flat64; the empty interface value has the concrate value's type information inside and  printf will recover it.

 In short, the Interface method is the inverse of the ValueOf function, except tat its result always of static type interface{}.
 retierating: Reflect goes from interface values to reflection objects and back again.

 The third law of reflection

 3. To modify a reflection object, the value must be settable.

 The third lay is the most subtle and confusing, but it's easy enought un understand if we start first principles.

 Hre is some code that does not work, but is worth studying.

 var x float64 = 3.4
 v := reflect.ValueOf(x)
 v.SetFloat(7.1)

 if you run this code, it will panic with th ecryptic message

 panic: reflect.Value.SetFloat using unaddressable value

 The problem is not that the value 7.1 is not addresable, it's that v  is not settable, settability is a property of a refelction Value, and not all reflection Values have it.

thre reflection object p isn't settable, but's not p we want to set, it's (in effect) *p. To get to what p points to, we call the Elem method of Value , which indirects through the pointer, and save the result in a reflection Value called v:


*/

import (
  "reflect"
)

func main() {
  var x float64 = 3.4
  fmt.Println("type:", reflect.TypeOf(x))
  fmt.Printf("type:%v\n", reflect.TypeOf(x))
  fmt.Println("value:", reflect.ValueOf(x))

  v := reflect.ValueOf(x)
  fmt.Println("kind is float64:", v.Kind() == reflect.Float64)
  fmt.Println("value:", v.Float())

  var x1 uint8 = 'x'
  v1 := reflect.ValueOf(x1)
  fmt.Println("type:", v1.Type())
  fmt.Println("kind is unit8: ", v1.Kind() == reflect.Uint8)
  x1 = uint8(v1.Uint())

  type MyInt int
  var i MyInt = 7
  fmt.Println("Type:", reflect.TypeOf(i))
  fmt.Println("kind:", reflect.TypeOf(i).Kind())
  fmt.Println("kind:", reflect.ValueOf(i).Kind())

  y := v1.Interface()
  fmt.Println(y)
  fmt.Println(v1.CanSet())

  var d float64 = 3.4
  p := reflect.ValueOf(&d)
  fmt.Println("type of p:", p.Type())
  fmt.Println("kind of p:", p.Kind())
  fmt.Println("settablity of p: ", p.CanSet())

  vv := p.Elem()
  fmt.Println("settability of v:", vv.CanSet())
  p.Elem().SetFloat(100.0)
  fmt.Println(d)

  type T struct {
    A int
    B string
  }
  t := T{23, "skidoo"}
  fmt.Println("t type: ", reflect.TypeOf(t))
  s := reflect.ValueOf(&t).Elem()
  fmt.Println("s type: ", s.Type())
  s1 := reflect.ValueOf(t)
  println(s == s1)
  typeOfT := s.Type()
  for i := 0; i < s.NumField(); i++ {
    f := s.Field(i)
    fmt.Printf("%d: %s %s = %v\n", i, typeOfT.Field(i).Name, f.Type(), f.Interface())
  }

  test()
}

type User struct {
  Name     string
  Password string
  Age      int
}

func test() {
  fmt.Println(toJSON(&User{"itang", "pwd", 30}))
}

func toJSON(obj interface{}) string {
  //if struct
  v := reflect.ValueOf(obj)
  if k := v.Kind(); k == reflect.Ptr {
    s := v.Elem()
    switch s.Kind() {
    case reflect.Struct:
      return "ptr struct"
    default:
      return "ptr {}"
    }
  } else {
    return v.Type().Name()
  }
  return ""
}
