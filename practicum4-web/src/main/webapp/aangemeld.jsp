<%@ page import="org.json.JSONObject" %>
<%@ page import="java.util.Iterator" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    JSONObject json = (JSONObject) session.getAttribute("persoon");

    if(json == null)
    {
        //response.sendRedirect("error.jsp");
        request.getRequestDispatcher("error.jsp?titel=Niet%20aangemeld!&msg=Je%20kan%20deze%20pagina%20alleen%20maar%20bekijken%20als%20je%20bent%20aangemeld.").forward(request, response);
    }
    Iterator<String> t = json.keys();

    String imgURL = ((JSONObject) json.get("image")).getString("url");


    /**
     * Beschikbare velden:
     * image
     gender
     kind
     displayName
     verified
     url
     objectType
     isPlusUser
     emails
     urls
     name
     etag
     id
     circledByCount
     */
%>
<html>
<head>
    <title>Aangemeld</title>
</head>
<body>
<h1>Welkom <%=json.getString("displayName")%></h1>
<img src="<%=imgURL%>"/>



<!-- Alle elementen opsommen

<%

    while(t.hasNext()) {
        String element = t.next();
        %><%=element%>:&nbsp;<%=json.get(element)%>
</br>
<%
    }
%>
-->
        </body>
</html>
