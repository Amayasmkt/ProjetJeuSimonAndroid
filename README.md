# Jeu du Simon - Android 

## Description 

Le **Jeu du Simon** est un jeu de mémoire populaire dans lequel le joueur doit répéter une séquence croissante de couleurs et de sons dans le bon ordre. À chaque niveau, la séquence s'allonge et devient plus rapide, augmentant ainsi le défi. Ce projet est une version Android du jeu Simon, développée avec Android Studio en Java.

### Fonctionnalités principales :
- **Deux activités** :
  - Une **page d'accueil** avec deux boutons : "Jouer" et "Quitter", ainsi que l'affichage des trois meilleurs scores avec les noms des joueurs.
  - Une **page de jeu** avec quatre boutons de couleurs pour jouer au Simon, accompagnée d'un affichage en temps réel du score.
- **Gameplay interactif** : 
  - Les joueurs utilisent l'écran tactile pour appuyer sur les couleurs.
  - Chaque couleur est associée à un son distinct qui se joue lorsque le bouton est pressé.
  - La séquence devient plus rapide à mesure que le score du joueur augmente.
- **Gestion des scores** : 
  - Les trois meilleurs scores, avec les noms des joueurs, sont enregistrés en utilisant `SharedPreferences`.

## Gameplay

Le joueur doit répéter la séquence de couleurs affichée à l'écran. Chaque niveau ajoute une nouvelle couleur à la séquence. 
Le jeu se termine lorsque le joueur se trompe dans l'ordre des couleurs. Le score final est calculé en fonction du nombre de séquences correctement reproduites.

### Déroulement du jeu :
1. La séquence de couleurs s'affiche à l'écran.
2. Le joueur doit toucher les pavés de couleurs dans le même ordre.
3. La séquence devient plus rapide et plus longue à chaque niveau.

## Ressources utilisées 

- **Sons personnalisés** pour chaque couleur.
- **Classe SoundManager** pour gérer la lecture des sons.
- **SharedPreferences** pour stocker et récupérer les meilleurs scores.

## Installation et Exécution 

### Pré-requis :
- Android Studio (version recommandée : 4.0+).
- SDK Android 21 ou version supérieure.
- Un appareil ou un émulateur Android.

### Étapes d'installation :

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/Amayasmkt/SimonGame-Android.git
   ```
2. **Ouvrir le projet dans Android Studio.**
3. **Construire et exécuter l'application** : 
   - Utilisez un appareil physique ou un émulateur pour tester l'application.

## Meilleurs scores et noms

Les trois meilleurs scores sont affichés sur l'écran d'accueil avec les noms des joueurs correspondants. 
Ces scores sont enregistrés localement sur le téléphone à l'aide de la classe `SharedPreferences`.

## Technologies utilisées
- **Java** pour le développement de l'application.
- **XML** pour la mise en page des activités.
- **Android Studio** pour l'environnement de développement.
- **SharedPreferences** pour stocker les scores.
- **SoundPool** pour la gestion des sons du jeu.

## Auteur 
- Amayas Mokhtari
