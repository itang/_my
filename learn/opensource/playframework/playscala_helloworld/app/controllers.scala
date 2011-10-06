package controllers

import play._
import play.mvc._

import play.data.validation.Annotations._

object Application extends Controller {

  def index = Template

  def sayHello(@Required myName: String) = {
    if (validation.hasErrors) {
      flash += "error" -> "Oops, please enter your name!"
      Action(index)
    } else {
      Template('myName -> myName)
    }
  }

}
