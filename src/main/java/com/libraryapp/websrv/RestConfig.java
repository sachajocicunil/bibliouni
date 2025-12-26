package com.libraryapp.websrv;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * CONFIGURATION REST
 * 
 * Ce fichier est tout petit mais OBLIGATOIRE.
 * Il dit au serveur : "Active le mode API REST à l'adresse /api".
 * 
 * Sans lui, les URLs comme /api/bibliotheque/ ne fonctionneraient pas.
 */
@ApplicationPath("/api")
public class RestConfig extends Application {

}