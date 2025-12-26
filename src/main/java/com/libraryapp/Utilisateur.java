package com.libraryapp;

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