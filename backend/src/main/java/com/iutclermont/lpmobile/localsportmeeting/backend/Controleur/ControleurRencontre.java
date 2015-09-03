package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;

import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;

/**
 * Created by vabancarel on 05/12/2014.
 */
public class ControleurRencontre extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {
            String action = request.getParameter("action");
            if (action.equals("ajouter")) {
                ajouter(request, response);
            } else if (action.equals("modifier")) {
                Long id = Long.valueOf(request.getParameter("id"));
                modifier(request, response, id);
            } else {
                notFound(request, response);
            }
        } else {
            notConnected(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {
            String action = request.getParameter("action");
            if (action.equals("supprimer")) {
                supprimer(request, response);
            } else if (action.equals("modifier")) {
                Rencontre r = Loader.getRencontre(Long.valueOf((String) request.getParameter("id")));
                modifier(request, response, r);
            } else if (action.equals("supprimerToutesRencontres")){
                supprimerToutesLesRencontresPassees(request,response);
            }else {
                notFound(request, response);
            }
        } else {
            notConnected(request, response);
        }
    }

    private void modifier(HttpServletRequest request, HttpServletResponse response, Rencontre rencontre) throws ServletException, IOException {
        if (rencontre == null) {
            notFound(request, response);
        }

        request.setAttribute("id", rencontre.getId());
        request.setAttribute("lieu", rencontre.getLieu());
        request.setAttribute("long", rencontre.getLongitude());
        request.setAttribute("lat", rencontre.getLatitude());
        request.setAttribute("date", rencontre.getDate());
        request.setAttribute("sport", rencontre.getIdSport());
        request.setAttribute("cat", Loader.getCompetition(rencontre.getIdCompetition()).getIdCategorie());
        request.setAttribute("comp", rencontre.getIdCompetition());
        request.setAttribute("p1", rencontre.getIdParticipant1());
        request.setAttribute("p2", rencontre.getIdParticipant2());

        getServletContext().getRequestDispatcher("/modifyRencontre.jsp").forward(request, response);
    }

    private void modifier(HttpServletRequest request, HttpServletResponse response, Long idRencontre) throws ServletException, IOException {

        if (idRencontre != null && idRencontre != 0) {
            request.setAttribute("idRencontre", idRencontre);
            ajouter(request, response);
        } else {
            notFound(request, response);
        }
    }

    private void supprimerToutesLesRencontresPassees (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Rencontre> list = Loader.getRencontresPassees();
        Calendar dateDuJour = Calendar.getInstance();
        int nRencontreSupp = 0;
        List<Rencontre> listeRencontre = ofy().load().type(Rencontre.class).filter("date <", dateDuJour.getTime()).list();
        for (Rencontre rencontre : listeRencontre)
        {
            ofy().delete().entity(rencontre).now();
            nRencontreSupp++;
        }
        String message = nRencontreSupp +" rencontre(s) ont été supprimées";
        returnMessage(message, Controleur.SUCCESS, "/rencontre.jsp", request, response);
    }

    private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        Rencontre current = Loader.getRencontre(id);

        supprimer(current);

        String message = "La rencontre a bien été supprimée";
        returnMessage(message, Controleur.SUCCESS, "/rencontre.jsp", request, response);
    }

    public static void supprimer(Rencontre rencontre) {
        ofy().delete().type(Rencontre.class).id(rencontre.getId()).now();
    }

    public static void supprRefPart(Participant part) {
        for (Rencontre r : Loader.getRencontresByPart(part)) {
            supprimer(r);
        }
    }

    public static void supprRefComp(Competition comp) {
        for (Rencontre r : Loader.getRencontresByComp(comp)) {
            ofy().delete().type(Rencontre.class).id(r.getId()).now();
        }
    }

    public static void supprRefSport(Sport sport) {
        for (Rencontre r : Loader.getRencontresBySport(sport)) {
            ofy().delete().type(Rencontre.class).id(r.getId()).now();
        }
    }

    private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Rencontre r = new Rencontre();
        if (request.getAttribute("idRencontre") != null) {
            Long idRencontre = (Long) request.getAttribute("idRencontre");
            r.setId(idRencontre);
        }

        String lieu = request.getParameter("lieu");
        Double latitude = Double.valueOf(request.getParameter("latitude"));
        Double longitude = Double.valueOf(request.getParameter("longitude"));
        String dateString = request.getParameter("date");
        String timeString = request.getParameter("heure");
        Long idPart1 = Long.valueOf(request.getParameter("idPart1"));
        Long idPart2 = Long.valueOf(request.getParameter("idPart2"));
        Long idSport = Long.valueOf(request.getParameter("idSport"));
        Long idComp = Long.valueOf(request.getParameter("idComp"));

        if (lieu == null || lieu == "") {
            String message = "Vous devez renseigner un lieu";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (latitude == null || latitude == 0L) {
            String message = "Vous devez renseigner une latitude";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (longitude == null || longitude == 0L) {
            String message = "Vous devez renseigner une longitude";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (dateString == null || dateString == "") {
            String message = "Vous devez renseigner une date";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (timeString == null || timeString == "") {
            String message = "Vous devez renseigner une heure";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (idPart1 == null || idPart1 == 0L) {
            String message = "Vous devez renseigner un 1er participant";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (idPart2 == null || idPart2 == 0L) {
            String message = "Vous devez renseigner un 2ème participant";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (idPart2 == idPart1) {
            String message = "Les deux participants ne peuvent pas être identiques";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (idSport == null || idSport == 0L) {
            String message = "Vous devez renseigner un sport";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        if (idComp == null || idComp == 0L) {
            String message = "Vous devez renseigner une compétition";
            returnMessage(message, Controleur.WARN, "/addRencontre.jsp", request, response);
        }

        String[] dateTab = dateString.split("-");
        String[] timeTab = timeString.split(":");
        GregorianCalendar date = new GregorianCalendar(Integer.valueOf(dateTab[0]), Integer.valueOf(dateTab[1])-1, Integer.valueOf(dateTab[2]), Integer.valueOf(timeTab[0]), Integer.valueOf(timeTab[1]));
        r.setLieu(lieu);
        r.setLatitude(latitude);
        r.setLongitude(longitude);
        r.setDate(date.getTime());
        r.setIdParticipant1(idPart1);
        r.setIdParticipant2(idPart2);
        r.setIdSport(idSport);
        r.setIdCompetition(idComp);

        ofy().save().entity(r).now();


        String message = "La rencontre à bien été ajoutée";
        returnMessage(message, Controleur.SUCCESS, "/rencontre.jsp", request, response);
    }

    private void returnMessage(String message, String type, String destination, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(type, message);
        getServletContext().getRequestDispatcher(destination).forward(request, response);
    }


    private void notFound(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "404 Not Found";
        returnMessage(message, Controleur.ERROR, "/index.jsp", request, response);
    }

    private void notConnected(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message = "Vous n'êtes pas connecté";
        returnMessage(message, Controleur.ERROR, "/index.jsp", request, response);
    }
}
