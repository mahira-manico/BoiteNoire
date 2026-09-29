# Note de Cadrage Architectural : Choix de MongoDB pour l'ingestion d'événements

## 1. Limites d'une modélisation relationnelle en SQL
Dans le cadre de l'ingestion d'événements applicatifs hétérogènes (connexions, erreurs système, paiements, requêtes HTTP), une modélisation SQL relationnelle classique présente trois impasses majeures :

1. **Approche par table unique (Single Table) :**
   Rassembler tous les événements dans une table commune imposerait de créer autant de colonnes qu'il existe de paramètres spécifiques. Chaque type d'événement ne concernant qu'une fraction de ces attributs, la table contiendrait une écrasante majorité de valeurs `NULL`. Ce modèle engendre un gaspillage d'espace de stockage et dégrade la lisibilité des données.
2. **Approche par tables dédiées :**
   Créer une table distincte par type d'événement résoudrait le problème des colonnes `NULL`, mais fragmenterait la donnée. Les analyses transverses (ex. identifier les utilisateurs les plus actifs ou reconstituer un parcours chronologique) exigeraient de lourdes jointures et des requêtes `UNION ALL` peu performantes sur de gros volumes.
3. **Rigidité du schéma :**
   L'ajout futur d'un nouvel événement ou de nouveaux champs obligerait à exécuter des migrations de schéma (`ALTER TABLE`), opérations coûteuses et bloquantes en production.

## 2. Bénéfices d'une modélisation documentaire en NoSQL / MongoDB
Le choix d'une base de données orientée documents avec MongoDB répond directement aux contraintes de flexibilité et de performance du projet :

* **Schéma dynamique et polymorphisme :**
  Tous les événements sont stockés au sein d'une unique collection. Chaque document JSON/BSON ne contient que les champs qui lui sont propres, éliminant totalement les valeurs `NULL` superflues.
* **Évolutivité sans migration :**
  Il est possible d'intégrer à tout moment de nouveaux formats d'événements sans interrompre le service ni modifier la structure des documents préexistants.
* **Recherches transverses et agrégations simplifiées :**
  Le partage de champs communs (identifiant, horodatage, type d'événement, identifiant utilisateur) au sein d'une même collection permet d'exécuter des pipelines d'agrégation performants directement sur le moteur de données.

## 3. Conclusion et critère de décision
Le critère décisif en faveur de MongoDB est **l'hétérogénéité structurelle des événements à ingérer**. Face à des données polymorphes, la base documentaire offre une flexibilité de schéma naturelle, une écriture optimisée et une maintenance simplifiée que le modèle relationnel ne permet pas d'atteindre efficacement.