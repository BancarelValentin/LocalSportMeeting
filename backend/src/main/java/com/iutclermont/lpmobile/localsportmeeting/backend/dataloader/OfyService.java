package com.iutclermont.lpmobile.localsportmeeting.backend.dataloader;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.Rencontre;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.*;

/**
 * Created by Thomas on 26/11/2014.
 */
public class OfyService {

    static {
        ObjectifyService.register(Sport.class);
        ObjectifyService.register(Categorie.class);
        ObjectifyService.register(Competition.class);
        ObjectifyService.register(Rencontre.class);
        ObjectifyService.register(Participant.class);
        ObjectifyService.register(Utilisateur.class);
    }

    public static Objectify ofy () {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory () {
        return ObjectifyService.factory();
    }
}
