# Étape 5 : Optimisation de performance

## 1. Requête la plus côuteuse
Analyse 2 : Répartition des erreurs par type et par date sur la collection `events`.
Critères de sélection : `eventType: "ERROR"` et `timestamps` compris dans l'intervalle annuel.

## 2. Mesure explain AVANT index
* **Type de scan :** `COLLSCAN`
* **Documents examinés (`docsExamined`) :** 100 000
* **Documents retournés (`nReturned`) :** 14 992
* **Temps d'exécution :** 158 ms

### Index créé
* **Nom de l'index :** `eventType_1_timestamps_1`
* **Définition :** `{ "eventType": 1, "timestamps": 1 }`
* **Champs et types :**
    - `eventType` : `1` Ascending
    - `timestamps` : `1` Ascending

### Justification de l'ordre des champs
L'index suit la règle d'optimisation MongoDB ESR (Equality, Sort, Range) :
1. **Égalité en premier (`eventType`) :** Permet d'isoler immédiatement la partition des erreurs (`ERROR`) dans l'arbre d'index et d'écarter l'ensemble des autres types d'événements sans lire la collection.
2. **Plage en second (`timestamps`) :** Une fois le type d'événement ciblé, le moteur parcourt directement la plage de dates ordonnée chronologiquement grâce au sens ascendant.

## 4. Mesure explain APRÈS index
* **Type de scan :** `IXSCAN` 
* **Documents examinés (`keysExamined`) :** 14 992
* **Documents retournés (`nReturned`) :** 14 992
* **Temps d'exécution :** 25 ms

## 5. Constat
Avant indexation, MongoDB devait inspecter l'intégralité des 100 000 documents en mémoire pour extraire les erreurs. Grâce à l'index composé sur `eventType` puis `timestamps`, le moteur cible exclusivement les documents utiles sans aucun scan superflu, divisant le temps de réponse par plus de 20.