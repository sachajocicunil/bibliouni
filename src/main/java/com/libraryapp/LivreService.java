package com.libraryapp;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class LivreService {

    @PersistenceContext(unitName = "BiblioPU")
    private EntityManager em;

    public void ajouter(Livre livre) {
        em.persist(livre);
    }

    public void modifier(Livre livre) {
        em.merge(livre);
    }

    public void supprimer(int id) {
        Livre l = em.find(Livre.class, id);
        if (l != null) {
            em.remove(l);
        }
    }

    public Livre trouver(int id) {
        return em.find(Livre.class, id);
    }

    public List<Livre> lister() {
        return em.createQuery("SELECT l FROM Livre l", Livre.class).getResultList();
    }

    public void emprunter(int userId, int livreId) throws Exception {
        Utilisateur u = em.find(Utilisateur.class, userId);
        Document l = em.find(Document.class, livreId);

        if (u != null && l != null) {
            l.emprunter(u);
            em.merge(l);
        }
    }

    public void rendre(int livreId) {
        Document l = em.find(Document.class, livreId);
        if (l != null) {
            l.retourner();
            em.merge(l);
        }
    }
}
