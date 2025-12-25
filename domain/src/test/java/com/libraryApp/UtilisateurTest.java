package com.libraryApp;

import org.junit.Test;
import static org.junit.Assert.*;

public class UtilisateurTest {

    @Test
    public void testEmprunterDocumentDisponible(){
        Utilisateur u1 = new Utilisateur("sacha");
        Livre l1 = new Livre("petit prince","anthoine");
        
        // Vérifie que l'emprunt réussit
        assertTrue(u1.emprunter(l1));
        // Vérifie que le livre n'est plus disponible
        assertFalse(l1.isEstDisponible());
    }

    @Test
    public void testEmprunterDocumentIndisponible(){
        Utilisateur u1 = new Utilisateur("sacha");
        Utilisateur u2 = new Utilisateur("pierre");
        Livre l1 = new Livre("petit prince","anthoine");
        
        // Premier emprunt par u1
        u1.emprunter(l1);
        
        // Tentative d'emprunt par u2 (doit échouer)
        assertFalse(u2.emprunter(l1));

    }



}