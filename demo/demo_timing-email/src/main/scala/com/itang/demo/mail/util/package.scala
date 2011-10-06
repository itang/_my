package com.itang.demo.mail

import java.util.Scanner

package object util {
  def debug(info: => String) = println(info)

  lazy val in: Scanner = new Scanner(System.in)
}