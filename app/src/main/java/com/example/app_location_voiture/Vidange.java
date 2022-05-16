package com.example.app_location_voiture;

public class Vidange {

    String numeroV,matricule,dateV,montant,typeV,kmP,idVidange;

    public Vidange( String matricule, String dateV, String montant, String typeV) {

        this.matricule = matricule;
        this.dateV = dateV;
        this.montant = montant;
        this.typeV = typeV;
    }

    public Vidange(String numeroV, String matricule, String dateV, String montant, String typeV, String kmP, String idVidange) {
        this.numeroV = numeroV;
        this.matricule = matricule;
        this.dateV = dateV;
        this.montant = montant;
        this.typeV = typeV;
        this.kmP = kmP;
        this.idVidange = idVidange;
    }

    public String getIdVidange() {
        return idVidange;
    }

    public void setIdVidange(String idVidange) {
        this.idVidange = idVidange;
    }

    public Vidange(String matricule, String dateV, String montant, String typeV, String kmP) {

        this.matricule = matricule;
        this.dateV = dateV;
        this.montant = montant;
        this.typeV = typeV;
        this.kmP = kmP;
    }

    public String getKmP() {
        return kmP;
    }

    public void setKmP(String kmP) {
        this.kmP = kmP;
    }

    public Vidange() {
    }



    public String getNumeroV() {
        return numeroV;
    }

    public void setNumeroV(String numeroV) {
        this.numeroV = numeroV;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDateV() {
        return dateV;
    }

    public void setDateV(String dateV) {
        this.dateV = dateV;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getTypeV() {
        return typeV;
    }

    public void setTypeV(String typeV) {
        this.typeV = typeV;
    }
}
