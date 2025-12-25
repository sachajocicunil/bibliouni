package com.libraryApp;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

@Entity
@NamedQuery(name="Livre.findByAuteur", query="SELECT l FROM Livre l WHERE l.auteur = :auteur")
public class Livre extends Document{
    private String Auteur;
    public Livre(String titre, String Auteur){
        super(titre);
        this.Auteur=Auteur;
    }

    public Livre() {

    }

    public String getAuteur() {
        return Auteur;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }

}