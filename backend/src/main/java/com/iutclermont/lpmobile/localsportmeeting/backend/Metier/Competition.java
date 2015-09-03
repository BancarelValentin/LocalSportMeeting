package com.iutclermont.lpmobile.localsportmeeting.backend.Metier;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * Created by Thomas on 01/12/2014.
 */
@Entity
public class Competition {

    @Id
    private Long id;
    private String libelle;
    @Index
    private Long idCategorie;


    public Competition() {
    }


        public Long getId() {
            return id;
        }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }
}
