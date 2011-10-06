<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<html>
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>404 - 请求路径不存在</title>
</head>
<body>
    <div><h1>404 page</h1></div>
    <div>
        <span>请求路径<%= request.getRequestURI() %>不存在!</span>
    </div>
</body>
</html>
