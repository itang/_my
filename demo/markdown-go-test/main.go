package main

import (
  "os"
  "bufio"
  "github.com/knieriem/markdown"
)

func main() {
  p := markdown.NewParser(&markdown.Extensions{Smart: true})

  file, _ := os.Open("test.md")
  outfile, _ := os.Create("test_out.html")
  defer file.Close()
  defer outfile.Close()

  in := bufio.NewReader(file)
  out := bufio.NewWriter(outfile)
  p.Markdown(in, markdown.ToHTML(out))
  out.Flush()
}