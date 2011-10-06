<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Tutorial: bdLoad AMD 加载模块</title>
<%
	String cp = request.getContextPath();
%>
<!-- load bdLoad -->
<script src="<%=cp%>/resources/libs/bdload/require.js"></script>
<script type="text/javascript">
	var start = (new Date()).getTime();
	require({
		packages : [ {
			name : 'dojo',
			location : './resources/libs/dojo',
			main : 'lib/main-browser',
			lib : '.'
		}, {
			name : 'app',
			location : './resources/scripts',
			main : '',
			lib : '.'
		}, {
			name : 'thirdpaths',
			location : './resources/thirdpaths',
			main : '',
			lib : '.'
		} ],
		deps : [ "dojo", "app/hello_define", "thirdpaths/user_define" ],
		callback : function(dojo, hello, user) {
			dojo.byId("status").innerHTML = "The dojo package has been loaded.";
			dojo.byId("time").innerHTML = (((new Date()).getTime() - start) / 1000)
					+ "s";

			hello.sayHello("greeting", "Hello, world from hello module");

			_show(dojo, "greeting1", user.test());
		}
	});
	var i = 0;
	function _show(dojo, id, msg) {
		setTimeout(function() {
			dojo.byId(id).innerHTML += "<hr/>" + msg;
		}, 3000 * ++i);
	}
</script>
</head>
<body>
	<h1>Load Time</h1>
	<p id="time"></p>
	<h1>Status</h1>
	<p id="status">loading</p>

	<h1 id="greeting"></h1>
	<h1 id="greeting1"></h1>
</body>
</html>