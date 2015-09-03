<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
  <jsp:include page="include/head.jsp"/>
  <title>Local Sport Meeting | Modification Sport</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>


<%
  String id = request.getParameter("id");

%>


<form action="ControleurSport?action=modifier" id="formAddSport" method="post"  onsubmit="return valider();">
  <p><label>Libelle</label><input  type="text" id="libelle" name="libelle"/></p>
  <input type="hidden" id="id" name="id"/>
  <input value="Modifier" type="submit"/>
</form>




<jsp:include page="include/footer.jsp"/>

<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
<script>
  getSport(<%=id%>);
  function getSport(id)
  {
    var libelle = document.getElementById('libelle');
    var id = document.getElementById('id');
    <%
    Long id1 = Long.parseLong(id);
     Sport s = new Loader().getSport(id1);
      %>
    libelle.value = "<%= s.getLibelle()%>";
    id.value = "<%= s.getId()%>";

  }
</script>


</body>
</html>