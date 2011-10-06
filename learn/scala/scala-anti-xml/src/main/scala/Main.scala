import java.net.URL

import scala.io.Source

import scala.collection.mutable.Map

//import com.codecommit.antixml._
import scala.xml._

object P {
  type Doc = Map[String, String]
  val Doc = Map
}
import P._
case class Hit(
  sum: Doc = Doc(),
  content: Doc = Doc())

case class Result(success: Boolean, hits: List[Hit] = Nil) {
  def numhits = hits.size
}

trait DResultParser {
  def parse(xml: Elem): Result = {
    val success = (xml \\ "response").head.text match {
      case "SUCCESS" => true
      case _ => false
    }

    var hits: List[Hit] = Nil
    for (it <- (xml \\ "responsedata").head.child) {
      it match {
        case <autn:hit>{ hs @ _* }</autn:hit> =>
          val hit = Hit()
          hs.foreach {
            case <autn:content>{ ds @ _* }</autn:content> =>
              for (
                <DOCUMENT>{ cs @ _* }</DOCUMENT> <- ds;
                d <- cs
              ) {
                //Document
                hit.content(d.label.toLowerCase) = d.text
              }
            case h if h.label != "#PCDATA" =>
              hit.sum(h.label) = h.text

            case _ =>
          }

          hits = hit :: hits
        case _ =>
      }
    }

    Result(success, hits)
  }
}
trait Search {
  def search(search: String = ""): Result
}

object DSearch extends Search with DResultParser {
  def search(search: String = ""): Result = {
    parse(doSearch(search))
  }

  def doSearch(searchs: String): Elem = {
    xml
  }
  private lazy val xml = XML.load(new URL("http://203.175.156.102:9000/action=Query&Text=%E7%94%B5%E5%AE%B9%E5%BC%8F%E8%A7%A6%E6%91%B8%E6%8E%A7%E5%88%B6%E8%8A%AF%E7%89%87&print=all&maxresults=10"))//"result.xml"
}

object Main {
  def main(args: Array[String]): Unit = {
    repeat(3) {
      val result = time { DSearch.search() }

      println(result.numhits)
      for (
        hit <- result.hits;
        it <- hit.sum
      ) {
        println(it._1 + ": " + it._2)
      }

      for (
        hit <- result.hits;
        it <- hit.content
      ) {
        println(it._1 + ": " + it._2)
      }
    }
  }

  def time[T](proc: => T) = {
    def now = System.currentTimeMillis
    val start = now
    val result = proc
    println("cost: " + (now - start) + "ms" + " *" * 50)
    result
  }

  def repeat(num: Int)(block: => Any) = for (i <- 0 until num) {
    block
  }

}

