package com.libraryApp;

import java.util.ArrayList;
import java.util.List;

public class Utilisateur {
    private String Nom;
    private List<Document> documentsEmpruntes;
    private int Id;

    public Utilisateur(String Nom){
        this.Nom = Nom;
        this.documentsEmpruntes= new ArrayList<Document>();
    }

    public boolean emprunter(Document doc){
        if (doc.isEstDisponible()) {
            doc.setEstDisponible(false); // On le marque comme indisponible
            documentsEmpruntes.add(doc); // On l'ajoute à la liste de l'utilisateur
            return true;
        }
        return false;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public List<Document> getDocumentsEmpruntes() {
        return documentsEmpruntes;
    }
}