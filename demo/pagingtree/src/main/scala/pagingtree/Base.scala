package pagingtree

import java.net.URL

import javax.servlet._

import org.slf4j.LoggerFactory

import org.scalatra._
import scalate.ScalateSupport

import pagingtree.plugins.Plugin

object Base {
    val ViewPath = "/WEB-INF/scalate/templates/"
}

abstract class Base extends ScalatraFilter with ScalateSupport {
    protected val logger = LoggerFactory getLogger getClass
    protected def plugins: List[Plugin]

    override def initialize(config: FilterConfig): Unit = {
        super.initialize(config)
        eachPlugin(_.start())
    }

    override def destroy = {
        eachPlugin(_.destroy())
    }

    before {
        //logger.info("request: " + request)
        eachPlugin(_.beforeRequest(request))
    }

    after {
        eachPlugin(_.afterRequest(request, response))
    }

    error {
        logger.warn("Error", caughtThrowable)
        eachPlugin(_.error(request, response, caughtThrowable))
    }

    notFound {
        val templateBase = requestPath match {
            case s if s.endsWith("/") => s + "index"
            case s => s
        }
        render(templateBase)
    }

    def render(template: String): Unit = render(template, null)

    def render(template: String, data: Map[String, Object]): Unit = {
        val templatePath = Base.ViewPath + template + (if (template.contains(".")) "" else ".jade")
        servletContext.getResource(templatePath) match {
            case url: URL =>
                contentType = "text/html"

                if (data == null || data.isEmpty) {
                    templateEngine.layout(templatePath)
                } else {
                    templateEngine.layout(templatePath, data)
                }

            case _ =>
                filterChain.doFilter(request, response)
        }
    }

    private def eachPlugin(func: (Plugin) => Unit) = for (p <- plugins) {
        func(p)
    }

    protected def p[T](name: String, defaultValue: T)(func: String => T): T = {
        request.getParameter(name) match {
            case null => defaultValue
            case v => func(v)
        }
    }

}
