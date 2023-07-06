<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br>
<form method="post" action="oay">
    <input type="text" name="idEtudiant" value="1">
    <input type="text" name="nom" value="az">
    <input type="text" name="prenom" value="erty">
    <input type="date" name="dtn">
    <label>male</label><input type="checkbox" value="1" name="sexe[]">
    <label>Tsy male</label><input type="checkbox"  value="0" name="sexe[]">
    <input type="submit" value="KITIO">
</form>
<a href="find?idEmp=1">Eto koa!!</a>
<a href="all">All</a>
<a href="envoyer?idEtudiant=99">Eto koa!!</a>
</body>
</html>