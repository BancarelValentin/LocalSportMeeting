package com.iutclermont.lpmobile.localsportmeeting.backend.Metier;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import static com.google.common.hash.Hashing.sha1;


/**
 * Created by Thomas on 01/12/2014.
 */
@Entity
public class Utilisateur {

    @Id
    private String login;
    private String mdp;

    public Utilisateur() {
    }

    public Utilisateur(String login, String mdp) {
        this.login = login;
        this.mdp = mdp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public static String crypt(String toCrypt){
        return Hashing.sha1().hashString( toCrypt, Charsets.UTF_8 ).toString();
    }
}
