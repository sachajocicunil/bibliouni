package com.libraryapp.websrv;

import com.libraryapp.Livre;
import com.libraryapp.Utilisateur;
import com.libraryapp.Document;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
// import jakarta.transaction.Transactional; // Plus besoin, le Service gère ça !
import java.io.Serializable;
import java.util.List;

/**
 * =================================================================================
 * LE CERVEAU : WEB CONTROLLER
 * =================================================================================
 * C'est ici que tout se passe pour le site Web.
 * Ce fichier fait le lien entre VOS CLICS (sur index.xhtml) et LA BASE DE
 * DONNÉES.
 * 
 * RÔLES :
 * 1. Recevoir les données des formulaires (titre, auteur...).
 * 2. Traiter la logique (enregistrer, supprimer...).
 * 3. Parler avec la base de données.
 * 
 * ANNOTATIONS :
 * - @Named : Donne un petit nom ("webController") utilisable directement dans
 * le HTML.
 * - @RequestScoped : Le contrôleur vit le temps d'une requête (un clic). Il
 * renaît à chaque fois.
 * - @Transactional : Ouvre une transaction pour la base de données (début ->
 * action -> valider/annuler).
 */
@Named(value = "webController")
@RequestScoped
public class WebController implements Serializable {

    // LE SERVICE (LE CHEF CUISINIER)
    // On ne parle plus directement au frigo (EntityManager), on demande au Chef !
    @Inject
    private LibraryService service;

    // --- CHAMPS DE FORMULAIRE ---
    // Ces variables stockent temporairement ce que l'utilisateur tape dans les
    // champs texte du site.
    private int currentLivreId = 0; // 0 = mode création, >0 = mode modification
    private String nouveauTitre;
    private String nouveauAuteur;
    private String nouveauNomUser;

    // Pour le formulaire d'emprunt (menus déroulants)
    private int selectedUserId;
    private int selectedLivreId;

    // =========================================================================
    // PARTIE 1 : GESTION DES LIVRES
    // =========================================================================

    /**
     * Liste tous les livres pour les afficher dans le tableau HTML.
     * Utilise le langage JPQL (Java Persistence Query Language) : "SELECT l FROM
     * Livre l".
     */
    public List<Livre> getLivres() {
        return service.findAllLivres();
    }

    /**
     * Appelé quand on clique sur "Ajouter Livre" ou "Sauvegarder".
     */
    public String enregisterLivre() {
        Livre l;
        if (currentLivreId == 0) {
            // CAS 1 : C'est un nouveau livre
            l = new Livre(nouveauTitre, nouveauAuteur);
            service.createLivre(l); // "Chef, prépare ça !"
        } else {
            // CAS 2 : On modifie un livre existant
            l = service.findLivre(currentLivreId); // "Chef, sors-moi le livre X"
            if (l != null) {
                l.setTitre(nouveauTitre);
                l.setAuteur(nouveauAuteur);
                service.updateLivre(l); // "Chef, c'est modifié !"
            }
        }
        resetLivreForm(); // On vide les champs pour la prochaine fois
        return "index.xhtml?faces-redirect=true"; // On recharge la page proprement
    }

    public void preparerModification(Livre l) {
        this.currentLivreId = l.getId();
        this.nouveauTitre = l.getTitre();
        this.nouveauAuteur = l.getAuteur();
    }

    public String supprimerLivre(int id) {
        service.deleteLivre(id);
        return "index.xhtml?faces-redirect=true";
    }

    private void resetLivreForm() {
        currentLivreId = 0;
        nouveauTitre = "";
        nouveauAuteur = "";
    }

    // --- GESTION UTILISATEURS ---

    public List<Utilisateur> getUtilisateurs() {
        return service.findAllUtilisateurs();
    }

    public String ajouterUtilisateur() {
        Utilisateur u = new Utilisateur(nouveauNomUser);
        service.createUtilisateur(u);
        nouveauNomUser = "";
        return "index.xhtml?faces-redirect=true";
    }

    public String supprimerUtilisateur(int id) {
        service.deleteUtilisateur(id);
        return "index.xhtml?faces-redirect=true";
    }

    // --- GESTION EMPRUNTS ---

    /**
     * Appelé quand on clique sur "Valider l'Emprunt".
     * C'est ici qu'on relie un Utilisateur et un Document.
     */
    public String validerEmprunt() {
        try {
            service.emprunter(selectedUserId, selectedLivreId);
        } catch (Exception e) {
            // Si le livre est déjà emprunté, on attrape l'erreur ici (dans une vraie app,
            // on afficherait un message à l'utilisateur)
            e.printStackTrace();
        }
        return "index.xhtml?faces-redirect=true";
    }

    /**
     * Appelé quand on clique sur "Rendre".
     */
    public String rendreLivre(int livreId) {
        service.retourner(livreId);
        return "index.xhtml?faces-redirect=true";
    }

    // --- Getters & Setters ---

    public int getCurrentLivreId() {
        return currentLivreId;
    }

    public void setCurrentLivreId(int currentLivreId) {
        this.currentLivreId = currentLivreId;
    }

    public String getNouveauTitre() {
        return nouveauTitre;
    }

    public void setNouveauTitre(String nouveauTitre) {
        this.nouveauTitre = nouveauTitre;
    }

    public String getNouveauAuteur() {
        return nouveauAuteur;
    }

    public void setNouveauAuteur(String nouveauAuteur) {
        this.nouveauAuteur = nouveauAuteur;
    }

    public String getNouveauNomUser() {
        return nouveauNomUser;
    }

    public void setNouveauNomUser(String nouveauNomUser) {
        this.nouveauNomUser = nouveauNomUser;
    }

    public int getSelectedUserId() {
        return selectedUserId;
    }

    public void setSelectedUserId(int selectedUserId) {
        this.selectedUserId = selectedUserId;
    }

    public int getSelectedLivreId() {
        return selectedLivreId;
    }

    public void setSelectedLivreId(int selectedLivreId) {
        this.selectedLivreId = selectedLivreId;
    }
}
