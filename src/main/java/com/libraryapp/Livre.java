package com.libraryapp;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQuery(name = "Livre.findByAuteur", query = "SELECT l FROM Livre l WHERE l.auteur = :auteur")
public class Livre extends Document implements Serializable {
    private String auteur;

    public Livre(String titre, String auteur) {
        super(titre);
        this.auteur = auteur;
    }

    public Livre() {
        super();
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}