> ## Bienvenue sur le Tutoriel notre application ##

# Description #
Pour pouvoir bien utiliser notre application, il y a des étapes importantes qu'il faut valider.



# Etape 1 #
## Configuration de la base de donnée ##

Cette étape permettra de bien configurer la base de donnée. Notre application utilise 2 tables majeurs pour les différentes manipulations. Ces tables sont USERBD et ROUTES, leur description se trouve [ici](https://docs.google.com/document/d/1BXa7TH9INh_CkOfj3eV0kYoMxeeWP2dspeH8kBXxDtA/edit?usp=sharing) .
Dans notre implementation nous avons travaillé avec JavaDB( Derby) sur Netbeans. Il est nécessaire de transmettre un lien pour la connection à la basse de donnée. Vous devez modifier la ligne 23 du fichier **"DB.java"** se trouvant dans le Model.
```
23      private final String url = "jdbc:derby://localhost:1527/SOPRADB;user=sorpa;password=sopra";
```

  * **SOPRADB** est le Nom de la table.

Une fois que la configuration de la base de donnée a bien été faite, vous pouvez passer à l'étape 2.


# Etape 2 #

Dans cette étape vous pouvez commencer à jouer avec l'application. Il suffit de lancer l'application à l'aide du bouton de compilation/execution ou le faire en ligne de commande et vous serez redirigé vers la page d'accueil de l'application.

> ## Page d'accueil ##

![http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/1.png](http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/1.png)

  * **Connexion**:
Si vous avez déjà créé votre compte vous pouvez vous logger et accéder à votre session.

  * **Créer un compte**:
Vous devez renseigner des donnée valides. Une fois cela fait, un mail vous sera envoyé pour confirmer votre inscription.

### Formulaire ###

![http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/2.png](http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/2.png)


## Session client ##
Une fois connecté, vous aurez accès à toutes vos données client.
  * **Recherche**: Vous affiche toutes les disponibilités correspondant à votre trajet.

  * **Recherche Avancé**: vous permet de réduire votre recherche à un conducteur de votre choix.

![http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/3.png](http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/3.png)

![http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/4.png](http://sopra-teametiss-covoit-2014-2015.googlecode.com/svn/4.png)


# suite #
Vous pouvez découvrir par vous même des fonctionnalités qui n'ont pas été abordé dans ce **Tutoriel**.

**La TeamMetiss Vous souhaite une  BONNE NAVIGATION SUR SopraCov**