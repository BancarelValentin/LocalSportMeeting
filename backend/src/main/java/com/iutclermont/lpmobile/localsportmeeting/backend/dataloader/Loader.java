package com.iutclermont.lpmobile.localsportmeeting.backend.dataloader;


import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.*;

import java.util.ArrayList;
import java.util.List;

import static com.iutclermont.lpmobile.localsportmeeting.backend.dataloader.OfyService.ofy;

/**
 * Created by vabancarel on 04/12/2014.
 */
public class Loader {

    public static List<Sport> getSports() {
        return ofy().load().type(Sport.class).list();
    }

    public static List<Categorie> getCategories() {
        return ofy().load().type(Categorie.class).list();
    }

    public static List<Participant> getParticipants() {
        return ofy().load().type(Participant.class).list();
    }

    public static List<Competition> getCompetitions() {
        return ofy().load().type(Competition.class).list();
    }

    public static List<Rencontre> getRencontres() {
        return ofy().load().type(Rencontre.class).list();
    }

    public static Sport getSport(Long id) {
        return ofy().load().type(Sport.class).id(id).now();
    }

    public static Categorie getCategorie(Long id) {
        return ofy().load().type(Categorie.class).id(id).now();
    }

    public static Participant getParticipant(Long id) {
        return ofy().load().type(Participant.class).id(id).now();
    }

    public static Competition getCompetition(Long id) {
        return ofy().load().type(Competition.class).id(id).now();
    }

    public static Rencontre getRencontre(Long id) {
        return ofy().load().type(Rencontre.class).id(id).now();
    }

    public static Categorie getCategorieByComp(Competition comp) {
        return ofy().load().type(Categorie.class).id(comp.getIdCategorie()).now();
    }

    public static List<Rencontre> getRencontresPassees() {
        return ofy().load().type(Rencontre.class).list();
    }


    public static List<Categorie> getCategorieBySport(Sport sport) {
        return ofy().load().type(Categorie.class).filter("idSport", sport.getId()).list();
    }

    public static List<Competition> getCompetitionsByCat(Categorie cat) {
        return ofy().load().type(Competition.class).filter("idCategorie", cat.getId()).list();
    }

    public static List<Rencontre> getRencontresByComp(Competition comp) {
        return ofy().load().type(Rencontre.class).filter("idCompetition", comp.getId()).list();
    }

    public static List<Rencontre> getRencontresBySport(Sport sport) {
        return ofy().load().type(Rencontre.class).filter("idSport", sport.getId()).list();
    }

    public static List<Competition> getRencontreByCat(Categorie cat) {
        return ofy().load().type(Competition.class).filter("idCategorie", cat.getId()).list();
    }

    public static List<Participant> getParticipantsBySport(Sport sport) {
        List<Participant> toReturn = new ArrayList<Participant>();
        List<Participant> all = ofy().load().type(Participant.class).list();
        for (Participant p : all) {
            if (p.getIdSport().equals(sport.getId())) {
                toReturn.add(p);
            }
        }
        return toReturn;
    }

    public static List<Rencontre> getRencontresByPart(Participant part) {
        List<Rencontre> r = ofy().load().type(Rencontre.class).filter("idParticipant2", part.getId()).list();
        r.addAll(ofy().load().type(Rencontre.class).filter("idParticipant1", part.getId()).list());
        return r;
    }

    public static Utilisateur getUserByLogin(String login) {
        return ofy().load().type(Utilisateur.class).id(login).now();
    }
}

