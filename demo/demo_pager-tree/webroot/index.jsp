<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>异步分页tree</title>
    <link rel="stylesheet" type="text/css" href="lib/ext-2.3.0/resources/css/ext-all.css"/>
    <link rel="stylesheet" type="text/css" href="PagingTreeLoader.css"/>
    <script src="lib/ext-2.3.0/adapter/ext/ext-base.js" type="text/javascript"></script>
    <script src="lib/ext-2.3.0/ext-all.js" type="text/javascript"></script>
    
    <script src="PagingTreeLoader.js" type="text/javascript"></script>
    <script src="bigtree.js" type="text/javascript"></script>
    <script type="text/javascript">
        Ext.BLANK_IMAGE_URL = 'lib/ext-2.3.0/resources/images/default/s.gif';
    </script>
</head>
<body>
    <div><h1>异步分页tree</h1>
        </div>
    <hr size="1" color="blue"/>
    <div id="tree-ct"></div>
    <div>
    参考:<a href="http://www.sencha.com/forum/showthread.php?45173-2.2-Ext.ux.tree.PagingTreeLoader" target="_blank">http://www.sencha.com/forum/showthread.php?45173-2.2-Ext.ux.tree.PagingTreeLoader</a>
    </div>
</body>
</html>
