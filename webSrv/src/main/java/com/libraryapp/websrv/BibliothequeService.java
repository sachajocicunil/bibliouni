package com.libraryapp.websrv;

import com.libraryApp.Livre;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/bibliotheque")
public class BibliothequeService {

    List<Livre> listeDeLivre = new ArrayList<>();
    Livre l1 = new Livre("belloboidormant","sacha");
    Livre l2 = new Livre("papiquidort","kaaris");

    public BibliothequeService() {
        listeDeLivre.add(l1);
        listeDeLivre.add(l2);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON) // On renvoie du JSON
    public List<Livre> getLivres() {
        return listeDeLivre;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{titre}")
    public Livre getLivreParTitre (@PathParam("titre") String titre){
        for (Livre livre : listeDeLivre) {
            if (livre.getTitre().equalsIgnoreCase(titre)) {
                return livre;
            }
        }
        return null;
    }







}