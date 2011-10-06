package com.itang.demo.mail.impl

import com.itang.demo.mail.{ Config, Email, EMailProduceService }
class MyEMailProduceService(implicit val config: Config) extends EMailProduceService {
  def email: Email = {
    try {
      val filename = config.files.head.getName
      new Email {
        def subject = filename.substring(0, filename.lastIndexOf("."))
        def from = config.from
        def to = config.to
        def text = filename
        def files = config.files.head :: Nil
      }
    } catch {
      case e => throw e
    }
  }
}
