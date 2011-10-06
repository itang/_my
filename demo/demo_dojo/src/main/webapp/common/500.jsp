<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  Throwable ex = null;
  if (exception != null)
    ex = exception;
  if (request.getAttribute("javax.servlet.error.exception") != null)
    ex = (Throwable) request.getAttribute("javax.servlet.error.exception");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta content="text/html;charset=UTF-8" http-equiv="Content-Type"/>
    <title>500 - 系统内部错误</title>
</head>

<body>
    <div><h1>系统发生内部错误.</h1></div>
    <div><a href="<c:url value="/"/>">返回首页</a></div>
</body>
</html>
