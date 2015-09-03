<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
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
<%
    String login = (String) session.getAttribute("login");
    Boolean connected = false;
    if (login != null && login != "") {
        connected = true;
    }
%>
<body>

<jsp:include page="include/header.jsp">
    <jsp:param name="connected" value="${connected}" />
</jsp:include>

<% if(!connected){ %>
    <form action="Controleur?action=auth" method="post">
    <input type="text" placeholder="Login" name="login">
    <input type="password" placeholder="Mot de passe" name="pass">
    <input type="submit" value="connexion">
    </form>
<% } %>

<jsp:include page="include/footer.jsp"/>
<jsp:include page="include/script.jsp"/>
</body>
</html>