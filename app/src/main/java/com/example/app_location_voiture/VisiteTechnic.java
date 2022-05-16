package com.example.app_location_voiture;

import java.util.Date;

public class VisiteTechnic {
    String Matricule,Nom_agence,dateDebut,dateFin,Montant,idVisite,numeroVisite;

    public VisiteTechnic(String matricule, String nom_agence, String dateDebut, String dateFin, String montant) {
        Matricule = matricule;
        Nom_agence = nom_agence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        Montant = montant;
    }

    public VisiteTechnic(String matricule, String nom_agence, String dateDebut, String dateFin, String montant, String idVisite, String numeroVisite) {
        Matricule = matricule;
        Nom_agence = nom_agence;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        Montant = montant;
        this.idVisite = idVisite;
        this.numeroVisite = numeroVisite;
    }

    public String getIdVisite() {
        return idVisite;
    }

    public void setIdVisite(String idVisite) {
        this.idVisite = idVisite;
    }

    public String getNumeroVisite() {
        return numeroVisite;
    }

    public void setNumeroVisite(String numeroVisite) {
        this.numeroVisite = numeroVisite;
    }

    public VisiteTechnic() {
    }

    public String getMatricule() {
        return Matricule;
    }

    public void setMatricule(String matricule) {
        Matricule = matricule;
    }

    public String getNom_agence() {
        return Nom_agence;
    }

    public void setNom_agence(String nom_agence) {
        Nom_agence = nom_agence;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    public String getMontant() {
        return Montant;
    }

    public void setMontant(String montant) {
        Montant = montant;
    }
}
