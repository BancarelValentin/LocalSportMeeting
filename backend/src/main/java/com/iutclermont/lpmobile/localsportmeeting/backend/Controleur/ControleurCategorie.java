package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;

import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Categorie;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;
import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Sport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;

/**
 * Created by romunuera on 04/12/2014.
 */
public class ControleurCategorie extends HttpServlet {
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
                modifierCategorie(request, response);
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
             else if (action.equals("modifier")) {
                modifier(request, response);
            } else {
                notFound(request,response);
            }
        } else {
            notConnected(request,response);
        }
    }

    private void modifierCategorie (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        Long idSport = (Long) Long.valueOf(request.getParameter("selectSport"));
        String libelle = request.getParameter("libelle");
        Long idCategorie = (Long) Long.valueOf(request.getParameter("idCategorie"));

        if (libelle != null && !libelle.equals("")) {
            if (idSport != null & idSport != 0L) {
                Loader loader = new Loader();
                Categorie categorie = loader.getCategorie(idCategorie);
                categorie.setIdSport(idSport);
                categorie.setLibelle(libelle);
                ofy().save().entity(categorie).now();
                String message = "La catégorie à bien été modifié";
                returnMessage(message, Controleur.SUCCESS, "/categorie.jsp", request, response);

            } else if (libelle != null & libelle != "") {
                String message = "Vous devez renseigner un sport: ";
                returnMessage(message, Controleur.WARN, "/categorie.jsp", request, response);
            }
        } else {
            String message = "Vous devez renseigner un libellé";

            returnMessage(message, Controleur.WARN, "/categorie.jsp", request, response);
        }

    }


    private void modifier (HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals(""))
        {
            String message ="La catégorie n'existe pas";
            returnMessage(message, Controleur.ERROR, "/categorie.jsp", request, response);
        }
        try {
            String.valueOf(id);
        }catch (Exception e)
        {
            String message ="La categorie n'existe pas";
            returnMessage(message, Controleur.ERROR, "/categorie.jsp", request, response);
        }
        getServletContext().getRequestDispatcher("/modifyCategorie.jsp").forward(request, response);
    }



    private void supprimer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long id = Long.valueOf(request.getParameter("id"));
        Categorie current = Loader.getCategorie(id);

        supprimer(current);

        String message = "La competition a bien été supprimée";
        returnMessage(message, Controleur.SUCCESS, "/categorie.jsp", request, response);
    }

    public static void supprimer(Categorie cat) {
        ControleurCompetition.supprRefCat(cat);
        OfyService.ofy().delete().type(Categorie.class).id(cat.getId()).now();
    }

    public static void supprRefSport(Sport sport) {
        for (Categorie c : Loader.getCategorieBySport(sport)) {
            supprimer(c);
        }
    }


    private void ajouter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long idSport = (Long) Long.valueOf(request.getParameter("idSport"));
        String libelle = request.getParameter("libelle");

        if (libelle != null & libelle != "") {
            if (idSport != null & idSport != 0L) {
                Categorie cat = new Categorie();
                cat.setLibelle(libelle);
                cat.setIdSport(idSport);
                ofy().save().entity(cat).now();

                String message = "La catégorie à bien été ajouté";
                returnMessage(message, Controleur.SUCCESS, "/addCategorie.jsp", request, response);

            } else if (libelle != null & libelle != "") {
                String message = "Vous devez renseigner un sport: ";
                returnMessage(message, Controleur.WARN, "/addCategorie.jsp", request, response);
            }
        } else {
            String message = "Vous devez renseigner un libellé";
            returnMessage(message, Controleur.WARN, "/addCategorie.jsp", request, response);
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
