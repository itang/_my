package controllers

import play._
import play.mvc._

case class User(name:String)
object Application extends Controller {
    import views.Application._
    
    def index = {
    	Template('title -> "Your Scala application is ready!")
    }
    
    def groovy = {
    	Template('users -> data)
    }
    
    def scala = {
    	html.scala("scala template demo", data)
    }
    
    def link = {
    	Html("hello,world")
    }
    
    val data = (1 to 100).toList.map(it => User("user" + it))
    
}
