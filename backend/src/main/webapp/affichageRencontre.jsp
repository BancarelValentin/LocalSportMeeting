<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader" %>
<%@ page import="com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!--[if IE 7]><html class="ie7 no-js" lang="en"> <![endif]-->
<!--[if lte IE 8]><html class="ie8 no-js" lang="en"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--><html class="not-ie no-js" lang="en"> <!--<![endif]-->
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <meta charset="utf-8">

    <meta name="description" content="">
    <meta name="author" content="JANOWIEZ Thomas BANCAREL Valentin MUNUERA Romain">

    <!--[if !lte IE 6]><!-->
    <link rel="stylesheet" href="../css/style.css" media="screen"/>

    <link rel="stylesheet"
          href="//fonts.googleapis.com/css?family=Open+Sans:400,600,300,800,700,400italic|PT+Serif:400,400italic"/>

    <link rel="stylesheet" href="../css/fancybox.min.css" media="screen"/>

    <link rel="stylesheet" href="../css/video-js.min.css" media="screen"/>

    <link rel="stylesheet" href="../css/audioplayerv1.min.css" media="screen"/>
    <!--<![endif]-->

    <!--[if lte IE 6]>
    <link rel="stylesheet" href="//universal-ie6-css.googlecode.com/files/ie6.1.1.css"
          media="screen, projection">
    <![endif]-->

    <!-- HTML5 Shiv + detect touch events -->
    <script src="../js/modernizr.custom.js"></script>

    <!-- HTML5 video player -->
    <script src="../js/video.min.js"></script>
    <script>_V_.options.flash.swf = 'http://localhost/smartstart/js/video-js.swf';</script>
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
<header id="header" class="container clearfix">

    <a id="logo">
        <img src="../img/logo.png" alt="SmartStart">
    </a>

    <nav id="main-nav">
    </nav>
</header>
<section id="content" class="container clearfix">


    <%
        if (rencontre == null) {
    %> <p class="error"><strong>Erreur</strong> - Rencontre introuvable</p> <%
} else {
%>


    <h2 class="slogan align-center"><%= Loader.getParticipant(rencontre.getIdParticipant1()).getLibelle().toString() + " - " + Loader.getParticipant(rencontre.getIdParticipant2()).getLibelle().toString()%>
    </h2>

    <<h2>
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

</section>
<footer id="footer" class="clearfix">

    <div class="container">

        <nav id="footer-nav" class="clearfix">

            <ul>
            </ul>

        </nav>
        <!-- end #footer-nav -->

        <!-- TODO: Modifier adresse,tel,mail,legal,terms .... -->
        <ul class="contact-info">
            <li class="address">012 Some Street 63000 Clermont-Ferrand, France</li>
            <li class="phone">+33 (0)6 00 00 00 00</li>
            <li class="email"><a
                    href="mailto:contact@localsportmeeting.com">contact@companyname.com</a></li>
        </ul>
        <!-- end .contact-info -->


    </div>
    <!-- end .container -->

</footer>
<!-- end #footer -->

<footer id="footer-bottom" class="clearfix">

    <div class="container">

        <ul>
            <li>SmartStart &copy; 2012</li>
            <li><a href="#">Legal Notice</a></li>
            <li><a href="#">Terms</a></li>
        </ul>

    </div>
    <!-- end .container -->

</footer>
<!-- end #footer-bottom -->

<!--[if !lte IE 6]><!-->
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../js/jquery-1.7.1.min.js"><\/script>')</script>
<!--[if lt IE 9]>
<script src="../js/selectivizr-and-extra-selectors.min.js"></script> <![endif]-->
<script src="../js/respond.min.js"></script>
<script src="../js/jquery.easing-1.3.min.js"></script>
<script src="../js/jquery.fancybox.pack.js"></script>
<script src="../js/jquery.smartStartSlider.min.js"></script>
<script src="../js/jquery.jcarousel.min.js"></script>
<script src="../js/jquery.cycle.all.min.js"></script>
<script src="../js/jquery.isotope.min.js"></script>
<script src="../js/audioplayerv1.min.js"></script>
<script src="//maps.google.com/maps/api/js?sensor=false"></script>
<script src="js/jquery.gmap.min.js"></script>
<script src="../js/jquery.touchSwipe.min.js"></script>
<script src="../js/custom.js"></script>
<!--<![endif]-->
</body>
</html>