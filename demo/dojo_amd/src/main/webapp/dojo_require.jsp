<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Tutorial: dojo 加载模块</title>
<%
	String cp = request.getContextPath();
%>
<!-- load Dojo -->
<script type="text/javascript">
	//dojo 配置对象
	var dojoConfig  = { //djConfig deprecated
		//parseOnLoad: true,
		modulePaths : {
			"thirdpaths" : "../../thirdpaths"
		}
	};
</script>
<script src="<%=cp%>/resources/libs/dojo/dojo.js"></script>
<script type="text/javascript">
	dojo.require("dojo.fx");
	dojo.ready(function() {
		dojo.byId("greeting").innerHTML += ", from " + dojo.version;

		//注册
		dojo.registerModulePath("app", "../../scripts");//相对 dojo.js
		dojo.require("app.hello");
		_show("greeting2", app.hello.sayHello());

		dojo.require("thirdpaths.user"); //已在配置中声明模块路径
		user = thirdpaths.user;
		_show("greeting3", user.test());
	});
	
	var i = 0;
	function _show(id, msg) {
		setTimeout(function() {
			dojo.byId(id).innerHTML += "<hr/>" + msg;
		}, 3000 * ++i);
	}
</script>
</head>
<body>
	<h1 id="greeting">Hello</h1>
	<h1 id="greeting2"></h1>
	<h1 id="greeting3"></h1>
</body>
</html>