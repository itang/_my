package com.itang.demo.mail

trait Email {
  def subject: String
  def from: String
  def to: String
  def text: String
  def files: List[java.io.File]
}