package com.example

import org.scalatra._
import java.net.URL
import scalate.ScalateSupport

import org.scalaeye.mvc.views.JSPSupport

class MyScalatraFilter extends ScalatraFilter
    with ScalateSupport
    with JSPSupport {
    before {
        val list = request.getAttributeNames
        while (list.hasMoreElements) {
            val name = list.nextElement().asInstanceOf[String]
            println("attr[%s]:%s".format(name, request.getAttribute(name).toString))
        }

        request.setAttribute("msg", "Hello,World!")
    }

    get("/") {
        renderJsp() // -> "/WEB-INF/views/index.jsp
    }

    get("/users/") {
        renderJsp() // -> "/WEB-INF/views/users/index.jsp
    }

    get("/hello/index") {
        renderJsp() // -> "/WEB-INF/views/hello/index.jsp
    }

    get("/hello/hello") {
        renderJsp("msg" -> "hello") // -> "/WEB-INF/views/hello/hello.jsp
    }

    get("/hello/index1") {
        renderJsp("hello/index", "user" -> "itang") // -> "/WEB-INF/views/hello/index.jsp
    }

    get("/hello/index2") {
        renderJsp("hello/index", "user" -> "itang", "nickname" -> "唐古") 
    }

    get("/hello/index3") {
        renderJsp("hello/index", Map("user" -> "itang", "nickname" -> "唐古"))
    }

    get("/hello/index4") {
        renderJsp(Map("user" -> "itang", "nickname" -> "唐古")) // -> "/WEB-INF/views/hello/index4.jsp  , 404
    }

    notFound {
        // If no route matches, then try to render a Scaml template
        val templateBase = requestPath match {
            case s if s.endsWith("/") => s + "index"
            case s => s
        }
        val templatePath = "/WEB-INF/scalate/templates/" + templateBase + ".scaml"
        servletContext.getResource(templatePath) match {
            case url: URL =>
                contentType = "text/html"
                templateEngine.layout(templatePath)
            case _ =>
                filterChain.doFilter(request, response)
        }
    }
}
