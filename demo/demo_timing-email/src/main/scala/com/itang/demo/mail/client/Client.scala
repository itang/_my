package com.itang.demo.mail.client

import com.itang.demo.mail.EMailProduceService
import com.itang.demo.mail.EMailSendService

class MyEmailClient(implicit val mailProduceService: EMailProduceService,
  implicit val mailSendService: EMailSendService) {
  def run() = try {
    mailSendService send mailProduceService.email
  } catch {
    case e: Exception => e.printStackTrace()
  }
}

import com.itang.demo.mail.module._
import com.itang.demo.mail.util._

object Client {
  def main(args: Array[String]) = template {
    new MyEmailClient().run
  }

  private def template(runFunc: => Unit) {
    outputHeader()
    print("确认发送(yes/no):")
    in.nextLine.trim match {
      case "yes" =>
        println("正在发送邮件...")
        runFunc
        println("发送完毕!")
      case _ =>
        println("取消发送!")
    }
    outputFooter()
  }

  private def outputHeader() {
    println("**********邮件发送demo*************\n")
    println("配置信息:\n" + config)
  }

  private def outputFooter() = println("退出!")
}