package main

import (
  "fmt"
  "os"
  "bufio"
  "github.com/knieriem/markdown"
)

func main() {
  p := markdown.NewParser(&markdown.Extensions{Smart: true})

  file, err := os.Open("test.md")
  if err != nil {
    fmt.Errorf("%s", err)
    return
  }
  outfile, _ := os.Create("test_out.html")
  in := bufio.NewReader(file)
  out := bufio.NewWriter(outfile)
  p.Markdown(in, markdown.ToHTML(out))
  out.Flush()
}