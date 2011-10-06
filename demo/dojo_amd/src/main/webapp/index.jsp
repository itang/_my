<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Tutorial: Hello Dojo!</title>
<!-- load Dojo -->
<script
	src="http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojo/dojo.xd.js"></script>
<script type="text/javascript">
	dojo.require("dojo.fx");
	dojo.ready(function() {
		dojo.byId("greeting").innerHTML += ", from " + dojo.version;
		alert("Dojo version " + dojo.version + " is loaded");

		setTimeout(function() {
			dojo.fx.slideTo({
				top : 100,
				left : 200,
				node : dojo.byId("greeting")
			});
		}, 5000);
	});
	dojo.addOnLoad(function() {
		alert("onload");
	});
</script>
</head>
<body>
	<h1 id="greeting">Hello</h1>
	<div></div>
	<div>
		<span><b><font></font>
		</b>
		</span>
		<ul>
			<li><a href="dojo_require.jsp">dojo_require.jsp</a>
			</li>
			<li><a href="dojo_bdload.jsp">dojo_bdload.jsp</a>
			</li>
		</ul>
	</div>
</body>
</html>