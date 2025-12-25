package com.libraryApp;

public class Livre extends Document{
    private String Auteur;
    public Livre(String titre, String Auteur){
        super(titre);
        this.Auteur=Auteur;
    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }
}
