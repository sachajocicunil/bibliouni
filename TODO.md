# 🚀 Roadmap : Rendre l'application BiblioUni "Complète"

Ce fichier liste les étapes nécessaires pour passer du stade de "Prototype" (état actuel) à une application "Production-Ready".

## 🔴 Priorité 1 : La Guerre des Données (Persistance)
Actuellement, les livres sont créés "en l'air" au démarrage. Si on redémarre le serveur, on perd tout. De plus, le site Web et l'API ne voient pas les mêmes données.

- [ ] **Configurer `persistence.xml`** :
    - Créer ce fichier dans `domain/src/main/resources/META-INF/`.
    - Choisir une base de données (ex: H2 en mode fichier pour commencer, ou PostgreSQL).
    - Configurer l'Unité de Persistance (Persistence Unit).
- [ ] **Implémenter le Repository Pattern** :
    - Créer une classe `BookRepository` dans `domain`.
    - Y déplacer la logique d'accès aux données (CRUD : Create, Read, Update, Delete) en utilisant l'`EntityManager` de JPA.
    - Remplacer les `ArrayList` par des appels à ce Repository.

## 🟠 Priorité 2 : Unification de l'Architecture
Pour l'instant, `BibliothequeBean` (JSF) et `BibliothequeService` (REST) font chacun leur vie.

- [ ] **Créer un Service Métier Partagé** :
    - Créer une classe `LivreService` (ou EJB) annotée `@ApplicationScoped` ou `@Stateless`.
    - Injecter (`@Inject`) ce service dans le Bean JSF et dans la ressource REST.
    - **Résultat** : Quand on ajoute un livre via l'API, il apparaît instantanément sur le site Web.

## 🟡 Priorité 3 : Fonctionnalités Manquantes

### A. Gestion des Emprunts
- [ ] **Identifier l'utilisateur** : Actuellement, on ne sait pas "qui" emprunte.
    - Créer une page de login simple (ou juste une liste déroulante "Je suis Sacha") pour sélectionner l'utilisateur courant.
- [ ] **Bouton Emprunter** :
    - Dans `index.xhtml`, ajouter un bouton "Emprunter" à côté de chaque livre libre.
    - Ce bouton doit appeler une méthode `emprunter(Livre l)` du Bean.
- [ ] **Gestion du Retour** :
    - Ajouter la possibilité de rendre un livre.

### B. Administration
- [ ] **Ajout de Livres** :
    - Créer un formulaire Web (`formulaire.xhtml`) pour ajouter un nouveau livre avec Titre/Auteur.
    - Connecter ce formulaire à une méthode `ajouterLivre()` qui persiste l'entité en BDD.
- [ ] **Suppression** : Bouton pour supprimer un livre du catalogue.

## 🟢 Priorité 4 : Expérience Utilisateur (UX/UI)
- [ ] **Design** : L'interface est brute.
    - Ajouter une feuille de style CSS (ou utiliser un framework comme Bootstrap ou PrimeFaces).
- [ ] **Feedback** : Afficher des messages de succès ("Livre emprunté !") ou d'erreur ("Ce livre n'est plus dispo...").

## 🛠 Résumé technique pour le développeur

1.  Ajouter driver JDBC (H2/Postgres) dans `pom.xml`.
2.  Setup `persistence.xml`.
3.  Injecter `@PersistenceContext EntityManager em`.
4.  Refactorer `BibliothequeBean` pour déléguer au lieu de stocker.
