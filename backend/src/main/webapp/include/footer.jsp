</section>

<footer id="footer" class="clearfix">

    <div class="container">

            <nav id="footer-nav" class="clearfix">

                <ul>
                    <li><a href="../index.jsp">Home</a></li>
                    <li><a href="sport.jsp">Sport</a></li>
                    <li><a href="../categorie.jsp">Categorie</a></li>
                    <li><a href="../competition.jsp">Competition</a></li>
                    <li><a href="../participant.jsp">Participant</a></li>
                    <li><a href="../rencontre.jsp">Rencontre</a></li>
                    <%
                        String login = (String) session.getAttribute("login");
                        Boolean connected = false;
                        if (login != null && login != "") {
                            connected = true;
                        }

                        if (connected) {%>
                    <li><a href="Controleur?action=deconnexion">Déconnexion</a></li>
                    <li><a href="../changePassword.jsp">Modifier mot de passe</a></li>
                    <%
                        }
                    %>
                    <li><a href="../_ah/api/explorer">API explorer</a></li>
                    <li><a href="https://console.developers.google.com/project/local-sport-meeting">Dev console</a></li>
                </ul>

            </nav>
            <!-- end #footer-nav -->

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