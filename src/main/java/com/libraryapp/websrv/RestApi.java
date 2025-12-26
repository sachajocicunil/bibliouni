package com.libraryapp.websrv;

import com.libraryapp.Livre;
import jakarta.ejb.Stateless;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.inject.Inject;
import java.util.List;

/**
 * =================================================================================
 * L'API (INTERFACE PROGRAMME)
 * =================================================================================
 * Ce fichier permet à d'AUTRES PROGRAMMES (pas des humains) de discuter avec
 * notre bibliothèque.
 * 
 * Par exemple : Une application mobile iPhone, un script Python, ou un autre
 * site web.
 * Ils envoient des requêtes HTTP (GET, POST...) et reçoivent du JSON (texte).
 * 
 * URL de base : http://localhost:8080/bibliouni2/api/bibliotheque
 */
@Stateless // C'est un EJB (Enterprise Java Bean), il vit côté serveur.
@Path("/bibliotheque") // L'adresse web pour parler à cette classe
public class RestApi {

    @Inject
    private LibraryService service;

    /**
     * GET /api/bibliotheque
     * Renvoie la liste de tous les livres en format JSON.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Livre> getLivres() {
        // On demande au Service, tout simplement.
        return service.findAllLivres();
    }

    /**
     * GET /api/bibliotheque/{id}
     * Exemple : /api/bibliotheque/5
     * Renvoie les détails d'un seul livre spécifique.
     */
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Livre getLivre(@PathParam("id") int id) {
        return service.findLivre(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void ajouterLivre(Livre livre) {
        service.createLivre(livre);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifierLivre(Livre livre) {
        service.updateLivre(livre);
    }

    @DELETE
    @Path("/{id}")
    public void supprimerLivre(@PathParam("id") int id) {
        service.deleteLivre(id);
    }
}