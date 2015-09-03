<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
  <jsp:include page="include/head.jsp"/>
  <title>Local Sport Meeting | Modification d'une catégorie</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>



<%

  String id = request.getParameter("id");

  if (id == null)
    id =(String) request.getAttribute("id");

%>


<form action="ControleurParticipant?action=modifier" id="formAddCategorie" method="post" onsubmit="return valider();">
  <p><label>Sport</label><select id="selectSport" name="selectSport">

  </select></p>
  <p><label>Libelle</label><input type="text" id="libelle" name="libelle"/></p>
  <p><label>URL</label><input type="text" id="libelleURL" name="libelleURL"></p>
  <input type="hidden" name="idParticipant" id="idParticipant">
  <input value="Modifier" type="submit"/>
</form>

</section>

<jsp:include page="include/footer.jsp"/>

<script>

  function objTempSport (idSport, libelleSport){
    this.idSport = idSport;
    this.libelle = libelleSport;
  }


  var arraySport = [
    <%
        for(Sport sport : new Loader().getSports()){
            %>

    new objTempSport("<%= sport.getId()%>",
            "<%= sport.getLibelle()%>"),
    <%
}
%>
  ];

  initialisationLabel(<%=id%>);

  function initialisationLabel(id){
    <%
    Long id1 = Long.parseLong(id);
    Participant TotoLeBuveurDessence= new Loader().getParticipant(id1);
    %>
    var select = document.getElementById('selectSport');
    select.length = 0;
    for (var i = 0 ; i< arraySport.length; i++)
    {
      select.options[i] = new Option(arraySport[i].libelle.toString(), arraySport[i].idSport.toString(), false, false);
      if (arraySport[i].idSport.toString() == "<%=TotoLeBuveurDessence.getIdSport().toString()%>")
      {
        select.selectedIndex = i ;
      }

    }
    var libelle = document.getElementById('libelle');
    var id = document.getElementById('idParticipant');
    var libelleURL = document.getElementById('libelleURL');
    libelle.value = "<%=TotoLeBuveurDessence.getLibelle()%>";
    id.value = "<%=TotoLeBuveurDessence.getId()%>";
    libelleURL.value = "<%=TotoLeBuveurDessence.getUrl()%>";
  }
</script>


<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>