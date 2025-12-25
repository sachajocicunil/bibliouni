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

        if (documentsEmpruntes.contains(doc)){
            return false;
        }
        documentsEmpruntes.add(doc);
        return true;

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
