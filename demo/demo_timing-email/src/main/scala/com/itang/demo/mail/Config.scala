package com.itang.demo.mail

import java.util.Scanner
import java.util.Properties

import java.io.File

trait ServerConfig {
  def host: String
  def username: String
  def password: String
  def port: Int
  def defaultEncoding: String
}

trait CommonEmailConfig {
  def from: String
  def to: String
  def files: List[File]
}

trait Config extends ServerConfig with CommonEmailConfig {
  def properties = {
    val p = new Properties
    p.setProperty("mail.smtp.auth", true.toString)
    p.setProperty("mail.smtp.starttls.enable", true.toString)
    p
  }

  override def toString: String = {
    val sb = new StringBuilder
    sb append getClass.getName append "{"
    sb append "\nhost: " append host
    sb append "\nusername: " append username
    sb append "\npassword: " append "*" * password.length
    sb append "\nport: " append port
    sb append "\ndefaultEncoding: " append defaultEncoding
    sb append "\nfrom: " append from
    sb append "\nto: " append to
    sb append "\nfiles: " append files
    sb append "\n}"
    sb.toString
  }
}
