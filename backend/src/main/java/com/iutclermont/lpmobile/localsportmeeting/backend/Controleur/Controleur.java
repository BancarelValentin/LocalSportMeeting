package com.iutclermont.lpmobile.localsportmeeting.backend.Controleur;


import com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.Loader;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;

/**
 * Created by vabancarel on 03/12/2014.
 */
public class Controleur extends HttpServlet {

    public static final String SUCCESS = "success";
    public static final String WARN = "notice";
    public static final String ERROR = "error";
    public static final String INFO = "info";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        String action = request.getParameter("action");


        if (action.equals("auth")) {
            connexion(request, response);
        }

        if (login != null && login != "") {
            if (action.equals("deconnexion")) {
                deconnexion(request, response);
            } else if (action.equals("changePassword")) {
                changePassword(request, response);
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

            if (action.equals("deconnexion")) {
                deconnexion(request, response);
            } else {
                notFound(request, response);
            }
        } else {
            notConnected(request, response);
        }
    }

    private void changePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String login = (String) session.getAttribute("login");
        String password = request.getParameter("old");
        String newPass = request.getParameter("new");
        String check = request.getParameter("check");

        Utilisateur userFromForm = new Utilisateur(login, Utilisateur.crypt(password));
        Utilisateur userFromDS = Loader.getUserByLogin(userFromForm.getLogin());

        if (userFromDS != null) {
            if (newPass.equals(check)) {
                userFromDS.setMdp(Utilisateur.crypt(newPass));
                ofy().save().entity(userFromDS).now();
                String message = "Votre mot de passe a été changé avec succés";
                returnMessage(message, Controleur.SUCCESS, "/index.jsp", request, response);
            } else {
                String message = "Les mot de passe ne correspondent pas";
                returnMessage(message, Controleur.ERROR, "/changePassword.jsp", request, response);
            }
        } else {
            String message = "L'utilisateur " + userFromForm.getLogin() + " est inconnu";
            returnMessage(message, Controleur.ERROR, "/changePassword.jsp", request, response);
        }
    }

    private void deconnexion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute("login", null);

        String message = " Vous avez été déconnecté avec succés";
        returnMessage(message, Controleur.SUCCESS, "/index.jsp", request, response);

    }

    private void connexion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");

        Utilisateur userFromForm = new Utilisateur(login, Utilisateur.crypt(password));
        Utilisateur userFromDS = Loader.getUserByLogin(userFromForm.getLogin());

        if (userFromDS != null) {
            if (userFromForm.getMdp().equals(userFromDS.getMdp())) {

                HttpSession session = request.getSession();
                session.setAttribute("login", userFromDS.getLogin());

                String message = "Bienvenue " + userFromForm.getLogin();
                returnMessage(message, Controleur.SUCCESS, "/index.jsp", request, response);
            } else {
                String message = "Le mot de passe ne correspond pas ";
                returnMessage(message, Controleur.ERROR, "/index.jsp", request, response);
            }
        } else {
            String message = "L'utilisateur " + userFromForm.getLogin() + " est inconnu";
            returnMessage(message, Controleur.ERROR, "/index.jsp", request, response);
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
