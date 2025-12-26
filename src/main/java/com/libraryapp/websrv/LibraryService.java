package com.libraryapp.websrv;

import com.libraryapp.Document;
import com.libraryapp.Livre;
import com.libraryapp.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * =================================================================================
 * SERVICE MÉTIER : LIBRARY SERVICE
 * =================================================================================
 * C'est le "Chef Cuisinier" de l'application.
 * 
 * Son rôle est de centraliser toute la logique de gestion des données.
 * - Le WebController (le serveur) lui passe commande.
 * - L'API REST (les robots) lui passe commande aussi.
 * 
 * Comme ça, tout le monde utilise les mêmes règles du jeu !
 */
@Stateless
public class LibraryService {

    @PersistenceContext(unitName = "BiblioPU")
    private EntityManager em;

    // --- GESTION DES LIVRES ---

    public List<Livre> findAllLivres() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
    }

    public Livre findLivre(int id) {
        return em.find(Livre.class, id);
    }

    public void createLivre(Livre livre) {
        em.persist(livre);
    }

    public void updateLivre(Livre livre) {
        em.merge(livre);
    }

    public void deleteLivre(int id) {
        Livre l = findLivre(id);
        if (l != null) {
            em.remove(l);
        }
    }

    // --- GESTION DES UTILISATEURS ---

    public List<Utilisateur> findAllUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    public Utilisateur findUtilisateur(int id) {
        return em.find(Utilisateur.class, id);
    }

    public void createUtilisateur(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    public void deleteUtilisateur(int id) {
        Utilisateur u = findUtilisateur(id);
        if (u != null) {
            em.remove(u);
        }
    }

    // --- GESTION DES EMPRUNTS (Logique métier complexe) ---

    public void emprunter(int userId, int documentId) throws Exception {
        Utilisateur u = findUtilisateur(userId);
        Document d = em.find(Document.class, documentId);

        if (u != null && d != null) {
            // On appelle la méthode métier de l'entité
            d.emprunter(u);
            em.merge(d); // On sauvegarde l'état modifié
        }
    }

    public void retourner(int documentId) {
        Document d = em.find(Document.class, documentId);
        if (d != null) {
            d.retourner();
            em.merge(d);
        }
    }
}
