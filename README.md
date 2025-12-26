# 📚 Bibliouni2 : Comprendre une Application Java EE

Bienvenue dans **Bibliouni2** !

Ce projet n'est pas seulement une application de gestion de bibliothèque ; c'est un **exemple pédagogique complet** conçu pour vous aider à comprendre comment fonctionne une application **Java Entreprise (Jakarta EE)** moderne.

Si vous êtes débutant, ce document est fait pour vous. Nous allons décortiquer chaque partie du code, expliquer "pourquoi" elle est là, et comment tout cela fonctionne ensemble.

---

## 🏗️ L'Architecture : Comment ça marche ?

Une application Java EE sépare les choses en **couches responsabilités**. Imaginez un restaurant :

1.  **La Vue (Présentation)** : C'est la salle du restaurant et le menu (`index.xhtml`). Le client (l'utilisateur) interagit ici.
2.  **Le Contrôleur (WebController)** : C'est le serveur ET le chef cuisinier. Il prend votre commande, la traite, et interagit directement avec le frigo (Base de Données).
3.  **Le Modèle (Entités)** : Ce sont les ingrédients (`Livre`, `Utilisateur`). Ils représentent les données réelles.
4.  **La Persistance (Base de Données)** : C'est le frigo. On y stocke les ingrédients pour qu'ils ne disparaissent pas quand on éteint la lumière.


```mermaid
graph TD
    %% --- ACTEURS ---
    User((Utilisateur Humain))
    Robot((Client API / Robot))

    %% --- CLIENTS ---
    Browser[Navigateur Web<br/>(Visualisation JSF)]
    MobileApp[App Mobile / Script]

    %% --- SERVEUR JAVA EE (WILDFLY) ---
    subgraph "Serveur d'Application (WildFly)"
        direction TB

        %% Configuration & Init
        Config[RestConfig<br/>(Config JAX-RS)]
        Init[DataInit<br/>(Données de Démarrage)]

        %% Controller Layer
        WC[WebController<br/>(Gestion Affichage)]
        API[RestApi<br/>(Point d'entrée JSON)]

        %% Service Layer
        Service[LibraryService<br/>(Logique Métier Centralisée)]

        %% Model / Entities Layer
        subgraph "Modèle de Données (JPA)"
            Doc[Document<br/>(Abstrait)]
            Livre[Livre<br/>(Concret)]
            Util[Utilisateur]
            
            Livre -- Hérite de --> Doc
            Doc "0..*" -- "Emprunté par" --> "0..1" Util
        end
    end

    %% --- BASE DE DONNÉES ---
    DB[(Base de Données<br/>H2 Memory)]

    %% --- FLUX D'INTERACTION ---
    User -->|1. Clique| Browser
    Browser -->|2. Envoie Formulaire HTTP| WC
    Robot -->|1. Requête JSON| MobileApp
    MobileApp -->|2. GET/POST| API

    WC -->|3. Appelle| Service
    API -->|3. Appelle| Service

    Service -->|4. Cherche/Modifie| Doc
    Service -->|4. Cherche/Modifie| Util
    Service -->|5. Persiste (EntityManager)| DB

    Init -.->|Initialise au démarrage| Service
    Config -.->|Définit l'URL| API
```

---

## 📂 Visite Guidée des Fichiers

Voici une explication détaillée de chaque fichier de votre projet, situé dans `src/main/java/com/libraryapp`.

### 1. Les Plans de Construction (Le Modèle / Entités)

Ce sont de simples classes Java (POJO) qui représentent les objets de la vie réelle. Mais grâce à **JPA (Java Persistence API)**, elles peuvent être sauvegardées automatiquement dans une base de données.

*   **`Document.java` (Classe Mère)**
    *   **C'est quoi ?** : C'est le modèle de base pour tout ce qui peut être emprunté.
    *   **Pourquoi ?** : Pour ne pas répéter le code. Un `Livre` est un `Document`. Un `DVD` pourrait être un `Document`. Ils partagent tous un `id`, un `titre` et un état (`estDisponible`).
    *   **Concept clé** : L'héritage (mot clé `extends`). De plus, on y a mis la logique métier de l'emprunt (`emprunter()`) pour que l'objet soit responsable de son propre état (Domain-Driven Design).
    
