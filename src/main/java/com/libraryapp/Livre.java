package com.libraryapp;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

/**
 * =================================================================================
 * CLASSE ENFANT : LIVRE
 * =================================================================================
 * Cette classe représente un type spécifique de document : le Livre.
 * Elle hérite (extends) de toutes les fonctionnalités de Document (id, titre,
 * emprunter...).
 * 
 * CONCEPTS :
 * - extends Document : "Je suis un Document, mais avec des trucs en plus (+)".
 * - @Entity : Je suis aussi une table dans la base de données.
 * - @NamedQuery : Une requête SQL préparée à l'avance qu'on peut appeler par
 * son nom.
 */
@Entity
@NamedQuery(name = "Livre.findByAuteur", query = "SELECT l FROM Livre l WHERE l.auteur = :auteur")
public class Livre extends Document implements Serializable {

    // Spécifique au Livre (un DVD aurait une 'durée', un Livre a un 'auteur')
    private String auteur;

    /**
     * CONSTRUCTEUR VIDE
     * Obligatoire pour que JPA (la base de données) puisse créer l'objet.
     */
    public Livre() {
        super(); // Appelle le constructeur de papa (Document)
    }

    /**
     * CONSTRUCTEUR COMPLET
     * Pour créer un livre facilement dans le code : new Livre("Harry Potter", "JK
     * Rowling");
     */
    public Livre(String titre, String auteur) {
        super(titre); // On utilise le constructeur de Document pour régler le titre
        this.auteur = auteur;
    }

    // --- Getters et Setters spécifiques ---

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
}