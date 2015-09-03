<!DOCTYPE html>

<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.*" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting | Ajout d'une rencontre</title>
</head>

<body>
<jsp:include page="include/header.jsp"/>
    <form action="ControleurRencontre?action=ajouter" id="formAddSport" method="post">

        <p>
            <div class="lineaire">
                <label>Adresse</label>
                <input type="text" name="lieu" id="address"/>
            </div>

            <div class="lineaire">
                <label>Longitude</label>
                <input type="number" name="longitude" step="0.0000000000000001" id="lon"/>
            </div>

            <div class="lineaire">
                <label>Latitude</label>
                <input type="number" name="latitude" step="0.0000000000000001" id="lat"/>
            </div>
            <div style="clear:both"></div>
        </p>

        <div>
            <div id="mapContainer">
                <div id="mapDummy"></div>
                <div id="mapCanvas"></div>
            </div>
        </div>

        <p>
            <label>Date</label>
            <input id="d"type="date" name="date"/>
        </p>

        <p>
            <label>Heure</label>
            <input type="time" name="heure"/>
        </p>

        <p>
            <label>Sport</label>
            <select name="idSport" id="selectSport" onchange="updateSelectCat();updateSelectPart();">
                <option value="0">Sélectionner un sport</option>
                <%
                    for (Sport s : new Loader().getSports()) {
                %><%= "<option value='" + s.getId() + "'>" + s.getLibelle() + "</option>" %><%
                }
            %>
            </select>
        </p>

        <p>
            <label>Catégorie</label>
            <select name="idCat" id="selectCat" onchange="updateSelectComp()">
            </select>
        </p>

        <p>
            <label>Compétition</label>
            <select name="idComp" id="selectComp">
            </select>
        </p>

        <p>
            <label>Participant 1</label>
            <select name="idPart1" id="selectP1">
            </select>
        </p>

        <p>
            <label>Participant 2</label>
            <select name="idPart2" id="selectP2">
            </select>
        </p>

        <input value="Ajouter" type="submit"/>
    </form>

<jsp:include page="include/footer.jsp"/>

<script>
    function objTemp(idSport, idComp, idCat, libComp, libCat) {
        this.idSport = idSport;
        this.idComp = idComp;
        this.idCat = idCat;
        this.libComp = libComp;
        this.libCat = libCat;
    }
    var array = [
        <%
            for(Competition comp : new Loader().getCompetitions()){//par conséquent une cqtégories n'ayant pas de compétition ne sera pas afficher dans le select
          Categorie cat  = new Loader().getCategorieByComp(comp);
                %>
        new objTemp("<%= cat.getIdSport()%>",
                "<%= comp.getId() %>",
                "<%= comp.getIdCategorie() %>",
                "<%= comp.getLibelle() %>",
                "<%= cat.getLibelle() %>"),
        <%
    }
%>
    ];

    function part(idSport, idP, lib) {
        this.idSport = idSport;
        this.id = idP;
        this.lib = lib;
    }
    var arrayPart = [
        <%
            for(Participant part : new Loader().getParticipants()){
                %>
        new part("<%= part.getIdSport()%>",
                "<%= part.getId() %>",
                "<%= part.getLibelle() %>"),
        <%
    }
%>
    ];
    updateSelectCat();
    updateSelectComp();
    updateSelectPart();

    function updateSelectPart() {
        var selectSport = document.getElementById("selectSport");
        var selectP1 = document.getElementById("selectP1");
        var selectP2 = document.getElementById("selectP2");

        if (selectSport.selectedIndex == 0) {
            selectP1.options.length = 0;
            selectP2.options.length = 0;
            selectP1.options[0] = new Option("Aucun sport n'est sélèctionné", "0", true, false);
            selectP2.options[0] = new Option("Aucun sport n'est sélèctionné", "0", true, false);
        }else {
            selectP1.options.length = 0;
            selectP2.options.length = 0;
            selectP1.options[0] = new Option("Sélectionner un participant", "0", true, false);
            selectP2.options[0] = new Option("Sélectionner un participant", "0", true, false);
            var index = 1;
            for (var i = 0; i < arrayPart.length; i++) {
                if (selectSport.options[selectSport.selectedIndex].value.toString() == arrayPart[i].idSport.toString()) {
                    selectP1.options[index] = new Option(arrayPart[i].lib.toString(), arrayPart[i].id.toString(), false, false);
                    selectP2.options[index] = new Option(arrayPart[i].lib.toString(), arrayPart[i].id.toString(), false, false);
                    index ++;
                }
            }
        }
    }

        function updateSelectCat() {
        var selectCat = document.getElementById("selectCat");
        var selectSport = document.getElementById("selectSport");
        var selectComp = document.getElementById("selectComp");
        selectCat.options.length = 0;
        selectComp.options.length = 0;

        if (selectSport.selectedIndex != 0) {
            selectCat.options[0] = new Option("Sélectionner une catégorie", "0", true, false);
            var contenuSelect = [];
            var index = 1;
            for (var i = 0; i < array.length; i++) {
                if (selectSport.options[selectSport.selectedIndex].value.toString() == array[i].idSport.toString() && !((contenuSelect.indexOf(array[i].idCat.toString())) > -1)) {
                    selectCat.options[index] = new Option(array[i].libCat.toString(), array[i].idCat.toString(), false, false);
                    contenuSelect[index] = array[i].idCat.toString();
                    index++;
                }
            }
            updateSelectComp();
        } else {
            selectCat.options[0] = new Option("Aucun sport n'est sélèctionné", "0", true, false);
            selectComp.options[0] = new Option("Aucune catégorie n'est sélèctionnée", "0", true, false);
        }
    }

    function updateSelectComp() {
        var selectCat = document.getElementById("selectCat");
        var selectComp = document.getElementById("selectComp");
        selectComp.options.length = 0;

        if (selectCat.selectedIndex != 0) {
            selectComp.options[0] = new Option("Sélectionner une compétition", "0", true, false);
            var index = 1;
            for (var i = 0; i < array.length; i++) {
                if (selectCat.options[selectCat.selectedIndex].value.toString() == array[i].idCat.toString()) {
                    selectComp.options[index] = new Option(array[i].libComp.toString(), array[i].idComp.toString(), false, false);
                    index++;
                }
            }
        } else {
            selectComp.options[0] = new Option("Aucune catégorie n'est sélèctionnée", "0", true, false);
        }
    }
</script>
<jsp:include page="include/script.jsp"/>

<script src="js/locationpicker.jquery.js"></script>
<script>
    $('#mapCanvas').locationpicker({
        location: {latitude: 45.77722199999999, longitude: 3.0870250000000397},
        radius: 0,
        scrollwheel: true,
        zoom: 15,
        inputBinding: {
            latitudeInput: $('#lat'),
            longitudeInput: $('#lon'),
            locationNameInput: $('#address')
        },
        enableAutocomplete: true,
        enableReverseGeocode: true,
        onlocationnotfound: function(locationName){
            alert('Attention, cette localisation n\'existe pas.')
        }
    });
</script>

</body>
</html>