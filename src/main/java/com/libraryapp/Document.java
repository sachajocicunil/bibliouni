package com.libraryapp;

import jakarta.persistence.*;
import java.io.Serializable; // Added for Serializable interface

/**
 * =================================================================================
 * CLASSE MÈRE : DOCUMENT
 * =================================================================================
 * Cette classe abstraite sert de modèle de base pour tout ce qui peut être
 * emprunté
 * dans la bibliothèque (Livres, DVD, Revues...).
 * 
 * POURQUOI CETTE CLASSE ?
 * - Évite la répétition de code (DRY - Don't Repeat Yourself).
 * - Tous les documents ont un ID, un titre, et un état de disponibilité.
 * 
 * CONCEPTS JAVA / JPA :
 * - @Entity : Dit à Java de transformer cette classe en table dans la base de
 * données.
 * - @Inheritance : Dit comment gérer l'héritage en base de données (ici
 * TABLE_PER_CLASS
 * veut dire une table pour les Livres, une table pour les DVD, etc.).
 * - abstract : On ne peut pas créer un "Document" vide. Ça doit être un Livre
 * ou autre.
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Document implements Serializable {

    // @Id : C'est la clé primaire. Comme un numéro de sécurité sociale unique pour
    // le document.
    // @GeneratedValue : La base de données va inventer ce numéro toute seule (1, 2,
    // 3...).
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected int id;

    protected String titre;

    // Est-ce que le document est là ou chez quelqu'un ?
    protected boolean estDisponible;

    // @ManyToOne : Relation "Plusieurs-à-Un".
    // PLUSIEURS documents peuvent être empruntés par UN seul utilisateur.
    @ManyToOne
    protected Utilisateur emprunteur;

    // Constructeur vide nécessaire pour JPA (la technologie de base de données)
    public Document() {
    }

    public Document(String titre) {
        this.titre = titre;
        this.estDisponible = true; // Par défaut, un nouveau document est disponible
    }

    /**
     * LOGIQUE MÉTIER : EMPRUNTER
     * C'est ici qu'on protège nos données. On vérifie les règles avant de changer
     * quoi que ce soit.
     */
    public void emprunter(Utilisateur utilisateur) throws Exception {
        // Règle 1 : On ne peut pas emprunter ce qui n'est pas là.
        if (!this.estDisponible) {
            throw new Exception("Désolé ! Ce document est déjà emprunté par quelqu'un d'autre.");
        }

        // Action : On marque le document comme pris et on note qui l'a.
        this.estDisponible = false;
        this.emprunteur = utilisateur;

        // Important : On met aussi à jour la liste de l'utilisateur pour que les deux
        // côtés soient d'accord.
        utilisateur.getDocumentsEmpruntes().add(this);
    }

    /**
     * LOGIQUE MÉTIER : RETOURNER
     */
    public void retourner() {
        // On enlève le document de la poche de l'emprunteur s'il y en a un
        if (this.emprunteur != null) {
            this.emprunteur.getDocumentsEmpruntes().remove(this);
        }
        // Le document redevient libre pour le prochain client
        this.emprunteur = null;
        this.estDisponible = true;
    }

    // --- Getters et Setters (Accesseurs) ---
    // Ils permettent de lire ou changer les valeurs des variables protégées
    // (protected).
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isEstDisponible() {
        return estDisponible;
    }

    public void setEstDisponible(boolean estDisponible) {
        this.estDisponible = estDisponible;
    }

    public Utilisateur getEmprunteur() {
        return emprunteur;
    }

    public void setEmprunteur(Utilisateur emprunteur) {
        this.emprunteur = emprunteur;
    }
}