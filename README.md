# BiblioUni - Gestion de Bibliothèque Universitaire

BiblioUni est une application Java simple permettant de gérer les emprunts de documents (livres, etc.) au sein d'une bibliothèque universitaire.

## Fonctionnalités

*   **Gestion des utilisateurs** : Création d'utilisateurs avec un nom.
*   **Gestion des documents** :
    *   Création de livres avec un titre et un auteur.
    *   Suivi de la disponibilité des documents.
*   **Système d'emprunt** :
    *   Un utilisateur peut emprunter un document s'il est disponible.
    *   Le document devient indisponible une fois emprunté.
    *   Vérification automatique de la disponibilité avant l'emprunt.

## Structure du Projet

Le projet est structuré selon l'architecture standard Maven :

*   `src/main/java` : Contient le code source de l'application (classes `Utilisateur`, `Livre`, `Document`, etc.).
*   `src/test/java` : Contient les tests unitaires (JUnit) pour valider la logique métier.

## Prérequis

*   Java 17
*   Maven

## Comment lancer les tests

Pour exécuter les tests unitaires et vérifier le bon fonctionnement de l'application, utilisez la commande suivante à la racine du projet :

```bash
mvn test
```

## Exemple d'utilisation

```java
Utilisateur etudiant = new Utilisateur("Sacha");
Livre livre = new Livre("Le Petit Prince", "Antoine de Saint-Exupéry");

// L'étudiant emprunte le livre
if (etudiant.emprunter(livre)) {
    System.out.println("Livre emprunté avec succès !");
} else {
    System.out.println("Le livre n'est pas disponible.");
}
```