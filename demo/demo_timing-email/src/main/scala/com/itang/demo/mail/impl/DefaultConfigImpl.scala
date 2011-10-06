package com.itang.demo.mail.impl

import java.io.File

import com.itang.demo.mail.Config
import com.itang.demo.mail.util.in

object DefaultConfigImpl extends Config {
  def host = "smtp.gmail.com"
  def username = "live.tang@gmail.com"
  val port = 587
  val defaultEncoding = "UTF-8"
  val password = {
    print("账户密码:")
    in.nextLine.trim
  }

  def from = "itang@yihongyu.com"
  def to = {
    print("发送给谁:")
    in.nextLine.trim
  }
  val files = {
    new File("""E:\documents\一红宇\周报""").listFiles.toList.sortWith((f1, f2) => f1.lastModified > f2.lastModified).head :: Nil
  }
}