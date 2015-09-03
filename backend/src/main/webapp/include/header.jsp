<header id="header" class="container clearfix">

    <a href="../index.jsp" id="logo">
        <img src="../img/logo.png" alt="SmartStart">
    </a>

    <%
        String login = (String) session.getAttribute("login");
        Boolean connected = false;
        if (login != null && login != "") {
            connected = true;
        }

        if (connected) {%>
    <jsp:include page="nav.jsp"/>
    <%
        }
    %>
</header>

<%
    String requestUrl = request.getRequestURL().toString();

    if (!connected && !requestUrl.substring(requestUrl.length() - 9).equals("index.jsp")) {%>
<script> document.location.href = "/index.jsp"</script>
<% } %>

<section id="content" class="container clearfix">
    <jsp:include page="checkForMessage.jsp"/>
