# Grand-Groupe-Plugin

## Bienvenue !
Tu es sur le point de rentrer dans le monde incroyable du pluginnage, du moins dans le monde du pluginnage du GrandGroupe :)  
Aucune limite si ce n'est celle de l'imagination, du respect et surtout du fun !

### Je n'ai jamais pluginné :(
Pas de problème ! On va faire un petit apperçu et un mini-tuto pour que tu te sentes à l'aise avec la méthode de travail, et on te conseillera une chaîne formidable qui fait même des tutos à jour (si si, nous non-plus on n'y croyait pas)

### J'ai déjà pluginné, qu'est ce que je fais là ? -.-
Descend de ton piedestal, ici on code pour le fun, ce qui va suivre va juste être une review de comment Ludovic et moi-même avons imaginé le plugin pour que tes features soient implémentées correctement et dans le respect du travail des autres (c'est toujours plus agréable comme ça)

### Je ne veux pas pluginner mais j'ai pleins de chouettes idées ! :D
Eh bien tu as 2 choix:
- Soit tu ajoutes tes idées dans les Issues du repository et prend le risque qu'elles ne soient jamais réalisées (c'est très long)
- Soit tu ajoutes tes idées dans les Issues, mais en plus tu les codes toi-même ! (beaucoup plus court, on t'en courage à prendre cette option)

### Je veux pluginner mais je n'ai pas d'imagination :(
Pas de problème, on a des idées pour toi ! Dans Issues tout le monde peut ajouter une idée qu'il aimerait voir implémentée dans le plugin: pleins de choses à faire !
Si tu es débutant, on te conseille évidemment de commencer par les good-first-issues, ce sont des issues ne demande pas une maîtrise du pluginnage pour être implémentées, selon nous.


## Review de l'implémentation actuelle (v0.3)
Actuellement il y a 3 packages:
- **asbyx**, pour les plugins d'Eugène
- **thechi2000**, pour les plugins de Ludovic
- **grandgroupe**, pour les plugins communs

Tu es libre d'ajouter ton package perso pour faire tes tests ou créer/importer tes propres plugins autonomes.  
Pour le plugin GrandGroupe en lui même, tout se passe dans **grandgroupe**.

Dans **grandgroupe**, on divise les packages ainsi:
1. common
    - Contient tout ce qui doit être accessible par n'importe quel mini-jeu (on y vient) et est commun à tout ce qui est contenu dans le plugin
      > Un package util pour des classes qui sont utiles (thx captain obvious) et des méthodes utiles, dans la class Misc (on s'en sert aussi pour recréer des choses qui n'existent pas en Java 8).  
      > Un package tabCompleter sur lequel on reviendra plus tard.  
      > Un package listeners sur lequel on reviendra aussi plus tard.  
    - La classe Main, qui lance le plugin (et qui doit être unique)
    - La classe rulesCommands, sur laquelle on reviendra plus tard
2. minigames
    - Contient tous les minijeux:  
      Un minijeu n'est pas qu'un petit truc dans le plugin, c'est un mode de jeu jouable, qui a un début et une fin, et qui doit être indépendant des autres minijeux.   
      **UN ET UN SEUL PACKAGE PAR MINIJEU !!!**  
  
Une fois le dans le package du minijeu, l'implémentation est libre. On demande juste à ce que la javadoc soit faites pour les méthodes 
publiques qui ne sont pas des Event (sauf si elle décrivent mal ce qu'elles font) pour les minijeux, et une javadoc complète pour les 
classes de **common**.

## Prérequis
*Tuto pour Windows + IntelliJ, pour les autres allez aussi sur le lien ci-dessous, tout est super bien documenté.*  
Installation de Spigot : https://www.spigotmc.org/wiki/spigot-installation/  
  
Il faut juste installer spigot en lui même, c'est suffisant (juste le .jar, que tu mets dans un dossier). Ensuite crée un fichier **start.bat** ou **init.bat** selon si tu veux avoir Ludovic ou Eugène en tant qu'ennemi (team **start.bat** on est là) qui contient cette commande:
`@echo off
java -jar spigot<version installée>.jar -nogui`  
Spigot permet de lancer un serveur Minecraft en local utilisant les plugins donnés, en lançant ton **start.bat**. Tu peux jouer dans ton serveur avec le minecraft normal: multiplayer: ip = localhost.  
  
On t'invite à lancer ton **start.bat** pour tout initialiser. La première fois ça devrait s'arrêter très tôt; un fichier nommé eula.txt a été créé. Dedans met eula à true pour accepter l'eula de mojang. Une fois que c'est fait, relance **start.bat** et ton serveur devrait se lancer correctement. Pour l'arrêter, tape la commande `stop` ou ferme simplement la console (la 2e option ne sauvegardera pas ton monde).  

Dans IntelliJ *(les utilisateurs d'Eclipse démerdez-vous avez votre IDE nul)*, clonez le repository à part du dossier tout spigot. Vous pouvez le faire, mais on le déconseille.  

Dans Project Settings:
- Project: tu dois utiliser adopt-openj9-1.8 et le SDK doit être en version 8
- Modules: Dependencies: ajoute `spigot<version>.jar` et sélectionne-le
- Artifacts: Ajoute un JAR, from modules with depedencies:
  - Name: GrandGroupePlugins:jar (comme ça tout le monde a le même)
  - Type: JAR
  - Output directory: le chemin absolu vers le dossier plugins de ton dossier contenant spigot
  - Output Layout: enleve ce qui est relatif à spigot, tu ne dois qu'avoir "'GrandGroupePlugins' compile output"  
  
Et voilà ! On est prêt à coder. 
*Si cela ne fonctionne pas, ou que tu as des questions sur le pourquoi du comment, contacte Eugène ou Ludovic.*

## Tuto: Feature
Tu veux rajouter une épée custom ou n'importe quoi custom ? ou un évenement de jeu ? ou un nouvel outil ? Bref, n'importe quoi qui a un caractère unique et qui n'est pas 
un minijeu.

D'abord, il faut savoir que ce genre de chose dépend d'évenements: un clic, un craft, la destruction d'un bloc, tout existe. On va donc commencer par créer une classe dans 
**common.listeners** qui extends **AbstractListener**.  
Cette classe, qui implémente Listener, une interface de Spigot qui permet de recevoir des évenements de jeu, permet à ta feature d'être activée ou désactivée. Par conséquent, garde à l'esprit que toute ta feature ne doit être active que lorsque `isDisable()` de AbstractListener revoit false.
On conseille de mettre `if(isDisable()) return;` au début de chaque méthode de ta classe.
Tu peux aussi override `enable` et `disable` pour des comportements spécifiques.  

Ensuite, c'est à toi de fouiller dans la doc de spigot pour savoir quelles méthodes sont disponibles pour implémenter facilement ta feature ! Regarde dans les différentes classes de **listeners** pour avoir des exemples :)  

Si tu n'es un boomer comme Eugène, tu peux aller voir cette chaîne qui expliquer tout super bien: https://www.youtube.com/c/CodedRed

On te donne quand même un exemple.
### Étape 1: On crée la feature.
Feature: chaque fois qu'un zombie meure, il drop un diamant.  
J'utilise l'event `EntityDeathEvent`. Ma méthode de ma classe ressemble donc pour l'instant à :

```
@EventHandler
public void onZombieDeath(EntityDeathEvent event){
    if (isDisabled()) return;
}
```

Ensuite, je fouille la documentation (ou je fais "event." et je regarde toutes les méthodes à disposition), je vois que l'event `EntityDeathEvent` propose plusieurs choses utiles:  
- `getEntity().getType()`: grâce à ça je vais pouvoir savoir si l'entitée tuée est un Zombie
- `getDrops().add(ItemStack item)` qui me permettra d'ajouter un diamant au items dropés par le zombie

Ensuite je fouille la documentation de Spigot pour savoir comment utiliser **ItemStack**, je vois que je peux lui passer un **Material** (une énumération bien pratique tu verras) en argument.

J'active mes doigts de manière frénétique et hasardeuse et j'obtiens:
```
@EventHandler
public void onZombieDeath(EntityDeathEvent event){
    if (isDisabled()) return;

    if(event.getEntity().getType() == EntityType.ZOMBIE){
        event.getDrops().add(new ItemStack(Material.DIAMOND));
    }
}
```
   
Et voilà ! Ma feature est terminée ! Il ne reste plus qu'à l'implémenter au niveau du plugin. Et la javadoquer proprement, cela va de soi (si si, c'est important).

### Étape 2: l'ajout dans **common.RulesCommand**, qui permet d'activer ou désactiver la feature en jeu.
1. Ici c'est assez simple: on ajoute la classe que l'on vient de coder à la liste de listeners trouvable dans le constructeur de **common.RulesCommands**.
  > listeners.add(new MaClasse()); // après les précédents 
2. Moins simple: il faut ajouter un case dans la méthode `onCommand`. On peut prendre exemple sur le `case "deathchest"`: 
si la commande `/rules deathchest true` est appelée, la class **RulesCommand** sait quoi faire: elle appelle la méthode `enabler` avec:
- l'argument `arg[1]` qui va contenir notre booléen,
- l'index de notre feature dans la liste `listeners`
- le nom de la feature pour les joueurs  

J'ajoute donc dans le switch:
```
case "diamandzombie": //doit être en minuscule !
    enabler(Boolean.parseBoolean(arg[1]), 1, "Diamand Zombie");
    break;
```

Et voilà ! On peut maintenant activer la feature avec la commande `/rules diamandzombie true`.

**Fondamentalement, on peut s'arrêter là**. Dans ce cas, il faut ajouter un `fixme missing from tabCompleter` pour indiquer à tous que la feature n'est pas implémentée dans le TabCompleter.

### Étape 2: l'ajout de la commande dans le TabCompleter.
Le tabCompleter permet d'ajouter l'auto-complétion de la commande avec `tab`. Pour ça, deux étapes:
1. L'ajout de l'enumération dans **common.tabCompleter.Argument**
> dans rules: ```DIAMAND_ZOMBIE("diamandZombie"),```
2. L'ajout de la liste d'arguments possibles dans **common.tabCompleter.Commands**
> dans rules:
```
DIAMAND_ZOMBIE(true,  //on doit être op pour activer les rules
"rules",              // la commande dépend de /rules
"diamandZombie",      //on ajoute diamandZombie dans la liste d'argument qui peut arriver après /rules
new ArgumentList("Enable or Disable the drop of diamand by zombies" /*description de la commande*/, Argument.DIAMAND_ZOMBIE, /*Argument initial*/, BOOLEAN /*Argument suivant*/)
),
```

FINIS ! Ma feature est maintenant implémentée ! Tout paraît compliqué car Ludovic et moi avons été très maniaques, on veut faire les choses bien. On vous conseille de faire d'abord quelques plugins de votre côté, dans un Projet IntelliJ complètement à part, pour vous entraîner et vous familiariser avec Spigot: comment on debug, comment on recherche dans la docs ou des tutos sur youtube, etc.


## Prérequis: Minijeu
*Je pars du principe que vous maîtrisez bien Spigot, et que vous avez compris l'architecture du repository.*
Outre l'ajout du label de ta commande de ton mini-jeu dans le `plugin.yml`, et de son implémentation dans le TabCompleter, il n'y a que 2 choses auxquelles il faut faire attention:
1. Ton minijeu doit avoir un `CommandExecutor`, qui sera instantié dans le Main (tu peux prendre example sur celui du FK).
2. Ton minijeu ne doit pas être actif par défault, on conseille d'ajouter une commande `startGame` ou quelque chose du genre.


**BON PLUGINNAGE À VOUS**

   
   
