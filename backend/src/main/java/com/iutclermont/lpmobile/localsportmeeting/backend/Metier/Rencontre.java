package com.iutclermont.lpmobile.localsportmeeting.backend.Metier;


import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.iutclermont.lpmobile.localsportmeeting.backend.Metier.MyDate;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Thomas on 01/12/2014.
 */
@Entity
public class Rencontre {

    @Id
    private Long id;
    private String lieu;
    private Double longitude;
    private Double latitude;
    @Index
    public Date date;

    @Index
    private Long idParticipant1;
    @Index
    private Long idParticipant2;
    @Index
    private Long idCompetition;
    @Index
    private Long idSport;

    public Rencontre() {
    }

    public Rencontre(Long id, String lieu, Double longitude, Double latitude, Date date, Long idParticipant1, Long idParticipant2, Long idCompetition, Long idSport) {
        this.id = id;
        this.lieu = lieu;
        this.longitude = longitude;
        this.latitude = latitude;
        this.date = date;
        this.idParticipant1 = idParticipant1;
        this.idParticipant2 = idParticipant2;
        this.idCompetition = idCompetition;
        this.idSport = idSport;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getIdParticipant1() {
        return idParticipant1;
    }

    public void setIdParticipant1(Long idParticipant1) {
        this.idParticipant1 = idParticipant1;
    }

    public Long getIdParticipant2() {
        return idParticipant2;
    }

    public void setIdParticipant2(Long idParticipant2) {
        this.idParticipant2 = idParticipant2;
    }

    public Long getIdCompetition() {
        return idCompetition;
    }

    public void setIdCompetition(Long idCompetition) {
        this.idCompetition = idCompetition;
    }

    public Long getIdSport() {
        return idSport;
    }

    public void setIdSport(Long idSport) {
        this.idSport = idSport;
    }
}
