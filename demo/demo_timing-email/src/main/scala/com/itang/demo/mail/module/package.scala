package com.itang.demo.mail

import com.itang.demo.mail.impl._
import com.itang.demo.mail.util._

package object module {
  implicit lazy val config = {
    print("加载配置(default,yaml):")
    in.nextLine.trim match {
      case "yaml" => YamlConfigImpl
      case _ => DefaultConfigImpl
    }
  }

  implicit lazy val eMailProduceService: EMailProduceService = new MyEMailProduceService

  implicit lazy val eMailSendService: EMailSendService = new MySpringEMailSendService
}