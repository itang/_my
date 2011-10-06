import dubious.*

import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;
import org.scribe.utils.*

import java.util.Scanner
import java.lang.System
import java.util.List
import java.lang.Class

class QQWeiboApi < DefaultApi10a
	def getAccessTokenEndpoint
		"https://open.t.qq.com/cgi-bin/access_token"
	end
	 
	def getRequestTokenEndpoint
		"https://open.t.qq.com/cgi-bin/request_token"
	end
  
	def getAuthorizationUrl(requestToken:Token)
		"https://open.t.qq.com/cgi-bin/authorize?oauth_token=#{requestToken.getToken}"
	end
end

class SinaWeiboApi < DefaultApi10a
	def getAccessTokenEndpoint
		"http://api.t.sina.com.cn/oauth/access_token"
	end
	 
	def getRequestTokenEndpoint
		"http://api.t.sina.com.cn/oauth/request_token"
	end
  
	def getAuthorizationUrl(requestToken:Token)
		"http://api.t.sina.com.cn/oauth/authorize?oauth_token=#{requestToken.getToken}"
	end
end

class Sina
	@@service = OAuthService(nil)
	def self.getService
		returns OAuthService
		@@service ||= ServiceBuilder.new.provider(SinaWeiboApi.class).apiKey('4265731367').apiSecret('5c75685b841fadef451bceb1f087c7f1').
		callback('http://localhost:8080/callback').
		build()
	end 
end

class CallbackController < ApplicationController
	def doGet(req, reps)
		puts 'callback get'
		oauth_verifier = req.getParameter("oauth_verifier") || ""
		service = Sina.getService
		requestToken = Token(req.getSession.getAttribute("requestToken"))
		puts("And paste the verifier here");

   		verifier = Verifier.new(oauth_verifier);
		puts "verifier"

		# Trade the Request Token and Verfier for the Access Token
		puts("Trading the Request Token for an Access Token...")
		accessToken = service.getAccessToken(requestToken, verifier)
		puts("Got the Access Token!")
		puts("(if your curious it looks like this: #{ accessToken}")
		puts ""

		# Now let's go and ask for a protected resource!
		puts("Now we're going to access a protected resource...")
		message =  req.getSession.getAttribute("message") || "test"
		map = {"status" => message }
		url = URLUtils.appendParametersToQueryString("http://api.t.sina.com.cn/statuses/update.json", map)
		request = OAuthRequest.new(Verb.POST, url)
		
		service.signRequest(accessToken, request)
		response = request.send();
		puts("Got it! Lets see what we found...")
		puts("")
		puts(response.getBody());

		puts ""
	end
end

class IndexController < ApplicationController
	# mapping: /
	def doGet(request, response)
		render_template index_erb
	end

	
	def doPost(req, reps)
		set_params Params.new(req)
	
		puts begin 
			msg = params.get("message")
			msg || "NO:"
		end
		
		req.getSession.setAttribute("message", request.getParameter("message") || "test")
		
		service = Sina.getService
		puts("=== LinkedIn's OAuth Workflow ===");
		puts ""

		# Obtain the Request Token
		puts("Fetching the Request Token...")
		
		requestToken = service.getRequestToken()
		puts("Got the Request Token!");
		puts ""

		puts("Now go and authorize Scribe here:");
		url = service.getAuthorizationUrl(requestToken)
		puts(url)
		
		req.getSession.setAttribute("requestToken", requestToken)
		
		response.sendRedirect("#{url}&oauth_callback=http://localhost:8080/callback")
	 
	end
	
	
	# render templates
	def_edb(index_erb, 'views/index/index.html.erb')
end 
