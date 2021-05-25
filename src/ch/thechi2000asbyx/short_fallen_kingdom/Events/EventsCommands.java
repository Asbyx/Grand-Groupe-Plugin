package ch.thechi2000asbyx.short_fallen_kingdom.Events;

import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class EventsCommands implements CommandExecutor {
    private int eventsId;
    private EventsManager eventsManager;

    public EventsCommands() {
        eventsId = -1;
        eventsManager = null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "startgame":
                if (sender.isOp()) {
                    if (eventsId != -1) break;
                    try {
                        if (args.length != 7 && args.length != 2) throw new NumberFormatException();
                        for (String s : args) {
                            if (!s.equals("default")) Integer.parseInt(s);
                        }

                        if (args[1].equals("default")) eventsManager = new EventsManager();
                        else
                            eventsManager = new EventsManager(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
                        eventsId = Main.SCHEDULER.runTaskTimer(Main.PLUGIN, () -> eventsManager.update(Main.PLUGIN.getServer().getWorld("world").getGameTime()), 0, 1).getTaskId();
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "Invalid arguments for command startGame: \n" +
                                "<number of seconds between each middle chest>\n" +
                                "<minimum number of seconds between each random chest>\n" +
                                "<maximum number of seconds between each random chest>\n" +
                                "<number of nights between each blood night>\n" +
                                "<radius of random events, center is the server spawn location> (must include both bases, or stop just in front of them)\n" +
                                "<day where PvP is allowed>");
                    }
                } else {
                    sender.sendMessage("You are not allowed to use this command");
                }
                break;

            case "parameters":
            case "getparmaters":
            case "getgameparameters":
                if (eventsManager == null) sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
                else sender.sendMessage(eventsManager.getGameParameters());
                break;

            case "stopgame":
                if (sender.isOp()) {
                    if (eventsId == -1) {
                        sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
                        break;
                    }
                    Main.SCHEDULER.cancelTask(eventsId);
                    eventsId = -1;
                    eventsManager = null;
                    Main.broadcast("The game is over !");
                }
                break;

            case "middlechest":
                EventsManager.initMiddleChest(Main.PLUGIN.getServer().getWorld("world"));
                break;

            case "help":
                if (sender.isOp()) sender.sendMessage(
                        "startgame : start the game (use default as argument to use default parameters)\n" +
                                "stopgame : stop the current game"
                );
                sender.sendMessage("parameters : display the current game parameters\n" + "middlechest : show the coordinates of the middle chest");
                break;
        }
        return true;
    }
}
