<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>dojo quick start</title>
    <link rel="stylesheet" type="text/css" href="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dijit/themes/claro/claro.css"/>
    <script src="http://ajax.googleapis.com/ajax/libs/dojo/1.5/dojo/dojo.xd.js" djConfig="parseOnLoad: true"></script>
    <script type="text/javascript">
      dojo.require("dojo.data.ItemFileReadStore");
      dojo.require("dijit.Tree");

      dojo.addOnLoad(function() {
	      var store = new dojo.data.ItemFileReadStore({
		      url: "menu.json"
		  });

	      var treeModel = new dijit.tree.ForestStoreModel({
		      store: store,
		      query: {
			  "type": "form"
		      },
		      rootId: "root",
		      rootLabel: "内容",
		      childrenAttrs: ["children"]
		  });

	      var menuTree = new dijit.Tree({model: treeModel}, "treeOne");

	      dojo.connect(menuTree, "onClick", function(item){
	         var url = store.getValue(item, "url");
		 if(url){
	             window.open(url, "");
		 }
              });

      });
    </script>
</head>
<body class="claro">
  <div><h1>dojo quick start</h1></div>
  <div id="treeOne"/>
</body>
</html>