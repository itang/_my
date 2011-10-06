package pagingtree.resources

import org.scalatra.ScalatraFilter
import org.scalatra.scalate.ScalateSupport

import pagingtree.Application
import pagingtree.Region
import pagingtree.Page

/**
 * 行政区划资源.
 * 
 */
trait RegionResource { this: Application =>
  get("/region") {
    render("/region/index")
  }

  post("/region/data") {
    val node = p("node", -1) { _.toInt }
    val start = p("start", 0) { _.toInt }
    val limit = p("limit", 15) { _.toInt }
    //val total = p("total", -1L) { _.toLong }

    val page = regionService.getChildRegions(node, start, limit)

    contentType = "application/json; charset=UTF-8"
    response.getWriter.println(regionJson(page))
  }

  private def regionJson(page: Page[Region]): String = {
    val sb = new StringBuilder();
    sb.append("{total:").append(page.total)
      .append(",nodes:[")

    val result: List[Region] = page.data
    var first = true

    for (c <- result) {
      if (!first) {
        sb.append(", ");
      }
      sb.append("{");
      sb.append("id:").append(c.id).append(",")
      sb.append("text:").append("\"").append(c.name).append("\"").append(",")
      sb.append("leaf:").append(false)
      sb.append("}")

      first = false;
    }

    sb.append("]}");
    return sb.toString();
  }
}
