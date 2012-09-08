package main

import (
"fmt"
"math"
)

func distance(x1, y1, x2, y2 float64) float64 {
  a := x2 - x1
  b := y2 - y1
  return math.Sqrt(a*a + b*b)
}

type Circle struct {
  x float64
  y float64
  r float64
}

type Rectangle struct {
  x1, y1, x2, y2 float64
}

func (c *Circle) Area() float64 {
  return math.Pi * c.r * c.r
}

func (r *Rectangle) Area() float64 {
  l := distance(r.x1, r.y1, r.x1, r.y2)
  w := distance(r.x1, r.y1, r.x2, r.y1)
  return l * w
}

type Person struct {
  Name string
}

func (p *Person) Talk() {
  fmt.Println("Hi, my name is", p.Name)
}

type Android struct {
  Person // Embedded Types
  Model string
}

type Shape interface {
  Area() float64
}

func totalArea(shapes ...Shape) float64 {
  var area float64
  for _, s := range shapes {
    area += s.Area()
  }
  return area
}

type MultiShape struct {
  shapes []Shape
}

func (m *MultiShape) Area() float64 {
  var area float64
  for _, s := range m.shapes {
    area += s.Area()
  }
  return area
}

func main() {
  var c Circle
  fmt.Printf("%T, %v, %v, area:%v\n", c, c, c.r, c.Area())

  d := Circle{1,2,3}
  fmt.Printf("%v, %v\n", d, d.Area())

  a := new(Android)
  a.Person.Talk()

  a.Name = "itang"
  a.Talk()

  e := Rectangle{1,2,3,4}
  fmt.Printf("areas:%f\n", totalArea(&c, &e))
}
