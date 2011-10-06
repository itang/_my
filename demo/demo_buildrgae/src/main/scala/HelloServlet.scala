package com.itang.buildrgae

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import com.google.inject.Singleton

@Singleton
class HelloServlet extends HttpServlet {
    override def init() = {}

    override def doGet(request: HttpServletRequest, response: HttpServletResponse) = {
        val out = response.getOutputStream()
        response.setContentType("text/html")
		
        val title = "A gae demo webapp"
        var margin = 0
        val html = <html>
        			<head><title>{title}</title>
        			</head>
        			<body>
        			<h1>{title}</h1> 
        			<h2>Based on Scala, Guice, Guice-servlet, and build by Apache Buildr</h2>
					<ul>Serlvet class hierarchy:
					{for(s <- clazzinfo) yield
						<li><span style={margin += 1; "margin-left:" + margin.toString + "em"}>{s}</span></li>
					}
					</ul></body></html>
		out.print(html.toString)
        out.close()
    }

    override def destroy() = {}
	
	private def clazzinfo = {
		var result: List[String] = Nil
		var clazz = this.getClass
		while( clazz != null){
			result = clazz.getName :: result 
			clazz = clazz.getSuperclass
		}
		result
	}
}
