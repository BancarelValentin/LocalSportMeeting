package com.iutclermont.lpmobile.localsportmeeting.backend.Metier;

import com.google.appengine.repackaged.com.google.common.base.Flag;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * Created by Thomas on 01/12/2014.
 */
@Entity
public class Sport {

    @Id
    private Long id;
    private String libelle;

    public Sport () {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return this.libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Sport(String libelle) {
        this.libelle = libelle;
    }
}
