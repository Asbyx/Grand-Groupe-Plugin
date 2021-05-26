package ch.thechi2000asbyx.short_fallen_kingdom.Events;

import ch.thechi2000asbyx.short_fallen_kingdom.Main;
import ch.thechi2000asbyx.short_fallen_kingdom.Scoreboards.FKScoreboard;
import ch.thechi2000asbyx.short_fallen_kingdom.Teams.FKTeam;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class EventsCommands implements CommandExecutor
{
	private int eventsId;
	private EventsManager eventsManager;
	private List<FKScoreboard> scoreboards;
	
	public EventsCommands() {
		eventsId      = -1;
		eventsManager = null;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0].toLowerCase()) {
			case "startgame":
				startGame(sender, args);
				break;
			
			case "parameters":
			case "getparmaters":
			case "getgameparameters":
				getGameParameters(sender);
				break;
			
			case "stopgame":
				stopGame(sender);
				break;
			
			case "middlechest":
				EventsManager.initMiddleChest(Objects.requireNonNull(Bukkit.getWorld("world")));
				break;
			
			case "help":
				help(sender);
				break;
		}
		return true;
	}
	
	private void startGame(CommandSender sender, String[] args) {
		if (sender.isOp()) {
			if (eventsId != -1) return;
			try {
				if (args.length != 7 && args.length != 2) throw new IllegalArgumentException();
				
				if (args[1].equals("default")) eventsManager = new EventsManager();
				else
					eventsManager = new EventsManager(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
				
				eventsId = Main.SCHEDULER.runTaskTimer(Main.PLUGIN, () -> eventsManager.update(Objects.requireNonNull(Bukkit.getWorld("world")).getGameTime()), 0, 1).getTaskId();
				
				scoreboards = Bukkit.getOnlinePlayers().stream().map(p -> new FKScoreboard(p, eventsManager)).collect(Collectors.toList());
				scoreboards.forEach(FKScoreboard::start);
				
				Bukkit.getOnlinePlayers().stream().filter(p -> FKTeam.getTeam(p) != null).forEach(this::initPlayer);
				FKTeam.allTeams.forEach(FKTeam::init);
			}
			catch (NumberFormatException e) {
				sender.sendMessage(ChatColor.RED + "Invalid arguments:\n" +
						"<number of seconds between each middle chest>\n" +
						"<minimum number of seconds between each random chest>\n" +
						"<maximum number of seconds between each random chest>\n" +
						"<number of nights between each blood night>\n" +
						"<radius of random events, center is the server spawn location> (must include both bases, or stop just in front of them)\n" +
						"<day where PvP is allowed>");
			}
			catch (IllegalArgumentException i) {
				sender.sendMessage(ChatColor.RED + "Invalid number of parameters.");
			}
		}
		else {
			sender.sendMessage(ChatColor.RED + "You are not allowed to use this command");
		}
	}
	private void getGameParameters(CommandSender sender) {
		if (eventsManager == null) sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
		else sender.sendMessage(eventsManager.getGameParameters());
	}
	private void stopGame(CommandSender sender) {
		if (sender.isOp()) {
			if (eventsId == -1) {
				sender.sendMessage(ChatColor.RED + "The game hasn't start yet !");
				return;
			}
			Main.SCHEDULER.cancelTask(eventsId);
			eventsId      = -1;
			eventsManager = null;
			
			if (scoreboards != null) scoreboards.forEach(FKScoreboard::stop);
			
			Main.broadcast("The game is over !");
		}
	}
	private void help(CommandSender sender) {
		if (sender.isOp()) sender.sendMessage(
				"startgame : start the game (use default as argument to use default parameters)\n" +
						"stopgame : stop the current game"
		);
		sender.sendMessage("parameters : display the current game parameters\n" + "middlechest : show the coordinates of the middle chest");
	}
	
	private void initPlayer(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		player.getInventory().clear();
		player.setExp(0);
		player.setLevel(0);
		player.setHealth(20);
		player.setFoodLevel(20);
		player.getActivePotionEffects().forEach(e -> player.removePotionEffect(e.getType()));
		
		player.getInventory().addItem(new ItemStack(Material.BREAD, 5), new ItemStack(Material.CLOCK));
		
		player.setInvulnerable(true);
		player.teleport(FKTeam.getTeam(player).getBaseCenter().add(0, 30, 0).toOverworldLocation());
		
		Main.SCHEDULER.scheduleSyncDelayedTask(Main.PLUGIN, () -> player.setInvulnerable(false), 200);
	}
}