*   **`Livre.java`**
    *   **C'est quoi ?** : Une version spécifique d'un `Document`.
    *   **Le petit plus** : Il ajoute un champ `auteur`. Grâce à l'annotation `@Entity`, Java sait qu'il doit créer une table "Livre" dans la base de données.

*   **`Utilisateur.java`**
    *   **C'est quoi ?** : La personne qui emprunte.
    *   **Le lien magique** : Regardez `List<Document> documentsEmpruntes`. C'est une relation **One-To-Many** (Un-à-Plusieurs). Un utilisateur peut avoir *plusieurs* documents. Java gère ce lien complexe pour vous.

### 2. Cerveau (Logique & Contrôle)
- **`com.libraryapp.websrv.LibraryService`** : *"Le Chef Cuisinier"*.
  - C'est lui qui fait tout le travail difficile (parler à la base de données, vérifier les emprunts).
  - Il est utilisé par le site web ET par l'API.
- **`com.libraryapp.websrv.WebController`** : *"Le Serveur du Restaurant"*.
  - Il prend les commandes des clients (formulaires HTML) et les passe au Chef (Service).
  - Il ne touche jamais directement aux ingrédients (Base de données).
- **`com.libraryapp.websrv.RestApi`** : *"Le Drive"*.
  - Pour les robots ou applications mobiles. Il reçoit des commandes JSON et demande aussi au Chef.

### 3. L'API (Pour les Robots / RestApi)

*   **`RestApi.java`**
    *   **C'est quoi ?** : Une porte d'entrée pour les programmes.
    *   **Rôle** : Elle permet de récupérer la liste des livres en format JSON via une adresse web.

### 4. Le Démarrage (DataInit)

*   **`DataInit.java`**
    *   **Mission** : Remplir la bibliothèque si elle est vide au lancement du serveur. Pratique pour ne pas tester avec une page blanche.

---

## 🛠️ Les Outils de l'Ombre (Configuration)

Ces fichiers ne contiennent pas de code Java, mais ils sont cruciaux.

*   **`pom.xml` (Maven)**
    *   C'est la **recette de cuisine** du projet. Il dit : "Pour construire ce projet, j'ai besoin de Java 17, de la librairie Jakarta EE pour le web, et de JUnit pour les tests". Maven télécharge tout ça pour vous automatiquement.

*   **`persistence.xml` (JPA)**
    *   C'est le **fichier de connexion**. Il dit à l'application : "Voici l'adresse de la base de données" et "Voici la liste de mes classes Modèles (Livre, Document...)".

*   **`beans.xml` & `web.xml`**
    *   Ce sont les interrupteurs "ON" pour activer certaines fonctionnalités avancées du serveur Java.

---

## 🧪 Pourquoi les Tests sont Importants ?

Vous avez vu le fichier **`DocumentTest.java`** ?
C'est votre **filet de sécurité**.
Imaginez que vous modifiez le code de l'emprunt demain. Comment être sûr que vous n'avez pas cassé la règle qui interdit d'emprunter un livre indisponible ?
Au lieu de lancer le serveur, d'ouvrir le navigateur, de cliquer partout... vous lancez juste le test (`mvn test`). En 1 seconde, il vous dit si votre logique métier est toujours solide.

> **Le bon réflexe** : On teste la logique critique (emprunt) le plus "près" possible du code (dans `Document`), sans dépendre de tout le tralala du serveur Web.

---

## 🚀 Résumé pour le Débutant

1.  L'utilisateur clique sur le **Site Web** (`index.xhtml`).
2.  Le site parle au **Contrôleur** (`WebController`).
3.  Le Contrôleur manipule directement les **Objets** (`Livre`) et les sauvegarde en **Base de Données** (via JPA).

C'est cette séparation qui rend le code propre, maintenable et professionnel !