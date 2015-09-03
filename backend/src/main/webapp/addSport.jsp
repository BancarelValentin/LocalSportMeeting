<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting | Ajout d'un sport 2</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>




    <form action="ControleurSport?action=ajouter" id="formAddSport" method="post" onsubmit="return valider();">
        <p><label>Libelle</label><input type="text" id="libelle" name="libelle"/></p>
        <input value="Ajouter" type="submit"/>
    </form>


<jsp:include page="include/footer.jsp"/>

<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>