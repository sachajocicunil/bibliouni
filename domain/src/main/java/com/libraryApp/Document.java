package com.libraryApp;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String titre;
    private boolean estDisponible;

    @ManyToOne
    private Utilisateur emprunteur;

    public Document(String titre){
        this.estDisponible = true;
        this.titre = titre;
    }

    public Document() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Utilisateur emprunteur) {
        this.emprunteur = emprunteur;
    }
}