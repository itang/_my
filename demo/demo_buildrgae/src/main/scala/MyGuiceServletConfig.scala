package com.itang.buildrgae

import com.google.inject.Guice
import com.google.inject.servlet.GuiceServletContextListener
import com.google.inject.servlet.ServletModule

class MyGuiceServletConfig extends GuiceServletContextListener {
  override protected def getInjector = Guice.createInjector(new ServletModule(){
	 override protected  def configureServlets() {
       serve("/*").by(classOf[HelloServlet])
     }
	})
}