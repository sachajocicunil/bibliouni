package com.libraryapp.websrv;

import com.libraryApp.Livre;
import com.libraryApp.LivreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

@Path("/bibliotheque")
public class BibliothequeService {

    @Inject
    private LivreService livreService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Livre> getLivres() {
        return livreService.lister();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Livre getLivre(@PathParam("id") int id) {
        return livreService.trouver(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void ajouterLivre(Livre livre) {
        livreService.ajouter(livre);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void modifierLivre(Livre livre) {
        livreService.modifier(livre);
    }

    @DELETE
    @Path("/{id}")
    public void supprimerLivre(@PathParam("id") int id) {
        livreService.supprimer(id);
    }
}