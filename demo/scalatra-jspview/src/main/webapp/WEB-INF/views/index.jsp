<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
--%>
<html>
<head>
<title>test jsp</title>
<style type="text/css">
div{
    border: 2px solid blue;
    margin: 5;
    padding: 5;
}
span{
	border:1px solid #000;
	width: 50px;
	color:red;
}
</style>
</head>
<body>
    <h1>JSP View</h1>
    <div>
    <%
    	String urls []={
    		"/users/",
    		"/hello/hello",
    		"/hello/index",
    		"/hello/index1",
    		"/hello/index2",
    		"/hello/index3",
    		"/hello/index4"
    };
    %>
    <ul>
    	<%for(String url : urls){ %>
    		<li><a href="<%=url %>"><%=url %></a>
    	<%} %>
    </ul>
</div>
<div>
    <pre>
    <code>
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

    </code>
    
    </pre>
    </div>
</body>
</html>
