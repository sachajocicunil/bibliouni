package com.libraryApp;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nom;

    @OneToMany(mappedBy = "emprunteur", fetch = FetchType.EAGER)
    private List<Document> documentsEmpruntes;

    public Utilisateur() {
        this.documentsEmpruntes = new ArrayList<>();
    }

    public Utilisateur(String nom) {
        this.nom = nom;
        this.documentsEmpruntes = new ArrayList<>();
    }

    public boolean emprunter(Document doc) {
        if (doc.isEstDisponible()) {
            doc.setEstDisponible(false); // On le marque comme indisponible
            doc.setEmprunteur(this); // On lie le document à l'utilisateur
            // documentsEmpruntes.add(doc); // Pas nécessaire de l'ajouter manuellement si
            // on gère bien la relation, mais pour l'objet en mémoire c'est mieux
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Document> getDocumentsEmpruntes() {
        return documentsEmpruntes;
    }

    public void setDocumentsEmpruntes(List<Document> documentsEmpruntes) {
        this.documentsEmpruntes = documentsEmpruntes;
    }
}