package ch.asbyx.firstPlugin;

import ch.asbyx.firstPlugin.ConcreteListener;
import ch.grandgroupe.common.utils.Coordinates;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

@SuppressWarnings("ALL") public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ConcreteListener(), this);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args) {
        Player player = (Player) sender;
        switch (command.getName()){
            case "coucou":
                getServer().broadcastMessage(player.getDisplayName() + " salue tout le monde !");
                break;
            case "showCoordinates":
                if (args.length == 0) getServer().broadcastMessage(player.getDisplayName() + ": " + new Coordinates(player.getLocation()));
                else getServer().getOnlinePlayers().stream().filter(p -> p.getDisplayName().equals(args[0])).collect(Collectors.toList()).get(0).sendMessage( player.getDisplayName() + " t'envoie sa location : " + new Coordinates(player.getLocation()));
                break;

            case "startGame":
                if(getServer().getScheduler().getPendingTasks().size() > 0) {
                    player.sendMessage("Une partie est déjà en cours !");
                    break;
                }
                if (args.length == 1) {
                    BukkitScheduler scheduler = getServer().getScheduler();
                    scheduler.scheduleSyncRepeatingTask(
                            this,
                            () -> getServer().getOnlinePlayers().forEach(p -> getServer().broadcastMessage(p.getDisplayName() + ": " + new Coordinates(p.getLocation()))),
                            Integer.parseInt(args[0]) * 20L,
                            Integer.parseInt(args[0]) * 20L
                    );
                    getServer().broadcastMessage("Puisse le sort vous être favorable ! Vos coordonnées apparaîtront toutes les " + Integer.parseInt(args[0]) + " secondes !");
                } else {
                    player.sendMessage("Il faut un argument valide ! (entier représentant le nombre de secondes entre chaque moment où les coordonnées des joueurs apparaîtront)");
                }
                break;

            case "stopGame":
                getServer().broadcastMessage("Fin de la partie !");
                getServer().getScheduler().cancelTasks(this);
                break;
        }
        return true;
    }
}
