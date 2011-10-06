package controllers

import play._
import play.mvc._

import search._

import util._

case class PageInfo(currPage: Int, lastPage: Int, pages: List[Int]) {
  def isLastPage = (currPage == lastPage)
}

object Application extends Controller {

  def index = Template

  def search(q: String = "深圳", page: Int = 1, limit: Int = 10) = {
    val start = (page - 1) * limit + 1
    val max = start + limit
    val result = DSearch.search(q, "start" -> start,
      "maxresults" -> max,
      "page" -> page)

    val lastPage = (result.total / limit + (if (result.total % limit == 0) 0 else 1))
    val pages = makePages(page, lastPage)

    Template('q -> q, 'result -> result, 'pageInfo -> PageInfo(page, lastPage, pages))
  }

  /**
   * 生成分页的索引.
   *
   */
  private def makePages(currPage: Int, total: Int, length: Int = 10) = {
    val start = {
      val s = currPage - length / 2
      if (s < 1) 1 else s
    }

    val end = {
      val e = start + length - 1
      if (e > total) total else e
    }
    (start to end).toList
  }

}

