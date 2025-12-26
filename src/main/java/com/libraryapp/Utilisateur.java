package com.libraryapp;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * =================================================================================
 * CLASSE : UTILISATEUR
 * =================================================================================
 * Représente un client de la bibliothèque.
 */
@Entity
public class Utilisateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String nom;

    // @OneToMany : Relation "Un-à-Plusieurs".
    // UN utilisateur peut avoir PLUSIEURS documents.
    // "mappedBy" : Dit que le "chef" de la relation est le champ 'emprunteur' dans
    // la classe Document.
    // FetchType.EAGER : Quand je charge un utilisateur, charge TOUT DE SUITE ses
    // livres (attention aux perfs sur des gros projets !).
    @OneToMany(mappedBy = "emprunteur", fetch = FetchType.EAGER)
    private List<Document> documentsEmpruntes = new ArrayList<>();

    public Utilisateur() {
    }

    public Utilisateur(String nom) {
        this.nom = nom;
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