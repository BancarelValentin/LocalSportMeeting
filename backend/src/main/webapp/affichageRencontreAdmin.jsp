<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <jsp:include page="include/head.jsp"/>
    <title>Local Sport Meeting</title>

    <%
        String id = (String) request.getParameter("id");
        Rencontre rencontre = null;
        Calendar cal = Calendar.getInstance();
        if (id != null) {
            rencontre = Loader.getRencontre(Long.valueOf(id));
            if (rencontre != null) {
                cal.setTime(rencontre.getDate());%>
    <script src="https://maps.googleapis.com/maps/api/js"></script>
    <script>
        function initialize() {
            var myLatLng = new google.maps.LatLng(<%= rencontre.getLongitude().toString()%>, <%= rencontre.getLatitude().toString()%>);
            var mapCanvas = document.getElementById('mapCanvas');
            var mapOptions = {
                center: myLatLng,
                zoom: 15,
                mapTypeId: google.maps.MapTypeId.ROADMAP
            };
            var map = new google.maps.Map(mapCanvas, mapOptions);

            var marker = new google.maps.Marker({
                position: myLatLng,
                map: map,
                title: '<%= rencontre.getLieu().toString()%>'
            });
        }

        google.maps.event.addDomListener(window, 'load', initialize);

    </script>
    <%
            }
        }
    %>
</head>

<body>
<jsp:include page="include/header.jsp"/>

        <%
        if (rencontre == null) {
    %> <p class="error"><strong>Erreur</strong> - Rencontre introuvable</p> <%
} else {
%>


    <h2 class="slogan align-center"><%= Loader.getParticipant(rencontre.getIdParticipant1()).getLibelle().toString() + " - " + Loader.getParticipant(rencontre.getIdParticipant2()).getLibelle().toString()%>
    </h2>

    <h2>
        Lieu: <%= rencontre.getLieu().toString()%>
        le <%= cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) %>
        à <%=String.format("%02d", cal.get(Calendar.HOUR)) + ":" + String.format("%02d", cal.get(Calendar.MINUTE))%>
    </h2>


    <div>
        <div id="mapContainer">
            <div id="mapDummy"></div>
            <div id="mapCanvas"></div>
        </div>
    </div>

        <%

        }
    %>

    <jsp:include page="include/footer.jsp"/>
    <jsp:include page="include/script.jsp"/>
</body>
</html>