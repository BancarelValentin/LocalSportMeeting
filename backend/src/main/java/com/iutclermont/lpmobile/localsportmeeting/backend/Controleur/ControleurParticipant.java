package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;

import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;

/**
 * Created by vabancarel on 05/12/2014.
 */
public class ControleurParticipant extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {
            String action = request.getParameter("action");
            if (action.equals("ajouter")) {
                ajouter(request, response);
            }
            else if (action.equals("modifier"))
            {
                modifierParticipant(request, response);
            } else {
                notFound(request,response);
            }
        } else {
            notConnected(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {

            String action = request.getParameter("action");
            if (action.equals("modifier")) {
                modifier(request, response);
            } else if (action.equals("supprimer")) {
                supprimer(request, response);
            }else {
                notFound(request,response);
            }
        } else {
            notConnected(request,response);
        }
    }


    private void modifierParticipant (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        Long idSport = (Long) Long.valueOf(request.getParameter("selectSport"));
        String nom = request.getParameter("libelle");
        String url = request.getParameter("libelleURL");
        Long idParticipant = Long.valueOf( request.getParameter("idParticipant"));

        if (nom != null & nom != "") {
            Loader loader = new Loader();
            Participant particiapnt = loader.getParticipant(idParticipant);
            particiapnt.setIdSport(idSport);
            particiapnt.setLibelle(nom);
            particiapnt.setUrl(url);
            ofy().save().entity(particiapnt).now();

            String message = "Le participant à bien été modifié";
            returnMessage(message, Controleur.SUCCESS, "/participant.jsp", request, response);
        } else {
            String message = "Vous devez renseigner un nom";
            returnMessage(message, Controleur.WARN, "/participant.jsp", request, response);
        }
    }

    private void modifier(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String id = request.getParameter("id");
        if (id == null || id.equals(""))
        {
            String message ="Le participant n'existe pas";
            returnMessage(message, Controleur.ERROR, "/participant.jsp", request, response);
        }
        try {
            String.valueOf(id);
        }catch (Exception e)
        {
            String message ="Le participant n'existe pas";
            returnMessage(message, Controleur.ERROR, "/participant.jsp", request, response);
        }
        getServletContext().getRequestDispatcher("/modifyParticipant.jsp").forward(request, response);

    }




    private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));//Long.valueOf(request.getParameter("id"));
        Participant current = Loader.getParticipant(id);

        supprimer(current);

        String message = "Le participant a bien été supprimé: ";
        returnMessage(message, Controleur.SUCCESS, "/participant.jsp", request, response);
    }

    public static void supprimer(Participant part) {
        ControleurRencontre.supprRefPart(part);
        ofy().delete().type(Participant.class).id(part.getId()).now();
    }

    public static void supprRefSport(Sport sport) {
        List<Participant> listeParticipant =ofy().load().type(Participant.class).list();
        for (Participant p : listeParticipant)
        {
            if (p.getIdSport() == sport.getId())
                ofy().delete().entity(p).now();
        }
    }

    private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long idSport = (Long) Long.valueOf(request.getParameter("idSport"));
        String nom = request.getParameter("nom");
        String url = request.getParameter("url");

        if (nom != null & nom != "") {
            if (idSport != null & idSport != 0L) {
                Participant part = new Participant();
                part.setLibelle(nom);
                part.setIdSport(idSport);
                part.setUrl(url);
                ofy().save().entity(part).now();

                String message = "Le participant a bien été ajouté";
                returnMessage(message, Controleur.SUCCESS, "/addParticipant.jsp", request, response);

            } else if (nom != null & nom != "") {
                String message = "Vous devez renseigner un sport: ";
                returnMessage(message, Controleur.WARN, "/addParticipant.jsp", request, response);
            }
        } else {
            String message = "Vous devez renseigner un nom";
            returnMessage(message, Controleur.WARN, "/addParticipant.jsp", request, response);
        }
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
