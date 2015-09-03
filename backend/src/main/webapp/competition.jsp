<%--
  Created by IntelliJ IDEA.
  User: romunuera
  Date: 05/12/2014
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="java.util.*" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition" %>
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

<header class="page-header">

  <div class="filtreSport lineaire">
  <p>
    <label>Sport</label>
    <select name="idSport" id="selectSport" onchange="updateListCategorie();updateListCompetition();">
      <option value="0">Tous</option>
      <%        for (Sport s : new Loader().getSports()) {      %>
        <%= "<option value='" + s.getId() + "'>" + s.getLibelle() + "</option>" %><%
      }
    %>

    </select>
  </p>
  </div>
  <div class="filtreCategorie lineaire">
  <p>
    <label>Catégorie</label>
    <select name="idCat" id="selectCat" onchange="updateListCompetition();">
      <option value="0">Tous</option>
      <%
        for (Categorie c : new Loader().getCategories())
        {
          %><%="<option value='" + c.getId() + "'>" + c.getLibelle() + "</option>"%><%
        }
      %>
    </select>
  </p>
  </div>
  <div style="clear:both"></div>
</header>

  <div id="Toto-la-terreur">
    <table id="tab">
    </table>
  </div>

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

  function objTempCompetition (idCompetition, idCategorie, libelleComp){
    this.idCompetition = idCompetition;
    this.idCategorie = idCategorie;
    this.libelle = libelleComp;
  }


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

  var arrayCompetition = [
    <%
        for(Competition competition : new Loader().getCompetitions()){
            %>

    new objTempCompetition("<%= competition.getId()%>",
            "<%= competition.getIdCategorie()%>",
            "<%= competition.getLibelle()%>"),
    <%
}
%>
  ];

  initialisation();
  function initialisation() {
    var table = document.getElementById('tab');
    var parentnode = table.parentNode;
    parentnode.removeChild(table);
    var newTable = document.createElement('table');
    newTable.setAttribute('id', 'tab');
    for(var i = 0 ; i< arrayCompetition.length; i++)
    {
      newTable = tableau(arrayCompetition[i], newTable);
    }
    parentnode.appendChild(newTable);
  }


  function updateListCategorie(){
    var selectCat = document.getElementById("selectCat");
    var selectSport = document.getElementById("selectSport");
    var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();


    selectCat.options.length = 1;
    var indexSelectSport = 1;
    if (idSelectSport != "0")
    {
      for (var i = 0; i < arrayCat.length; i++)
      {
        if (idSelectSport ==arrayCat[i].idSport.toString())
        {
          selectCat.options[indexSelectSport] = new Option(arrayCat[i].libelle.toString(), arrayCat[i].idCat.toString(), false, false);
          indexSelectSport++;
        }
      }
    }
    else
    {
      for (var i = 0; i < arrayCat.length; i++)
      {
          selectCat.options[indexSelectSport] = new Option(arrayCat[i].libelle.toString(), arrayCat[i].idCat.toString(), false, false);
          indexSelectSport++;
      }
    }
  }

  function updateListCompetition(){
    var selectCat = document.getElementById("selectCat");
    var selectSport = document.getElementById("selectSport");
    var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();
    var idSelectCat = selectCat.options[selectCat.selectedIndex].value.toString();

    var table = document.getElementById('tab');
    var parentnode = table.parentNode;
    parentnode.removeChild(table);

    var newTable = document.createElement('table');
    newTable.setAttribute('id', 'tab');

    var indexTableau = 1;

    if (idSelectCat == '0')
    {
      if (idSelectSport != '0')
      {
        for (var j = 0; j< arrayCat.length; j++)
        {
          if ( arrayCat[j].idSport.toString() == idSelectSport)
          {
            for (var i = 0 ; i< arrayCompetition.length; i++)
            {
              if (arrayCompetition[i].idCategorie.toString() == arrayCat[j].idCat.toString())
              {
                newTable = tableau(arrayCompetition[i], newTable);
              }
            }
          }
        }
      }else
      {
        for (var i = 0 ; i< arrayCompetition.length; i++)
        {
          newTable = tableau(arrayCompetition[i], newTable);
        }
      }


    }
    else
    {
      for (var i = 0; i< arrayCompetition.length; i++)
      {
        if ( idSelectCat == arrayCompetition[i].idCategorie.toString())
        {
          newTable = tableau(arrayCompetition[i], newTable);
        }
      }
    }

    parentnode.appendChild(newTable);
  }

  function tableau (competition, newTable) {
    var tr = document.createElement('tr');
    var tdcolonne1 = document.createElement('td');
    var tdcolonne2 = document.createElement('td');
    var lnboutonSupp = document.createElement('a');
    lnboutonSupp.setAttribute('class', 'buttonTableau');
    var lnboutonModif = document.createElement('a');
    lnboutonModif.setAttribute('class', 'buttonTableau');
    lnboutonModif.setAttribute('onclick','document.location.href = \'ControleurCompetition?action=modifier&id='+competition.idCompetition+'\'');
    lnboutonSupp.setAttribute('onclick','alertSupprr('+competition.idCompetition+')');
    lnboutonModif.innerHTML = "Modifier";
    lnboutonSupp.innerHTML = "Supprimer";
    tdcolonne2.appendChild(lnboutonModif);
    tdcolonne2.appendChild(lnboutonSupp);
    tdcolonne1.innerHTML = competition.libelle.toString();
    tr.appendChild(tdcolonne1);
    tr.appendChild(tdcolonne2);
    newTable.appendChild(tr);
    return newTable;
  }


</script>
<script> function alertSupprr(id) {
  if (confirm("Attention, vous êtes sur le point de supprimer une compétition.\nSi vous continuez toutes Rencontres dépendant de cette compétition sera également supprimée.\nVoulez vous continuez ?")) {
    document.location.href = "ControleurCompetition?action=supprimer&id=" + id;
  }
} </script>


<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>