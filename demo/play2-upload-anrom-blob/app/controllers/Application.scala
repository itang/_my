package controllers

import play.api.mvc._
import play.api.mvc.BodyParsers._

import views._

object Application extends Controller {
  def index = Action {
    import play.api.db._
    import play.api.Play.current
    import anorm._
    val ret = DB.withConnection {
      implicit conn =>
        SQL("delete from user").executeUpdate() //cleanup data
        SQL("""insert into user(id,name,introduce) values({id}, {name}, {introduce})
                """)
          .on(
          'id -> new java.util.Random().nextInt(10000),
          'name -> "itang",
          'introduce -> "<li>你好世界!</li>" * 1000
        ).executeInsert()

         SQL("select * from user")().map {
          row =>  row[String]("introduce")
        }.mkString("<ul>", "", "</ul>")
    }
    Ok(html.index("ret:" + ret))
  }

  def upload_form() = Action {
    implicit request =>
      Ok(html.upload())
  }

  /**
   *  100KB
   */
  def upload() = Action(parse.maxLength(1024 * 100, parser = parse.multipartFormData)) {implicit request =>
    request.body.fold(
    it => BadRequest(it.toString()), {
      case f => {
        f.file("pic").map {pic =>
          import java.io.File
          val filename = pic.filename
          val contentType = pic.contentType
          val length = pic.ref.file.length()
          pic.ref.moveTo(new File("F:/test/", filename))
          Ok("文件上传成功" + Map("filename" -> filename, "contentType" -> contentType, "length" -> length))
        }.getOrElse {
          Redirect(routes.Application.upload_form).flashing("message" -> "Missing File")
        }
      }
    }
    )
  }
}