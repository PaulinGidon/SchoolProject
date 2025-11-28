# EDT

Projet de SAÉ - Création et affichage d'emploi du temps.

## Résumé

### Principe

À partir de fichiers de configuration, ce projet génère un emploi du temps de type BUT semaine par semaine pour l'année.

Pour simplifier :
- Deux types de séances : CM et TD.
- Nombre de séances fixe par semaine pour chaque ressource.
- Enseignant de TD fixe.
- Gestion d'une seule année avec semaines numérotées de 1 à 52.

L'emploi du temps peut être consulté en ligne de commande ou via un site web interactif.

### Remarques

- Application écrite en Java (OpenJDK 17) sans librairie externe.
- Fonctionne sous Linux (Debian 12).
- Encodage UTF-8 pour tous les fichiers.

### Utilisation

L'exécutable est `edt` avec les sous-commandes :
- `creer` : création d'un nouvel emploi du temps.
- `verifier` : vérifie la cohérence de l'emploi du temps.
- `voir` : visualisation dans le terminal.
- `web` : visualisation sous forme de site web interactif.

## Génération d'un emploi du temps

```
edt creer [--cfg <config-directory>] [--dst <edt-directory>] [--force]
```

Cette commande génère un fichier par semaine. Les fichiers contiennent les séances CM et TD avec l'heure, le type de séance, la ressource et l'enseignant.

## Vérification de l'emploi du temps

```
edt verifier [<semaine>]
```

Vérifie la cohérence des séances et la complétude des heures prévues pour chaque ressource.

## Visualisation

### Dans le terminal

```
edt voir <semaine> [--cfg <config-directory>] [--groupe <group>]
```

Permet de visualiser l'emploi du temps, avec option de filtrer par groupe.

### Sous forme de site web

```
edt web [--cfg <config-directory>] [--semaine <semaine>] [--groupe <group>] [--enseignant <enseignant>] [--ressource <ressource>] [--port <port>]
```

Le site web permet de naviguer entre les semaines et de filtrer les séances. Il est léger, réactif et n'utilise que HTML et CSS.

## Configuration

Le dossier `config` contient les fichiers de configuration :
- `enseignants.txt` : liste des enseignants.
- `groupes.txt` : liste des groupes.
- `horaires.txt` : horaires disponibles pour CM et TD.
- `ressources.toml` : informations sur les ressources, responsables et intervenants.

Les fichiers ignorent les lignes vides et les commentaires (`#`). L'indentation et les espaces en fin de ligne sont ignorés.

# EDT

SAÉ Project – Timetable Creation and Display.

## Summary

### Concept

This project generates a weekly timetable for a BUT program based on configuration files.

Simplifications:
- Two types of sessions: CM and TD.
- Fixed number of sessions per week for each resource.
- Fixed teacher for TD sessions.
- Only one academic year is managed, with weeks numbered from 1 to 52.

The timetable can be viewed in the command line or via an interactive web interface.

### Notes

- Written in Java (OpenJDK 17) without any external libraries.
- Runs on Linux (Debian 12).
- UTF-8 encoding is used for all files.

### Usage

The executable is `edt` with the following subcommands:
- `creer` : create a new timetable.
- `verifier` : check the timetable for consistency.
- `voir` : view the timetable in the terminal.
- `web` : view the timetable as an interactive web page.

## Generating a Timetable

```
edt creer [--cfg <config-directory>] [--dst <edt-directory>] [--force]
```

This command generates one file per week. Files contain CM and TD sessions with start and end times, session type, resource, and teacher.

## Verifying the Timetable

```
edt verifier [<week>]
```

Checks the timetable for conflicts and ensures all hours for each resource are assigned.

## Viewing the Timetable

### In the Terminal

```
edt voir <week> [--cfg <config-directory>] [--groupe <group>]
```

Allows viewing of the timetable, optionally filtering by group.

### As a Web Page

```
edt web [--cfg <config-directory>] [--semaine <week>] [--groupe <group>] [--enseignant <teacher>] [--ressource <resource>] [--port <port>]
```

The web interface allows navigation between weeks and filtering by sessions. It is lightweight, responsive, and uses only HTML and CSS.

## Configuration

The `config` folder contains the configuration files:
- `enseignants.txt` : list of teachers.
- `groupes.txt` : list of groups.
- `horaires.txt` : available times for CM and TD.
- `ressources.toml` : information about resources, responsible teachers, and TD instructors.

Empty lines and lines starting with `#` are ignored. Indentation and trailing spaces are also ignored.



