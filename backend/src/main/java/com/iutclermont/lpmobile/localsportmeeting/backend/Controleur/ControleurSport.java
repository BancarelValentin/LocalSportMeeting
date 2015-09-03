package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;

import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Participant;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;
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
public class ControleurSport extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {
            String action = request.getParameter("action");
            if (action.equals("ajouter")) {
                ajouter(request, response);
            }
            if (action.equals("modifier"))
            {
                modifierSport(request, response);
            }
            else {
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
            if (action.equals("supprimer")) {
                supprimer(request, response);
            }
            if (action.equals("modifier"))
            {
                modifier(request, response);
            }
            else {
                notFound(request,response);
            }
        } else {
            notConnected(request,response);
        }
    }

    private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        Sport current = Loader.getSport(id);

        supprimer(current);

        String message = "Le sport a bien été supprimé avec ses "+Loader.getParticipantsBySport(current).size()+" participants";//TODO: supprimer debug dans le message.
        returnMessage(message, Controleur.SUCCESS, "/sport.jsp", request, response);
    }

    public static void supprimer(Sport sport) {
        ControleurCategorie.supprRefSport(sport);
        //TODO: Pourquoi les participants d'un sport ne sont pas supprimés en meme temps que le sport ?
        ControleurParticipant.supprRefSport(sport);
        ControleurRencontre.supprRefSport(sport);
        ofy().delete().type(Sport.class).id(sport.getId()).now();



    }

    private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String libelle = request.getParameter("libelle");

        if (libelle != null & libelle != "") {
            Sport sport = new Sport();
            sport.setLibelle(libelle);
            ofy().save().entity(sport).now();

            String message = "Le sport a bien été ajouté";
            returnMessage(message, Controleur.SUCCESS, "/addSport.jsp", request, response);
        } else {
            String message = "Vous devez renseigner un libellé";
            returnMessage(message, Controleur.WARN, "/addSport.jsp", request, response);
        }
    }

    private void modifier (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals(""))
        {
            String message ="Le sport n'existe pas";
            returnMessage(message, Controleur.ERROR, "/sport.jsp", request, response);
        }
        try {
            String.valueOf(id);
        }catch (Exception e)
        {
            String message ="Le sport n'existe pas";
            returnMessage(message, Controleur.ERROR, "/sport.jsp", request, response);
        }
        getServletContext().getRequestDispatcher("/modifySport.jsp").forward(request, response);

    }

    private void modifierSport (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String libelle = request.getParameter("libelle");
        Long id1=0L;
        try {
            id1 = Long.parseLong(id);
        }catch (Exception e)
        {
            String message = "Erreur id Sport";
            returnMessage(message, Controleur.ERROR,"/modifySport.jsp", request, response);
        }

        if (libelle != null && libelle != "" && id != null && !id.equals("")) {
            Loader loader = new Loader();
            Sport sport = loader.getSport(id1);
            sport.setLibelle(libelle);
            ofy().save().entity(sport).now();

            String message = "Le sport a bien été modifié";
            returnMessage(message, Controleur.SUCCESS, "/sport.jsp", request, response);
        } else {
            String message = "Vous devez renseigner un libellé";
            returnMessage(message, Controleur.WARN, "/addSport.jsp", request, response);
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
