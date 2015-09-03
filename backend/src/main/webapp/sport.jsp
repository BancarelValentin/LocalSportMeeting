<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="java.util.*" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting MAJ</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>


<table id="tab">
    <% for (Sport s : new Loader().getSports()) { %>
    <tr>
        <td><%=s.getLibelle()%></td>
        <td>
            <a class="buttonTableau" onclick="document.location.href = 'ControleurSport?action=modifier&id=<%= s.getId()%>'">modifier</a>
            <a class="buttonTableau" onclick="alertSupprr(<%= s.getId() %>)">supprimer</a>
        </td>
    </tr>
    <% } %>

</table>








<jsp:include page="include/footer.jsp"/>
<script> function alertSupprr(idSport) {
    if (confirm("Attention, vous êtes sur le point de supprimer un sport.\nSi vous continuez toutes Catégories, Compéttions, Rencontres ou Participants dépendant de ce sport sera également supprimé.\nVoulez vous continuez ?")) {
        document.location.href = "ControleurSport?action=supprimer&id=" + idSport;
    }
} </script>
<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>
