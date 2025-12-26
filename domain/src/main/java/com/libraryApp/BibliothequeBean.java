package com.libraryApp;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named(value = "bibliothequeBean")
@RequestScoped
public class BibliothequeBean implements Serializable {

    @Inject
    private LivreService livreService;

    @Inject
    private UtilisateurService utilisateurService;

    // Champs pour le formulaire Livre
    private int currentLivreId = 0; // 0 = Ajout, >0 = Modif
    private String nouveauTitre;
    private String nouveauAuteur;

    // Champs pour le formulaire Utilisateur
    private String nouveauNomUser;

    // Champs pour Emprunt
    private int selectedUserId;
    private int selectedLivreId;

    // --- GESTION LIVRES ---

    public List<Livre> getLivres() {
        return livreService.lister();
    }

    public String enregisterLivre() {
        Livre l;
        if (currentLivreId == 0) {
            l = new Livre(nouveauTitre, nouveauAuteur);
            livreService.ajouter(l);
        } else {
            l = livreService.trouver(currentLivreId);
            l.setTitre(nouveauTitre);
            l.setAuteur(nouveauAuteur);
            livreService.modifier(l);
        }
        resetLivreForm();
        return "index.xhtml?faces-redirect=true";
    }

    public void preparerModification(Livre l) {
        this.currentLivreId = l.getId();
        this.nouveauTitre = l.getTitre();
        this.nouveauAuteur = l.getAuteur();
    }

    public String supprimerLivre(int id) {
        livreService.supprimer(id);
        return "index.xhtml?faces-redirect=true";
    }

    private void resetLivreForm() {
        currentLivreId = 0;
        nouveauTitre = "";
        nouveauAuteur = "";
    }

    // --- GESTION UTILISATEURS ---

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurService.lister();
    }

    public String ajouterUtilisateur() {
        Utilisateur u = new Utilisateur(nouveauNomUser);
        utilisateurService.ajouter(u);
        nouveauNomUser = "";
        return "index.xhtml?faces-redirect=true";
    }

    public String supprimerUtilisateur(int id) {
        utilisateurService.supprimer(id);
        return "index.xhtml?faces-redirect=true";
    }

    // --- GESTION EMPRUNTS ---

    public String validerEmprunt() {
        try {
            livreService.emprunter(selectedUserId, selectedLivreId);
        } catch (Exception e) {
            // Dans une vraie app, on afficherait un message d'erreur
            e.printStackTrace();
        }
        return "index.xhtml?faces-redirect=true";
    }

    public String rendreLivre(int livreId) {
        livreService.rendre(livreId);
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
