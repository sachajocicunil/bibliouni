package com.libraryapp.websrv;

import com.libraryapp.Livre;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * BOOTSTRAP (INITIALISATION)
 * 
 * Ce code s'exécute TOUT SEUL au démarrage du serveur (grâce à @Startup).
 * 
 * Son but : Vérifier si la base de données est vide. Si oui, il crée quelques
 * livres et utilisateurs pour qu'on ait quelque chose à voir en arrivant sur le
 * site.
 */
@Singleton
@Startup
public class DataInit {

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