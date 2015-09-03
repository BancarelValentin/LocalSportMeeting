<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>

<form action="Controleur?action=changePassword" method="post">
    <p>
        <label>Ancien mot de passe</label>
        <input type="password" name="old"/>
    </p>
    <p>
        <label>Nouveau mot de passe</label>
        <input type="password" name="new"/>
    </p>
    <p>
        <label>Vérification</label>
        <input type="password" name="check"/>
    </p>
    <input value="Modifier" type="submit"/>
</form>

<jsp:include page="include/footer.jsp"/>
<jsp:include page="include/script.jsp"/>
</body>
</html>