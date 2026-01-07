# 🏕️ CampPlanner : Votre Compagnon de Camping

## 🌟 Description du Projet

**CampPlanner** est une application mobile Android conçue pour simplifier l'organisation et la gestion de vos séjours en camping. Elle centralise tous les outils nécessaires, de la planification des voyages au suivi du budget, en passant par la gestion des équipements et la tenue d'un journal de bord.

L'objectif principal est de fournir une solution tout-en-un pour les campeurs passionnés, leur permettant de se concentrer pleinement sur leur aventure.

## ✨ Fonctionnalités Principales

L'application offre une suite complète de fonctionnalités pour couvrir tous les aspects de votre voyage :

| Catégorie | Fonctionnalité | Description Détaillée |
| :--- | :--- | :--- |
| **Planification** | Gestion des Séjours | Créez, modifiez et suivez l'état de vos voyages (planifié, en cours, terminé). |
| | Activités | Planifiez les activités spécifiques pour chaque séjour. |
| **Budget** | Suivi des Dépenses | Enregistrez et catégorisez toutes vos transactions. |
| | Catégories Personnalisées | Gérez votre budget camping avec une répartition par catégories (Hébergement, Nourriture, Transport, etc.). |
| **Équipement** | Listes Personnalisées | Créez et gérez des listes d'équipement pour ne rien oublier (Essentiel, Abri, Cuisine, etc.). |
| **Réservations** | Gestion des Réservations | Enregistrez et suivez les détails de vos réservations d'emplacements ou d'activités. |
| **Journal** | Journal de Bord | Créez des entrées de journal avec notes et photos pour immortaliser vos souvenirs. |
| **Météo & Sécurité** | Prévisions et Conseils | Consultez les conditions météorologiques actuelles et les conseils de sécurité pour votre destination. |
| **Authentification** | Connexion Sécurisée | Système d'inscription et de connexion pour gérer vos données personnelles. |

## 🎥 Démonstration Vidéo

Découvrez CampPlanner en action ! Cette courte vidéo présente les fonctionnalités clés de l'application, de la planification d'un séjour au suivi du budget.

<video src="assets/video_thumbnail.mp4" controls width="100%" height="auto" muted autoplay loop></video>

## 🛠️ Technologies Utilisées

Ce projet est une application native Android développée en Java.

| Composant | Technologie | Version Clé |
| :--- | :--- | :--- |
| **Plateforme** | Android | API 34 (Android 14) |
| **Langage** | Java | Java 8 |
| **Interface Utilisateur** | AndroidX & Material Design | `androidx.appcompat:1.4.1`, `com.google.android.material:1.5.0` |
| **Base de Données** | SQLite | Implémentation via `DatabaseHelper` et classes DAO |
| **Système de Build** | Gradle | - |

## 🚀 Installation et Démarrage

Pour installer et exécuter le projet CampPlanner localement, suivez les étapes ci-dessous.

### Prérequis

*   **Android Studio** (version recommandée : Hedgehog ou supérieure)
*   **SDK Android** (API 34 ou supérieure)
*   **Java Development Kit (JDK)** 8 ou supérieur

### Étapes

1.  **Cloner le dépôt** (ou dézipper le fichier `CampPlannerpresque.zip`):
    ```bash
    # Si c'était un dépôt Git
    # git clone https://github.com/votre-utilisateur/CampPlanner.git
    # cd CampPlanner
    ```
2.  **Ouvrir le Projet dans Android Studio** :
    *   Lancez Android Studio.
    *   Sélectionnez `File` > `Open` et naviguez jusqu'au dossier racine du projet (`CampPlanner/CampPlanner`).
3.  **Synchronisation Gradle** :
    *   Laissez Gradle synchroniser les dépendances. Si des problèmes surviennent, assurez-vous que votre version de Gradle et les versions des dépendances dans `build.gradle` sont compatibles avec votre environnement.
4.  **Exécuter l'Application** :
    *   Sélectionnez un émulateur ou un appareil physique connecté.
    *   Cliquez sur le bouton `Run` (▶️) pour compiler et déployer l'application.




