<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%--
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
--%>
<html>
<head>
<title>test jsp</title>
<style type="text/css">
div{
    border: 2px solid blue;
    margin: 5;
    padding: 5;
}
span{
	border:1px solid #000;
	width: 50px;
	color:red;
}
</style>
</head>
<body>
    <h1>test jsp </h1>
    <h2>welcome: <span>${user }&nbsp;</span></h2>
    <h2>message from server: <span>${msg}&nbsp;</span></h2>
    <div>
        <code>
        &lt;%= "Hello,World"%&gt;
        </code>:
        <%= "Hello,World"%>
    </div>
    <div>
        <code>
        &lt;c:out value="hello,world"/&gt;
        </code>:
        <c:out value="hello,world"/>
    </div>
    <div>
        <code><pre>
&lt;%
List<String> list = new ArrayList<String>();
list.add("Hello");
list.add("World");
request.setAttribute("list", list);
%&gt

&lt;c:forEach var="it" items="${list}"&gt
 &lt;c:out value="\${it}"/&gt
&lt;/c:forEach&gt;
        </pre></code>
        :
        <%
            List<String> list = new ArrayList<String>();
            list.add("Hello");
            list.add("World");
            request.setAttribute("list", list);
        %>
        <c:forEach var="it" items="${list}">
             <c:out value="${it}"/>
        </c:forEach>
       
    </div>
    
    <div>
    <code>
    &lt;%@include file="hello.jsp"%&gt;
    </code>:
    <%@include file="hello.jsp"%>
    </div>
    
    <div>
    <code>
    &lt;jsp:include page="hello.jsp" flush="true"/&gt;
    </code>:
    <jsp:include page="hello.jsp" flush="true"/>
    </div>
    
    <div id="footer">footer
    </div>
</body>
</html>
