package com.example.app_location_voiture;

public class Entretien {
    String numero,dateEntretien,matricule,typeEntretien,Montant,idEntretien;

    public Entretien(String dateEntretien, String matricule, String typeEntretien, String montant) {
        this.dateEntretien = dateEntretien;
        this.matricule = matricule;
        this.typeEntretien = typeEntretien;
        Montant = montant;
    }

    public Entretien(String numero, String dateEntretien, String matricule, String typeEntretien, String montant) {
        this.numero = numero;
        this.dateEntretien = dateEntretien;
        this.matricule = matricule;
        this.typeEntretien = typeEntretien;
        Montant = montant;
    }

    public String getIdEntretien() {
        return idEntretien;
    }

    public void setIdEntretien(String idEntretien) {
        this.idEntretien = idEntretien;
    }

    public Entretien() {
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(String dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getTypeEntretien() {
        return typeEntretien;
    }

    public void setTypeEntretien(String typeEntretien) {
        this.typeEntretien = typeEntretien;
    }

    public String getMontant() {
        return Montant;
    }

    public void setMontant(String montant) {
        Montant = montant;
    }
}
