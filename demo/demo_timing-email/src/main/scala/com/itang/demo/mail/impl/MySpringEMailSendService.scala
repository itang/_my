package com.itang.demo.mail.impl

import javax.mail.MessagingException
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeUtility

import org.springframework.core.io.ByteArrayResource
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper

import com.itang.demo.mail._
trait SpringEMailSendService extends EMailSendService {
  def mailSender: JavaMailSenderImpl

  def send(email: Email) = {
    mailSender.send(createMimeMessage(email))
  }

  def createMimeMessage(email: Email): MimeMessage = {
    val message = mailSender.createMimeMessage
    val attachmented = !email.files.isEmpty
    // 附件 需设置第二个参数为true
    val mimeMessageHelper = new MimeMessageHelper(message, attachmented, "UTF-8")
    try {
      mimeMessageHelper.setFrom(email.from)
      mimeMessageHelper.setSubject(email.subject)
      mimeMessageHelper.setTo(email.to)
      // 第二个参数为true,支持HTML
      mimeMessageHelper.setText(email.text, true)
      email.files.foreach { file =>
        val filename = MimeUtility.encodeWord(file.getName) // 注意中文问题
        mimeMessageHelper.addAttachment(filename, file)
      }
    } catch {
      case e: MessagingException =>
        e.printStackTrace()
    }

    message
  }
}

class MySpringEMailSendService(implicit val config: Config) extends SpringEMailSendService {
  lazy val mailSender = {
    val sender = new JavaMailSenderImpl
    sender.setHost(config.host)
    sender.setUsername(config.username)
    sender.setPassword(config.password)
    sender.setPort(config.port)
    sender.setDefaultEncoding(config.defaultEncoding)
    sender.setJavaMailProperties(config.properties)
    sender
  }
}
