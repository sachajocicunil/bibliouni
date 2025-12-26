package com.libraryapp.websrv;

import com.libraryApp.Livre;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Singleton
@Startup
public class DatabaseInitializer {

    @PersistenceContext(unitName = "BiblioPU")
    private EntityManager em;

    @PostConstruct
    public void init() {
        // Vérifier si la base est vide
        long count = em.createQuery("SELECT COUNT(l) FROM Livre l", Long.class).getSingleResult();
        
        if (count == 0) {
            System.out.println("--- Initialisation de la base de données ---");
            
            Livre l1 = new Livre("Le Petit Prince", "Antoine de Saint-Exupéry");
            Livre l2 = new Livre("1984", "George Orwell");
            Livre l3 = new Livre("L'Étranger", "Albert Camus");
            Livre l4 = new Livre("Harry Potter", "J.K. Rowling");

            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.persist(l4);
            
            System.out.println("--- 4 livres ajoutés ---");
        }
    }
}