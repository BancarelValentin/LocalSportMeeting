<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition" %>
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


<form action="ControleurCompetition?action=modifier" id="formAddCategorie" method="post" onsubmit="return valider();">
  <p><label>Sport</label><select id="selectSport" name="selectSport" onchange="updateListCategorie();">
      <option value="0">Tous</option>
  </select></p>
  <p><label>Categorie</label><select id="selectCategorie" name="selectCategorie">

  </select></p>
  <p><label>Libelle</label><input type="text" id="libelle" name="libelle"/></p>
  <input type="hidden" name="idCompetition" id="idCompetition">
  <input value="Modifier" type="submit"/>
</form>

</section>

<jsp:include page="include/footer.jsp"/>

<script>


  function objTempCat(idSport, idCat, libelleCat) {
    this.idSport = idSport;
    this.idCat = idCat;
    this.libelle = libelleCat;
  }
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

  var arrayCat = [
    <%
        for(Categorie c : new Loader().getCategories()){
            %>

    new objTempCat("<%= c.getIdSport() %>",
            "<%= c.getId()%>",
            "<%= c.getLibelle()%>"),
    <%
}
%>
  ];


  initialisationLabel(<%=id%>);


  function initialisationLabel(id){
    <%
    Long id1 = Long.parseLong(id);
    Competition competition= new Loader().getCompetition(id1);
    %>
    var select = document.getElementById('selectSport');
    var indexSelectSport = 1;
    var indexSelectCategorie = 0;
    var selectCat = document.getElementById('selectCategorie');
    select.length = 1;
    selectCat.length = 0;
    var sport;
    for (var i = 0; i<arraySport.length; i++) {
      select.options[indexSelectSport] = new Option(arraySport[i].libelle.toString(), arraySport[i].idSport.toString(), false, false);
      for (var j = 0; j < arrayCat.length; j++) {
        if (arrayCat[j].idSport.toString() == arraySport[i].idSport.toString() && arrayCat[j].idCat.toString() == "<%=competition.getIdCategorie().toString()%>") {
          sport = arraySport[i];
          select.selectedIndex = indexSelectSport;
        }


      }
      indexSelectSport++;
    }

    for (var i = 0; i < arrayCat.length; i++)
    {
      if (arrayCat[i].idSport.toString() == sport.idSport.toString())
      {
        selectCat.options[indexSelectCategorie] = new Option(arrayCat[i].libelle.toString(), arrayCat[i].idCat.toString(), false, false);
        if (arrayCat[i].idCat.toString() == "<%=competition.getIdCategorie().toString()%>")
        {
          selectCat.selectedIndex = indexSelectCategorie;
        }
        indexSelectCategorie ++;
      }
    }

    var libelle = document.getElementById('libelle');
    var id = document.getElementById('idCompetition');
    libelle.value = "<%=competition.getLibelle()%>";
    id.value = "<%=competition.getId()%>";
  }

  function updateListCategorie() {
    var selectCat = document.getElementById("selectCategorie");
    var selectSport = document.getElementById("selectSport");
    var index = 1;
    selectCat.options.length = 0;

    if (selectSport.selectedIndex != 0) {
      selectCat.options[0] = new Option("Sélectionner une catégorie", "0", true, false);
      for (var i = 0; i < arrayCat.length; i++) {
        if (selectSport.options[selectSport.selectedIndex].value.toString() == arrayCat[i].idSport.toString()) {
          selectCat.options[index] = new Option(arrayCat[i].libelle.toString(),
                  arrayCat[i].idCat.toString(), false, false);
          index++;
        }
      }
    } else {
      selectCat.options[0] = new Option("Aucun sport n'est sélèctionner", "0", true, false);
    }
  }


</script>


<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>