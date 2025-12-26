package com.libraryApp;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class UtilisateurService {

    @PersistenceContext(unitName = "BiblioPU")
    private EntityManager em;

    public void ajouter(Utilisateur utilisateur) {
        em.persist(utilisateur);
    }

    public void supprimer(int id) {
        Utilisateur u = em.find(Utilisateur.class, id);
        if (u != null) {
            em.remove(u);
        }
    }

    public Utilisateur trouver(int id) {
        return em.find(Utilisateur.class, id);
    }

    public List<Utilisateur> lister() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }
}
