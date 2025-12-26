# 📘 BiblioUni - La Bible du Développeur (Documentation Complète)

> "Une documentation pour les gouverner tous."  
> Ce guide est conçu pour qu'un développeur débutant puisse comprendre l'intégralité de l'application, des concepts théoriques jusqu'à la moindre ligne de code.

---

## 📑 Table des Matières
1.  [Introduction : Qu'est-ce que c'est ?](#1-introduction)
2.  [Philosophie & Architecture](#2-philosophie--architecture)
3.  [La Stack Technique (Les Outils)](#3-la-stack-technique)
4.  [Analyse Approfondie : Le Module `domain`](#4-analyse-approfondie--le-module-domain)
5.  [Analyse Approfondie : Le Module `webSrv`](#5-analyse-approfondie--le-module-websrv)
6.  [Le Liant : Comment les modules se parlent ?](#6-le-liant--comment-les-modules-se-parlent)
7.  [Guide de Démarrage (Tutoriel)](#7-guide-de-démarrage)
8.  [Glossaire pour Débutants](#8-glossaire-pour-débutants)

---

## 1. Introduction

**BiblioUni** est une application de gestion de bibliothèque universitaire.
Elle permet de :
*   Gérer un catalogue de livres.
*   Gérer des utilisateurs (étudiants).
*   Gérer des emprunts.

Mais au-delà de ses fonctionnalités, **c'est un projet pédagogique**. Il sert à démontrer comment construire une application Web robuste, moderne et évolutive en utilisant le langage **Java** et le standard **Jakarta EE** (anciennement Java EE).

---

## 2. Philosophie & Architecture

L'application ne met pas tout son code au même endroit. Elle suit le principe de **Séparation des Préoccupations (Separation of Concerns)**.

Nous avons divisé le projet en deux "sous-projets" (appelés **Modules Maven**) :

1.  🧠 **Le Cerveau (`domain`)** : Il contient les règles du jeu.
    *   Ex: "Un livre a un titre", "On ne peut pas emprunter un livre déjà pris".
    *   Ce module ignore totalement qu'il existe un site web ou une application mobile. Il est pur.

2.  🗣 **La Voix (`webSrv`)** : Il s'occupe de parler au monde extérieur.
    *   Il contient les pages Web (HTML) et les API (JSON).
    *   Il utilise le "Cerveau" pour faire le travail réel.

### Diagramme de Flux
Imaginez un utilisateur cliquant sur un bouton :

```mermaid
graph TD
    User((Utilisateur)) -->|1. Clic| Browser[Navigateur Web]
    Browser -->|2. Requête HTTP| Server[Serveur d'Application (Wildfly/Payara)]
    
    subgraph "Module: webSrv"
        Server -->|3. Intercepte| JSF[Page JSF (Xhtml)]
        JSF -->|4. Appelle| API[API REST]
    end
    
    subgraph "Module: domain"
        JSF -->|5. Utilise| Bean[BibliothequeBean]
        Bean -->|6. Manipule| Ent[Entité Livre]
        Ent -->|7. Stockage| DB[(Base de Données)]
    end
```

---

## 3. La Stack Technique

Pour construire cette maison, nous utilisons des outils précis. Voici pourquoi :

*   **Java 17** : Le langage de programmation. Robuste et orienté objet.
*   **Maven** : Le chef de chantier. Il sert à :
    *   Télécharger les librairies nécessaires (dépendances).
    *   Compiler le code.
    *   Packager le tout (créer les fichiers `.jar` et `.war`).
*   **Jakarta EE 10** : "L'Enterprise Edition". C'est un ensemble de *spécifications* pour les grosses applications. Nous en utilisons 4 piliers :
    1.  **JPA (Persistence)** : Pour sauvegarder les objets Java dans une base de données.
    2.  **JAX-RS (REST)** : Pour créer des API Web modernes.
    3.  **JSF (Faces)** : Pour créer des pages Web dynamiques.
    4.  **CDI (Injection)** : Pour connecter tous ces composants automatiquement ("La colle").

---

## 4. Analyse Approfondie : Le Module `domain`

📁 Chemin : `/domain`
C'est ici que vit la "Logique Métier".

### A. Les Entités (Le Modèle de Données)
Les entités sont des classes Java qui correspondent à des tables dans la base de données.

#### 📄 `Document.java` (Classe Mère)
C'est un modèle générique.
*   `@Entity` : Cette annotation dit à Java "Ceci doit être sauvegardé en base de données".
*   `@Inheritance(strategy = JOINED)` : C'est très puissant. Cela dit : *"Crée une table 'Document' commune, et une table séparée pour chaque enfant (comme Livre), et lie-les ensemble"*.
*   `@Id @GeneratedValue` : Crée automatiquement un ID unique (1, 2, 3...) pour chaque document.

#### 📕 `Livre.java` (Classe Fille)
*   `extends Document` : Un Livre *EST UN* Document. Il hérite de son ID et de sa disponibilité.
*   `@NamedQuery` : On écrit ici notre SQL (ou JPQL).
    *   `SELECT l FROM Livre l WHERE l.auteur = :auteur`
    *   *Traduction* : "Sélectionne, depuis la table Livre (que j'appelle 'l'), tous les livres dont l'auteur correspond à ma variable".

### B. La Logique (Le Service)

#### 👤 `Utilisateur.java`
C'est un objet Java simple (POJO - Plain Old Java Object).
La méthode clé est `emprunter(Document doc)` :
```java
public boolean emprunter(Document doc){
    if (doc.isEstDisponible()) { // 1. Vérification
        doc.setEstDisponible(false); // 2. Action (Marquer comme pris)
        documentsEmpruntes.add(doc); // 3. Action (Donner à l'utilisateur)
        return true; // Succès
    }
    return false; // Échec
}
```
> **Notez bien** : Cette logique est ici, dans le domain. Pas dans le bouton web. Ainsi, si demain vous changez d'interface web, la règle "on ne peut pas emprunter un livre indisponible" reste vraie et protégée.

### C. Le Backing Bean (Le Pont)

#### ☕ `BibliothequeBean.java`
Son rôle est de préparer les données pour qu'elles soient affichées à l'écran.
*   `@Named("bibliothequeBean")` : Donne un petit nom à cette classe pour qu'on puisse l'appeler depuis le HTML.
*   `@RequestScoped` : *Concept Important*. Cela veut dire "Crée cet objet quand une requête Web arrive, et détruis-le quand la réponse est envoyée". Chaque utilisateur a son propre Bean le temps de sa visite.
*   Il contient une liste `List<Livre> livres`. Dans le futur, cette liste viendra de la base de données. Pour l'instant, elle est "en dur" (simulée).

---

## 5. Analyse Approfondie : Le Module `webSrv`

📁 Chemin : `/webSrv`
C'est l'interface publique. C'est ce que les gens voient.

### A. L'API REST (Pour les machines)
Aujourd'hui, les applications discutent entre elles. Votre application expose ses données via une API.

#### `HelloApplication.java`
*   `@ApplicationPath("/api")` : Définit la porte d'entrée principale.
*   Toutes les URL commenceront par `http://.../api`.

#### `BibliothequeService.java`
*   `@Path("/bibliotheque")` : Définit le couloir spécifique.
    *   URL complète : `http://.../api/bibliotheque`
*   `@GET` : Indique que cette méthode répond quand on demande à *lire* des données (Verbe HTTP GET).
*   `@Produces(MediaType.APPLICATION_JSON)` : Indique que la réponse sera formatée en JSON (le langage standard du web : `{ "titre": "Harry Potter" }`).

### B. Le Site Web (Pour les humains)

#### `webapp/index.xhtml`
C'est un fichier XHTML (un HTML strict). Il utilise **JSF**.
Regardons cette ligne magique :
```xml
<h:dataTable value="#{bibliothequeBean.livres}" var="livre">
```
1.  `#{...}` : C'est le langage d'expression (EL).
2.  `bibliothequeBean` : Il cherche la classe Java qui a ce `@Named`.
3.  `.livres` : Il appelle automatiquement la méthode `getLivres()` de cette classe.
4.  `var="livre"` : Pour chaque élément trouvé, il l'appelle "livre".

Ensuite :
```xml
<p>#{livre.titre}</p>
```
Il prend l'objet "livre" en cours, appelle `getTitre()`, et l'affiche.

---

## 6. Le Liant : Comment les modules se parlent ?

Q: Comment le module `webSrv` connaît-il l'existence de la classe `Livre` qui est dans `domain` ?

R: Grâce à **Maven**.
Dans le fichier `pom.xml` du module `webSrv`, nous avons déclaré une **Dépendance** :

```xml
<dependency>
    <groupId>com.libraryApp</groupId>
    <artifactId>domain</artifactId> <!-- "J'ai besoin du module domain" -->
    <version>1.0-SNAPSHOT</version>
</dependency>
```

Quand on compile, Maven prend le code compilé de `domain` (un fichier `.jar`) et le met à l'intérieur de `webSrv` (dans le `.war`). C'est comme mettre une bibliothèque dans votre sac à dos.

---

## 7. Guide de Démarrage

Vous êtes prêt ? Lançons la bête.

### Étape 1 : Le "Build" (Construction)
Il faut transformer le code Java (texte) en code exécutable (binaire).
On doit respecter l'ordre car `webSrv` a besoin de `domain`.

1.  Ouvrez un terminal.
2.  Allez dans le dossier `domain` : `cd domain`
3.  Lancez la commande :
    ```bash
    mvn clean install
    ```
    *   `clean` : Supprime les anciens fichiers (nettoyage).
    *   `install` : Compile, teste, package en `.jar`, et *l'installe dans votre réserve locale Maven* (sur votre disque dur). C'est crucial pour que `webSrv` puisse le trouver.

4.  Allez dans le dossier `webSrv` : `cd ../webSrv`
5.  Lancez la commande :
    ```bash
    mvn clean package
    ```
    *   `package` : Crée le fichier final `.war` (Web ARchive).

### Étape 2 : Le Déploiement
Il vous faut un serveur d'application (Wildfly, Payara, OpenLiberty).
1.  Prenez le fichier généré : `webSrv/target/webSrv-1.0-SNAPSHOT.war`.
2.  Déposez-le dans le dossier de déploiement de votre serveur.
3.  Démarrez le serveur.

### Étape 3 : Test
*   **Web** : `http://localhost:8080/webSrv/`
*   **API** : `http://localhost:8080/webSrv/api/bibliotheque`

---

## 8. Glossaire pour Débutants

*   **Bean** : Un objet Java géré par le serveur, souvent utilisé pour stocker des données temporaires ou faire le lien avec l'interface.
*   **CDI (Contexts and Dependency Injection)** : Mécanisme qui permet de relier les composants entre eux sans avoir à faire `new MaClasse()` partout. On utilise `@Inject`.
*   **Endpoint** : Une adresse URL spécifique (ex: `/api/books`) qui déclenche une action sur le serveur.
*   **JPA (Java Persistence API)** : Le standard pour gérer les bases de données en Java. C'est un ORM (Object Relational Mapping).
*   **Entité** : Une classe Java liée à une table de base de données. "1 objet Entité = 1 ligne dans la table".
*   **Maven** : L'outil qui gère la compilation, les tests et les librairies externes du projet.
*   **WAR (Web Archive)** : Un fichier compressé (comme un zip) qui contient tout votre site web et votre code compilé, prêt à être mis sur un serveur.