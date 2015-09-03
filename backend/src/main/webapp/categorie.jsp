<%--
  Created by IntelliJ IDEA.
  User: romunuera
  Date: 10/12/2014
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
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

<header class="page-header">

  <div class="filtreSport lineaire">
    <p>
      <label>Sport</label>
      <select name="idSport" id="selectSport" onchange="updateListCategorie();">
        <option value="0">Tous</option>
        <%
          for (Sport s : new Loader().getSports()) {
        %>                <%= "<option value='" + s.getId() + "'>" + s.getLibelle() + "</option>" %><%
        }
      %>

      </select>
    </p>
  </div>

  <div style="clear:both"></div>
</header>

  <div class="tableauResultat">
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


  initialisation();
  function initialisation() {

    var table = document.getElementById('tab');
    var parentnode = table.parentNode;
    parentnode.removeChild(table);

    var newTable = document.createElement('table');
    newTable.setAttribute('id', 'tab');

    for (var i = 0 ; i< arrayCat.length; i++)
    {
      newTable = tableau(arrayCat[i], newTable);
    }
    parentnode.appendChild(newTable);

  }

  function updateListCategorie(){
    var selectSport = document.getElementById("selectSport");
    var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();
    var indexSelectSport = 1;
    var table = document.getElementById('tab');
    var parentNode = table.parentNode;
    parentNode.removeChild(table);

    var newTable = document.createElement('table');
    newTable.setAttribute('id','tab');
    if (idSelectSport != '0')
    {
      for (var i = 0; i < arrayCat.length; i++)
      {
        if (idSelectSport ==arrayCat[i].idSport.toString())
        {
          newTable = tableau(arrayCat[i], newTable);
        }
      }
    }
    else
    {
      for (var i = 0; i < arrayCat.length; i++)
      {

        newTable = tableau(arrayCat[i], newTable);
      }
    }
    parentNode.appendChild(newTable);
  }


  function tableau (categorie, newTable){
    var tr = document.createElement('tr');
    var tdcolonne1 = document.createElement('td');
    var tdcolonne2 = document.createElement('td');
    var lnboutonSupp = document.createElement('a');
    lnboutonSupp.setAttribute('class', 'buttonTableau');
    var lnboutonModif = document.createElement('a');
    lnboutonModif.setAttribute('class', 'buttonTableau');
    lnboutonModif.setAttribute('onclick','document.location.href = \'ControleurCategorie?action=modifier&id='+categorie.idCat+'\'');
    lnboutonSupp.setAttribute('onclick','alertSupprr('+categorie.idCat+')');
    lnboutonModif.innerHTML = "Modifier";
    lnboutonSupp.innerHTML = "Supprimer";
    tdcolonne2.appendChild(lnboutonModif);
    tdcolonne2.appendChild(lnboutonSupp);
    tdcolonne1.innerHTML = categorie.libelle.toString();
    tr.appendChild(tdcolonne1);
    tr.appendChild(tdcolonne2);
    newTable.appendChild(tr);
    return newTable;
  }

  function alertSupprr(idCat) {
    if (confirm("Attention, vous êtes sur le point de supprimer une catégorie.\nSi vous continuez toutes Compéttions ou Rencontres dépendant de cette catégorie sera également supprimé.\nVoulez vous continuez ?")) {
      document.location.href = "ControleurCategorie?action=supprimer&id=" + idCat;
    }
  }
</script>
<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>