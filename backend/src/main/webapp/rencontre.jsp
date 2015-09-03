<%--
  Created by IntelliJ IDEA.
  User: romunuera
  Date: 10/12/2014
  Time: 15:11
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant" %>
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
                <select name="idSport" id="selectSport" onchange="updateListSport();">
                    <option value="0">Tous</option>
                    <% for (Sport s : new Loader().getSports()) { %>
                    <%= "<option value='" + s.getId() + "'>" + s.getLibelle() + "</option>" %><%
                    }
                %>

                </select>
            </p>
        </div>
        <div class="filtreCategorie lineaire">
            <p>
                <label>Catégorie</label>
                <select name="idCat" id="selectCat" onchange="updateListCategorie();">
                    <option value="0">Tous</option>
                    <%
                        for (Categorie c : new Loader().getCategories()) {
                    %><%="<option value='" + c.getId() + "'>" + c.getLibelle() + "</option>"%><%
                    }
                %>
                </select>
            </p>
        </div>

        <div class="filtreCompetition lineaire" onchange="updateListCompetition();">
            <p>
                <label>Compétition</label>
                <select name="idComp" id="selectComp">
                    <option value="0">Tous</option>
                    <%
                        for (Competition competition : new Loader().getCompetitions()) {
                    %><%="<option value='" + competition.getId() + "'>" + competition.getLibelle() + "</option>"%><%
                    }
                %>
                </select>
            </p>
        </div>
        <div class="lineaire">
            <p>
                <label>&nbsp;</label>
                <button onclick="alertSupprrPasse();">Supprimer rencontres passées</button>
            </p>
        </div>
        <div style="clear:both"></div>
    </header>

<div id="Toto-la-terreur">
    <table id="tab">



    </table>


