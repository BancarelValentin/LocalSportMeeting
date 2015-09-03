<!DOCTYPE html>

<%@ page import="java.io.PrintWriter" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting | Ajout d'une competition</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>




    <form action="ControleurCompetition?action=ajouter" id="formAddSport" method="post"
          onsubmit="return valider();">
        <p>
            <label>Sport</label>
            <select name="idSport" id="selectSport" onchange="updateListCategorie();">
                <option value="0">Sélectionner un sport</option>
                <%
                    for (Sport s : new Loader().getSports()) {
                %>                <%= "<option value='" + s.getId() + "'>" + s.getLibelle() + "</option>" %><%
                }
            %>

            </select>
        </p>

        <!-- TO-DO: filtrerls comp en fonction du sport séléctionné -->
        <p>
            <label>Catégorie</label>
            <select name="idCat" id="selectCat">
                <option value="0">Aucun sport sélectionner</option>
            </select>
        </p>

        <p>
            <label>Libelle</label>
            <input type="text" name="libelle"/>
        </p>
        <input value="Ajouter" type="submit"/>
    </form>


<jsp:include page="include/footer.jsp"/>

<script>
    function objTemp(idSport, idCat, libelleCat) {
        this.idSport = idSport;
        this.idCat = idCat;
        this.libelleCat = libelleCat;
    }
    var arrayCat = [
        <%
            for(Categorie c : new Loader().getCategories()){
                %>

        new objTemp("<%= c.getIdSport() %>",
                "<%= c.getId()%>",
                "<%= c.getLibelle()%>"),
        <%
    }
%>
    ];
    updateListCategorie();
    function updateListCategorie() {
        var selectCat = document.getElementById("selectCat");
        var selectSport = document.getElementById("selectSport");
        var index = 1;
        selectCat.options.length = 0;

        if (selectSport.selectedIndex != 0) {
            selectCat.options[0] = new Option("Sélectionner une catégorie", "0", true, false);
            for (var i = 0; i < arrayCat.length; i++) {
                if (selectSport.options[selectSport.selectedIndex].value.toString() == arrayCat[i].idSport.toString()) {
                    selectCat.options[index] = new Option(arrayCat[i].libelleCat.toString(),
                            arrayCat[i].idCat.toString(), false, false);
                    index++;
                }
            }
        } else {
            selectCat.options[0] = new Option("Aucun sport n'est sélèctionner", "0", true, false);
        }
    }
</script>

<jsp:include page="include/script.jsp"/>
</body>
</html>