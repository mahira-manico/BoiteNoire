# Schéma documentaire — Boîte Noire

## Partie 1. La collection
Le schéma s’articule autour d’une collection unique nommée `events`.
Celle-ci rassemble l'ensemble des événements du système avec un socle de champs communs à chaque document :
- `_id` : Identifiant unique généré par MongoDB.
- `timestamp` : Date et heure UTC de l'événement, indispensable pour filtrer et ordonner l'historique temporel.
- `eventType` : Chaîne identifiant la nature de l’événement pour catégoriser les flux.
- `userId` : Référencement de l'utilisateur externe concerné, permettant de centraliser l'activité d'un compte sur plusieurs événements.

---

## Partie 2. La structure des événements

### 2.1 USER_LOGIN (Connexion utilisateur)
- **Champs communs :** `_id`, `timestamp`, `eventType`, `userId`
- **Embedding (`connectionDetails`) :**
    - `ipAddress` (String)
    - `device` (String)

### 2.2 PAYMENT (Paiement)
- **Champs communs :** `_id`, `timestamp`, `eventType`, `userId`
- **Embedding (`paymentDetails`) :**
    - `amount` (Double)
    - `status` (String)

### 2.3 API_CALL (Appel d’API)
- **Champs communs :** `_id`, `timestamp`, `eventType`, `userId`
- **Embedding (`apiDetails`) :**
    - `httpMethod` (String)
    - `endpoint` (String)
    - `responseTimeMs` (Integer)

### 2.4 NOTIFICATION (Notification utilisateur)
- **Champs communs :** `_id`, `timestamp`, `eventType`, `userId`
- **Référencement secondaire :**
    - `channelId` (String : référence vers l'identifiant du canal ou groupe de discussion)

### 2.5 ERROR (Erreur applicative)
- **Champs communs :** `_id`, `timestamp`, `eventType`, `userId`
- **Embedding (`errorDetails`) :**
    - `errorType` (String : type ou catégorie d'erreur)
    - `errorMessage` (String : message descriptif)

---

## Partie 3. Justification des choix de modélisation

### 3.1 Les choix d'embedding
Pour les événements `USER_LOGIN`, `PAYMENT`, `API_CALL` et `ERROR`, l'utilisation de documents imbriqués (embedding) s'impose selon trois critères :
- **Taille du document :** Les sous-documents créés (`connectionDetails`, `paymentDetails`, `apiDetails`, `errorDetails`) ont une taille minime et fixe. Ils n'induisent aucun risque de saturer la limite des 16 Mo par document MongoDB.
- **Fréquence de lecture conjointe :** Lors de l'analyse d'une transaction, d'un appel réseau ou d'un incident, les informations détaillées doivent être restituées immédiatement. L'embedding permet d'obtenir l'ensemble du contexte en une seule lecture sans jointure coûteuse.
- **Croissance dans le temps :** Ces sous-documents représentent une photographie figée au moment où l'action se produit. Une fois l'événement enregistré, ses informations ne grossissent plus dans le temps.

### 3.2 Les choix de référencement (Referencing)
Le référencement par identifiant externe a été retenu pour `userId` et `channelId` :
- **`userId` (Commun à tous les événements) :** Un utilisateur actif peut générer des milliers d'événements par jour. Stocker l'ensemble de son profil dans chaque log provoquerait une duplication massive et un gaspillage d'espace disque. De plus, les données de l'utilisateur vivent dans un autre service, un simple identifiant texte assure le découplage et l'immuabilité des logs en cas de modification de profil.
- **`channelId` (Événement NOTIFICATION) :** Un même canal de discussion peut être utilisé par des milliers d'utilisateurs. L'événement de notification a uniquement besoin de pointer vers le canal concerné sans devoir dupliquer les métadonnées de ce salon dans chaque document, les données du canal vivent aussi dans un autre service, en faire un embedding risquerait de créer des problèmes en cas de modification des informations du canal.

---

## Partie 4. Conclusion
L'**embedding** garantit des lectures rapides et centralisées pour les données spécifiques immuables propres à chaque action.
Le **referencing** protège la base de données contre la redondance excessive en reliant efficacement les événements répétés aux entités externes (`User`, `Channel`) sans surcharger le stockage. Cela permet aussi d'éviter les erreurs en cas de modification des entités externes.