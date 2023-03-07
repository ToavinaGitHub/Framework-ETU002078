<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des étudiants</title>
</head>
<body>
<h1>Liste des étudiants</h1>
<table>
    <thead>
    <tr>
        <th>ETU</th>
        <th>Nom</th>
        <th>Prenom</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>ETU002078</td>
        <td>Razakarivony</td>
        <td>Toavina</td>
        <td>
            <a href="FrontServlet/update?id=1">Editer</a>
            <a href="FrontServlet/delete?id=1">Supprimer</a>
        </td>
    </tr>
    </tbody>
</table>
<h2>Ajouter un nouvel étudiant</h2>
<form method="post" action="FrontServlet/create">
    <label for="etu">Etu:</label>
    <input type="text" id="etu" name="etu"><br>
    <label for="nom">Nom:</label>
    <input type="text" id="nom" name="nom"><br>
    <label for="prenom">Prenom:</label>
    <input type="text" id="prenom" name="prenom"><br>
    <input type="submit" value="Ajouter">
</form>

</body>
</html>