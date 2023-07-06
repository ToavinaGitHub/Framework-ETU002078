<%@ page import="java.util.Vector" %><%--
  Created by IntelliJ IDEA.
  User: toavina
  Date: 2023-04-03
  Time: 15:20
  To change this template use File | Settings | File Templates.
--%>
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
