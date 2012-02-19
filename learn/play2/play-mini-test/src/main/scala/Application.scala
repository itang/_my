package playminitest

import scala.collection.mutable.{ Map ⇒ MutableMap }
import akka.actor.{ ActorSystem, Props, Actor }
import akka.pattern.ask
import akka.util.Timeout
import akka.util.duration._

import play.api.libs.concurrent._
import play.api.mvc.{ Action, AsyncResult }
import play.api.mvc.Results.{ Ok, BadRequest }
import play.api.data.Form
import play.api.data.Forms.{ tuple, nonEmptyText, number }

import com.typesafe.play.mini.{ Application, GET, Path, POST }

object PlayMiniApplication extends Application {
  private val system = ActorSystem("sample")
  private final val StatementPattern = """/account/statement/(\w+)""".r
  private lazy val accountActor = system.actorOf(Props[AccountActor])
  implicit val timeout = Timeout(1000 milliseconds)

  def route = {
    case GET(Path("/ping")) ⇒ Action {
      Ok("Pong @ " + System.currentTimeMillis)
    }
    case GET(Path(StatementPattern(accountId))) ⇒ Action {
      AsyncResult {
        (accountActor ask Status(accountId)).mapTo[Int].asPromise.map { r ⇒
          if (r >= 0) Ok("Account total: " + r)
          else BadRequest("Unknown account: " + accountId)
        }
      }
    }
    case POST(Path("/account/deposit")) ⇒ Action { implicit request ⇒
      val (accountId, amount) = commonForm.bindFromRequest.get
      AsyncResult {
        (accountActor ask Deposit(accountId, amount)).mapTo[Int].asPromise.map { r ⇒ Ok("Updated account total: " + r) }
      }
    }
    case POST(Path("/account/withdraw")) ⇒ Action { implicit request ⇒
      val (accountId, amount) = commonForm.bindFromRequest.get
      AsyncResult {
        (accountActor ask Withdraw(accountId, amount)).mapTo[Int].asPromise.map { r ⇒
          if (r >= 0) Ok("Updated account total: " + r)
          else BadRequest("Unknown account or insufficient funds. Get your act together.")
        }
      }
    }
  }
  val commonForm = Form(
    tuple(
      "accountId" -> nonEmptyText,
      "amount" -> number(min = 1)
    )
  )
}

case class Status(accountId: String)
case class Deposit(accountId: String, amount: Int)
case class Withdraw(accountId: String, amount: Int)

class AccountActor extends Actor {
  var accounts = MutableMap[String, Int]()
  def receive = {
    case Status(accountId)           ⇒ sender ! accounts.getOrElse(accountId, -1)
    case Deposit(accountId, amount)  ⇒ sender ! deposit(accountId, amount)
    case Withdraw(accountId, amount) ⇒ sender ! withdraw(accountId, amount)
  }

  private def deposit(accountId: String, amount: Int): Int = {
    accounts.get(accountId) match {
      case Some(value) ⇒
        val newValue = value + amount
        accounts += accountId -> newValue
        newValue
      case None ⇒
        accounts += accountId -> amount
        amount
    }
  }

  private def withdraw(accountId: String, amount: Int): Int = {
    accounts.get(accountId) match {
      case Some(value) ⇒
        if (value < amount) -1
        else {
          val newValue = value - amount
          accounts += accountId -> newValue
          newValue
        }
      case None ⇒ -1
    }
  }
}


