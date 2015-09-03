package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;

import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Competition;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService;
import com.sun.net.httpserver.HttpServer;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by romunuera on 05/12/2014.
 */
public class ControleurCompetition extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");

        if (login != null && login != "") {
            String action = request.getParameter("action");
            if (action.equals("ajouter")) {
                ajouter(request, response);
            }else if (action.equals("modifier"))
            {
              modifierCompetition(request, response);
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

            Map<Sport, List<Categorie>> collectionCategories = new HashMap<Sport, List<Categorie>>();
            Map<Categorie, List<Competition>> collectionCompetitions = new HashMap<Categorie, List<Competition>>();

            if (action.equals("supprimer")) {
                supprimer(request, response);
            } else if (action.equals("modifier")) {
                modifier(request,response);
            } else {
                notFound(request,response);
            }
        } else {
            notConnected(request, response);
        }
    }


    private void modifierCompetition (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {

        Long idCat = Long.valueOf(request.getParameter("selectCategorie"));
        String libelle = request.getParameter("libelle");
        Long idCompetition = Long.valueOf(request.getParameter("idCompetition"));

        if (libelle != null & libelle != "") {
            if (idCat != null & idCat != 0L) {
                Loader loader = new Loader();
                Competition competition = loader.getCompetition(idCompetition);
                competition.setIdCategorie(idCat);
                competition.setLibelle(libelle);
                ofy().save().entity(competition).now();


                String message = "La compétition à bien été modifié";


                returnMessage(message, Controleur.SUCCESS, "/competition.jsp", request, response);

            } else {
                String message = "Vous devez renseigner une catégorie: ";
                returnMessage(message, Controleur.WARN, "/competition.jsp", request, response);
            }
        } else {
            String message = "Vous devez renseigner un libellé";
            returnMessage(message, Controleur.WARN, "/competition.jsp", request, response);
        }

    }

    private void modifier ( HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals(""))
        {
            String message ="La compétition n'existe pas";
            returnMessage(message, Controleur.ERROR, "/competition.jsp", request, response);
        }
        try {
            String.valueOf(id);
        }catch (Exception e)
        {
            String message ="La compétition n'existe pas";
            returnMessage(message, Controleur.ERROR, "/competition.jsp", request, response);
        }
        getServletContext().getRequestDispatcher("/modifyCompetition.jsp").forward(request, response);

    }

    private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        Competition current = Loader.getCompetition(id);

        supprimer(current);

        String message = "La catégorie a bien été supprimée";
        returnMessage(message, Controleur.SUCCESS, "/competition.jsp", request, response);
    }

    public static void supprimer(Competition comp) {
        ControleurRencontre.supprRefComp(comp);
        OfyService.ofy().delete().type(Competition.class).id(comp.getId()).now();
    }

    public static void supprRefCat(Categorie cat) {
        for (Competition c : Loader.getCompetitionsByCat(cat)) {
            supprimer(c);
        }
    }

    private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long idCat = Long.valueOf(request.getParameter("idCat"));
        String libelle = request.getParameter("libelle");

        if (libelle != null & libelle != "") {
            if (idCat != null & idCat != 0L) {
                Competition comp = new Competition();
                comp.setLibelle(libelle);
                comp.setIdCategorie(idCat);
                OfyService.ofy().save().entity(comp).now();

                String message = "La compétition à bien été ajouté";


                returnMessage(message, Controleur.SUCCESS, "/addCompetition.jsp", request, response);

            } else {
                String message = "Vous devez renseigner une catégorie: ";
                returnMessage(message, Controleur.WARN, "/addCompetition.jsp", request, response);
            }
        } else {
            String message = "Vous devez renseigner un libellé";
            returnMessage(message, Controleur.WARN, "/addCompetition.jsp", request, response);
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
