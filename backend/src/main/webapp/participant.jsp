<!DOCTYPE html>

<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="java.util.List" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre" %>
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

<%
    List<Sport> listSport = Loader.getSports();
    List<Participant> listPart = Loader.getParticipants();
%>

<header class="page-header">
    <div class="lineaire">
        <p>
            <label>Sport</label>
            <select id="selectSport" onchange="updateListPart()">
                <option value="0">Tous</option>
                <% for (Sport s : listSport) {%>
                <option value="<%=s.getId()%>"><%=s.getLibelle()%>
                </option>
                <% } %>
            </select>
        </p>
    </div>
    <div style="clear:both"></div>
</header>

<table id="tab"></table>


<jsp:include page="include/footer.jsp"/>

<script>
    function participant(idSport, id, libelle, url) {
        this.idSport = idSport;
        this.id = id;
        this.libelle = libelle;
    }

    var array = [
        <%
            for(Participant p : Loader.getParticipants()){
                %>
        new participant("<%= p.getIdSport()%>",
                "<%= p.getId() %>",
                "<%= p.getLibelle() %>"
        ),
        <%
    }
%>
    ];

    initialisation();
    function initialisation () {
        var table = document.getElementById('tab');
        var parentnode = table.parentNode;
        parentnode.removeChild(table);
        var newTable = document.createElement('table');
        newTable.setAttribute('id', 'tab');
        for (var i = 0 ; i < array.length ; i++)
        {
            newTable = tableau(array[i], newTable);
        }
        parentnode.appendChild(newTable);
    }

    function updateListPart() {
        var selectSport = document.getElementById("selectSport");
        var idSport = selectSport.options[selectSport.selectedIndex].value.toString();
        var table = document.getElementById('tab');
        var parentnode = table.parentNode;
        parentnode.removeChild(table);

        var newTable = document.createElement('table');
        newTable.setAttribute('id', 'tab');
        for (var i = 0; i < array.length; i++) {
            if (array[i].idSport == idSport || idSport == 0) {
                newTable = tableau(array[i], newTable);
            }
        }
        parentnode.appendChild(newTable);
    }


    function tableau (participant, newTable)
    {
        var tr = document.createElement('tr');
        var tdcolonne1 = document.createElement('td');
        var tdcolonne2 = document.createElement('td');
        var lnboutonSupp = document.createElement('a');
        lnboutonSupp.setAttribute('class', 'buttonTableau');
        var lnboutonModif = document.createElement('a');
        lnboutonModif.setAttribute('class', 'buttonTableau');
        lnboutonModif.setAttribute('onclick','document.location.href = \'ControleurParticipant?action=modifier&id='+participant.id+'\'');
        lnboutonSupp.setAttribute('onclick','alertSupprr('+participant.id+')');
        lnboutonModif.innerHTML = "Modifier";
        lnboutonSupp.innerHTML = "Supprimer";
        tdcolonne2.appendChild(lnboutonModif);
        tdcolonne2.appendChild(lnboutonSupp);
        tdcolonne1.innerHTML = participant.libelle.toString();
        tr.appendChild(tdcolonne1);
        tr.appendChild(tdcolonne2);
        newTable.appendChild(tr);
        return newTable;
    }

    function alertSupprr(idPart) {
        if (confirm("Attention, vous êtes sur le point de supprimer un participant.\nSi vous continuez toutes Rencontres auquel il participe sera également supprimées.\nVoulez vous continuez ?")) {
            document.location.href = "ControleurParticipant?action=supprimer&id=" + idPart;
        }
    }
</script>
<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>