</div>

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

    function objTempCompetition (idCompetition, idCategorie, libelleComp){
        this.idCompetition = idCompetition;
        this.idCategorie = idCategorie;
        this.libelle = libelleComp;
    }

    function objTempRencontre (idRencontre , lieu, date, idParticipant1, idParticipant2, idCompetition, idSport){

        this.idRencontre = idRencontre;
        this.lieu = lieu;

        this.date = new Date(date);
        this.idParticipant1 = idParticipant1;
        this.idParticipant2 = idParticipant2;
        this.idCompetition = idCompetition;
        this.idSport = idSport;
    }

    function objTempParticipant (idParticipant, libelleParticipant, urlSite, idSport) {
        this.idParticipant = idParticipant;
        this.libelle = libelleParticipant;
        this.urlSite = urlSite;
        this.idSport = idSport;
    }

    var arrayRencontre = [
        <%
            for(Rencontre rencontre : new Loader().getRencontres()){
                %>

        new objTempRencontre("<%= rencontre.getId()%>",
                "<%= rencontre.getLieu()%>",
                "<%= rencontre.getDate()%>",
                "<%= rencontre.getIdParticipant1()%>",
                "<%= rencontre.getIdParticipant2()%>",
                "<%= rencontre.getIdCompetition()%>",
                "<%= rencontre.getIdSport()%>"),
        <%
    }
    %>
    ];


    var arrayParticipant = [
        <%
            for(Participant p : new Loader().getParticipants()){
                %>

        new objTempParticipant("<%= p.getId()%>",
                "<%= p.getLibelle()%>",
                "<%= p.getUrl()%>",
                "<%= p.getIdSport()%>"),
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

    var dateFormat = function () {
        var token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
                timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
                timezoneClip = /[^-+\dA-Z]/g,
                pad = function (val, len) {
                    val = String(val);
                    len = len || 2;
                    while (val.length < len) val = "0" + val;
                    return val;
                };

        // Regexes and supporting functions are cached through closure
        return function (date, mask, utc) {
            var dF = dateFormat;

            // You can't provide utc if you skip other args (use the "UTC:" mask prefix)
            if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
                mask = date;
                date = undefined;
            }

            // Passing date through Date applies Date.parse, if necessary
            date = date ? new Date(date) : new Date;
            if (isNaN(date)) throw SyntaxError("invalid date");

            mask = String(dF.masks[mask] || mask || dF.masks["default"]);

            // Allow setting the utc argument via the mask
            if (mask.slice(0, 4) == "UTC:") {
                mask = mask.slice(4);
                utc = true;
            }

            var _ = utc ? "getUTC" : "get",
                    d = date[_ + "Date"](),
                    D = date[_ + "Day"](),
                    m = date[_ + "Month"](),
                    y = date[_ + "FullYear"](),
                    H = date[_ + "Hours"](),
                    M = date[_ + "Minutes"](),
                    s = date[_ + "Seconds"](),
                    L = date[_ + "Milliseconds"](),
                    o = utc ? 0 : date.getTimezoneOffset(),
                    flags = {
                        d:    d,
                        dd:   pad(d),
                        ddd:  dF.i18n.dayNames[D],
                        dddd: dF.i18n.dayNames[D + 7],
                        m:    m + 1,
                        mm:   pad(m + 1),
                        mmm:  dF.i18n.monthNames[m],
                        mmmm: dF.i18n.monthNames[m + 12],
                        yy:   String(y).slice(2),
                        yyyy: y,
                        h:    H % 12 || 12,
                        hh:   pad(H % 12 || 12),
                        H:    H,
                        HH:   pad(H),
                        M:    M,
                        MM:   pad(M),
                        s:    s,
                        ss:   pad(s),
                        l:    pad(L, 3),
                        L:    pad(L > 99 ? Math.round(L / 10) : L),
                        t:    H < 12 ? "a"  : "p",
                        tt:   H < 12 ? "am" : "pm",
                        T:    H < 12 ? "A"  : "P",
                        TT:   H < 12 ? "AM" : "PM",
                        Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
                        o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
                        S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
                    };

            return mask.replace(token, function ($0) {
                return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
            });
        };
    }();

    // Some common format strings
    dateFormat.masks = {
        "default":      "ddd mmm dd yyyy HH:MM:ss",
        shortDate:      "m/d/yy",
        mediumDate:     "mmm d, yyyy",
        longDate:       "mmmm d, yyyy",
        fullDate:       "dddd, mmmm d, yyyy",
        shortTime:      "h:MM TT",
        mediumTime:     "h:MM:ss TT",
        longTime:       "h:MM:ss TT Z",
        isoDate:        "yyyy-mm-dd",
        isoTime:        "HH:MM:ss",
        isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
        isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
    };

    // Internationalization strings
    dateFormat.i18n = {
        dayNames: [
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
        ],
        monthNames: [
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        ]
    };

    // For convenience...
    Date.prototype.format = function (mask, utc) {
        return dateFormat(this, mask, utc);
    };

    initialisationRencontre();

    function updateListSport(){
        var selectCat = document.getElementById("selectCat");
        var selectSport = document.getElementById("selectSport");
        var selectComp = document.getElementById("selectComp");
        var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();

        selectComp.options.length = 1;
        selectCat.options.length = 1;
        var indexSelectComp = 1;
        var indexSelectSport = 1;
        if (idSelectSport != "0")
        {
            for (var i = 0; i < arrayCat.length; i++)
            {
                if (idSelectSport ==arrayCat[i].idSport.toString())
                {
                    selectCat.options[indexSelectSport] = new Option(arrayCat[i].libelle.toString(), arrayCat[i].idCat.toString(), false, false);
                    for (var j = 0 ; j < arrayCompetition.length; j++)
                    {
                        if (arrayCompetition[j].idCategorie.toString() == arrayCat[i].idCat.toString())
                        {
                            selectComp.options[indexSelectComp]= new Option(arrayCompetition[j].libelle.toString(), arrayCompetition[j].idCompetition, false, false);
                            indexSelectComp ++;
                        }
                    }

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
            for (var i = 0 ; i< arrayCompetition.length ; i++)
            {
                selectComp.options[indexSelectComp] = new Option(arrayCompetition[i].libelle.toString(), arrayCompetition[i].idCompetition.toString(), false, false);
                indexSelectComp ++;
            }
        }
        updateListRencontre(idSelectSport,0,0);
    }

    function updateListCategorie(){
        var selectCat = document.getElementById("selectCat");
        var selectComp = document.getElementById("selectComp");
        var idSelectCategorie = selectCat.options[selectCat.selectedIndex].value.toString();
        var selectSport = document.getElementById("selectSport");
        var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();

        selectComp.options.length = 1;
        var indexComp = 1;
        var indexSelectSport = 1;
        if (idSelectCategorie != 0)
        {
            for (var i = 0 ; i< arrayCompetition.length; i++)
            {
                if (arrayCompetition[i].idCategorie.toString() == idSelectCategorie)
                {
                    selectComp.options[indexComp]= new Option(arrayCompetition[i].libelle.toString(), arrayCompetition[i].idCompetition.toString(), false, false);
                    indexComp ++;
                }
            }
        }
        else
        {
            if (idSelectSport != '0')
            {
                for (var i = 0; i < arrayCat.length; i++)
                {
                    if (idSelectSport ==arrayCat[i].idSport.toString())
                    {
                        for (var j = 0 ; j < arrayCompetition.length; j++)
                        {
                            if (arrayCompetition[j].idCategorie.toString() == arrayCat[i].idCat.toString())
                            {
                                selectComp.options[indexComp]= new Option(arrayCompetition[j].libelle.toString(), arrayCompetition[j].idCompetition, false, false);
                                indexComp ++;
                            }
                        }
                    }
                }

            }
            else
            {
                for (var i = 0 ; i< arrayCompetition.length; i++)
                {
                    selectComp.options[indexComp]= new Option(arrayCompetition[i].libelle.toString(), arrayCompetition[i].idCompetition.toString(), false, false);
                    indexComp ++;
                }
            }

        }
        updateListRencontre(idSelectSport,idSelectCategorie,0);
    }


    function updateListCompetition ()
    {
        var selectCat = document.getElementById("selectCat");
        var selectComp = document.getElementById("selectComp");
        var idSelectCategorie = selectCat.options[selectCat.selectedIndex].value.toString();
        var selectSport = document.getElementById("selectSport");
        var idSelectSport = selectSport.options[selectSport.selectedIndex].value.toString();
        var idSelectComp = selectComp.options[selectComp.selectedIndex].value.toString();
        updateListRencontre(idSelectSport, idSelectCategorie, idSelectComp);
    }


    function updateListRencontre (idSport, idCategorie, idCompetition) {
        var table = document.getElementById('tab');
        var parentNode = table.parentNode;
        parentNode.removeChild(table);

        var newTable = document.createElement('table');
        newTable.setAttribute('id', 'tab');

        if (idCompetition != 0)
        {
            for (var i = 0; i< arrayRencontre.length; i++)
            {
                if (arrayRencontre[i].idCompetition.toString() == idCompetition)
                {
                    var participant = getParticipantsByRencontre(arrayRencontre[i].idParticipant1, arrayRencontre[i].idParticipant2);
                    newTable =tableau(participant[0], participant[1], newTable, arrayRencontre[i]);
                }
            }
        }
        else
        {
            if (idCategorie != 0)
            {
                for (var i = 0; i< arrayCompetition.length; i++)
                {
                    if (arrayCompetition[i].idCategorie.toString() == idCategorie)
                    {
                        for (var j = 0; j< arrayRencontre.length; j++)
                        {
                            if (arrayRencontre[j].idCompetition.toString() == arrayCompetition[i].idCompetition.toString())
                            {
                                var participant = getParticipantsByRencontre(arrayRencontre[j].idParticipant1, arrayRencontre[j].idParticipant2);
                                newTable =tableau(participant[0], participant[1], newTable, arrayRencontre[j]);
                            }
                        }
                    }
                }
            }
            else
            {

                if (idSport != 0)
                {
                    for (var i = 0; i< arrayRencontre.length; i++)
                    {
                        if (arrayRencontre[i].idSport.toString() == idSport)
                        {
                            var participant = getParticipantsByRencontre(arrayRencontre[i].idParticipant1, arrayRencontre[i].idParticipant2);
                            newTable =tableau(participant[0], participant[1], newTable, arrayRencontre[i]);
                        }
                    }
                }
                else
                {
                    for (var i = 0; i< arrayRencontre.length; i++)
                    {
                        var participant = getParticipantsByRencontre(arrayRencontre[i].idParticipant1, arrayRencontre[i].idParticipant2);
                        newTable = tableau(participant[0], participant[1], newTable, arrayRencontre[i]);
                    }
                }
            }
        }
        parentNode.appendChild(newTable);
    }

    function initialisationRencontre (){
        var table = document.getElementById('tab');
        for (var i = 0; i< arrayRencontre.length; i++)
        {
            var participant = getParticipantsByRencontre(arrayRencontre[i].idParticipant1, arrayRencontre[i].idParticipant2);
            table = tableau(participant[0], participant[1], table, arrayRencontre[i]);
        }
    }

    function tableau (participant1, participant2, newTable, rencontre)
    {
        var tr = document.createElement('tr');
        var tdcolonne1 = document.createElement('td');
        var tdcolonne2 = document.createElement('td');
        var lnboutonSupp = document.createElement('a');
        lnboutonSupp.setAttribute('class', 'buttonTableau');
        var lnboutonModif = document.createElement('a');
        lnboutonModif.setAttribute('class', 'buttonTableau');
        lnboutonModif.setAttribute('onclick','document.location.href = \'ControleurRencontre?action=modifier&id='+rencontre.idRencontre+'\'');
        lnboutonSupp.setAttribute('onclick','alertSupprr('+rencontre.idRencontre+')');
        lnboutonModif.innerHTML = "Modifier";
        lnboutonSupp.innerHTML = "Supprimer";
        tdcolonne2.appendChild(lnboutonModif);
        tdcolonne2.appendChild(lnboutonSupp);
        tdcolonne1.innerHTML = participant1.libelle +" contre "+ participant2.libelle+ " ("+ rencontre.date.format('dd/mm/yyyy')+")";
        tr.appendChild(tdcolonne1);
        tr.appendChild(tdcolonne2);
        newTable.appendChild(tr);
        return newTable;
    }

    function getParticipantsByRencontre (idParticipant1, idParticipant2){

        var arrayPart = new Array();
        var index = 0;
        for (var i = 0 ; i< arrayParticipant.length; i++)
        {
            if (arrayParticipant[i].idParticipant.toString() == idParticipant1.toString()|| arrayParticipant[i].idParticipant.toString() == idParticipant2.toString() )
            {
                arrayPart[index] = arrayParticipant[i];
                index++;
            }
        }
        return arrayPart;


    }


</script>
<script>
    function alertSupprr(id) {
        if (confirm("Attention, vous êtes sur le point de supprimer une rencontre.\nVoulez vous continuez ?")) {
            document.location.href = "ControleurRencontre?action=supprimer&id=" + id;
        }
    }
    function alertSupprrPasse() {
        if (confirm("Attention, vous êtes sur le point de supprimer toutes les rencontres passées.\nVoulez vous continuez ?")) {
            document.location.href = "ControleurRencontre?action=" +
            "supprimerToutesRencontres";
        }
    }
</script>


<script src="https://apis.google.com/js/client.js?onload=init"></script>
<jsp:include page="include/script.jsp"/>
</body>
</html>