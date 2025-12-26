package com.libraryapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest {

    @Test
    public void testEmpruntReussi() {
        // Arrange
        Document doc = new Livre("Titre", "Auteur");
        Utilisateur user = new Utilisateur("Alice");

        // Assert initial state
        assertTrue(doc.isEstDisponible());

        // Act
        try {
            doc.emprunter(user);
        } catch (Exception e) {
            fail("L'emprunt devrait réussir");
        }

        // Assert
        assertFalse(doc.isEstDisponible());
        assertEquals(user, doc.getEmprunteur());
    }

    @Test
    public void testEmpruntEchoueSiDejaEmprunte() {
        // Arrange
        Document doc = new Livre("Titre", "Auteur");
        Utilisateur user1 = new Utilisateur("Alice");
        Utilisateur user2 = new Utilisateur("Bob");

        // Premier emprunt
        try {
            doc.emprunter(user1);
        } catch (Exception e) {
            fail("Le premier emprunt devrait réussir");
        }

        // Act & Assert - Deuxième emprunt doit échouer
        Exception exception = assertThrows(Exception.class, () -> {
            doc.emprunter(user2);
        });

        assertEquals("Désolé ! Ce document est déjà emprunté par quelqu'un d'autre.", exception.getMessage());
        assertEquals(user1, doc.getEmprunteur(), "Le propriétaire ne doit pas changer");
    }

    @Test
    public void testRetourner() {
        // Arrange
        Document doc = new Livre("Titre", "Auteur");
        Utilisateur user = new Utilisateur("Alice");
        try {
            doc.emprunter(user);
        } catch (Exception e) {
        }

        // Act
        doc.retourner();

        // Assert
        assertTrue(doc.isEstDisponible());
        assertNull(doc.getEmprunteur());
    }
}
