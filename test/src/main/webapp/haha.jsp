<%@ page import="java.util.Vector" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Vector<Object> data = (Vector<Object>) request.getAttribute("lst");
%>
<html>
<head>
    <title>Hahaha</title>
</head>
<body>
<% for (int i = 0; i < data.size(); i++) { %>
<p><% out.println(data.get(i).toString()); %></p>
<% } %>
Voakitika!!!!
</body>
</html>
