import dubious.*
import javax.servlet.http.*

class ApplicationController < ActionController
	class Context
		def initialize(req: HttpServletRequest, resp: HttpServletResponse)
			@request = req
			@response = resp
		end
		
		def req; @request; end
		def resp; @response; end
	end
	
	def request
		Context(@@context_hold.get).req
	end
	
	def response
		Context(@@context_hold.get).resp
	end
	
	def before(req: HttpServletRequest, resp: HttpServletResponse)
		returns void
	end
	
	def after(req: HttpServletRequest, resp: HttpServletResponse)
		returns void
	end
	
	def service(req: HttpServletRequest, resp: HttpServletResponse)
		puts "invoke service "
		req.setCharacterEncoding "utf-8"
		resp.setCharacterEncoding "utf-8"
		@@context_hold ||= ThreadLocal.new
		@@context_hold.set Context.new(req, resp)
		
		before req, resp
		
		if request.getMethod == "GET"
			doGet(req, resp)
		else 
			doPost(req, resp)
		end
		
		after req, resp
	end
	
	def render_template(content: String)
		render_template(content, main_erb)
	end
	
	def render_template(content: String, layout: String)
		html = String.new(render(content, layout).getBytes("iso8859_1"), "utf-8")
		action_response(response, html)
	end
	
	def_edb(main_erb,  'views/layouts/application.html.erb')
end
