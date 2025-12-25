package com.libraryApp;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named(value = "bibliothequeBean") // Permet d'accéder au bean via #{bibliothequeBean} dans le XHTML
@RequestScoped // Le bean est recréé à chaque requête HTTP
public class BibliothequeBean implements Serializable {

    private List<Livre> livres;

    public BibliothequeBean() {
        // Simulation de données (en attendant une vraie connexion DB avec JPA)
        livres = new ArrayList<>();
        livres.add(new Livre("Le Petit Prince", "Antoine de Saint-Exupéry"));
        livres.add(new Livre("1984", "George Orwell"));
        livres.add(new Livre("Le Mythe de Sisyphe", "Albert Camus"));
    }

    // Getter indispensable pour que JSF puisse lire la liste
    public List<Livre> getLivres() {
        return livres;
    }
}
