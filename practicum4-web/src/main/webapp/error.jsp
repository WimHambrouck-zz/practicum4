<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Probleem</title>
</head>
<body>
<% if(request.getParameter("titel") == null || request.getParameter("msg") == null)
{
    response.sendRedirect("index.html");
}
%>
<h1><%=request.getParameter("titel")%></h1>
<p><%=request.getParameter("msg")%></p>
<jsp:include page="index.html"/>
</body>
</html>
