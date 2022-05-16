package com.example.app_location_voiture;
import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Belal on 2/26/2017.
 */
@IgnoreExtraProperties
public class Vehicule {
    String idVehicule,matricule,mark,model,couleur,puissance,km,prixJ,prixM,etat,Urli,ExtensionImage;


    public Vehicule(String idVehicule, String matricule, String mark, String model, String couleur, String puissance, String km, String prixJ, String prixM, String etat, String urli) {
        this.idVehicule = idVehicule;
        this.matricule = matricule;
        this.mark = mark;
        this.model = model;
        this.couleur = couleur;
        this.puissance = puissance;
        this.km = km;
        this.prixJ = prixJ;
        this.prixM = prixM;

        this.etat = etat;
        Urli = urli;
    }

    public String getExtensionImage() {
        return ExtensionImage;
    }

    public void setExtensionImage(String extensionImage) {
        ExtensionImage = extensionImage;
    }

    public String getPrixM() {
        return prixM;
    }

    public void setPrixM(String prixM) {
        this.prixM = prixM;
    }



    public String getUrli() {
        return Urli;
    }

    public void setUrli(String urli) {
        Urli = urli;
    }

    public Vehicule() {
    }

    public String getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(String idVehicule) {
        this.idVehicule = idVehicule;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getPuissance() {
        return puissance;
    }

    public void setPuissance(String puissance) {
        this.puissance = puissance;
    }

    public String getKm() {
        return km;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public String getPrixJ() {
        return prixJ;
    }

    public void setPrixJ(String prixJ) {
        this.prixJ = prixJ;
    }



    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }
}
