package org.scalaeye.mvc.views

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.ServletException

package org.scalaeye.mvc {
    package object views {
        type modelType = Map[String, Any]
        type ContextType = {
            def request: HttpServletRequest
            def response: HttpServletResponse
        }
    }
}

import org.scalaeye.mvc.views._

trait View {
    def contentType: String
    def render(data: AnyRef, request: HttpServletRequest, response: HttpServletResponse): Unit
}

case class JSPView(view: String = null,
    model: modelType = Map.empty,
    viewPathPrefix: String = "/WEB-INF/views",
    viewFileSuffix: String = ".jsp") extends View {
    def contentType: String = "texdt/html; charset=UTF-8"

    def render(model: AnyRef, request: HttpServletRequest, response: HttpServletResponse): Unit = {
        val path = viewPath(request)
        println("path: " + path)
        request.getRequestDispatcher(path) match {
            case null =>
                throw new ServletException("Could not get RequestDispatcher for [" + path +
                    "]: Check that the corresponding file exists within your web application archive!");
            case rd =>
                fillRequestAttribute(model, request)
                if (isIncludeRequest(request)) {
                    response.setContentType(contentType)
                    rd.include(request, response)
                } else {
                    rd.forward(request, response)
                }
        }
    }

    private def fillRequestAttribute(model: AnyRef, request: HttpServletRequest) = {
        model match {
            case null =>
            case None =>
            case map if model.isInstanceOf[Map[String, _]] =>
                map.asInstanceOf[Map[String, _]].foreach(it => request.setAttribute(it._1, it._2))
            case _ => request.setAttribute("model", model);
        }
    }

    private def viewPath(request: HttpServletRequest): String = view match {
        case null => requestURI2ViewPath(request)
        case _ => view2ViewPath()
    }

    private def view2ViewPath() = view match {
        case v if view.startsWith("/") =>
            viewPathPrefix + view + viewFileSuffix
        case v =>
            viewPathPrefix + "/" + view + viewFileSuffix
    }

    private def requestURI2ViewPath(request: HttpServletRequest): String = {
        val uri = request.getRequestURI
        uri.substring(request.getContextPath.length, uri.length) match {
            case path if path.endsWith("/") =>
                viewPathPrefix + path + "/index" + viewFileSuffix
            case path => viewPathPrefix + path + viewFileSuffix
        }
    }

    private def isIncludeRequest(request: HttpServletRequest) = request.getAttribute(INCLUDE_REQUEST_URI_ATTRIBUTE) != null
    private val INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri"
}

trait JSPSupport { self: ContextType =>
    def jsp(view: String = null, model: Map[String, Any] = Map.empty) = {
        JSPView(view, model).render(model, request, response)
    }

    def renderJsp(view: String) = jsp(view, Map.empty)

    def renderJsp(view: String, model: Map[String, Any]) = jsp(view, model)
    def renderJsp(model: Map[String, Any]) = jsp(null, model)

    def renderJsp(view: String, model: Tuple2[String, Any]*) = jsp(view, Map(model:_*))
    def renderJsp(model: Tuple2[String, Any]*) = jsp(null, Map(model:_*))
}

