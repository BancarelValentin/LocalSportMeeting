<%
  String requestURL = request.getRequestURL().toString();
%>

<nav id="main-nav">
  <ul>
          <%= getLiTag("sport", requestURL) %>
      <a href="../sport.jsp" data-description="">Sport</a>
      <ul>
        <li><a href="../addSport.jsp">Ajouter</a></li>
      </ul>
    </li>
      <%= getLiTag("categorie", requestURL) %>
      <a href="../categorie.jsp" data-description="">Categorie</a>
      <ul>
        <li><a href="../addCategorie.jsp">Ajouter</a></li>
      </ul>
    </li>
      <%= getLiTag("competition", requestURL) %>
      <a href="../competition.jsp" data-description="">Competition</a>
      <ul>
        <li><a href="../addCompetition.jsp">Ajouter</a></li>
      </ul>
    </li>
      <%= getLiTag("participant", requestURL) %>
      <a href="../participant.jsp" data-description="">Participant</a>
      <ul>
        <li><a href="../addParticipant.jsp">Ajouter</a></li>
      </ul>
    </li>
      <%= getLiTag("rencontre", requestURL) %>
      <a href="../rencontre.jsp" data-description="">Rencontre</a>
      <ul>
        <li><a href="../addRencontre.jsp">Ajouter</a></li>
      </ul>
    </li>
  </ul>
</nav>

<%!
  private String getLiTag(String toTest, String requestURL){
    String current = requestURL.substring(0,requestURL.length()-4);
    current=current.substring(current.length()-toTest.length());

    if(current.equalsIgnoreCase(toTest)){
      return "<li class=current >";
    }else{
      return "<li>";
    }
  }
%>