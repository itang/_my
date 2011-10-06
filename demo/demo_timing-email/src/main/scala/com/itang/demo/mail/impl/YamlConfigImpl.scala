package com.itang.demo.mail.impl

import java.util.Properties
import java.io.File

import scala.reflect.BeanProperty
import org.yaml.snakeyaml.Yaml
import com.itang.demo.mail.Config

object YamlConfigImpl extends Config {
  def host = yamlConfig.server.host
  def username = yamlConfig.server.username
  def password = yamlConfig.server.password
  def port = yamlConfig.server.port
  def defaultEncoding = yamlConfig.server.defaultEncoding
  def from = yamlConfig.email.from
  def to = yamlConfig.email.to
  def files = new File(yamlConfig.email.fileDir).
    listFiles.toList.sortWith((f1, f2) => f1.lastModified > f2.lastModified).head :: Nil

  val YamlConfigFile = "config.yml"
  private lazy val yamlConfig: YamlConfig =
    (new Yaml load getClass.getResourceAsStream("/" + YamlConfigFile)).asInstanceOf[YamlConfig]
}

class ServerConfig {
  @BeanProperty
  var host: String = _
  @BeanProperty
  var username: String = _
  @BeanProperty
  var password: String = _
  @BeanProperty
  var port: Int = _
  @BeanProperty
  var defaultEncoding: String = _
}

class EmailConfig {
  @BeanProperty
  var from: String = _
  @BeanProperty
  var to: String = _
  @BeanProperty
  var fileDir: String = _
}

class YamlConfig {
  @BeanProperty
  var server: ServerConfig = _

  @BeanProperty
  var email: EmailConfig = _
}