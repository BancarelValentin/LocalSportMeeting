<!DOCTYPE html>

<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting | Ajout d'un participant</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>




    <form action="ControleurParticipant?action=ajouter" id="formAddSport" method="post" onsubmit="return valider();">
        <p><label>Sport</label><select name="idSport">
            <option value="0">Sélectionner un sport</option>
            <%
            for(Sport s : new Loader().getSports()){
%>                <%= "<option value='"+s.getId()+"'>"+s.getLibelle()+"</option>" %><%
            }
    %>
        </select></p>
        <p><label>Nom</label><input type="text" name="nom"/></p>
        <p><label>Site web</label><input type="url" name="url"/></p>
        <input value="Ajouter" type="submit"/>
    </form>


<jsp:include page="include/footer.jsp"/>

<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>