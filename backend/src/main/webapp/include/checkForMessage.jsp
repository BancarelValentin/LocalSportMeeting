<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Controleur.Controleur" %>
<%
  String success = (String) request.getAttribute(Controleur.SUCCESS);
  if (success != null) {
    %><%="<p class=\"success\">" + success + "</p>" %><%
  }

  String error = (String) request.getAttribute(Controleur.ERROR);
  if (error != null) {
      %><%="<p class=\"error\"><strong>Erreur</strong> - " + error + "</p>" %><%
  }

  String notice = (String) request.getAttribute(Controleur.WARN);
  if (notice != null) {
    %><%="<p class=\"notice\"><strong>Attention</strong> - " + notice + "</p>" %><%
  }

  String info = (String) request.getAttribute(Controleur.INFO);
  if (info != null) {
    %><%="<p class=\"info\"><strong>Attention</strong> - " + notice + "</p>" %><%
  }

  request.setAttribute(Controleur.WARN, null);
  request.setAttribute(Controleur.ERROR, null);
  request.setAttribute(Controleur.INFO, null);
  request.setAttribute(Controleur.SUCCESS, null);
%>